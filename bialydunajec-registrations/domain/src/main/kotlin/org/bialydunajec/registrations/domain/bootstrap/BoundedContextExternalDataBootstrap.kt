package org.bialydunajec.registrations.domain.bootstrap

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.Url
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.time.ZonedDateTimeRange
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistry
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryId
import org.bialydunajec.registrations.domain.academicministry.AcademicMinistryRepository
import org.bialydunajec.registrations.domain.campedition.CampEdition
import org.bialydunajec.registrations.domain.campedition.CampEditionId
import org.bialydunajec.registrations.domain.campedition.CampEditionRepository
import org.bialydunajec.registrations.domain.cottage.CottageRepository
import org.bialydunajec.registrations.domain.camper.CamperRepository
import org.springframework.context.annotation.Configuration
import java.time.*
import javax.annotation.PostConstruct

@Configuration
class BoundedContextExternalDataBootstrap(
        val campEditionRepository: CampEditionRepository,
        val academicMinistryRepository: AcademicMinistryRepository,
        val cottageRepository: CottageRepository,
        val camperRepository: CamperRepository
) {


    fun bootstrap() {
        val (redemptor, maciejowka) = initAcademicMinistries()
        val campEdition = initCampEdition()
    }

    private fun initCampEdition(): CampEdition {
        val campEdition = campEditionRepository.save(
                CampEdition(
                        campEditionId = CampEditionId(36),
                        startDate = LocalDate.of(2019, 8, 1),
                        endDate = LocalDate.of(2019, 8, 15)
                )
        )
        campEditionRepository.save(campEdition)
        return campEdition
    }

    private fun initAcademicMinistries(): Pair<AcademicMinistry, AcademicMinistry> {
        val redemptor = academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("1"),
                        "Redemptor",
                        Url.ExternalUrl("https://lh3.googleusercontent.com/NopHXZjFGG2AcY5xWgRyKnzcRgKATiAgPCFLQHfrwEX4YjqbZ7nMfvL4F9TuZFhl55Axx-TGZcipxKdXUI6X7UEpbqSKmeqhGhHT5jG9wjzNo2zHhF5Hc_7gNJE7Yx1fnSAPSw46JDN5apArt02SoPt4RCnvFhdYHQD6vmJ136cs6pbQuSb73xkze34Z0Kx66N62yELLuevc_h7bG20yLr-FtG1hQGiFP_Ieq_ovTn-d6TBT-eciEIKc0luRlzQXLm5EeMzVfPLgCj7Y-pCLzu5J1LhFw_PG-MV_dUn2k8NTH9QQZRVjPLalcshdNoACVwgMcBwXyzUJHSTvQKpu-do2Z_g8hAPTZeP5TXXbz3rFb2A0Bew0QstsuYhw37EMkFmhDXP0-GHzoUPZrILL7xp3AKVXEw7RS1TJVOAwzg6Gw_3poUW31NRV2Y3GBaJEdwRpuGWoexcUQqZF-Bweiqn_Pslwe4Gz7TamEyZ14uNrOAQlLvEMJdvU3e8qoENS0hkshd9xXD_QL4QANdgkRyH-krq7vIDCDTu3JX7Zd6mE3Do5z8RVSwqUblZOwh7aXLmP3SSzqasRZedvaMqtuWcAcVSw6LynL5WkRzwflywlaVZg_s0JlAWheJIQvegem9zt0sP-a0-5fEKT-dWYovYjicf6eZ7m0DNBdsGAwGRJylkGjZQpAY8=w175-h128-no")
                )
        )
        val maciejowka = academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("2"),
                        "Maciejówka"
                )
        )
        return Pair(redemptor, maciejowka)
    }

    /*
    @PostConstruct
    fun bootstrap() {
        val (redemptor, maciejowka) = initAcademicMinistries()


        val campEdition = initCampEditionWithRegistrations()

        val redemptorCottage = cottageRepository.save(campEdition.createAcademicMinistryCottage(redemptor))
        val maciejowkaCottage = cottageRepository.save(campEdition.createAcademicMinistryCottage(maciejowka))
        val standaloneCottage = cottageRepository.save(campEdition.createStandaloneCottage("Złomy"))

        academicMinistryRepository.findAll().forEach {
            println(it)
        }
    }

    private fun initCampEditionWithRegistrations(): CampEdition {
        val campEdition = campEditionRepository.save(
                CampEdition(
                        campEditionId = CampEditionId(36),
                        startDate = LocalDate.of(2019, 8, 1),
                        endDate = LocalDate.of(2019, 8, 15)
                )
        )

        campEdition.updateCampRegistrationsDuration(
                ZonedDateTimeRange(
                        ZonedDateTime.of(LocalDateTime.of(2019, 6, 15, 12, 0), ZoneId.systemDefault()),
                        ZonedDateTime.of(LocalDateTime.of(2019, 8, 15, 23, 59), ZoneId.systemDefault())
                )
        )
        println("PRINT")
        campEditionRepository.save(campEdition)
        return campEdition
    }

    private fun initAcademicMinistries(): Pair<AcademicMinistry, AcademicMinistry> {
        val redemptor = academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("1"),
                        "Redemptor",
                        Url.ExternalUrl("https://lh3.googleusercontent.com/NopHXZjFGG2AcY5xWgRyKnzcRgKATiAgPCFLQHfrwEX4YjqbZ7nMfvL4F9TuZFhl55Axx-TGZcipxKdXUI6X7UEpbqSKmeqhGhHT5jG9wjzNo2zHhF5Hc_7gNJE7Yx1fnSAPSw46JDN5apArt02SoPt4RCnvFhdYHQD6vmJ136cs6pbQuSb73xkze34Z0Kx66N62yELLuevc_h7bG20yLr-FtG1hQGiFP_Ieq_ovTn-d6TBT-eciEIKc0luRlzQXLm5EeMzVfPLgCj7Y-pCLzu5J1LhFw_PG-MV_dUn2k8NTH9QQZRVjPLalcshdNoACVwgMcBwXyzUJHSTvQKpu-do2Z_g8hAPTZeP5TXXbz3rFb2A0Bew0QstsuYhw37EMkFmhDXP0-GHzoUPZrILL7xp3AKVXEw7RS1TJVOAwzg6Gw_3poUW31NRV2Y3GBaJEdwRpuGWoexcUQqZF-Bweiqn_Pslwe4Gz7TamEyZ14uNrOAQlLvEMJdvU3e8qoENS0hkshd9xXD_QL4QANdgkRyH-krq7vIDCDTu3JX7Zd6mE3Do5z8RVSwqUblZOwh7aXLmP3SSzqasRZedvaMqtuWcAcVSw6LynL5WkRzwflywlaVZg_s0JlAWheJIQvegem9zt0sP-a0-5fEKT-dWYovYjicf6eZ7m0DNBdsGAwGRJylkGjZQpAY8=w175-h128-no")
                )
        )
        val maciejowka = academicMinistryRepository.save(
                AcademicMinistry(
                        AcademicMinistryId("1"),
                        "Maciejówka"
                )
        )
        return Pair(redemptor, maciejowka)
    }*/
}