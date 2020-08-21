package org.bialydunajec.email.readmodel.emailmessage

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailMessageLogExternalEvent
import org.springframework.stereotype.Component

const val EMAIL_STATUS_PENDING = "PENDING"
const val EMAIL_STATUS_SENT = "SENT"
const val EMAIL_STATUS_FAIL_TO_SEND = "FAIL_TO_SEND"

@Component
internal class EmailMessageLogEventsProjection(
    private val emailMessageRepository: EmailMessageMongoRepository,
    private val emailMessageStatisticsRepository: EmailMessageStatisticsMongoRepository,
    private val emailMessageLogEventStream: EmailMessageLogEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailMessageLogExternalEvent.EmailMessageCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailMessageLogExternalEvent.EmailMessageSentSuccess::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailMessageLogExternalEvent.EmailMessageSentFailure::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailMessageLogExternalEvent.EmailMessageCreated -> {
                with(payload) {
                    emailMessageRepository.findById(emailMessageLogId).orElseGet {
                        EmailMessage(
                            emailMessageLogId
                        )
                    }
                            .also {
                                it.recipient = recipientEmailAddress
                                it.subject = subject
                                it.content = content
                                if (it.status == null) {
                                    it.status = EMAIL_STATUS_PENDING
                                }
                                it.createdDate = createdDate.toInstant()
                            }.also {
                                emailMessageRepository.save(it)
                            }

                    emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                            .ifPresent {
                                it.messagesCount++
                                emailMessageStatisticsRepository.save(it)
                            }
                }.also {
                    emailMessageLogEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailMessageLogExternalEvent.EmailMessageSentSuccess -> {
                with(payload) {
                    emailMessageRepository.findById(emailMessageLogId).orElseGet {
                        EmailMessage(
                            emailMessageLogId
                        )
                    }
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
                }.also {
                    emailMessageLogEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailMessageLogExternalEvent.EmailMessageSentFailure -> {
                with(payload) {
                    var statusBeforeProjection: String? = null;
                    emailMessageRepository.findById(emailMessageLogId).orElseGet {
                        EmailMessage(
                            emailMessageLogId
                        )
                    }
                            .also {
                                statusBeforeProjection = it.status;
                                it.status = EMAIL_STATUS_FAIL_TO_SEND
                                it.lastError = lastError
                            }.also {
                                emailMessageRepository.save(it)
                            }.also {
                                if (statusBeforeProjection != EMAIL_STATUS_FAIL_TO_SEND) {
                                    emailMessageStatisticsRepository.findById(DEFAULT_EMAIL_MESSAGE_STATISTICS_ID)
                                            .ifPresent { statistics ->
                                                statistics.sentFailureCount++
                                                emailMessageStatisticsRepository.save(statistics)
                                            }
                                }
                            }
                }.also {
                    emailMessageLogEventStream.updateStreamWith(externalEvent)
                }
            }
        }
    }
}
