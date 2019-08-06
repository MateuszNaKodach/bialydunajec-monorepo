package org.bialydunajec.gallery.infrastructure


internal class GooglePhotosClient {
/*
    init {
        val photosLibrarySettings = PhotosLibrarySettings.newBuilder()
                .setCredentialsProvider(
                        FixedCredentialsProvider.create(
                                UserCredentials.create()
                        )
                )
                .build()
    }


    @Throws(IOException::class, GeneralSecurityException::class)
    private fun getUserCredentials(credentialsPath: String, selectedScopes: List<String>): Credentials {
        val clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, InputStreamReader(FileInputStream(credentialsPath)))
        val clientId = clientSecrets.getDetails().getClientId()
        val clientSecret = clientSecrets.getDetails().getClientSecret()

        val flow = GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                selectedScopes)
                .setDataStoreFactory(FileDataStoreFactory(DATA_STORE_DIR))
                .setAccessType("offline")
                .build()
        val receiver = LocalServerReceiver.Builder().setPort(LOCAL_RECEIVER_PORT).build()
        val credential = AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
        return UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(credential.getRefreshToken())
                .build()
    }*/
}
