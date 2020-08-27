package org.bialydunajec.rest.v1.dev

import org.bialydunajec.configuration.profile.ProfileName
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Profile(value = [ProfileName.DEVELOPMENT_ENVIRONMENT, ProfileName.LOCAL_ENVIRONMENT, "env_local_dependencies_docker", "env_local_inside_docker"])
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
    fun registerDummyCampers(@RequestParam maxPerCottage: Int = 8) {
        //TODO: Cleanup db!
        dummyDatabaseInitializator.registerDummyCampers(maxPerCottage)
    }

    @GetMapping("/register-dummy-camper")
    fun registerDummyCamper(@RequestParam count: Int = 1, @RequestParam cottage: String = "Redemptor") {
        //TODO: Cleanup db!
        dummyDatabaseInitializator.registerDummyCamper(count, cottage)
    }

    @GetMapping("/db-cleanup")
    fun cleanupWholeDatabase() {

    }

}
