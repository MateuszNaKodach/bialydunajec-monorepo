import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

version = "0.0.2"

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
    "bootRun"(org.springframework.boot.gradle.tasks.run.BootRun::class) {
        enabled = false
    }
    "jar"(Jar::class){
        enabled = true
    }
}
