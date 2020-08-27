import org.bialydunajec.gradle.Dependencies
import org.bialydunajec.gradle.Projects
import org.bialydunajec.gradle.TestDependencies
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
    api(project(Projects.BialyDunajec.DDD.INFRASTRUCTURE))

    api(project(Projects.BialyDunajec.AcademicMinistry.DOMAIN))
    api(project(Projects.BialyDunajec.AcademicMinistry.APPLICATION))
    api(project(Projects.BialyDunajec.AcademicMinistry.PRESENTATION))

    compile(Dependencies.Spring.Boot.SPRING_BOOT_STARTER_DATA_JPA)
    runtime(Dependencies.Database.H2.DATABASE_H2)
    runtime(Dependencies.Database.MySQL.MYSQL_CONNECTOR_JAVA)

    testCompile(TestDependencies.Spring.Boot.SPRING_BOOT_STARTER_TEST)
}
