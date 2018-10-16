package org.bialydunajec.registrations.application.query.api

sealed class CampParticipantQuery {
    class All : CampParticipantQuery()
    class ById(val campParticipantId: String) : CampParticipantQuery()
    class ByCottageId(val cottageId: String) : CampParticipantQuery()
    class CountByCottageId(val cottageId: String) : CampParticipantQuery()

}