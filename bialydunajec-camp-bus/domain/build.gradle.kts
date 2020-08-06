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

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/nowakprojects/kttimetraveler")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    testCompile("com.github.nowakprojects:kt-time-traveler-test:${org.bialydunajec.gradle.Versions.ktTimeTravelerVersion}")
    compile("com.github.nowakprojects:kt-time-traveler-core:${org.bialydunajec.gradle.Versions.ktTimeTravelerVersion}")
}
