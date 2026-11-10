# Runtime: Java agent

## Prerequisites

- The agent jar (from a Clarity build, or the demo's volume `clarity-demo-agent`), the server's CA as PEM, and a
  service name (letters, digits, `.`, `_`, `-`).
- Support table: `instrumentation/java/diamond-agent/README.md`, "Supported runtimes". Java 8 and later.
- Beyond that table, tested on bialydunajec (2026-11-10): Spring Boot 2.3.3 with Spring 5.2.8 (the table says 5.3+)
  and Hibernate 5.4.20 (it says 5.6+) on JDK 8. Routes, calls between beans, JDBC and Hibernate events all recorded.

## Configure

Host JVM:

```bash
DIAMOND_ENDPOINT=https://localhost:8080/api/observations \
DIAMOND_SERVICE_NAME=<service> \
DIAMOND_TRUSTED_CERTIFICATES=<ca.pem> \
java -javaagent:diamond-agent.jar -jar app.jar
```

Container: the same variables plus `JAVA_TOOL_OPTIONS=-javaagent:/clarity-agent/diamond-agent.jar`, with the jar and
the CA mounted read-only. The endpoint is `https` with a host name the server certificate covers; TLS checks stay on.

## Verify

- The app log has `Picked up JAVA_TOOL_OPTIONS` and no `[diamond-agent] Diamond agent disabled` line.
- `/api/applications`: the service with `exported` above 0 and `failed` 0.
- A request shows in `/api/trails?service=<service>`, named `<METHOD> <route pattern>`.

## Known gaps

- Work on a thread the agent does not follow runs outside the decision: Kotlin coroutines on kotlinx dispatchers
  (axoniq-clarity issue 258) and RxJava schedulers (`observeOn(Schedulers.io())`, stack frames
  `io.reactivex.internal.schedulers.ScheduledRunnable` over `ScheduledThreadPoolExecutor`). Their SQL is Uncovered
  Activity, and their writes become capture-only trails. Followed: `ThreadPoolExecutor`, `ForkJoinPool`,
  `CompletableFuture`, virtual threads.
- A 4xx or 404 forwarded to Tomcat's error page (`StandardHostValve.status` then `/error`) runs after the decision
  ended; a security filter's work there is Uncovered Activity.
- A request no handler maps is named `GET /`.
- Startup work (schema scripts, Hibernate metadata) is Uncovered Activity by design.
- Response bodies are not recorded, so business error codes in a 4xx body are invisible; the status is there.
- No MongoDB recording.
- PostgreSQL `database-transaction` references need PostgreSQL 13+ (`pg_current_xact_id_if_assigned()`); MySQL needs
  GTIDs (see the capture references).

## Gotchas

- Uncovered Activity entries are deduplicated by stack; `firstSeen` is when the agent flushed them (every 30 s), not
  when the work ran.
