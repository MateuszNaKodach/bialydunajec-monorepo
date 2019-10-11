version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-domain")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
