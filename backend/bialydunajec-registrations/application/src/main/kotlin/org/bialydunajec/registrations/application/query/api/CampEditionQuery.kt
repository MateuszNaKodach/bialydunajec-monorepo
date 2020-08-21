package org.bialydunajec.registrations.application.query.api

sealed class CampEditionQuery {
    class All : CampEditionQuery()
    class ById(val campRegistrationsEditionId: Int) : CampEditionQuery()
}