package org.bialydunajec.email.readmodel.emailwithgroups.rest.v1

import org.bialydunajec.email.readmodel.emailwithgroups.EmailWithGroups
import org.bialydunajec.email.readmodel.emailwithgroups.EmailWithGroupsEventStream
import org.bialydunajec.email.readmodel.emailwithgroups.EmailWithGroupsMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/emails-with-groups")
@RestController
internal class EmailWithGroupsReadAdminController(
    private val repository: EmailWithGroupsMongoRepository,
    private val eventStream: EmailWithGroupsEventStream
) {

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
        eventStream.streamingEvents()

    @GetMapping
    fun getEmailsWithGroups(
        @RequestParam(required = false) emailGroupId: String?,
        @RequestParam(required = false) ownerFirstName: String?,
        @RequestParam(required = false) ownerLastName: String?,
        @RequestParam(required = false) emailAddress: String?
    ): Collection<EmailWithGroups> {
        var foundEmailWithGroups = repository.findAll()
        emailGroupId?.let {
            foundEmailWithGroups = foundEmailWithGroups.filter { it.groups.map { group -> group.emailGroupId }.contains(emailGroupId) }
        }
        ownerFirstName?.let {
            foundEmailWithGroups = foundEmailWithGroups.filter { it.owner.firstName.toLowerCase().contains(ownerFirstName.toLowerCase()) }
        }
        ownerLastName?.let {
            foundEmailWithGroups = foundEmailWithGroups.filter { it.owner.lastName.toLowerCase().contains(ownerLastName.toLowerCase()) }
        }
        emailAddress?.let {
            foundEmailWithGroups = foundEmailWithGroups.filter { it.address.toLowerCase().contains(emailAddress.toLowerCase()) }
        }
        return foundEmailWithGroups.sortedByDescending { it.address }
    }

}
