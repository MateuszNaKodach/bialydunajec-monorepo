package org.bialydunajec.registrations.readmodel

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

//TODO: Nasłuchwianie na messages!!! Uzywac Instant, jesli nie potrzebne nam dane o tym kiedy cos sie stalo w danej strefie
internal class ReadModelUpdater {
}

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"))
    System.setProperty("user.timezone", "Europe/Warsaw")


    println(Instant.now())
    println(ZonedDateTime.now())
    println(ZonedDateTime.now().toInstant())
    println(Instant.now().atZone(ZoneId.systemDefault()))
}