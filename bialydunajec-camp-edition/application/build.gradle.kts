import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
}


dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    compile(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-domain"))
    compile(project(":bialydunajec-camp-edition:bialydunajec-camp-edition-messages"))

    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-validation")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
