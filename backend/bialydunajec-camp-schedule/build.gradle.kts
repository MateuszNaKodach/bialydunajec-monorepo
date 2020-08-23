import org.bialydunajec.gradle.Versions

version = "0.0.2"

dependencies {
    api(project(":bialydunajec-camp-schedule:bialydunajec-camp-schedule-ddd-axon"))

    compile("org.springframework.boot:spring-boot-starter-web")

    compile("org.axonframework:axon-spring-boot-starter:${Versions.axonFrameworkVersion}") {
        exclude(group = "org.axonframework", module = "axon-server-connector")
    }

    compile("org.axonframework.extensions.mongo:axon-mongo:4.1.1")
    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    compile("org.springframework.boot:spring-boot-starter-data-mongodb")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")

    //compile("org.springframework.boot:spring-boot-starter-data-jpa")
    //runtime("com.h2database:h2")

    compileOnly("org.springframework.boot:spring-boot-configuration-processor")


    testCompile("org.springframework.boot:spring-boot-starter-test")

}
