package org.bialydunajec.ddd.domain.sharedkernel.valueobject.notes

interface MutableWithNotes<T> {
    fun withNote(note: Note)
    fun getNote(): Note
}