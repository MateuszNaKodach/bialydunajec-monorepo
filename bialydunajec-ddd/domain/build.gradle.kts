version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-validation")
    compile("commons-codec","commons-codec","1.11")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
