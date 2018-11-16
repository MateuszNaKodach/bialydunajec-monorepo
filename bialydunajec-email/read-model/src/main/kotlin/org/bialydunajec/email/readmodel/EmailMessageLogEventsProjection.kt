package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailMessageExternalEvent
import org.springframework.stereotype.Component

const val EMAIL_STATUS_PENDING = "PENDING"
const val EMAIL_STATUS_SENT = "SENT"
const val EMAIL_STATUS_FAIL_TO_SEND = "FAIL_TO_SEND"

@Component
internal class EmailMessageLogEventsProjection(
        private val emailMessageRepository: EmailMessageRepository,
        private val emailMessageStatisticsRepository: EmailMessageStatisticsRepository
) : SerializedExternalEventListener() {

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailMessageExternalEvent.EmailMessageCreated -> {
                with(payload) {
                    emailMessageRepository.findById(emailMessageLogId)
                            .orElseGet { EmailMessage(emailMessageLogId) }
                            .also {
                                it.recipient = recipientEmailAddress
                                it.subject = subject
                                it.content = content
                                if (it.status == null) {
                                    it.status = EMAIL_STATUS_PENDING
                                }
                                it.createdDate = createdDate?.toInstant()
                            }.also {
                                emailMessageRepository.save(it)
                            }

                    emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                            .ifPresent {
                                it.messagesCount++
                                emailMessageStatisticsRepository.save(it)
                            }
                }
            }

            is EmailMessageExternalEvent.EmailMessageSentSuccess -> {
                with(payload) {
                    emailMessageRepository.findById(emailMessageLogId)
                            .orElseGet { EmailMessage(emailMessageLogId) }
                            .also {
                                it.status = EMAIL_STATUS_SENT
                                it.sentDate = sentDate.toInstant()
                            }.also {
                                emailMessageRepository.save(it)
                            }.also { it ->
                                emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                                        .ifPresent { statistics ->
                                            statistics.sentSuccessCount++
                                            if (it.status == EMAIL_STATUS_FAIL_TO_SEND) {
                                                statistics.sentFailureCount--
                                            }
                                            emailMessageStatisticsRepository.save(statistics)
                                        }
                            }
                }
            }

            is EmailMessageExternalEvent.EmailMessageSentFailure -> {
                with(payload) {
                    emailMessageRepository.findById(emailMessageLogId)
                            .orElseGet { EmailMessage(emailMessageLogId) }
                            .also {
                                it.status = EMAIL_STATUS_FAIL_TO_SEND
                                it.lastError = lastError
                            }.also {
                                emailMessageRepository.save(it)
                            }

                    emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                            .ifPresent {
                                it.sentFailureCount++
                                emailMessageStatisticsRepository.save(it)
                            }
                }
            }
        }

        log.info("Projected external event with payload: {}", externalEvent.payload.toString())
    }
}