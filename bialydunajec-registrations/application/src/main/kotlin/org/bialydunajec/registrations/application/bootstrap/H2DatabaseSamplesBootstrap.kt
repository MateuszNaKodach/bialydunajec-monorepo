package org.bialydunajec.registrations.application.bootstrap

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.application.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.api.CampRegistrationsCommand.*
import org.bialydunajec.registrations.application.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.bootstrap.BoundedContextExternalDataBootstrap
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.annotation.PostConstruct

@Component
internal class H2DatabaseSamplesBootstrap(
        private val boundedContextExternalDataBootstrap: BoundedContextExternalDataBootstrap,
        private val commandGateway: CampRegistrationsCommandGateway
) {

    @PostConstruct
    fun bootstrap() {
        boundedContextExternalDataBootstrap.bootstrap()

        commandGateway.process(
                SetupCampRegistrations(CampEditionId(36), ZonedDateTimeRange(
                        ZonedDateTime.of(LocalDateTime.of(2019, 6, 15, 12, 0), ZoneId.systemDefault()),
                        ZonedDateTime.of(LocalDateTime.of(2019, 8, 15, 23, 59), ZoneId.systemDefault())
                ))
        )

        commandGateway.process(
                CreateAcademicMinistryCottage(CampEditionId(36), AcademicMinistryId("1"))
        )

        commandGateway.process(
                CreateStandaloneCottage(CampEditionId(36), "Złomy")
        )
    }
}