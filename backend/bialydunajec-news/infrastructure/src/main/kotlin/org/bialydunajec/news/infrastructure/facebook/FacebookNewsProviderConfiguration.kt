package org.bialydunajec.news.infrastructure.facebook

import com.restfb.DefaultFacebookClient
import com.restfb.Version
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class FacebookNewsProviderConfiguration {

    @Bean
    fun facebookNewsProvider(facebookProperties: BialyDunajecFacebookProperties)
            = FacebookNewsProvider(DefaultFacebookClient(facebookProperties.accessToken, Version.VERSION_3_2))

}