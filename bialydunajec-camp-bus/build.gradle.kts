import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
}
