package org.bialydunajec.rest.v1.dev

import org.bialydunajec.academicministry.application.command.api.AcademicMinistryAdminCommandGateway
import org.bialydunajec.academicministry.application.command.api.AcademicMinistryCommand
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQuery
import org.bialydunajec.academicministry.application.query.api.AcademicMinistryQueryGateway
import org.bialydunajec.authorization.server.api.AuthorizationServerFacade
import org.bialydunajec.authorization.server.api.command.CreateUserCredentialsCommand
import org.bialydunajec.campedition.application.command.api.CampEditionCommand
import org.bialydunajec.campedition.application.command.api.CampEditionCommandGateway
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Pesel
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.*
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.*
import org.bialydunajec.registrations.application.command.api.CampRegistrationsAdminCommandGateway
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommand
import org.bialydunajec.registrations.application.command.api.CampRegistrationsCommandGateway
import org.bialydunajec.registrations.application.query.api.CampEditionShirtQuery
import org.bialydunajec.registrations.application.query.api.CampRegistrationsQueryGateway
import org.bialydunajec.registrations.application.query.api.CottageQuery
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.valueobject.CamperApplication
import org.bialydunajec.registrations.domain.camper.valueobject.CamperEducation
import org.bialydunajec.registrations.domain.camper.valueobject.CamperPersonalData
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.cottage.valueobject.BankTransferDetails
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageBoss
import org.bialydunajec.registrations.domain.cottage.valueobject.CottageSpace
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.domain.shirt.valueobject.CamperShirtOrder
import org.bialydunajec.registrations.domain.shirt.valueobject.Color
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtSize
import org.bialydunajec.registrations.domain.shirt.valueobject.ShirtType
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DummyDatabaseInitializator(
        private val authorizationServerFacade: AuthorizationServerFacade,
        private val campEditionCommandGateway: CampEditionCommandGateway,
        private val academicMinistryAdminCommandGateway: AcademicMinistryAdminCommandGateway,
        private val academicMinistryQueryGateway: AcademicMinistryQueryGateway,
        private val campRegistrationsAdminCommandGateway: CampRegistrationsAdminCommandGateway,
        private val campRegistrationsCommandGateway: CampRegistrationsCommandGateway,
        private val campRegistrationsQueryGateway: CampRegistrationsQueryGateway
) {

    fun initialize() {
        listOf(
                CreateUserCredentialsCommand(
                        "nmateusz96@gmail.com",
                        "mate96",
                        "test1234"
                ),
                CreateUserCredentialsCommand(
                        "dev.zapisy@bialydunajec.org",
                        "admin",
                        "test1234"
                )
        ).forEach { authorizationServerFacade.createUserCredentials(it) }


        listOf(
                CampEditionCommand.CreateCampEdition.from(
                        36,
                        LocalDate.now().plusDays(15),
                        LocalDate.now().plusDays(30),
                        419.0,
                        99.0
                ),
                CampEditionCommand.CreateCampEdition.from(
                        35,
                        LocalDate.now().plusDays(15).minusYears(1),
                        LocalDate.now().plusDays(30).minusYears(1),
                        399.0,
                        99.0
                )
        ).forEach { campEditionCommandGateway.process(it) }


        listOf(
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Duszpasterstwo Akademickie Redemptor",
                        shortName = "Redemptor",
                        logoImageUrl = Url.ExternalUrl("https://lh3.googleusercontent.com/UrJ3r2ps10B7nb6PCw7wQRu2LGwvObZGBmpgQ_stKEbPqlNsQs2cfcG1PbMLkiQK56N5E5t82FVvjraV=w219-h160-rw"),
                        place = Place(
                                Address(Street("Wittiga"), HomeNumber("10"), CityName("Wrocław"), PostalCode("51-123"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("www.da.redemptor.pl"),
                                FacebookPage("https://www.facebook.com/da.redemptor/")
                        ),
                        emailAddress = EmailAddress("duszpasterz@da.redemptor.pl"),
                        photoUrl = null,
                        description = null
                )
        ).forEach { academicMinistryAdminCommandGateway.process(it) }

        academicMinistryQueryGateway.process(AcademicMinistryQuery.All())
                .forEach {
                    campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(36, it.academicMinistryId))
                    campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(35, it.academicMinistryId))
                }

        campRegistrationsQueryGateway.process(CottageQuery.AllByCampRegistrationsEditionId("36"))
                .forEach {
                    campRegistrationsAdminCommandGateway.process(
                            CampRegistrationsCommand.UpdateCottage(
                                    cottageId = CottageId(it.cottageId),
                                    name = it.name,
                                    logoImageUrl = it.logoImageUrl?.let { imageUrl -> Url.ExternalUrl(imageUrl) },
                                    buildingPhotoUrl = it.buildingPhotoUrl?.let { imageUrl -> Url.ExternalUrl(imageUrl) },
                                    place = Place(
                                            Address(Street("Jana Pawła II"), HomeNumber("${it.cottageId}12a"), CityName("Biały Dunajec"))
                                    ),
                                    cottageSpace = CottageSpace(15, 0),
                                    campersLimitations = null,
                                    bankTransferDetails = BankTransferDetails(
                                            "${it.cottageId} 1234 5667",
                                            "Jan${it.cottageId} Kowalski",
                                            "ul. Testowa 12/${it.cottageId}A, 12-345 Testowo Małe",
                                            "Biały Dunajec 2018 <IMIE i NAZWISKO>"
                                    ),
                                    cottageBoss = CottageBoss(
                                            firstName = FirstName("Jan${it.cottageId}"),
                                            lastName = LastName("Kowalski"),
                                            phoneNumber = PhoneNumber("123-456-789"),
                                            emailAddress = EmailAddress("chatka${it.cottageId}@bialydunajec.org"),
                                            university = "Politechnika Wrocławska",
                                            fieldOfStudy = "Zarządzanie",
                                            photoUrl = null,
                                            personalDescription = null
                                    )
                            )
                    )

                    campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.ActivateCottage(CottageId(it.cottageId)))
                }


        listOf(
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("ExampleSize", ShirtType.MALE, 123.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("ExampleSize", ShirtType.FEMALE, 123.0),
                        true
                )
        ).forEach { campRegistrationsAdminCommandGateway.process(it) }


        listOf(
                CampRegistrationsCommand.AddCampEditionShirtColorOption(
                        CampEditionShirtId(36),
                        Color("czarny", null),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtColorOption(
                        CampEditionShirtId(36),
                        Color("czerwony", null),
                        true
                )
        ).forEach { campRegistrationsAdminCommandGateway.process(it) }

        campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.StartCampRegistrationsNow(36))

        val campShirt = campRegistrationsQueryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId("36"))

        campRegistrationsQueryGateway.process(CottageQuery.All())
                .first().let {
                    CampRegistrationsCommand.RegisterCampParticipantCommand(
                            CampRegistrationsEditionId(36),
                            CamperApplication(
                                    CottageId(it.cottageId),
                                    CamperPersonalData(
                                            FirstName("Antoni"),
                                            LastName("Antoniewicz"),
                                            Gender.MALE,
                                            Pesel("97031986579")
                                    ),
                                    Address(Street("św. Anotniego"), HomeNumber("12/33"), CityName("Wrocław"), PostalCode("51-123")),
                                            EmailAddress ("anotni.antoniewicz@niepodam.pl"),
                                    PhoneNumber("660123123"),
                                    CamperEducation(
                                            "Politechnika Wrocławska",
                                            "Podstawowych Problemów Techniki",
                                            "Informatyka",
                                            null,
                                            false
                                    )
                            ),
                            CamperShirtOrder(
                                    ShirtColorOptionId(campShirt!!.colorOptions.first().shirtColorOptionId),
                                    ShirtSizeOptionId(campShirt!!.sizeOptions.first().shirtSizeOptionId)
                            )
                    ).let { registration -> campRegistrationsCommandGateway.process(registration) }
                }

    }
}