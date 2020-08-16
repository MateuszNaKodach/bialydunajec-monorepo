package org.bialydunajec.email.readmodel.groupwithemails.rest.v1

import org.bialydunajec.email.readmodel.groupwithemails.GroupWithEmailsEventStream
import org.bialydunajec.email.readmodel.groupwithemails.GroupWithEmailsMongoRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/rest-api/v1/admin/group-with-emails")
@RestController
internal class GroupWithEmailsReadAdminController(
    private val repository: GroupWithEmailsMongoRepository,
    private val eventStream: GroupWithEmailsEventStream
) {

    @GetMapping
    fun getAllGroupWithEmails() =
        repository.findAll()
            .sortedByDescending { it.groupName }

    @GetMapping("/{emailGroupId}")
    fun getGroupWithEmailsByGroupId(@PathVariable emailGroupId: String) =
        repository.findById(emailGroupId)

    @GetMapping(value = ["/projected-events-stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getProjectedEventsStream() =
        eventStream.streamingEvents()

}
