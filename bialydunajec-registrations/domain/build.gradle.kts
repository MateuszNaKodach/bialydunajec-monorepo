import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
}


dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-domain"))
    compile("org.springframework.boot:spring-boot-starter-data-jpa")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}