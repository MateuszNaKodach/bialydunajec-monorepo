package org.bialydunajec.gradle

object Dependencies {

    object Database {

        object H2 {

            const val DATABASE_H2 = "com.h2database:h2"

        }

        object MySQL {

            const val MYSQL_CONNECTOR_JAVA = "mysql:mysql-connector-java"

        }

    }

    object Jackson {

        const val JACKSON_MODULE_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"

    }

    object Spring {

        object Boot {

            const val SPRING_BOOT_STARTER_CACHE = "org.springframework.boot:spring-boot-starter-cache"
            const val SPRING_BOOT_STARTER_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
            const val SPRING_BOOT_STARTER_WEB_FLUX = "org.springframework.boot:spring-boot-starter-webflux"
            const val SPRING_BOOT_STARTER_VALIDATION = "org.springframework.boot:spring-boot-starter-validation"

        }

    }

}
