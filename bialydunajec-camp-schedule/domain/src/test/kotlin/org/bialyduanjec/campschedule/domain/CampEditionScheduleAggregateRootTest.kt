package org.bialyduanjec.campschedule.domain

import org.axonframework.test.aggregate.AggregateTestFixture
import org.bialydunajec.campschedule.domain.CampEditionSchedule
import org.bialydunajec.campschedule.domain.CampEditionScheduleCommand
import org.bialydunajec.campschedule.domain.CampEditionScheduleEvent
import org.bialydunajec.campschedule.domain.exception.CampEditionScheduleDomainRule
import org.bialydunajec.campschedule.domain.valueobject.CampDayId
import org.bialydunajec.campschedule.domain.valueobject.CampEditionScheduleId
import org.bialydunajec.campschedule.domain.valueobject.DayActivityId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate


private const val campEditionId = 36
private const val CAMP_EDITION_DURATION = 13L

internal class CampEditionScheduleAggregateRootTest {

    private lateinit var fixture: AggregateTestFixture<CampEditionSchedule>

    private val campFirstDayDate = LocalDate.of(2019, 2, 20)
    private val firstDayId = CampDayId()
    private val campEditionScheduleId = CampEditionScheduleId(campEditionId)

    @BeforeEach
    internal fun `reset aggregate text fixture`() {
        fixture = AggregateTestFixture(CampEditionSchedule::class.java)
    }


    @Test
    internal fun `starting camp edition scheduling`() {
        fixture.givenNoPriorActivity()
                .`when`(CampEditionScheduleCommand.StartCampEditionScheduling(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)))
                .expectEvents(CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)))
    }

    @Test
    internal fun `scheduling first day in camp edition should add new day`() {
        fixture.given(CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)))
                .`when`(
                        CampEditionScheduleCommand.ScheduleCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = campFirstDayDate
                        )
                )
                .expectEvents(
                        CampEditionScheduleEvent.CampDayScheduled(
                                CampEditionScheduleId(campEditionId),
                                firstDayId,
                                campFirstDayDate)
                )
    }

    @Test
    internal fun `scheduling the same date as before should not be allowed`() {
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)),
                CampEditionScheduleEvent.CampDayScheduled(
                        campEditionScheduleId = CampEditionScheduleId(campEditionId),
                        campDayId = firstDayId,
                        date = campFirstDayDate
                )
        )
                .`when`(
                        CampEditionScheduleCommand.ScheduleCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = campFirstDayDate
                        )
                )
                .expectNoEvents()
                .expectExceptionMessage(CampEditionScheduleDomainRule.CAMP_DAY_DATE_CAN_NOT_BE_DUPLICATED_IN_ONE_SCHEDULE.getDescription())
    }

    @Test
    internal fun `scheduling day before camp start should throw exception`() {
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)))
                .`when`(
                        CampEditionScheduleCommand.ScheduleCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = campFirstDayDate.minusDays(1L)
                        )
                ).expectNoEvents()
                .expectExceptionMessage(CampEditionScheduleDomainRule.CAMP_DAY_CAN_NOT_BE_SCHEDULE_BEFORE_CAMP_EDITION_START.getDescription())
    }

    @Test
    internal fun `scheduling day after camp finish should throw exception`() {
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(CampEditionScheduleId(campEditionId), campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)))
                .`when`(
                        CampEditionScheduleCommand.ScheduleCampDay(
                                campEditionScheduleId = CampEditionScheduleId(campEditionId),
                                campDayId = firstDayId,
                                date = campFirstDayDate.plusDays(CAMP_EDITION_DURATION).plusDays(1)
                        )
                ).expectNoEvents()
                .expectExceptionMessage(CampEditionScheduleDomainRule.CAMP_DAY_CAN_NOT_BE_SCHEDULE_AFTER_CAMP_EDITION_END.getDescription())
    }

    @Test
    internal fun `scheduling day activity`() {
        val dayActivityId = DayActivityId()
        val dayActivityDetails = SampleTestObjectsFactory.getSampleDayActivityDetails()
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(campEditionScheduleId, campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)),
                CampEditionScheduleEvent.CampDayScheduled(campEditionScheduleId, firstDayId, campFirstDayDate)
        ).`when`(
                CampEditionScheduleCommand.ScheduleCampDayActivity(
                        campEditionScheduleId,
                        firstDayId,
                        dayActivityId,
                        dayActivityDetails
                )
        ).expectEvents(
                CampEditionScheduleEvent.CampDayActivityScheduled(
                        campEditionScheduleId,
                        firstDayId,
                        dayActivityId,
                        dayActivityDetails
                )
        )
    }

    @Test
    internal fun `cancelled day and planned after should contain previous scheduled activities and new id`() {
        val dayActivityId = DayActivityId()
        val dayActivityDetails = SampleTestObjectsFactory.getSampleDayActivityDetails()
        val newCampDayId = CampDayId()
        fixture.given(
                CampEditionScheduleEvent.CampEditionSchedulingStarted(campEditionScheduleId, campFirstDayDate, campFirstDayDate.plusDays(CAMP_EDITION_DURATION)),
                CampEditionScheduleEvent.CampDayScheduled(campEditionScheduleId, firstDayId, campFirstDayDate),
                CampEditionScheduleEvent.CampDayActivityScheduled(
                        campEditionScheduleId,
                        firstDayId,
                        dayActivityId,
                        dayActivityDetails
                ),
                CampEditionScheduleEvent.CampDayCancelled(campEditionScheduleId, firstDayId, campFirstDayDate)
        ).`when`(
                CampEditionScheduleCommand.ScheduleCampDay(
                        campEditionScheduleId,
                        newCampDayId,
                        campFirstDayDate
                )
        ).expectEvents(
                CampEditionScheduleEvent.CampDayScheduled(
                        CampEditionScheduleId(campEditionId),
                        newCampDayId,
                        campFirstDayDate
                )
        )
    }

}
