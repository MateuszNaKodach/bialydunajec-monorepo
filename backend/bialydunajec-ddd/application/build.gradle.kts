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
    api(project(":bialydunajec-ddd:bialydunajec-ddd-domain"))
    api(project(":bialydunajec-email:bialydunajec-email-messages"))
    api(project(":bialydunajec-ddd:bialydunajec-ddd-dto"))


    compile("io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}")
    compile("io.reactivex.rxjava2:rxkotlin:${Versions.rxJavaVersion}")

    compile("org.springframework.boot:spring-boot-starter-validation")
    compile("org.springframework.boot:spring-boot-starter-cache")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.willowtreeapps.assertk:assertk-jvm:${Versions.assertkVersion}")
    implementation("io.mockk:mockk:${Versions.mockkVersion}")
}
