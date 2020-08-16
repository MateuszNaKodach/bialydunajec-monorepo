import org.bialydunajec.gradle.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.spring") version "1.3.70"
    kotlin("plugin.jpa") version "1.3.70"
    id("org.springframework.boot") version "2.3.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
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
            includeEngines("spek2", "junit-jupiter")
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://dl.bintray.com/arrow-kt/arrow-kt/") }
        maven { url = uri("https://dl.bintray.com/spekframework/spek") }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        //JAVA 9 integration
        implementation("javax.xml.bind:jaxb-api:${Versions.jaxbApiVersion}")
        implementation("javax.activation:activation:1.1")
        implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0")

        implementation("org.springframework.boot:spring-boot-starter-validation")

        //Kotlin Arrow
        implementation("io.arrow-kt:arrow-core:${Versions.arrowVersion}")
        implementation("io.arrow-kt:arrow-syntax:${Versions.arrowVersion}")
        implementation("io.arrow-kt:arrow-typeclasses:${Versions.arrowVersion}")
        implementation("io.arrow-kt:arrow-data:${Versions.arrowVersion}")
        implementation("io.arrow-kt:arrow-instances-core:${Versions.arrowVersion}")
        implementation("io.arrow-kt:arrow-instances-data:${Versions.arrowVersion}")
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

        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.spockframework:spock-core:${Versions.spockVersion}")
        testImplementation("org.spockframework:spock-spring:${Versions.spockVersion}")
        testImplementation("org.codehaus.groovy:groovy:${Versions.groovyVersion}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.jUnitVersion}")
        testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.jUnitVersion}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnitVersion}")
        testImplementation("org.assertj:assertj-core:${Versions.assertjVersion}")
        testImplementation("org.spekframework.spek2:spek-dsl-jvm:${Versions.spekVersion}")
        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:${Versions.spekVersion}")
        testImplementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertkVersion}")
        testImplementation("com.tngtech.archunit:archunit-junit5-api:${Versions.archUnitVersion}")
        testRuntimeOnly("com.tngtech.archunit:archunit-junit5-engine:${Versions.archUnitVersion}")
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

    api(project(":bialydunajec-gallery:bialydunajec-gallery-presentation"))
    api(project(":bialydunajec-gallery:bialydunajec-gallery-infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:${Versions.jasyptVersion}")
    implementation("io.springfox:springfox-boot-starter:${Versions.swaggerVersion}")

    //Fake data generator
    implementation("com.devskiller:jfairy:${Versions.jfairyVersion}")

    if (!project.hasProperty("release")) {
        runtimeOnly("com.h2database:h2")
    }
    runtimeOnly("mysql:mysql-connector-java:${Versions.mysqlConnectorVersion}")
    runtimeOnly("org.postgresql:postgresql:${Versions.postgresqlConnectorVersion}")


    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

}
