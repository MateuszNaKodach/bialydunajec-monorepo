package org.bialydunajec.registrations.application.bootstrap

import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand.*
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.bootstrap.BoundedContextExternalDataBootstrap
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.campedition.valueobject.TimerSettings
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

        val campEdition36Id = CampRegistrationsEditionId(36)
        commandGateway.process(
                UpdateCampRegistrationsTimer(campEdition36Id,
                        TimerSettings(
                                ZonedDateTime.of(LocalDateTime.of(2019, 6, 15, 12, 0), ZoneId.systemDefault()),
                                ZonedDateTime.of(LocalDateTime.of(2019, 8, 15, 23, 59), ZoneId.systemDefault()
                        )
                ))
        )

        commandGateway.process(
                CreateAcademicMinistryCottage(campEdition36Id, AcademicMinistryId("1"))
        )

        commandGateway.process(
                CreateStandaloneCottage(campEdition36Id, "Złomy")
        )
    }
}