package org.bialydunajec.news.infrastructure.facebook

import com.restfb.DefaultFacebookClient
import com.restfb.Version
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["bialydunajec.camp-news-provider"], havingValue = "facebook", matchIfMissing = true)
internal class FacebookNewsProviderConfiguration {

    @Bean
    fun facebookNewsProvider(facebookProperties: BialyDunajecFacebookProperties)
            = FacebookNewsProvider(DefaultFacebookClient(facebookProperties.accessToken, Version.VERSION_3_2))

}