package org.bialydunajec.news.infrastructure.facebook

import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.types.Post
import org.bialydunajec.news.application.CampNewsProvider
import org.bialydunajec.news.application.dto.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled

const val FACEBOOK_NEWS_SPRING_CACHE = "org.bialydunajec.news.FACEBOOK_NEWS_SPRING_CACHE"

open class FacebookNewsProvider(private val facebookClient: FacebookClient) : CampNewsProvider {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheNames = [FACEBOOK_NEWS_SPRING_CACHE], key = "{#root.methodName}")
    override fun getCampNewsPage(limit: Int?, after: String?): CampNewsPageDto {
        log.info("Downloading facebook posts...")
        val feed = facebookClient.fetchConnection(
                "bialydunajec/posts",
                Post::class.java,
                Parameter.with("fields", "id,message,full_picture,name,created_time,description")
        )
        log.info("Facebook posts downloaded...")
        val content = feed.data.map { CampNewsDto(it.id, it.message, NewsPictureDto(it.fullPicture, it.name, it.description), it.createdTime.toInstant()) }
        return CampNewsPageDto(content, NewsPagingDto(PagingCursorsDto(feed.beforeCursor, feed.afterCursor)))
    }

    @CacheEvict(cacheNames = [FACEBOOK_NEWS_SPRING_CACHE], allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    open fun cacheEvict(){
        log.info("Facebook news cache evicted!")
    }
}
