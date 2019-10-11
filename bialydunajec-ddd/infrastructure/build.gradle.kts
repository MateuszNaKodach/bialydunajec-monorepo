version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile project(":bialydunajec-ddd:bialydunajec-ddd-application")
    compile project(":bialydunajec-ddd:bialydunajec-ddd-domain")
    compile project(":bialydunajec-rx-bus")

    compile("org.springframework.boot:spring-boot-starter-amqp")
    compile("org.springframework.boot:spring-boot-starter-cache")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-integration")
    compile("org.springframework.boot:spring-boot-starter-mail")
    //compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.springframework.kafka:spring-kafka")

    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("io.projectreactor:reactor-test")
}
