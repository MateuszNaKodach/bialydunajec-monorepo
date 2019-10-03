package org.bialydunajec.email.readmodel.email

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.springframework.stereotype.Component

@Component
internal class EmailEventsProjection(
    private val emailRepository: EmailMongoRepository,
    private val emailStatisticsMongoRepository: EmailStatisticsMongoRepository,
    private val emailEventStream: EmailEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressDeactivated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressUpdated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated::class) {
            processingQueue.process(it)
        }

    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailAddressExternalEvent.EmailAddressCreated -> {
                with(payload) {
                    emailRepository.findById(emailId).orElseGet {
                        Email(
                            emailId,
                            emailAddress
                        )
                    }
                            .also {
                                it.isActive = isActive
                            }.also {
                                emailRepository.save(it)
                            }

                    emailStatisticsMongoRepository.findById(DEFAULT_EMAIL_STATISTICS_ID)
                            .ifPresent {
                                if (emailId.contains(DEFAULT_EMAIL_GROUP)){
                                    it.addressesCount++
                                }
                                emailStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup -> {
                with(payload) {
                    emailRepository.findById(emailId).orElseGet {
                        Email(
                            emailId,
                            emailAddress
                        )
                    }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                                it.emailGroupName = emailGroupName
                                it.ownerFirstName = ownerFirstName
                                it.ownerLastName = ownerLastName
                            }.also {
                                emailRepository.save(it)
                            }
                }.also {
                    emailEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressDeactivated -> {
                with(payload) {
                    emailRepository.findById(emailId).orElseGet {
                        Email(
                            emailId,
                            emailAddress
                        )
                    }
                            .also {
                                it.isActive = false
                            }.also {
                                emailRepository.save(it)
                            }
                }.also {
                    emailEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressUpdated -> {
                with(payload) {
                    emailRepository.findById(newEmailId).orElseGet {
                        Email(
                            newEmailId,
                            newEmailAddress
                        )
                    }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                            }.also {
                                emailRepository.save(it)
                            }
                    emailStatisticsMongoRepository.findById(DEFAULT_EMAIL_STATISTICS_ID)
                            .ifPresent {
                                if (newEmailId.contains(DEFAULT_EMAIL_GROUP)) it.addressesCount--
                                emailStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated -> {
                with(payload) {
                    emailRepository.findById(newEmailId).orElseGet {
                        Email(
                            newEmailId,
                            newEmailAddress
                        )
                    }
                            .also {
                                it.previousEmailAddressId = previousEmailAddressId
                                it.isActive = true
                                it.emailGroupName = emailGroupName
                                it.ownerFirstName = ownerFirstName
                                it.ownerLastName = ownerLastName
                            }.also {
                                emailRepository.save(it)
                            }
                    emailStatisticsMongoRepository.findById(DEFAULT_EMAIL_STATISTICS_ID)
                            .ifPresent {
                                if (newEmailId.contains(DEFAULT_EMAIL_GROUP)) it.addressesCount--
                                emailStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailEventStream.updateStreamWith(externalEvent)
                }
            }
        }
    }

}
