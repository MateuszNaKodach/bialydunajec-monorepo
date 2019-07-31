package org.bialydunajec.campbus.domain

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

        describe("Scenario: Free seat") {

            describe("Given seat for reservation is added for course") {

                val seatAddedForCourse = SeatEvent.SeatAddedForCourse(seatId, seat.aggregateVersion, fixedClock.instant(), campBusCourseId)

                describe("When reserve the seat for passenger") {

                    val reserveSeat = SeatCommand.ReserveSeat(seatId, seatAddedForCourse.aggregateVersion.increase(), passengerId)

                    it("Then the seat should be reserved for the passenger") {

                        val expectedEvent = SeatEvent.SeatReservedForPassenger(seatId, reserveSeat.aggregateVersion, fixedClock.instant(), campBusCourseId, passengerId)

                        givenAggregate { seat }
                                .withPriorEvent { seatAddedForCourse }
                                .whenCommand { reserveSeat }
                                .thenExpectEvent { expectedEvent }
                    }

                }

            }

        }


        describe("Scenario: Already reserved seat") {

            describe("Given seat for reservation is added for course") {

                val seatAddedForCourse = SeatEvent.SeatAddedForCourse(seatId, seat.aggregateVersion, fixedClock.instant(), campBusCourseId)

                describe("And it's already reserved") {

                    val seatReserved = SeatEvent.SeatReservedForPassenger(seatId, seatAddedForCourse.aggregateVersion, fixedClock.instant(), campBusCourseId, passengerId)

                    describe("When reserve the seat for passenger") {

                        val reserveSeat = SeatCommand.ReserveSeat(seatId, seatReserved.aggregateVersion.increase(), passengerId)

                        it("Then try should fail") {

                            givenAggregate { seat }
                                    .withPriorEvents(seatAddedForCourse, seatReserved)
                                    .whenCommand { reserveSeat }
                                    .thenExpectException()

                        }

                    }


                }

            }

        }

        describe("Scenario: Already occupied seat") {

            describe("Given seat for reservation is added for course") {

                val seatAddedForCourse = SeatEvent.SeatAddedForCourse(seatId, seat.aggregateVersion, fixedClock.instant(), campBusCourseId)

                describe("And it's already reserved") {

                    val seatReserved = SeatEvent.SeatReservedForPassenger(seatId, seatAddedForCourse.aggregateVersion, fixedClock.instant(), campBusCourseId, passengerId)

                    describe("And the reservation is confirmed") {

                        val reservationConfirmed = SeatEvent.SeatReservationConfirmed(seatId, seatReserved.aggregateVersion, fixedClock.instant(), campBusCourseId, passengerId)

                        describe("When reserve the seat for passenger") {

                            val reserveSeat = SeatCommand.ReserveSeat(seatId, seatReserved.aggregateVersion.increase(), passengerId)

                            it("Then try should fail") {

                                givenAggregate { seat }
                                        .withPriorEvents(seatAddedForCourse, seatReserved, reservationConfirmed)
                                        .whenCommand { reserveSeat }
                                        .thenExpectException()

                            }

                        }

                    }


                }

            }

        }

        describe("Scenario: Already removed seat") {

            describe("Given seat for reservation is added for course") {

                val seatAddedForCourse = SeatEvent.SeatAddedForCourse(seatId, seat.aggregateVersion, fixedClock.instant(), campBusCourseId)

                describe("And it's already removed") {

                    val seatRemoved = SeatEvent.SeatRemovedFromCourse(seatId, seatAddedForCourse.aggregateVersion, fixedClock.instant(), campBusCourseId, passengerId)

                    describe("When reserve the seat for passenger") {

                        val reserveSeat = SeatCommand.ReserveSeat(seatId, seatRemoved.aggregateVersion.increase(), passengerId)

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


    }

})

