package org.bialydunajec.email.readmodel.email

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
//EMAIL_WITH_GROUPS
@Document("emailReadModel")
data class Email(
    @Id
    val emailId: String,
    val address: String,
    var previousEmailAddressId: String? = null,
    var owner: Owner
) {
    data class Owner(val firstName: String, val lastName: String)
}
