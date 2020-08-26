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
    api(project(":bialydunajec-ddd:bialydunajec-ddd-application"))
    api(project(":bialydunajec-email:bialydunajec-email-messages"))

    compile("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    compile("io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}")
    compile("io.reactivex.rxjava2:rxkotlin:${Versions.rxJavaVersion}")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    //compile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    compile("org.springframework.boot:spring-boot-starter-web")
}
