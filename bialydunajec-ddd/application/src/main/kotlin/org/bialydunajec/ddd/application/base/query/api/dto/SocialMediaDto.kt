package org.bialydunajec.ddd.application.base.query.api.dto

data class SocialMediaDto(
        val webPageUrl: String?,
        val facebookPageUrl: String?,
        val facebookGroupUrl: String?,
        val instagramUrl: String?,
        val youTubeChannelUrl: String?
)