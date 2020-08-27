import org.bialydunajec.gradle.Versions
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

    api(project(":bialydunajec-gallery:bialydunajec-gallery-domain"))
    api(project(":bialydunajec-gallery:bialydunajec-gallery-application"))
    api(project(":bialydunajec-gallery:bialydunajec-gallery-presentation"))

    compile("com.google.photos.library:google-photos-library-client:${Versions.googlePhotosLibraryClientVersion}")
    compile("com.google.auth:google-auth-library-oauth2-http:${Versions.googleAuthLibraryOauth2HttpVersion}")
    compile("com.google.api-client:google-api-client:${Versions.googleApiClientVersion}")

    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    runtime("mysql:mysql-connector-java")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
