import org.bialydunajec.gradle.Versions

version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure")

    compile project(":bialydunajec-news:bialydunajec-news-domain")
    compile project(":bialydunajec-news:bialydunajec-news-application")
    compile project(":bialydunajec-news:bialydunajec-news-presentation")

    compile("com.restfb:restfb:${Versions.restfbVersion}")
    compile group: 'com.restfb', name: 'restfb-examples', version: '1.6.7'

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
