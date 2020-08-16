package org.bialydunajec.email.presentation.rest.v1.admin.request

internal class CatagolizeEmailRequest(
    val emailAddress: String,
    val owner: EmailOwner,
    val emailGroupId: String?
) {
    data class EmailOwner(val firstName: String, val lastName: String)
}
