package org.bialydunajec.gradle

object Projects {

    object BialyDunajec {

        object DDD {

            const val DOMAIN = ":bialydunajec-ddd:bialydunajec-ddd-domain"
            const val APPLICATION = ":bialydunajec-ddd:bialydunajec-ddd-application"
            const val PRESENTATION = ":bialydunajec-ddd:bialydunajec-ddd-presentation"
            const val INFRASTRUCTURE = ":bialydunajec-ddd:bialydunajec-ddd-infrastructure"
        }

        object AcademicMinistry {

            const val DOMAIN = ":bialydunajec-academic-ministry:bialydunajec-academic-ministry-domain"
            const val APPLICATION = ":bialydunajec-academic-ministry:bialydunajec-academic-ministry-application"
            const val PRESENTATION = ":bialydunajec-academic-ministry:bialydunajec-academic-ministry-presentation"
            const val MESSAGES = ":bialydunajec-academic-ministry:bialydunajec-academic-ministry-messages"

        }

    }

}
