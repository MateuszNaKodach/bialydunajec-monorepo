package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Embeddable
data class ExtendedDescription(
        @NotEmpty
        @Column(name = "description_title")
        val title: String?,

        @Lob
        @NotEmpty
        @Column(name = "description_content")
        val content: String?
): ValueObject