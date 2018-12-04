package org.bialydunajec.configuration.time

import org.springframework.context.annotation.Configuration
import java.util.*
import javax.annotation.PostConstruct

@Configuration
internal class TimeZoneConfiguration {

    @PostConstruct
    fun setUpTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"))
        System.setProperty("user.timezone", "Europe/Warsaw")
    }
}