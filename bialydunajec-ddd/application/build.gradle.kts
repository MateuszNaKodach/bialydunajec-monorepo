import org.bialydunajec.gradle.Versions

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
    compile(project(":bialydunajec-email:bialydunajec-email-messages"))
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-dto"))


    compile("io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}")
    compile("io.reactivex.rxjava2:rxkotlin:${Versions.rxJavaVersion}")

    compile("org.springframework.boot:spring-boot-starter-validation")
    compile("org.springframework.boot:spring-boot-starter-cache")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
