package org.bialydunajec.campbus.domain.seat

import assertk.assertThat
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant

object CampBusSeatSpecification : Spek({


    Feature("Reserve camp bus seat") {

        val campBusCourseId: CampBusCourseId by memoized { CampBusCourseId() }
        val seatId: SeatId by memoized { SeatId() }
        var seat: Seat = Seat.newInstance { Instant.now() }.handle(SeatCommand.AddSeatForCourse(seatId, campBusCourseId))

        Scenario("Free camp bus seat") {

            Given("seat for reservation is free") {
                assumeTrue { seat is Seat.Free }
            }

            When("passenger reserve seat") {
                seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.version, PassengerId()))
            }

            Then("seat should be reserved for the passenger") {
                assertThat(seat).isInstanceOf(Seat.Reserved::class)
            }

        }

        Scenario("Already reserved camp bus seat") {

            Given("seat for reservation is reserved for passenger Anna") {
                seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.version, PassengerId()))
                assumeTrue { seat is Seat.Reserved }
            }

            lateinit var exception: Throwable

            When("passenger Mateusz reserve seat") {
                //exception = shouldThrow<> {
                //    seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.version, PassengerId()))
                //}
            }

            Then("seat should be still reserved for the passenger Anna") {
                assertThat(seat).isInstanceOf(Seat.Reserved::class)
            }


        }

        Scenario("Already occupied camp bus seat") {

        }

        Scenario("Already removed camp bus seat") {

        }

    }

})
