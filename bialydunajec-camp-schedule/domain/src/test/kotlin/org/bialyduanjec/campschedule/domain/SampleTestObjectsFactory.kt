package org.bialyduanjec.campschedule.domain

import org.bialydunajec.campschedule.domain.valueobject.DayActivityDetails
import java.time.LocalTime

internal object SampleTestObjectsFactory {

    fun getSampleDayActivityDetails() = DayActivityDetails(
            "Przykładowy punkt dnia",
            LocalTime.now(),
            LocalTime.now().plusHours(1).plusMinutes(15),
            "Przykładowy opis punktu dnia"
    )
}
