package org.bialyduanjec.campschedule.domain

import org.axonframework.test.aggregate.AggregateTestFixture
import org.bialydunajec.campschedule.domain.CampEditionSchedule
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.CampEditionScheduleEvent
import org.bialydunajec.campschedule.domain.exception.CampEditionScheduleDomainRule
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate


const val campEditionId = 36

internal class CampEditionScheduleAggregateRootTest {

    private lateinit var fixture: AggregateTestFixture<CampEditionSchedule>

    @BeforeEach
    internal fun `reset aggregate text fixture`() {
        fixture = AggregateTestFixture(CampEditionSchedule::class.java)
    }


    @Test
    internal fun `starting camp edition scheduling`() {
        fixture.givenNoPriorActivity()
                .`when`(CampEditionScheduleCommand.StartCampEditionScheduling(CampEditionScheduleId(campEditionId)))
                .expectEvents(CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId)))
    }

    @Test
    internal fun `scheduling first day in camp edition should add new day`() {
        val firstDayDate = LocalDate.of(2019, 2, 20)
        val firstDayId = CampDayId()
        fixture.given(CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId)))
                .`when`(
                        CampEditionScheduleCommand.ScheduleNewCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = firstDayDate
                        )
                )
                .expectEvents(CampEditionScheduleEvent.NewCampDayScheduled(CampEditionScheduleId(campEditionId), firstDayId, firstDayDate))
    }

    @Test
    internal fun `scheduling the same date as before should not be allowed`() {
        val firstDayDate = LocalDate.of(2019, 2, 20)
        val firstDayId = CampDayId()
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId)),
                CampEditionScheduleEvent.NewCampDayScheduled(
                        campEditionScheduleId = CampEditionScheduleId(campEditionId),
                        campDayId = firstDayId,
                        date = firstDayDate
                )
        )
                .`when`(
                        CampEditionScheduleCommand.ScheduleNewCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = firstDayDate
                        )
                )
                .expectNoEvents()
                .expectExceptionMessage(CampEditionScheduleDomainRule.CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE.getDescription())
    }

}
