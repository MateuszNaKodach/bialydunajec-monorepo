package org.bialydunajec.news.infrastructure.facebook

import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.types.Post
import org.bialydunajec.news.application.CampNewsProvider
import org.bialydunajec.news.application.dto.*

class FacebookNewsProvider(private val facebookClient: FacebookClient) : CampNewsProvider {

    override fun getCampNewsPage(limit: Int?, after: String?): CampNewsPageDto {
        val feed = facebookClient.fetchConnection(
                "bialydunajec/posts",
                Post::class.java,
                Parameter.with("fields", "id,message,full_picture,name,created_time,description")
        )
        val content = feed.data.map { CampNewsDto(it.id, it.message, NewsPictureDto(it.fullPicture, it.name, it.description), it.createdTime.toInstant()) }
        return CampNewsPageDto(content, NewsPagingDto(PagingCursorsDto(feed.beforeCursor, feed.afterCursor)))
    }
}