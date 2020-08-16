package org.bialydunajec.eventsourcing.domain

abstract class DomainRule(
        private val description: String,
        private val violationMessage: String?
) {

    companion object {
        private fun toDomainRuleName(text: String?) =
                text.toUpperSnakeCase()
    }

    fun getRuleName() = toDomainRuleName(this::class.simpleName)

    fun getDescription() = description

    fun getViolationMessage() = violationMessage ?: description
}

fun String?.toUpperSnakeCase() = this
        ?.foldIndexed("") { i, acc, c -> acc + if (i == 0) c else (if (c.isUpperCase()) "_$c" else c) }
        ?.toUpperCase()
        ?: "UNKNOWN"
