package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class GooglePhotosGalleryProviderConfiguration {
    @Bean
    fun googlePhotosGalleryProvider(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosGalleryProvider()

    @Bean
    fun googlePhotosCredentialService(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosCredentialService(googleProperties.refreshToken, googleProperties.credentialsJsonPath)
}
