import org.bialydunajec.gradle.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    //kotlin("kapt") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("plugin.jpa") version "1.3.50"
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    `maven-publish`
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    //apply(plugin = "kotlin-kapt")
    apply(plugin = "groovy")
    apply(plugin = "maven")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "maven-publish")

    group = "org.bialydunajec"
    java.sourceCompatibility = JavaVersion.VERSION_1_8

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.test {
        useJUnitPlatform {
            includeEngines("spek2","junit-jupiter")
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    val nexusReleaseUrl: String by project
    val nexusSnapshotUrl: String by project
    val nexusUser: String by project
    val nexusPassword: String by project


    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven {
            url = uri(nexusReleaseUrl)
            credentials {
                username = nexusUser
                password = nexusPassword
            }
        }
        maven {
            url = uri(nexusSnapshotUrl)
            credentials {
                username = nexusUser
                password = nexusPassword
            }
        }
        maven { url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/") }
        maven { url = uri("https://dl.bintray.com/spekframework/spek") }
    }

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compile("org.jetbrains.kotlin:kotlin-reflect")

        //JAVA 9 integration
        compile("javax.xml.bind:jaxb-api:${Versions.jaxbApiVersion}")
        compile("javax.activation:activation:1.1")
        compile("org.glassfish.jaxb:jaxb-runtime:2.3.0")

        //Kotlin Arrow
        compile("io.arrow-kt:arrow-core:${Versions.arrowVersion}")
        compile("io.arrow-kt:arrow-syntax:${Versions.arrowVersion}")
        compile("io.arrow-kt:arrow-typeclasses:${Versions.arrowVersion}")
        compile("io.arrow-kt:arrow-data:${Versions.arrowVersion}")
        compile("io.arrow-kt:arrow-instances-core:${Versions.arrowVersion}")
        compile("io.arrow-kt:arrow-instances-data:${Versions.arrowVersion}")
        //kapt("io.arrow-kt:arrow-annotations-processor:${Versions.arrowVersion}")
        //compile("io.arrow-kt:arrow-query-language:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-free:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-instances-free:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-mtl:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-instances:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-rx2:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-rx2-instances:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-reactor:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-reactor-instances:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-kotlinx-coroutines:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-effects-kotlinx-coroutines-instances:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-optics:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-generic:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-recursion:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-instances-recursion:${Versions.arrowVersion}") //optional
        //compile("io.arrow-kt:arrow-integration-retrofit-adapter:${Versions.arrowVersion}") //optional

        compile("com.github.nowakprojects:kt-time-traveler-core:${Versions.ktTimeTravelerVersion}")

        testCompile("org.jetbrains.kotlin:kotlin-test")
        testCompile("org.spockframework:spock-core:${Versions.spockVersion}")
        testCompile("org.spockframework:spock-spring:${Versions.spockVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.jUnitVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-params:${Versions.jUnitVersion}")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnitVersion}")
        testCompile("org.assertj:assertj-core:${Versions.assertjVersion}")
        testCompile("org.spekframework.spek2:spek-dsl-jvm:${Versions.spekVersion}")
        testRuntime("org.spekframework.spek2:spek-runner-junit5:${Versions.spekVersion}")
        testCompile("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertkVersion}")
        testCompile("com.github.nowakprojects:kt-time-traveler-test:${Versions.ktTimeTravelerVersion}")
        testCompile("com.tngtech.archunit:archunit-junit5-api:${Versions.archUnitVersion}")
        testRuntime("com.tngtech.archunit:archunit-junit5-engine:${Versions.archUnitVersion}")
        testImplementation("io.mockk:mockk:${Versions.mockkVersion}")
    }

}

version = "0.0.2"

dependencies {
    api(project(":bialydunajec-news:bialydunajec-news-presentation"))
    api(project(":bialydunajec-news:bialydunajec-news-infrastructure"))

    api(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-presentation"))
    api(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-infrastructure"))

    api(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-presentation"))
    api(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-infrastructure"))

    api(project(":bialydunajec-registrations:bialydunajec-registrations-presentation"))
    api(project(":bialydunajec-registrations:bialydunajec-registrations-infrastructure"))
    api(project(":bialydunajec-registrations:bialydunajec-registrations-read-model"))

    api(project(":bialydunajec-users:bialydunajec-users-presentation"))
    api(project(":bialydunajec-users:bialydunajec-users-infrastructure"))

    api(project(":bialydunajec-email:bialydunajec-email-presentation"))
    api(project(":bialydunajec-email:bialydunajec-email-infrastructure"))
    api(project(":bialydunajec-email:bialydunajec-email-read-model"))

    api(project(":bialydunajec-authorization:bialydunajec-authorization-server"))

    //compile project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-presentation")
    //compile project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-infrastructure")
    //compile project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-read-model")

    //compile project(":bialydunajec-faq:bialydunajec-faq-presentation")
    //compile project(":bialydunajec-faq:bialydunajec-faq-infrastructure")

    compile(project(":bialydunajec-gallery:bialydunajec-gallery-presentation"))
    compile(project(":bialydunajec-gallery:bialydunajec-gallery-infrastructure"))

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-hateoas")
    compile("org.springframework.boot:spring-boot-starter-mail")
    compile("org.springframework.boot:spring-boot-starter-quartz")
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.github.ulisesbocchio:jasypt-spring-boot-starter:${Versions.jasyptVersion}")


    //Swagger 2 - REST Api documentation
    compile("io.springfox:springfox-swagger2:${Versions.swaggerVersion}")
    compile("io.springfox:springfox-swagger-ui:${Versions.swaggerVersion}")

    //Fake data generator
    compile("com.devskiller:jfairy:${Versions.jfairyVersion}")

    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java:${Versions.mysqlConnectorVersion}")
    runtime("org.postgresql:postgresql:${Versions.postgresqlConnectorVersion}")


    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.projectreactor:reactor-test")

    if (!project.hasProperty("release")) {
        runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    }
}
