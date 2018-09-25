package org.bialydunajec.ddd.domain.persistence

interface Archivable {
    fun delete()
    fun getLastDeletionDate()
    fun restore()
}