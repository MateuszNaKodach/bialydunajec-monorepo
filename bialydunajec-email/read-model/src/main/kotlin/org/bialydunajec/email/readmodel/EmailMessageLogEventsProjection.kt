package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventListener
import org.bialydunajec.email.messages.event.EmailMessageExternalEvent
import org.bialydunajec.email.readmodel.rest.DEFAULT_EMAIL_MESSAGE_STATISTICS_ID
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

const val EMAIL_STATUS_PENDING = "PENDING"
const val EMAIL_STATUS_SENT = "SENT"
const val EMAIL_STATUS_FAIL_TO_SEND = "FAIL_TO_SEND"

@Component
internal class EmailMessageLogEventsProjection(
        private val emailMessageRepository: EmailMessageRepository,
        private val emailMessageStatisticsRepository: EmailMessageStatisticsRepository
) : ExternalEventListener {

    private val log = LoggerFactory.getLogger(this.javaClass)


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    override fun handleExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailMessageExternalEvent.EmailMessageCreated -> {
                with(payload) {
                    emailMessageRepository.save(
                            EmailMessage(
                                    emailMessageLogId, recipientEmailAddress, subject, content, EMAIL_STATUS_PENDING
                            )
                    )
                }
                emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                        .ifPresent { it.messagesCount++ }
            }

            is EmailMessageExternalEvent.EmailMessageSentSuccess -> {
                with(payload) {
                    val emailMessageLog = emailMessageRepository.findById(emailMessageLogId)
                    if (emailMessageLog.isPresent) {
                        val emailMessage = emailMessageLog.get()
                        emailMessage.status = EMAIL_STATUS_SENT
                        emailMessage.sentDate = emailMessage.sentDate
                        emailMessageRepository.save(emailMessage)

                        emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                                .ifPresent {
                                    it.sentSuccessCount++
                                    if (emailMessage.status == EMAIL_STATUS_FAIL_TO_SEND) {
                                        it.sentSuccessCount--
                                    }
                                }
                    }
                }
            }

            is EmailMessageExternalEvent.EmailMessageSentFailure -> {
                with(payload) {
                    val emailMessageLog = emailMessageRepository.findById(emailMessageLogId)
                    if (emailMessageLog.isPresent) {
                        val emailMessage = emailMessageLog.get()
                        emailMessage.status = EMAIL_STATUS_FAIL_TO_SEND
                        emailMessage.lastError = emailMessage.lastError
                        emailMessageRepository.save(emailMessage)

                        emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                                .ifPresent {
                                    it.sentFailureCount++
                                }
                    }
                }
            }
        }

        log.info("Projected external event with payload: {}", externalEvent.payload.toString())
    }
}