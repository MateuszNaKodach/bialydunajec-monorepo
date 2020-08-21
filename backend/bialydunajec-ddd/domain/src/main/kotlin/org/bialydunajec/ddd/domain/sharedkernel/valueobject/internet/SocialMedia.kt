package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet.*
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

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
