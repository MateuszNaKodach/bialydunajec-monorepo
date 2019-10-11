import org.bialydunajec.gradle.Versions

version = "0.0.2"

bootJar {
    enabled = false
}

jar {
    enabled = true
}

dependencies {
    compile("io.reactivex.rxjava2:rxjava:${Versions.rxJavaVersion}")
    compile("io.reactivex.rxjava2:rxkotlin:${Versions.rxJavaVersion}")
    compile "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelayVersion}"
}
