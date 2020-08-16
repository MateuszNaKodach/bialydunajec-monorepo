package org.bialydunajec.users.presentation.v1.admin.request

internal data class ChangePasswordRequest(
        val oldPassword: String,
        val newPassword: String,
        val newPasswordRepeated: String
)