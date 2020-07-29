import org.bialydunajec.gradle.Versions

import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
    "jar"(Jar::class){
        enabled = true
    }
}


dependencies {
    api(project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-messages"))
    api(project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-dto"))
    api(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-messages"))

    api(project(":bialydunajec-ddd:bialydunajec-ddd-presentation"))
    api(project(":bialydunajec-ddd:bialydunajec-ddd-domain"))
    api(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    api(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))
    //compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.axonframework:axon-spring-boot-starter:${Versions.axonFrameworkVersion}") {
        exclude(group = "org.axonframework", module = "axon-server-connector")
    }
    compile("org.springframework.boot:spring-boot-starter-mail")
    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-validation")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")

    //runtime("com.h2database:h2")
    //runtime("mysql:mysql-connector-java")
    testCompile("org.axonframework:axon-test:${Versions.axonFrameworkVersion}")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.jUnitVersion}")
    testCompile("org.junit.jupiter:junit-jupiter-engine:${Versions.jUnitVersion}")
    testCompile("io.projectreactor:reactor-test")
}
