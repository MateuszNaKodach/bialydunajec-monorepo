package org.bialydunajec.news.infrastructure.facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Parameter
import com.restfb.Version
import com.restfb.types.Page
import com.restfb.types.Post
import org.bialydunajec.news.application.dto.CampNewsDto
import org.bialydunajec.news.application.dto.NewsPictureDto
import java.time.ZoneId

/*
https://developers.facebook.com/docs/graph-api/reference/v3.2/user/feed
 */
internal class FacebookTest {
}

fun main(args: Array<String>) {
    /*
    val facebookClient: FacebookClient = DefaultFacebookClient(FACEBOOK_ACCESS_TOKEN, Version.VERSION_3_2)
    val me = facebookClient.fetchObject("bialydunajec", Page::class.java)
    println(me)
    val feed = facebookClient.fetchConnection("bialydunajec/posts", Post::class.java, Parameter.with("fields", "id,message,picture,name,created_time,description"))
    println(feed)
    val result = feed.data.map { CampNewsDto(it.id, it.message, NewsPictureDto(it.picture, it.name, it.description), it.createdTime.toInstant()) }
        */
}