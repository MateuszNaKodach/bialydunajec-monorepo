package org.bialydunajec.campbus.domain

import arrow.core.Try
import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.time.Instant

object SeatSpecification : Spek({

    Feature("Seat reservations for Camp Bus") {

        val currentInstant = Instant.now()
        val campBusCourseId: BusCourseId by memoized { BusCourseId() }
        val seatId: SeatId by memoized { SeatId() }
        val passengerId: PassengerId by memoized { PassengerId() }
        val initialSeat: Seat by memoized { Seat.newInstance { currentInstant }.handle(SeatCommand.AddSeatForCourse(seatId, campBusCourseId)) }

        Scenario("Free seat") {

            var seat: Seat = initialSeat

            Given("seat for reservation is free") {
                assumeTrue { seat is Seat.Free }
            }

            When("passenger reserves seat") {
                seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId))
            }

            Then("seat should be reserved for the passenger") {
                assertThat(seat).isInstanceOf(Seat.Reserved::class)
            }

        }

        Scenario("Already reserved seat") {

            var seat: Seat = initialSeat

            Given("seat for reservation is reserved") {
                seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId))
                assumeTrue { seat is Seat.Reserved }
            }

            var reserveSeatFailure = false

            When("passenger tries to reserve seat") {
                reserveSeatFailure = Try { seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId)) }.isFailure()
            }

            Then("try should fail") {
                assertThat(reserveSeatFailure).isTrue()
            }

            Then("seat should be still reserved for the previous passenger") {
                assertThat(seat).isInstanceOf(Seat.Reserved::class)
            }


        }

        Scenario("Already occupied seat") {

            var seat: Seat = initialSeat

            Given("seat for reservation is occupied") {
                seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId))
                seat = seat.handle(SeatCommand.ConfirmReservation(seatId, seat.aggregateVersion))
                assumeTrue { seat is Seat.Occupied }
            }

            var reserveSeatFailure = false

            When("passenger tries to reserve seat") {
                reserveSeatFailure = Try { seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId)) }.isFailure()
            }

            Then("try should fail") {
                assertThat(reserveSeatFailure).isTrue()
            }

            Then("seat should be still occupied by the previous passenger") {
                assertThat(seat).isInstanceOf(Seat.Occupied::class)
            }


        }

        Scenario("Already removed seat") {

            var seat: Seat = initialSeat

            Given("seat for reservation is removed") {
                seat = seat.handle(SeatCommand.RemoveSeatFromCourse(seatId, seat.aggregateVersion))
                assumeTrue { seat is Seat.Removed }
            }

            var reserveSeatFailure = false

            When("passenger tries to reserve seat") {
                reserveSeatFailure = Try { seat = seat.handle(SeatCommand.ReserveSeat(seatId, seat.aggregateVersion, passengerId)) }.isFailure()
            }

            Then("try should fail") {
                assertThat(reserveSeatFailure).isTrue()
            }

            Then("seat should still be removed") {
                assertThat(seat).isInstanceOf(Seat.Removed::class)
            }

        }

    }

})
