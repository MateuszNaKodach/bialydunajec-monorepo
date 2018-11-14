package org.bialydunajec.configuration.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.Executor


@EnableAsync
@Configuration
internal class SpringAsyncConfiguration : AsyncConfigurer {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun getAsyncExecutor(): Executor {
        return taskExecutor()
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler =
            AsyncUncaughtExceptionHandler { ex, method, params ->
                log.error("Async excpetion occured in method {} with params {}: {}", method, params, ex)
            }

    @Bean
    fun taskExecutor(): Executor {
        return SimpleAsyncTaskExecutor()
    }
}