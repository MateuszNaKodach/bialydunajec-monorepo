import org.bialydunajec.gradle.Dependencies
import org.bialydunajec.gradle.Projects
import org.bialydunajec.gradle.TestDependencies
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
    api(project(Projects.BialyDunajec.DDD.PRESENTATION))
    api(project(Projects.BialyDunajec.AcademicMinistry.APPLICATION))

    compile(Dependencies.Spring.Boot.SPRING_BOOT_STARTER_WEB_FLUX)
    compile(Dependencies.Jackson.JACKSON_MODULE_KOTLIN)

    testCompile(TestDependencies.Spring.Boot.SPRING_BOOT_STARTER_TEST)
    testCompile(TestDependencies.Reactor.REACTOR_TEST)
}
