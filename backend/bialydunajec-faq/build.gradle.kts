import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

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
