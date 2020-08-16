package org.bialydunajec.email.readmodel.emailwithgroups

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailExternalEvent
import org.bialydunajec.email.readmodel.shared.EmailOwner
import org.springframework.stereotype.Component

@Component
internal class EmailWithGroupsEventsProjection(
    private val emailRepository: EmailWithGroupsMongoRepository,
    private val emailEventStream: EmailWithGroupsEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailExternalEvent.EmailCatalogized::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailExternalEvent.EmailOwnerCorrected::class) {
            processingQueue.process(it)
        }

        eventSubscriber.subscribe(EmailExternalEvent.EmailAddressChanged::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload as EmailExternalEvent
        when (payload) {
            is EmailExternalEvent.EmailCatalogized -> {
                with(payload) {
                    saveEmailWithGroup(emailAddress, ownerFirstName, ownerLastName, emailGroupId, emailGroupName)
                }
                emailEventStream.updateStreamWith(externalEvent)
            }

            is EmailExternalEvent.EmailAddressChanged -> {
                with(payload) {
                    removeEmailFromGroupWhereChanged(oldEmailAddress, emailGroupId)
                    saveEmailWithGroup(newEmailAddress, ownerFirstName, ownerLastName, emailGroupId, emailGroupName)
                }
                emailEventStream.updateStreamWith(externalEvent)
            }

            is EmailExternalEvent.EmailOwnerCorrected -> {
                with(payload) {
                    removeEmailFromGroupWhereChanged(emailAddress, emailGroupId)
                    saveEmailWithGroup(emailAddress, ownerFirstName, ownerLastName, emailGroupId, emailGroupName)
                }
                emailEventStream.updateStreamWith(externalEvent)
            }

        }
    }

    private fun saveEmailWithGroup(
        emailAddress: String,
        ownerFirstName: String,
        ownerLastName: String,
        emailGroupId: String,
        emailGroupName: String?
    ) {
        val email = emailRepository.findByAddress(emailAddress) ?: EmailWithGroups(
            emailAddress,
            EmailOwner(
                ownerFirstName,
                ownerLastName
            )
        )
        email.also { it.groups.add(EmailWithGroups.EmailGroup(emailGroupId, emailGroupName)) }
            .let { emailRepository.save(it) }
    }

    private fun removeEmailFromGroupWhereChanged(emailAddress: String, emailGroupId: String) {
        emailRepository.findByAddress(emailAddress)
            ?.also {
                it.removeGroupById(emailGroupId)
                if (it.groups.isEmpty()) {
                    emailRepository.delete(it)
                }
            }
    }

}
