package org.bialydunajec.architecture.c4diagrams.structurizr

import cc.catalysts.structurizr.ViewProvider
import cc.catalysts.structurizr.kotlin.addComponent
import cc.catalysts.structurizr.kotlin.addContainer
import cc.catalysts.structurizr.kotlin.addSoftwareSystem
import com.structurizr.Workspace
import com.structurizr.model.Model
import com.structurizr.model.Tags
import com.structurizr.view.Shape
import com.structurizr.view.ViewSet
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

//https://github.com/Catalysts/structurizr-extensions/tree/master/structurizr-spring-boot
@SpringBootApplication
class ArchitectureDiagramsApplication

fun main(args: Array<String>) {
    runApplication<ArchitectureDiagramsApplication>(*args)
}

@Component
class ViewConfigurer(workspace: Workspace) : ViewProvider {

    init {
        with(workspace.views.configuration.styles) {
            addElementStyle(Tags.PERSON).shape(Shape.Person)
            addElementStyle(MyTags.Database).shape(Shape.Cylinder)
            addElementStyle(MyTags.Frontend).shape(Shape.WebBrowser)
            addElementStyle(MyTags.MobileApp).shape(Shape.MobileDeviceLandscape)
        }
    }

    override fun createViews(viewSet: ViewSet) {
        viewSet.createSystemLandscapeView("shop", "my cool architecture")
                .addAllElements()
    }

}

@Component
class Personas(model: Model) {
    val customer = model.addPerson("Customer", "brings money")
    val sysAdmin = model.addPerson("SysAdmin", "hero of developers")
}

@Component
class Webshop(model: Model, personas: Personas, sap:Sap):ViewProvider {

    val webShop = model.addSoftwareSystem("Webshop", "") {
        usedBy(personas.customer, "buys something")
    }

    init {
        val database = webShop.addContainer("Database", "", "Postgres") {
            withTags(MyTags.Database)
        }

        val backend = webShop.addContainer("Backend", "", "Spring Boot, Embedded Tomcat") {
            uses(sap.sap, "fetch prices", "SOAP")
        }

        personas.sysAdmin.uses(database, "performs backups", "some shell tool")

        with(backend) {
            val api = addComponent("API", "", "Swagger")
            val serviceLayer = addComponent("Service Layer", "", "Spring") {
                usedBy(api, "", "")
            }
            addComponent("Data Access Layer", "", "Hibernate") {
                uses(database, "", "SQL")
            }
        }

        val frontend = webShop.addContainer("Frontend", "", "Angular") {
            usedBy(personas.customer, "purchases data")
            uses(backend, "")
            withTags(MyTags.Frontend)
        }
        val mobileApp = webShop.addContainer("Mobile App", "", "Android") {
            usedBy(personas.customer, "purchases data")
            uses(backend, "")
            withTags(MyTags.MobileApp)
        }
    }
    override fun createViews(viewSet: ViewSet) {
        viewSet.createContainerView(webShop, "webshop-container", "").also {
            it.addAllContainers()
            it.addAllInfluencers()
        }
    }
}


@Component
class Sap(model: Model) {
    val sap = model.addSoftwareSystem("SAP", "")
}

object MyTags {
    const val Frontend: String = "Frontend"
    const val MobileApp: String = "MobileApp"
    const val Database: String = "Database"
}