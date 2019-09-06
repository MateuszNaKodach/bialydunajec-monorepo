package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosConnectionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class GooglePhotosGalleryProviderConfiguration {
    @Bean
    fun googlePhotosGalleryProvider(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosGalleryProvider()

    @Bean
    fun googlePhotosConnectionService(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosConnectionService(googleProperties.credentialsJsonPath, googleProperties.refreshToken)
}
