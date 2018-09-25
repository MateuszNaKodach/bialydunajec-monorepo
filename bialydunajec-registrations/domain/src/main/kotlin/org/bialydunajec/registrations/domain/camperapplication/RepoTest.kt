package org.bialydunajec.registrations.domain.camperapplication

import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class RepoTest(val camperApplicationRepository: CamperApplicationRepository) {

    @PostConstruct
    fun postContruct(){
        this.camperApplicationRepository.save(CamperApplication("Camper1"))
        this.camperApplicationRepository.save(CamperApplication("Camper2"))
    }
}