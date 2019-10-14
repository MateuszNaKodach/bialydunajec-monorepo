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
    api(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))

    api(project(":bialydunajec-news:bialydunajec-news-domain"))
    api(project(":bialydunajec-news:bialydunajec-news-application"))
    api(project(":bialydunajec-news:bialydunajec-news-presentation"))

    compile("com.restfb:restfb:${Versions.restfbVersion}")
    compile("com.restfb", "restfb-examples", "1.6.7")

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
