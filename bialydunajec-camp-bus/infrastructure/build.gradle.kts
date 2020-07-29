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
    api(project(":bialydunajec-camp-bus:bialydunajec-camp-bus-domain"))

    compile("org.springframework.boot:spring-boot-starter-data-mongodb")
    compile("com.fasterxml.jackson.core:jackson-annotations")
    compile("com.fasterxml.jackson.core:jackson-core")
    compile("com.fasterxml.jackson.core:jackson-databind")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.module:jackson-module-parameter-names")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.2")

    testCompile("org.springframework.boot:spring-boot-starter-test")

    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}
