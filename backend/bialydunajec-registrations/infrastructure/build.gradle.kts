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

	api(project(":bialydunajec-registrations:bialydunajec-registrations-domain"))
	api(project(":bialydunajec-registrations:bialydunajec-registrations-application"))
	api(project(":bialydunajec-registrations:bialydunajec-registrations-presentation"))

	compile("org.springframework.boot:spring-boot-starter-security")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
	runtime("com.h2database:h2")
	runtime("mysql:mysql-connector-java")
	testCompile("org.springframework.boot:spring-boot-starter-test")
}