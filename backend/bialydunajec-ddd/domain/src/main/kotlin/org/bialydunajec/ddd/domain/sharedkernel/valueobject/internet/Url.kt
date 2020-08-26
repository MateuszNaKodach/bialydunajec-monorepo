package org.bialydunajec.ddd.domain.sharedkernel.valueobject.internet

import org.bialydunajec.ddd.domain.sharedkernel.valueobject.ValueObject
import org.hibernate.validator.constraints.URL
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Embeddable
open class Url private constructor(
        @Lob
        @URL
        @NotBlank
        private val url: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        private val pathType: PathType
) : ValueObject {
    @Embeddable
    class InternalUrl(url: String) : Url(url, PathType.RELATIVE_INTERNAL)

    @Embeddable
    class ExternalUrl(url: String) : Url(url, PathType.ABSOLUTE_EXTERNAL)

    override fun toString() = url
}

enum class PathType {
    ABSOLUTE_EXTERNAL,
    RELATIVE_INTERNAL;
}
