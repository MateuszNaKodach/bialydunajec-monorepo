import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}


dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    compile(project(":bialydunajec-registrations:bialydunajec-registrations-domain"))
    compile(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-messages"))
    compile(project(":bialydunajec-academic-ministry:bialydunajec-academic-ministry-messages"))
    compile(project(":bialydunajec-registrations:bialydunajec-registrations-messages"))
    compile(project(":bialydunajec-registrations:bialydunajec-registrations-dto"))
    compile(project(":bialydunajec-email:bialydunajec-email-messages"))

    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-validation")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
