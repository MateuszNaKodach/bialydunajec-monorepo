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
    api(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    api(project(":bialydunajec-registrations:bialydunajec-registrations-messages"))

    compile("org.springframework.boot:spring-boot-starter-data-mongodb")
    testCompile("org.springframework.boot:spring-boot-starter-test")

    compile("org.springframework.boot:spring-boot-starter-web")
}
