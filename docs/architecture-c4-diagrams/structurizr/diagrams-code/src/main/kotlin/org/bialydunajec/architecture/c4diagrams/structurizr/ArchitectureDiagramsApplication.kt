package org.bialydunajec.architecture.c4diagrams.structurizr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//https://github.com/Catalysts/structurizr-extensions/tree/master/structurizr-spring-boot
@SpringBootApplication
open class ArchitectureDiagramsApplication

fun main(args: Array<String>) {
    runApplication<ArchitectureDiagramsApplication>(*args)
}

