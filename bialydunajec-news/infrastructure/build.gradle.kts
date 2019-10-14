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
    compile(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))

    compile(project(":bialydunajec-news:bialydunajec-news-domain"))
    compile(project(":bialydunajec-news:bialydunajec-news-application"))
    compile(project(":bialydunajec-news:bialydunajec-news-presentation"))

    compile("com.restfb:restfb:${Versions.restfbVersion}")
    compile("com.restfb", "restfb-examples", "1.6.7")

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
