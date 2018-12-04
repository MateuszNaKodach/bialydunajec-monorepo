package org.bialydunajec.rest.v1.dev

import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.PersonProperties
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
import java.util.*

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

    private val fairy = Fairy.create(Locale.forLanguageTag("PL"))

    private val personProperties = arrayOf(PersonProperties.ageBetween(18, 26))

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
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Duszpasterstwo Akademickie Wawrzyny",
                        shortName = "Wawrzyny",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/JrCyiqJqLmDtzmfrlaCOAH5B32aJ1i4GO7Us9SZAF0Y0xMGaSsQv2fP-jO0ePOkPunTkUvKuykb4UC-I=w175-h130-rw"),
                        place = Place(
                                Address(Street("Odona Bujwida"), HomeNumber("51"), CityName("Wrocław"), PostalCode("50-345"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://wawrzyny.wroc.pl/")
                        ),
                        emailAddress = EmailAddress("wawrzyny@wawrzyny.wroc.pl"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Centralny Ośrodek Duszpasterstwa Akademickiego Maciejówka",
                        shortName = "Maciejówka",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/e1DLOGY5DO3Rg_EOPQ5GyfxvMh4GnIXwCOa4OLRBMnFm6um1oY4LCjIFPAg0rakRNWJcSLZAZYoajCdq=w175-h128-rw"),
                        place = Place(
                                Address(Street("Nankiera"), HomeNumber("17a"), CityName("Wrocław"), PostalCode("50-140"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://www.maciejowka.org/")
                        ),
                        emailAddress = EmailAddress("ks.malinski@gmail.com"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Salezjańskie Duszpasterstwo Akademickie MOST",
                        shortName = "Most",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/Q2wNM_M1JuWMH5mOX-QqSbFeckKs7Ix71Xi0m-VjuVm4Y_dpfHK2WYVvLzSKxC-JOobz_JezNNXSVxd1=w175-h128-rw"),
                        place = Place(
                                Address(Street("Grunwaldzki"), HomeNumber("3"), CityName("Wrocław"), PostalCode("50-377"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://www.most.salezjanie.pl")
                        ),
                        emailAddress = EmailAddress("longus.sdb@gmail.com"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Franciszkańskie Duszpasterstwo Akademickie \"Antoni\"",
                        shortName = "Anotni",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/hJrcjzSbQwt2QMB7ZquZB91-0ORqbxaJKUUnIy2JEO6CBD4fUCuD8FmDY95D_RF5em_gy_ub1xPg5UOD=w175-h128-rw"),
                        place = Place(
                                Address(Street("Kasprowicza"), HomeNumber("26"), CityName("Wrocław"), PostalCode("51-001"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("www.antoni.org.pl/")
                        ),
                        emailAddress = EmailAddress("oskarofm@op.pl"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Salezjańskie Duszpasterstwo Akademickie HOREB",
                        shortName = "Horeb",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/2o2vZtcccsloq2mp5IsC5I0RPqjHYdNtjIeCB4iBcyMixzuDaWFKYUfEDhTFsntsAFOKSOlmZqjIO12I=w175-h128-rw"),
                        place = Place(
                                Address(Street("Bolesława Prusa"), HomeNumber("78"), CityName("Wrocław"), PostalCode("50-318"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://www.horeb.salezjanie.pl")
                        ),
                        emailAddress = EmailAddress("sdahoreb@gmail.com"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Xaverianum",
                        shortName = "Xaverianum",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/7FY-bdfg7KSwFzSq1Dif-6dwDs27n_l-a0Ft4VJeoFwx-pW1IMcux8szPIJzFwgdbrX0PdlYY5pvbcOp=w175-h130-rw"),
                        place = Place(
                                Address(Street("o. Józefa Czaplaka"), HomeNumber("1A"), CityName("Opole"), PostalCode("45-055"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://xaverianum.pl")
                        ),
                        emailAddress = EmailAddress("xaverianum.opole@gmail.com"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Duszpasterstwo Akademickie Dominik",
                        shortName = "Dominik",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/ipDlsAC1V_HZI2dcrfbHSNjo4XrTwHQTLu5HBEpdoTuNSLfffWVRLOC-SG0oRaLQch8gSmfOmGE9UDKz=w175-h130-rw"),
                        place = Place(
                                Address(Street("Dominikański"), HomeNumber("2"), CityName("Wrocław"), PostalCode("50-159"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://dadominik.pl/")
                        ),
                        emailAddress = EmailAddress("administrator@dadominik.pl"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Jezuickie Duszpasterstwo Akademickie DACH",
                        shortName = "Dach",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/FqAhfKQJ0_eFgZ-eSIPgqSscDkR--lqHNANL3X8nwCN6C8XxdwM-H7SptaS8-Cu4tKMSzXMlhlcL2i-S=w175-h130-rw"),
                        place = Place(
                                Address(Street("Wincentego Stysia"), HomeNumber("16"), CityName("Wrocław"), PostalCode("53-110"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://www.dadach.pl/")
                        ),
                        emailAddress = null,
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Góra św. Anny",
                        shortName = null,
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/VTnZ1lyholovO6vp0lYixUQJmP17pYKMf-ejd2GBxSqSLNVbMBkDGczDYs-emqWFApCHlg-j7oMoQw0v=w175-h128-rw"),
                        place = Place(
                                Address(Street("Jana Pawła II"), HomeNumber("7"), CityName("Góra Świętej Anny"), PostalCode("47-154"))
                        ),
                        socialMedia = SocialMedia(
                                WebPage("http://www.swanna.org")
                        ),
                        emailAddress = EmailAddress("duszpasterstwo.swanna@gmail.com"),
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Duszpasterstwo Akademickie \"Karmel\"",
                        shortName = "Karmel",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/43OZOCd1oYBqDOIp4u_JKXJZAedg9Xw7fs1DHPlqDeq74yHfVaQ_rdmz2hp56sz2iLuMQlMA-UISXH8y=w175-h130-rw"),
                        place = null,
                        socialMedia = SocialMedia(
                                WebPage("http://www.da.karmel.pl")
                        ),
                        emailAddress = null,
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Duszpasterstwo Akademickie \"Przystań\"",
                        shortName = "Przystań",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/eXQfxqNuhAoxFirpkMNeXuWe3WeluC8rZEWDlaUTVdu9HQN8b7f1kKt_0dTWZmtuxDnh3dV2VJWap_eJ=w175-h128-rw"),
                        place = null,
                        socialMedia = SocialMedia(
                                WebPage("http://da-przystan.wroclaw.pl/")
                        ),
                        emailAddress = null,
                        photoUrl = null,
                        description = null
                ),
                AcademicMinistryCommand.CreateAcademicMinistry(
                        officialName = "Franciszkańskie Duszpasterstwo Akademickie \"Porcjunkula\"",
                        shortName = "Porcjunkula",
                        logoImageUrl = Url.ExternalUrl("//lh3.googleusercontent.com/lIksuDzDy_ceLfzqFnloVniiG1XKOJegXwFtxuJYlBEFwX9bJPEdL1RqrtysIvYazQ3IOmYGuOT-6IH6=w175-h128-rw"),
                        place = null,
                        socialMedia = SocialMedia(
                                WebPage("http://porcjunkula.org/")
                        ),
                        emailAddress = null,
                        photoUrl = null,
                        description = null
                )
        ).sortedBy { it.shortName }.forEach { academicMinistryAdminCommandGateway.process(it) }

        academicMinistryQueryGateway.process(AcademicMinistryQuery.All())
                .forEach {
                    campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(36, it.academicMinistryId))
                    campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.CreateAcademicMinistryCottage(35, it.academicMinistryId))
                }

        campRegistrationsQueryGateway.process(CottageQuery.AllByCampRegistrationsEditionId("36"))
                .forEachIndexed { index, it ->
                    val cottageBoss = fairy.person(*personProperties)

                    campRegistrationsAdminCommandGateway.process(
                            CampRegistrationsCommand.UpdateCottage(
                                    cottageId = CottageId(it.cottageId),
                                    name = it.name,
                                    logoImageUrl = it.logoImageUrl?.let { imageUrl -> Url.ExternalUrl(imageUrl) },
                                    buildingPhotoUrl = it.buildingPhotoUrl?.let { imageUrl -> Url.ExternalUrl(imageUrl) },
                                    place = Place(
                                            Address(Street("Jana Pawła II"), HomeNumber(index.toString()), CityName("Biały Dunajec"))
                                    ),
                                    cottageSpace = CottageSpace(fairy.baseProducer().randomBetween(40, 70), 0),
                                    campersLimitations = null,
                                    bankTransferDetails = BankTransferDetails(
                                            fairy.iban().ibanNumber.drop(2),
                                            "${cottageBoss.firstName} ${cottageBoss.lastName}",
                                            "ul. ${cottageBoss.address.street} ${cottageBoss.address.streetNumber}, ${cottageBoss.address.postalCode} ${cottageBoss.address.city}",
                                            "Biały Dunajec 2018 <IMIE i NAZWISKO>"
                                    ),
                                    cottageBoss = CottageBoss(
                                            firstName = FirstName(cottageBoss.firstName),
                                            lastName = LastName(cottageBoss.lastName),
                                            phoneNumber = PhoneNumber(cottageBoss.telephoneNumber),
                                            emailAddress = EmailAddress(cottageBoss.email),
                                            university = "Politechnika Wrocławska",
                                            fieldOfStudy = "Zarządzanie",
                                            photoUrl = null,
                                            personalDescription = null
                                    )
                            )
                    )

                }

        campRegistrationsQueryGateway.process(CottageQuery.AllByCampRegistrationsEditionId("36")).forEach {
            campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.ActivateCottage(CottageId(it.cottageId)))
        }


        listOf(
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("S", ShirtType.MALE, 164.0, 46.0, 68.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("M", ShirtType.MALE, 170.0, 51.0, 71.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("L", ShirtType.MALE, 176.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("XL", ShirtType.MALE, 182.0, null, 76.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("XXL", ShirtType.MALE, 188.0),
                        false
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("S", ShirtType.FEMALE, 158.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("L", ShirtType.FEMALE, 164.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("M", ShirtType.FEMALE, 170.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("XL", ShirtType.FEMALE, 176.0),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtSizeOption(
                        CampEditionShirtId(36),
                        ShirtSize("XXL", ShirtType.FEMALE, 182.0),
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
                ),
                CampRegistrationsCommand.AddCampEditionShirtColorOption(
                        CampEditionShirtId(36),
                        Color("niebieski", null),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtColorOption(
                        CampEditionShirtId(36),
                        Color("zielony", null),
                        true
                ),
                CampRegistrationsCommand.AddCampEditionShirtColorOption(
                        CampEditionShirtId(36),
                        Color("burgundowy", null),
                        true
                )
        ).forEach { campRegistrationsAdminCommandGateway.process(it) }

        campRegistrationsAdminCommandGateway.process(CampRegistrationsCommand.StartCampRegistrationsNow(36))
    }

    fun registerDummyCampers() {
        val campShirt = campRegistrationsQueryGateway.process(CampEditionShirtQuery.ByCampRegistrationsEditionId("36"))

        campRegistrationsQueryGateway.process(CottageQuery.All())
                .filter { it.cottageState == "ACTIVATED" }
                .filter { it.name != "Redemptor" }
                .parallelStream()
                .forEach {
                    for (x in 0..fairy.baseProducer().randomBetween(0, 8)) {
                        val person = fairy.person(*personProperties)
                        CampRegistrationsCommand.RegisterCampParticipantCommand(
                                CampRegistrationsEditionId(36),
                                CamperApplication(
                                        CottageId(it.cottageId),
                                        CamperPersonalData(
                                                FirstName(person.firstName),
                                                LastName(person.lastName),
                                                if (person.isMale) Gender.MALE else Gender.FEMALE,
                                                Pesel(person.nationalIdentificationNumber)
                                        ),
                                        Address(Street(person.address.street), HomeNumber("${person.address.streetNumber} / ${person.address.apartmentNumber}"), CityName(person.address.city), PostalCode(person.address.postalCode)),
                                        EmailAddress(person.email),
                                        PhoneNumber(person.telephoneNumber),
                                        CamperEducation(
                                                "Politechnika Wrocławska",
                                                "Podstawowych Problemów Techniki",
                                                "Informatyka",
                                                null,
                                                fairy.baseProducer().trueOrFalse()
                                        )
                                ),
                                CamperShirtOrder(
                                        ShirtColorOptionId(campShirt!!.colorOptions.random().shirtColorOptionId),
                                        ShirtSizeOptionId(campShirt!!.sizeOptions.random().shirtSizeOptionId)
                                )
                        ).let { registration -> campRegistrationsCommandGateway.process(registration, false) }
                    }
                }


        campRegistrationsQueryGateway.process(CottageQuery.All())
                .find { it.name == "Redemptor" }
                ?.let {
                    for (x in 0..26) {
                        val person = fairy.person(*personProperties)
                        CampRegistrationsCommand.RegisterCampParticipantCommand(
                                CampRegistrationsEditionId(36),
                                CamperApplication(
                                        CottageId(it.cottageId),
                                        CamperPersonalData(
                                                FirstName(person.firstName),
                                                LastName(person.lastName),
                                                if (person.isMale) Gender.MALE else Gender.FEMALE,
                                                Pesel(person.nationalIdentificationNumber)
                                        ),
                                        Address(Street(person.address.street), HomeNumber("${person.address.streetNumber} / ${person.address.apartmentNumber}"), CityName(person.address.city), PostalCode(person.address.postalCode)),
                                        EmailAddress(person.email),
                                        PhoneNumber(person.telephoneNumber),
                                        CamperEducation(
                                                "Politechnika Wrocławska",
                                                "Podstawowych Problemów Techniki",
                                                "Informatyka",
                                                null,
                                                fairy.baseProducer().trueOrFalse()
                                        )
                                ),
                                CamperShirtOrder(
                                        ShirtColorOptionId(campShirt!!.colorOptions.random().shirtColorOptionId),
                                        ShirtSizeOptionId(campShirt!!.sizeOptions.random().shirtSizeOptionId)
                                )
                        ).let { registration -> campRegistrationsCommandGateway.process(registration, false) }
                    }
                }
    }
}


