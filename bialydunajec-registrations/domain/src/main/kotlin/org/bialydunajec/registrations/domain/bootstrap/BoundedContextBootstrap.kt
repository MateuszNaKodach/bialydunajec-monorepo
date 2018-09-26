package org.bialydunajec.registrations.domain.bootstrap

import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodationRepository
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class BoundedContextBootstrap(
        val academicMinistryRepository: AcademicMinistryRepository,
        val campersRegistrationsRepository: CampRegistrationsRepository,
        val cottageRepository: CottageRepository,
        val cottageAccommodationRepository: CottageAccommodationRepository
) {


    @PostConstruct
    fun bootstrap() {
        academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("1"),
                        "Redemptor"
                )
        )
    }
}