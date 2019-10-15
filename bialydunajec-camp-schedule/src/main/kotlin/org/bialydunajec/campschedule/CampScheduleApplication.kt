package org.bialydunajec.campschedule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CampScheduleApplication

fun main(args: Array<String>) {
    runApplication<CampScheduleApplication>(*args)
}
