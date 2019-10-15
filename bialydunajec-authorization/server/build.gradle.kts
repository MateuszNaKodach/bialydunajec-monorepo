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
    //implementation project(":bialydunajec-ddd:bialydunajec-ddd-application")
    //implementation project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure")


    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-mail")
    compile("org.springframework.boot:spring-boot-starter-quartz")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:${Versions.oAuth2AutoconfigureVersion}")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.springframework.retry:spring-retry")

    runtimeOnly("mysql:mysql-connector-java")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.projectreactor:reactor-test")
    testCompile("org.springframework.security:spring-security-test")
}
