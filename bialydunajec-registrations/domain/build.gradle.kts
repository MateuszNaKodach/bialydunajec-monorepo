import org.springframework.boot.gradle.tasks.bundling.BootJar

version = "0.0.2"

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Jar> {
    enabled = true
}


dependencies {
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-domain"))
    compile("org.springframework.boot:spring-boot-starter-data-jpa")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
