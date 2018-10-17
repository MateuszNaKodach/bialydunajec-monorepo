package org.bialydunajec.registrations.application.query.api

import org.bialydunajec.ddd.application.base.query.Query

sealed class CampParticipantQuery : Query {
    class All : CampParticipantQuery()
    class ById(val campParticipantId: String) : CampParticipantQuery()
    class ByCottageId(val cottageId: String) : CampParticipantQuery()
    class CountByCottageId(val cottageId: String) : CampParticipantQuery()

}