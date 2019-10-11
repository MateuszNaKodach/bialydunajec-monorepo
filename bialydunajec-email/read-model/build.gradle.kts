import org.bialydunajec.gradle.Versions

version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-application")
    compile project(":bialydunajec-email:bialydunajec-email-messages")

    compile("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    compile("io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}")
    compile("io.reactivex.rxjava2:rxkotlin:${Versions.rxJavaVersion}")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    //compile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    compile("org.springframework.boot:spring-boot-starter-webflux")
}
