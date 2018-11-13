package org.bialydunajec.configuration.profile

class ProfileName {
    companion object{
        const val DEVELOPMENT_ENVIRONMENT = "env_dev"
        const val PRODUCTION_ENVIRONMENT = "env_prod"
    }
}

class SubProfileName {
    companion object{
        const val DATABASE_H2 = "database_h2"
    }
}
