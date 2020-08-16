package org.bialydunajec.ddd.domain.base.persistence

interface Versioned {
    fun getVersion(): Long?
}