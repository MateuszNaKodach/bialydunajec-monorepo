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
    compile(project(":bialydunajec-gallery:bialydunajec-gallery-domain"))
    compile(project(":bialydunajec-gallery:bialydunajec-gallery-messages"))

    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-validation")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
