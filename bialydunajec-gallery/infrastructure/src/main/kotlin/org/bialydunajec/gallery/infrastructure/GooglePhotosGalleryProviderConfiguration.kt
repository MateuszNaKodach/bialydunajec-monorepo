package org.bialydunajec.gallery.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class GooglePhotosGalleryProviderConfiguration {
    @Bean
    fun googlePhotosGalleryProvider(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosGalleryProvider(googleProperties.refreshToken, googleProperties.credentialsJsonPath)
}
