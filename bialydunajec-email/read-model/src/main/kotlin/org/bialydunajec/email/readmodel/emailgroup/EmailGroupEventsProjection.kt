package org.bialydunajec.email.readmodel.emailgroup

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.bialydunajec.email.messages.event.EmailGroupExternalEvent
import org.springframework.stereotype.Component

@Component
internal class EmailGroupEventsProjection(
    private val emailGroupMongoRepository: EmailGroupMongoRepository,
    private val emailGroupStatisticsMongoRepository: EmailGroupStatisticsMongoRepository,
    private val emailGroupEventStream: EmailGroupEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailGroupExternalEvent.EmailGroupCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressDeactivated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailGroupExternalEvent.EmailGroupCreated -> {
                with(payload) {
                    emailGroupMongoRepository.findById(aggregateId).orElseGet {
                        EmailGroup(
                            aggregateId
                        )
                    }
                            .also {
                                it.groupName = name
                            }.also {
                                emailGroupMongoRepository.save(it)
                            }

                    emailGroupStatisticsMongoRepository.findById(DEFAULT_EMAIL_GROUP_STATISTICS_ID)
                            .ifPresent {
                                it.groupsCount++
                                emailGroupStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailGroupEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup -> {
                with(payload) {
                    emailGroupMongoRepository.findById(emailGroupId).orElseGet {
                        EmailGroup(
                            emailGroupId
                        )
                    }
                            .also {
                                it.groupName = emailGroupName
                                it.emailAddresses?.add(emailAddress)
                            }.also {
                                emailGroupMongoRepository.save(it)
                            }
                }.also {
                    emailGroupEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressDeactivated -> {
                with(payload) {
                    emailGroupId?.let {
                        emailGroupMongoRepository.findById(it).get()
                                .also {
                                    it.emailAddresses?.remove(emailAddress)
                                }.also {
                                    emailGroupMongoRepository.save(it)
                                }
                    }
                }.also {
                    emailGroupEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressBelongingToGroupUpdated -> {
                with(payload) {
                    emailGroupMongoRepository.findById(emailGroupId).orElseGet {
                        EmailGroup(
                            emailGroupId
                        )
                    }
                            .also {
                                it.groupName = emailGroupName
                                it.emailAddresses?.add(newEmailAddress)
                            }.also {
                                emailGroupMongoRepository.save(it)
                            }
                }.also {
                    emailGroupEventStream.updateStreamWith(externalEvent)
                }
            }
        }
    }

}
