package org.bialydunajec.ddd.domain.persistence

interface Versioned {
    fun getVersion(): Long
}