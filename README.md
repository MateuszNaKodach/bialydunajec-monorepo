# Biały Dunajec - Kotlin backend

<p align="center">
  <img src="/.github/images/BialyDunajecDevelopers.jpg">
</p>

![Development Environment Deploy](https://github.com/nowakprojects/bialydunajec-kotlin-backend/workflows/Build%20Spring%20Boot%20JAR%20for%20bialydunajec-kotlin-backend%20&%20deploy%20on%20development%20environment/badge.svg?branch=develop)

![Staging Environment Deploy](https://github.com/nowakprojects/bialydunajec-kotlin-backend/workflows/Build%20Spring%20Boot%20JAR%20for%20bialydunajec-kotlin-backend/badge.svg?branch=staging)

![Production Environment Deploy](https://github.com/nowakprojects/bialydunajec-kotlin-backend/workflows/Build%20Spring%20Boot%20JAR%20for%20bialydunajec-kotlin-backend%20&%20deploy%20on%20production%20environment/badge.svg)


## Uruchomienie lokalne - Docker Compose (bez instalacji Javy - zalecane)

1. Zainstaluj docker oraz docker-compose i uruchom dockera.
2. Wywołaj polecenie `docker-compose -f docker-compose.local.yml up`.

Dzięki temu będą możliwe następujące połączenia:
- Aplikacja Spring (backend): `localhost:3344`
- Swagger (dokumentacja REST API): `localhost:3344/swagger-ui/index.html`
- Baza danych PostgreSQL: `localhost:3345`
- Baza danych MongoDB: `localhost:3346`

UWAGA! Jeśli zmienisz coś w kodzie,to konieczne będzie wykonanie polecenia `docker-compose -f docker-compose.local.yml build`

## Uruchomienie lokalne - instalacja Javy

1. **Zainstaluj JDK \(wersja 11\)** odpowiednie dla Twojego systemu: [https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
2. **Zainstaluj środowisko programistyczne dla Java.** Obecnie najlepszy wybór to IntelliJ IDEA \(wersja Community, lub Enterprise - jeśli jesteś studentem, to masz ją za darmo\).

   Community: [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/) 

   Enterprise \(dla studentów\): [https://www.jetbrains.com/student/](https://www.jetbrains.com/student/)

   Wersja Community nie dostarcza wsparcia dla frameworka Spring, który jest używany w projekcie, dlatego proponują zamiast niej wersję EAP: [https://www.jetbrains.com/idea/nextversion/](https://www.jetbrains.com/idea/nextversion/) - zazwyczaj beta wersji Enterprise.

3. **Pobierz repozytorium** spod adresu: [https://github.com/nowakprojects/bialydunajec-kotlin-backend](https://github.com/nowakprojects/bialydunajec-kotlin-backend) używając narzędzia GIT.
4. **Zaimportuj projekt do IntelliJ IDEA**
5. Uruchom projekt Spring Boot używając klasy **BialyDunajecBackendApplication.kt** lub wpisując w terminalu:

Windows:

```text
gradlew.bat bootRun
```

Linux/macOS:

```text
./gradlew bootRun
```

Jeśli pojawia się błąd:

```text
Error running 'BialyDunajecBackendApplication': Command line is too long. Shorten command line for BialyDunajecBackendApplication or also for Spring Boot default configuration.
```

To zajrzyj tutaj: [https://github.com/nowakprojects/bialydunajec-kotlin-backend/issues/40](https://github.com/nowakprojects/bialydunajec-kotlin-backend/issues/40)



W przypadku innych błędów przeszukaj GitHub filtrując po etykiecie _development issue:_

\_\_[_https://github.com/nowakprojects/bialydunajec-kotlin-backend/issues?q=is%3Aissue+label%3A%22development+issue%22+is%3Aclosed_](https://github.com/nowakprojects/bialydunajec-kotlin-backend/issues?q=is%3Aissue+label%3A%22development+issue%22+is%3Aclosed)\_\_

