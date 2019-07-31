package org.bialydunajec.campbus.domain

import org.bialydunajec.eventsourcing.domain.AggregateVersion
import org.bialydunajec.eventsourcing.domain.givenAggregate
import org.bialydunajec.eventsourcing.domain.toFixed
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.Clock


object SeatSpecification : Spek({

    describe("Feature: Reserve seat in Camp Bus for a passenger") {

        val fixedClock = Clock.systemUTC().toFixed()
        val campBusCourseId = BusCourseId("example-buscourse-id")
        val seatId = SeatId("example-seat-id")
        val passengerId = PassengerId("example-passenger-id")
        val seat: Seat by memoized { Seat.newInstance { fixedClock.instant() } }

        context("Given seat for reservation is added for course") {

            val seatAddedForCourse = SeatEvent.SeatAddedForCourse(seatId, AggregateVersion.ZERO, fixedClock.instant(), campBusCourseId)

            describe("When reserve the seat for passenger") {

                val reserveSeat = SeatCommand.ReserveSeat(seatId, AggregateVersion.ONE, passengerId)

                it("Then the seat should be reserved for the passenger") {

                    val seatReservedForPassenger = SeatEvent.SeatReservedForPassenger(seatId, AggregateVersion.ONE, fixedClock.instant(), campBusCourseId, passengerId)

                    givenAggregate { seat }
                            .withPriorEvent { seatAddedForCourse }
                            .whenCommand { reserveSeat }
                            .thenExpectEvent { seatReservedForPassenger }
                }

            }

            context("And the seat already reserved") {

                val seatReserved = SeatEvent.SeatReservedForPassenger(seatId, AggregateVersion.ONE, fixedClock.instant(), campBusCourseId, passengerId)

                describe("When reserve the seat for passenger") {

                    val reserveSeat = SeatCommand.ReserveSeat(seatId, AggregateVersion.TWO, passengerId)

                    it("Then try should fail, because of previous reservation") {

                        val seatReservationFailed = SeatEvent.SeatReservationFailed(
                                seatId,
                                reserveSeat.aggregateVersion,
                                fixedClock.instant(),
                                campBusCourseId,
                                reserveSeat.passengerId,
                                SeatDomainRule.SeatForReservationCannotBeAlreadyReserved)

                        givenAggregate { seat }
                                .withPriorEvents(seatAddedForCourse, seatReserved)
                                .whenCommand { reserveSeat }
                                .thenExpectEvent { seatReservationFailed } //FIXME: Coś nie tak!? Brak eventów. czy metoda uzywana w withPrioerEvent nie zmienia stanu? Replay nie działa dobrze!?

                    }

                }

                context("And the reservation is confirmed") {

                    val reservationConfirmed = SeatEvent.SeatReservationConfirmed(seatId, AggregateVersion.TWO, fixedClock.instant(), campBusCourseId, passengerId)

                    describe("When reserve the seat for passenger") {

                        val reserveSeat = SeatCommand.ReserveSeat(seatId, AggregateVersion.THREE, passengerId)

                        it("Then try should fail") {

                            givenAggregate { seat }
                                    .withPriorEvents(seatAddedForCourse, seatReserved, reservationConfirmed)
                                    .whenCommand { reserveSeat }
                                    .thenExpectException()

                        }

                    }

                }

            }

            context("And the seat is already removed") {

                val seatRemoved = SeatEvent.SeatRemovedFromCourse(seatId, AggregateVersion.ONE, fixedClock.instant(), campBusCourseId, passengerId)

                describe("When reserve the seat for passenger") {

                    val reserveSeat = SeatCommand.ReserveSeat(seatId, AggregateVersion.TWO, passengerId)

                    it("Then try should fail") {

                        givenAggregate { seat }
                                .withPriorEvents(seatAddedForCourse, seatRemoved)
                                .whenCommand { reserveSeat }
                                .thenExpectException()

                    }

                }


            }

        }


    }

})

