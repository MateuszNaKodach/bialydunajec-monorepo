package org.bialydunajec.email.readmodel

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailAddressExternalEvent
import org.springframework.stereotype.Component

@Component
internal class EmailAddressEventsProjection(
        private val emailAddressRepository: EmailAddressMongoRepository,
        private val emailAddressStatisticsMongoRepository: EmailAddressStatisticsMongoRepository,
        private val emailAddressEventStream: EmailAddressEventStream,
        eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCreated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressUpdated::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailAddressExternalEvent.EmailAddressCreated -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).orElseGet { EmailAddress(emailId) }
                            .also {
                                it.emailAddress = emailAddress
                            }.also {
                                emailAddressRepository.save(it)
                            }

                    emailAddressStatisticsMongoRepository.findById(DEFAULT_EMAIL_ADDRESS_STATISTICS_ID)
                            .ifPresent {
                                it.addressesCount++
                                emailAddressStatisticsMongoRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressUpdated -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).get()
                            .also {
                                it.emailAddress = newEmailAddress
                            }.also {
                                emailAddressRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }

            is EmailAddressExternalEvent.EmailAddressCatalogizedToEmailGroup -> {
                with(payload) {
                    emailAddressRepository.findById(emailId).get()
                            .also {
                                it.ownerFirstName = ownerFirstName
                                it.ownerLastName = ownerLastName
                                it.emailGroupIds?.add(newEmailGroupId)
                            }.also {
                                emailAddressRepository.save(it)
                            }
                }.also {
                    emailAddressEventStream.updateStreamWith(externalEvent)
                }
            }
        }
    }

}