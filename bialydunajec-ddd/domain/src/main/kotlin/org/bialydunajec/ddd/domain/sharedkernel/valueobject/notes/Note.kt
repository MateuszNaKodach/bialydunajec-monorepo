package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

import org.bialydunajec.ddd.domain.base.valueobject.ValueObject
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob
import javax.validation.constraints.NotBlank

@Embeddable
data class Note(
        @Column(name = "note_title")
        val title: String?,

        @Lob
        @NotBlank
        @Column(name = "note_content")
        val content: String
) : ValueObject