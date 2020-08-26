package org.bialydunajec.campbus.presentation.rest.v1

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.router
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.util.concurrent.Executor

//TODO: Use Spring WebFlux Kotlin DSL
internal class SeatReservationRoutes {

    @Bean
    fun restRouter() = router {
        "/rest-api/v1/seat".nest {
            //POST("") { serverRequest ->  "Response".toMono() }
        }
    }


}



@EnableAsync
@EnableScheduling
@Configuration
internal class SpringAsyncConfiguration : AsyncConfigurer {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Bean(name = ["taskExecutor","asyncExecutor"])
    override fun getAsyncExecutor(): Executor =
            ThreadPoolTaskExecutor().apply {
                setCorePoolSize(7)
                setMaxPoolSize(42)
                setQueueCapacity(11)
                setThreadNamePrefix("threadPoolExecutor-")
                initialize()
            }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler =
            AsyncUncaughtExceptionHandler { ex, method, params ->
                log.error("Async excpetion occured in method {} with params {}: {}", method, params, ex)
            }

    @Bean
    fun taskExecutor(): Executor {
        return asyncExecutor
    }
}

@Configuration
class WebConfig: WebFluxConfigurer
{
    override fun addCorsMappings(registry: CorsRegistry)
    {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*") // any host or put domain(s) here
                .allowedMethods("POST, GET, PUT, OPTIONS, DELETE, PATCH") // put the http verbs you want allow
                .allowedHeaders("x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN") // put the http headers you want allow
    }
}
