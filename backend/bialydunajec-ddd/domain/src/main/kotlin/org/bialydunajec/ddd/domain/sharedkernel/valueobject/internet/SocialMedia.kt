package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class SocialMedia(
        @Embedded
        val webPage: WebPage? = null,
        @Embedded
        val facebookPage: FacebookPage? = null,
        @Embedded
        val facebookGroup: FacebookGroup? = null,
        @Embedded
        val instagram: Instagram? = null,
        @Embedded
        val youTubeChannel: YouTubeChannel? = null
) : ValueObject
