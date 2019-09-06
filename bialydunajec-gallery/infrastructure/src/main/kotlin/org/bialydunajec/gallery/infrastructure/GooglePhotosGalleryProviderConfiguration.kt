package org.bialydunajec.gallery.infrastructure

import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosConnectionService
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosCredentialHandler
import org.bialydunajec.gallery.infrastructure.utils.GooglePhotosTokenHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class GooglePhotosGalleryProviderConfiguration {
    @Bean
    fun googlePhotosGalleryProvider(googleProperties: BialyDunajecGoogleProperties)
            = GooglePhotosGalleryProvider(
            GooglePhotosConnectionService(
                    GooglePhotosTokenHandler(googleProperties.refreshToken),
                    GooglePhotosCredentialHandler(googleProperties.credentialsJsonPath, googleProperties.refreshToken)
    ))
}
