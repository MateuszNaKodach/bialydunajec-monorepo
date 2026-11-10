# Run with Axoniq Clarity

Runs the whole app from the root `compose.yaml` with the backend recorded by Axoniq Clarity's Java agent. Each HTTP
request to the backend becomes a decision in Clarity, with the calls between Spring beans, the JDBC transactions and
their SQL, and Hibernate's entity loads and changes.

The overlay `compose.yaml` here adds an Axoniq Clarity server of this app's own, on https://localhost:8080, that holds
only this app's data, and changes the backend:

- `clarity` runs the server image that the axoniq-clarity repo's demo builds (`clarity-demo-clarity:local`), with
  `application.properties` from here: TLS with the demo certificate, no change capture. Its store is the volume
  `clarity-data` of this Compose project.
- `JAVA_TOOL_OPTIONS=-javaagent:...` loads the agent from the demo's volume `clarity-demo-agent` (read-only);
- the backend trusts the demo CA from the volume `clarity-demo-tls` (read-only), TLS checks stay on, and sends to
  `https://clarity:8080/api/observations`, the name the demo's certificate is valid for.

The image and the two volumes come from running the demo once (`demo/demo.sh up clarity` in the axoniq-clarity repo,
which needs Axoniq's Nexus credentials); the demo itself need not run. Nothing is copied into this repo, so there is no
agent jar or CA to keep out of git.

## Start and stop

```bash
docker/clarity/run.sh up      # same as: docker compose -f compose.yaml -f docker/clarity/compose.yaml up --build -d
docker/clarity/run.sh down    # stop; add -v to also drop the databases and Clarity's store
```

`down -v` gives a Clarity from scratch on the next `up`. Ports are those of the plain run (6655, 4200, 4201, 6645, 6646)
and 8080 for Clarity (`CLARITY_PORT` moves it). Stop the axoniq-clarity demo and any plain run of this app from another
checkout first, they use the same ports. The service name is `bialydunajec-backend`; set `DIAMOND_SERVICE_NAME` to
change it.

## See it in Clarity

Use the app (http://localhost:4200, admin panel http://localhost:4201), or for example:

```bash
curl -s localhost:6655/rest-api/v1/camp-edition
curl -s 'localhost:6655/rest-api/v1/development/register-dummy-camper?count=1&cottage=Redemptor'
```

Then open https://localhost:8080, or ask the API with the demo CA:

```bash
docker run --rm -v clarity-demo-tls:/tls:ro alpine cat /tls/ca.pem > /tmp/clarity-demo-ca.pem
curl -s --cacert /tmp/clarity-demo-ca.pem 'https://localhost:8080/api/trails?service=bialydunajec-backend'
curl -s --cacert /tmp/clarity-demo-ca.pem 'https://localhost:8080/api/decisions/<rootDecisionId>'
curl -s --cacert /tmp/clarity-demo-ca.pem 'https://localhost:8080/api/uncovered-activity'
```

## What it does not show

- Work of the RxJava event bus (`RxExternalEventBus`, `observeOn(Schedulers.io())`) runs on RxJava's threads, outside
  the request's decision. The decision shows the `RxExternalEventBus.send` call; the listeners' SQL shows as Uncovered
  Activity. The same gap as Kotlin coroutines (axoniq-clarity issue 258); this app uses no coroutines.
- A 4xx or 404 goes through Tomcat's error page (`/error`) after the decision ended: the OAuth2 token lookup that the
  security filter makes again there is Uncovered Activity. A request that no controller maps is named `GET /`.
- MongoDB (the read models and the email module) is not recorded: the agent has no MongoDB support.
- Axon Framework is not used by the running backend (the `camp-schedule` Axon module is not in `settings.gradle.kts`).
- No `@Scheduled` job runs in the local profiles, so there are no job decisions.
- Change capture of this app's PostgreSQL is not set up: it needs a change of the demo server's config (below).

## Change capture (not set up)

For Clarity to link the backend's transactions to the rows they changed, as it does for the demo's `orders`:

1. The app's PostgreSQL with `wal_level=logical`, replication slots, and TLS with the demo certificate (`ssl=on`,
   `ssl_cert_file`/`ssl_key_file` from `clarity-demo-tls`), as the axoniq-clarity repo's
   `sample/enterprise/infra/postgres/compose.yaml` does; a user with the `REPLICATION` role.
2. PostgreSQL 13 or later: the agent records the transaction's id with `pg_current_xact_id()`, which PostgreSQL 11,
   used here, does not have.
3. A `diamond.debezium.databases[0]` entry in `application.properties` here, as in the demo's
   `demo/clarity/application.properties`, with `system` and `name` both `bialydunajec-postgres-db` (the database name in
   the backend's JDBC URL), and a restart of Clarity.
