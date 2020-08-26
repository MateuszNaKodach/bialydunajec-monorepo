import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
    "bootRun"(BootRun::class) {
        enabled = false
    }
    "jar"(Jar::class){
        enabled = true
    }
}


dependencies {
    api(project(":bialydunajec-ddd:bialydunajec-ddd-infrastructure"))

    api(project(":bialydunajec-faq:bialydunajec-faq-domain"))
    api(project(":bialydunajec-faq:bialydunajec-faq-application"))
    api(project(":bialydunajec-faq:bialydunajec-faq-presentation"))

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
