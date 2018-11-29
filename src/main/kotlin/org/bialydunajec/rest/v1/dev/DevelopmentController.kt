package org.bialydunajec.rest.v1.dev

import org.bialydunajec.configuration.profile.ProfileName
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile(value = [ProfileName.DEVELOPMENT_ENVIRONMENT, ProfileName.LOCAL_ENVIRONMENT])
@RequestMapping("/rest-api/v1/development")
@RestController
internal class DevelopmentController(
        private val dummyDatabaseInitializator: DummyDatabaseInitializator
) {

    @GetMapping("/db-init")
    fun initializeDatabaseWithDummyData() {
        //TODO: Cleanup db!
        dummyDatabaseInitializator.initialize()
    }

    @GetMapping("/register-dummy-campers")
    fun registerDummyCampers() {
        //TODO: Cleanup db!
        dummyDatabaseInitializator.registerDummyCampers()
    }


    @GetMapping("/db-cleanup")
    fun cleanupWholeDatabase() {

    }

}
