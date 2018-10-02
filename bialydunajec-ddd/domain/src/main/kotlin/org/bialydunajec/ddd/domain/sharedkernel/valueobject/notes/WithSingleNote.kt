package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

interface WithSingleNote<T> {
    fun withNote(note: Note): T
    fun getNote(): Note
}