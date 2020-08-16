package org.bialydunajec.registrations.application.query.api

sealed class CampRegistrationsEditionQuery {
    class All : CampRegistrationsEditionQuery()
    class ById(val campRegistrationsEditionId: Int) : CampRegistrationsEditionQuery()
    class InProgress : CampRegistrationsEditionQuery()
}