package org.bialydunajec.news.infrastructure.facebook

import com.restfb.DefaultFacebookClient
import com.restfb.FacebookClient
import com.restfb.Version
import com.restfb.types.Page
import com.restfb.types.Post

/*
https://developers.facebook.com/docs/graph-api/reference/v3.2/user/feed
 */
internal class FacebookTest {
}

const val FACEBOOK_ACCESS_TOKEN = "EAAD8VBKY89IBAJb1wj1Gp7KF9W63TRQpyUEGXujrFIEIb9GLEPmpTnrFAZBYwIpqEZC86kn3MZAAUZCzLAYxElf1258HYAkr8iJpyV6QZCJ6goGsPSvLKfUmSy4KPPAnmIz39U8zJahFp5HS8pqHPccVsgsdzBAH5KzVNXfXptJN5FY0J0gMUwN8opSKFtXmxrjBsOl98wAZDZD"

fun main(args: Array<String>) {
    val facebookClient: FacebookClient = DefaultFacebookClient(FACEBOOK_ACCESS_TOKEN, Version.VERSION_3_1)
    val me = facebookClient.fetchObject("bialydunajec", Page::class.java)
    println(me)
    val feed = facebookClient.fetchConnection("bialydunajec/feed", Post::class.java)
    println(feed)
}