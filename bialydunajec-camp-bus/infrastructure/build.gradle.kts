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

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/nowakprojects/kttimetraveler")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
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

    testCompile("com.github.nowakprojects:kt-time-traveler-test:${org.bialydunajec.gradle.Versions.ktTimeTravelerVersion}")
    compile("com.github.nowakprojects:kt-time-traveler-core:${org.bialydunajec.gradle.Versions.ktTimeTravelerVersion}")


    runtime("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}
