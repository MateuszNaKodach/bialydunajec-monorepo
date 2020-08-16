package org.bialydunajec.email.readmodel.groupwithemails

import org.bialydunajec.ddd.application.base.external.event.ExternalEvent
import org.bialydunajec.ddd.application.base.external.event.ExternalEventSubscriber
import org.bialydunajec.ddd.application.base.external.event.SerializedExternalEventListener
import org.bialydunajec.email.messages.event.EmailExternalEvent
import org.bialydunajec.email.messages.event.EmailGroupExternalEvent
import org.bialydunajec.email.readmodel.shared.EmailOwner
import org.springframework.stereotype.Component

@Component
internal class GroupWithEmailsEventsProjection(
    private val repository: GroupWithEmailsMongoRepository,
    private val eventStream: GroupWithEmailsEventStream,
    eventSubscriber: ExternalEventSubscriber
) : SerializedExternalEventListener() {

    init {
        eventSubscriber.subscribe(EmailGroupExternalEvent.EmailGroupCreated::class) {
            processingQueue.process(it)
        }
    }

    override fun processExternalEvent(externalEvent: ExternalEvent<*>) {
        val payload = externalEvent.payload
        when (payload) {
            is EmailGroupExternalEvent.EmailGroupCreated ->
                with(payload) {
                    val group = findGroupOrCreate(emailGroupId, name)
                    repository.save(group)
                    eventStream.updateStreamWith(externalEvent)
                }

            is EmailGroupExternalEvent.EmailGroupNameChanged ->
                with(payload) {
                    val group = findGroupOrCreate(emailGroupId, name)
                    group.groupName = name
                    repository.save(group)
                    eventStream.updateStreamWith(externalEvent)
                }

            is EmailExternalEvent.EmailCatalogized ->
                with(payload) {
                    val group = findGroupOrCreate(emailGroupId, emailGroupName)
                    group.addOrUpdateEmail(
                        GroupWithEmails.Email(
                            emailId,
                            emailAddress,
                            EmailOwner(ownerFirstName, ownerLastName)
                        )
                    )
                    repository.save(group)
                    eventStream.updateStreamWith(externalEvent)
                }

            is EmailExternalEvent.EmailOwnerCorrected ->
                with(payload) {
                    val group = findGroupOrCreate(emailGroupId, emailGroupName)
                    group.addOrUpdateEmail(
                        GroupWithEmails.Email(
                            emailId,
                            emailAddress,
                            EmailOwner(ownerFirstName, ownerLastName)
                        )
                    )
                    repository.save(group)
                    eventStream.updateStreamWith(externalEvent)
                }

            is EmailExternalEvent.EmailAddressChanged ->
                with(payload) {
                    val group = findGroupOrCreate(emailGroupId, emailGroupName)
                    group.addOrUpdateEmail(
                        GroupWithEmails.Email(
                            emailId,
                            newEmailAddress,
                            EmailOwner(ownerFirstName, ownerLastName)
                        )
                    )
                    repository.save(group)
                    eventStream.updateStreamWith(externalEvent)
                }

        }

    }

    private fun findGroupOrCreate(emailGroupId: String, name: String?) =
        repository.findById(emailGroupId).orElseGet { GroupWithEmails(emailGroupId, name) }

}
