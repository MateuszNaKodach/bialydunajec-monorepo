package org.bialydunajec.registrations.readmodel

import java.time.*
import java.util.*

//TODO: Nasłuchwianie na messages!!! Uzywac Instant, jesli nie potrzebne nam dane o tym kiedy cos sie stalo w danej strefie. Wszedzie UTC
//Serwer mam w 1 kraju, to na razie moze byc Polska strefa.
internal class ReadModelUpdater {
}

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"))
    System.setProperty("user.timezone", "Europe/Warsaw")

    println(Instant.now())
    println(ZonedDateTime.now())
    println(ZonedDateTime.now().toInstant())
    println(Instant.now().atZone(ZoneId.systemDefault()))
    println(LocalDateTime.now())
}

/*
Zobaczycc przyklady, zoneId zapisywanie osobno:
https://www.callicoder.com/spring-boot-quartz-scheduler-email-scheduling-example/

https://www.opencodez.com/java/quartz-scheduler-with-spring-boot.htm
 */