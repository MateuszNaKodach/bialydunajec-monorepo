package org.bialydunajec.ddd.infrastructure.base.configuration

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
internal class CachingConfiguration {

    fun cacheManager(): CacheManager =
            ConcurrentMapCacheManager()
}