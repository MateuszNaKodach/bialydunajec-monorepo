package org.bialydunajec.gallery.infrastructure.utils.pojo

import com.google.gson.annotations.SerializedName

class Web {
    @SerializedName("client_id")
    var clientId: String? = null
    @SerializedName("project_id")
    var projectId: String? = null
    @SerializedName("auth_uri")
    var authUri: String? = null
    @SerializedName("token_uri")
    var tokenUri: String? = null
    @SerializedName("auth_provider_x509_cert_url")
    var authProviderX509CertUrl: String? = null
    @SerializedName("client_secret")
    var clientSecret: String? = null
    @SerializedName("redirect_uris")
    var redirectUris: List<String>? = null
}
