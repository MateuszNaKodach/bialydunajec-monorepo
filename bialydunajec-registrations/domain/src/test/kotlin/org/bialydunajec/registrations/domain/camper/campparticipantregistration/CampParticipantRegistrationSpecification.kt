package org.bialydunajec.registrations.domain.camper.campparticipantregistration

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.bialydunajec.ddd.domain.base.validation.exception.DomainRuleViolationException
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.PhoneNumber
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.contact.email.EmailAddress
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.BirthDate
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.FirstName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.Gender
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.human.LastName
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.location.Address
import org.bialydunajec.registrations.domain.campedition.CampRegistrationsEditionId
import org.bialydunajec.registrations.domain.camper.campparticipant.CampParticipantId
import org.bialydunajec.registrations.domain.camper.valueobject.*
import org.bialydunajec.registrations.domain.cottage.CottageId
import org.bialydunajec.registrations.domain.shirt.CampEditionShirtId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtColorOptionId
import org.bialydunajec.registrations.domain.shirt.entity.ShirtSizeOptionId
import org.bialydunajec.registrations.domain.shirt.valueobject.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate

internal class CampParticipantRegistrationSpecification : Spek({

    describe("Cancelling camp participant registration") {

        describe("Registration waiting for confirmation") {

            describe("Given registration waiting for confirmation") {

                val registration = TestFixture.campParticipantRegistration(ParticipationStatus.WAITING_FOR_CONFIRM)

                describe("When authorized person cancel the registration") {

                    registration.cancelByAuthorized()

                    it("Then the registration should be cancelled") {
                        assertThat(registration.getSnapshot().status).isEqualTo(RegistrationStatus.CANCELLED_BY_AUTHORIZED)
                    }

                }

            }
        }

        describe("Registration already cancelled by camper") {

            describe("Given registration already cancelled") {

                val registration = TestFixture.campParticipantRegistration(ParticipationStatus.UNREGISTERED_BY_AUTHORIZED)

                describe("When authorized person cancel the registration") {

                    it("Then the registration should not be cancelled once more") {
                        assertThat {
                            registration.cancelByAuthorized()
                        }.thrownError {
                            isInstanceOf(DomainRuleViolationException::class) //TODO: Assertion for validating violated rules
                        }
                    }

                }

            }

        }


    }
}
)

object TestFixture {

    private val campParticipantId = CampParticipantId("camp-participant-id")

    val campParticipantRegistration =
            { status: ParticipationStatus -> CampParticipantRegistration.createFrom(campParticipantSnapshot(status), shirtOrderSnapshot) }

    private val campParticipantSnapshot = { status: ParticipationStatus ->
        val camperApplication = CamperApplication(
                CottageId("cottage-id"),
                CamperPersonalData.withoutPeselNumber(
                        FirstName("Mateusz"),
                        LastName("Nowak"),
                        Gender.MALE,
                        BirthDate.of(1993, 8, 23)
                ),
                Address(),
                EmailAddress("email@email.com"),
                PhoneNumber("123-123-123"),
                CamperEducation("Politechnika Wrocławska", "WX", "CS", null, false)
        )
        CampParticipantSnapshot(
                campParticipantId,
                CampRegistrationsEditionId(35),
                camperApplication,
                camperApplication,
                StayDuration.withCheckInDate(LocalDate.now()),
                status
        )
    }

    private val shirtOrderSnapshot = ShirtOrderSnapshot(
            CampEditionShirtId(35),
            campParticipantId,
            ShirtColorOptionSnapshot(ShirtColorOptionId("1"), Color("color", "hex"), true),
            ShirtSizeOptionSnapshot(ShirtSizeOptionId("1"), ShirtSize("XXL", ShirtType.MALE, height = 177.0), true)
    )

}
