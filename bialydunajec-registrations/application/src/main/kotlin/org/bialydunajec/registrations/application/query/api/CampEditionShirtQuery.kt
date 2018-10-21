package org.bialydunajec.registrations.application.query.api

sealed class CampEditionShirtQuery {
    class All : CampEditionShirtQuery()
    class ById(val campEditionShirtId: String) : CampEditionShirtQuery()
    class ByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CampEditionShirtQuery()
    class AvailableColorsByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CampEditionShirtQuery()
    class AvailableSizesByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CampEditionShirtQuery()
    class AvailableTypesByCampRegistrationsEditionId(val campRegistrationsEditionId: String) : CampEditionShirtQuery()
}