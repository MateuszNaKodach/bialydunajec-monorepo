package org.bialydunajec.campbus.presentation.rest.v1

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.router

//TODO: Use Spring WebFlux Kotlin DSL
internal class SeatReservationRoutes {

    @Bean
    fun restRouter() = router {
        "/rest-api/v1/seat".nest {
            //POST("") { serverRequest ->  "Response".toMono() }
        }
    }


}