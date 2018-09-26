package org.bialydunajec.registrations.domain.bootstrap

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.Url
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrations
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsId
import org.bialydunajec.registrations.domain.campregistrations.CampRegistrationsRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.cottageaccommodation.CottageAccommodationRepository
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class BoundedContextBootstrap(
        val academicMinistryRepository: AcademicMinistryRepository,
        val campRegistrationsRepository: CampRegistrationsRepository,
        val cottageRepository: CottageRepository,
        val cottageAccommodationRepository: CottageAccommodationRepository
) {


    @PostConstruct
    fun bootstrap() {
        val campRegistrations = campRegistrationsRepository.save(CampRegistrations(CampRegistrationsId(36)))

        val academicMinistry = academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("1"),
                        "Redemptor",
                        Url.ExternalUrl("https://lh3.googleusercontent.com/NopHXZjFGG2AcY5xWgRyKnzcRgKATiAgPCFLQHfrwEX4YjqbZ7nMfvL4F9TuZFhl55Axx-TGZcipxKdXUI6X7UEpbqSKmeqhGhHT5jG9wjzNo2zHhF5Hc_7gNJE7Yx1fnSAPSw46JDN5apArt02SoPt4RCnvFhdYHQD6vmJ136cs6pbQuSb73xkze34Z0Kx66N62yELLuevc_h7bG20yLr-FtG1hQGiFP_Ieq_ovTn-d6TBT-eciEIKc0luRlzQXLm5EeMzVfPLgCj7Y-pCLzu5J1LhFw_PG-MV_dUn2k8NTH9QQZRVjPLalcshdNoACVwgMcBwXyzUJHSTvQKpu-do2Z_g8hAPTZeP5TXXbz3rFb2A0Bew0QstsuYhw37EMkFmhDXP0-GHzoUPZrILL7xp3AKVXEw7RS1TJVOAwzg6Gw_3poUW31NRV2Y3GBaJEdwRpuGWoexcUQqZF-Bweiqn_Pslwe4Gz7TamEyZ14uNrOAQlLvEMJdvU3e8qoENS0hkshd9xXD_QL4QANdgkRyH-krq7vIDCDTu3JX7Zd6mE3Do5z8RVSwqUblZOwh7aXLmP3SSzqasRZedvaMqtuWcAcVSw6LynL5WkRzwflywlaVZg_s0JlAWheJIQvegem9zt0sP-a0-5fEKT-dWYovYjicf6eZ7m0DNBdsGAwGRJylkGjZQpAY8=w175-h128-no")
                )
        )

        //TODO: Consider add Cottage as entity to CampRegistrations! Co bylo w ksiazce o referencjach do wewnetrznych encji?
        val cottage = cottageRepository.save(campRegistrations.createAcademicMinistryCottage(academicMinistry))

    }
}