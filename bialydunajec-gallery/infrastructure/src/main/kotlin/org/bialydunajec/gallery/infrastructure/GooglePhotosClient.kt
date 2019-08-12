package org.bialydunajec.gallery.infrastructure

import com.google.auth.Credentials
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.UserCredentials


internal class GooglePhotosClient {

    val credentials: Credentials = setCredentials()

    private fun setCredentials() : Credentials{
        val accessToken = AccessToken(ACCESS_TOKEN, null)

        return UserCredentials.newBuilder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setAccessToken(accessToken)
                .build()
    }

    companion object {
        private const val ACCESS_TOKEN: String = "ya29.GlxiB849Cdxeb8TvVZJQjAuDFVR5kTN-Z3_o6KGl1wx-jiooW_bU8vURLHFSiUKwjjZQfKknBALgPWdDiFmbXEyBilnEfiznPbPCJt7mDilu_Ejmpu9byMlG5Xv9fw"
        private const val CLIENT_ID: String = "449611120307-br5s5t3l6r329i6pk0tcr067jbs8f942.apps.googleusercontent.com"
        private const val CLIENT_SECRET: String = "u3oTMeQSDe0ZpprgPtUuKAv9"
    }

}
