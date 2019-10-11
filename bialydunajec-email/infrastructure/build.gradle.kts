version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure")

    compile project(":bialydunajec-email:bialydunajec-email-domain")
    compile project(":bialydunajec-email:bialydunajec-email-application")
    compile project(":bialydunajec-email:bialydunajec-email-presentation")

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-mail")

    runtime("com.h2database:h2")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
