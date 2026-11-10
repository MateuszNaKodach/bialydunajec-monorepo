# Run with Axoniq Clarity

Runs the whole app from the root `compose.yaml` with the backend recorded by Axoniq Clarity's Java agent. Each HTTP
request to the backend becomes a decision in Clarity, with the calls between Spring beans, the JDBC transactions and
their SQL, and Hibernate's entity loads and changes.

It needs the Axoniq Clarity demo running in Docker (Compose project `clarity-demo`, UI on https://localhost:8080).
The overlay `compose.yaml` here only changes the backend:

- `JAVA_TOOL_OPTIONS=-javaagent:...` loads the agent from the demo's volume `clarity-demo-agent` (read-only);
- the backend trusts the demo CA from the volume `clarity-demo-tls` (read-only), TLS checks stay on;
- the backend joins the demo's network `clarity-demo` and sends to `https://clarity:8080/api/observations`, the name
  the demo's certificate is valid for.

Nothing is copied into this repo, so there is no agent jar or CA to keep out of git.

## Start and stop

```bash
docker/clarity/run.sh up      # same as: docker compose -f compose.yaml -f docker/clarity/compose.yaml up --build -d
docker/clarity/run.sh down    # stop; add -v to also drop the databases
docker compose up -d          # back to the plain backend, without the agent
```

Ports are those of the plain run (6655, 4200, 4201, 6645, 6646), apart from the demo's (8080, 28000-28004, 2xxxx). Stop a
plain run of this app started from another checkout first, it uses the same ports. The service name is
`bialydunajec-backend`; set `DIAMOND_SERVICE_NAME` to change it.

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

1. The app's PostgreSQL on the `clarity-demo` network, with `wal_level=logical`, replication slots, and TLS with the demo
   certificate (`ssl=on`, `ssl_cert_file`/`ssl_key_file` from `clarity-demo-tls`), as the demo's
   `sample/enterprise/infra/postgres/compose.yaml` does; a user with the `REPLICATION` role.
2. PostgreSQL 13 or later: the agent records the transaction's id with `pg_current_xact_id()`, which PostgreSQL 11,
   used here, does not have.
3. A new `diamond.debezium.databases[n]` entry in the demo's `demo/clarity/application.properties`, with `system` and
   `name` both `bialydunajec-postgres-db` (the database name in the backend's JDBC URL), and a restart of Clarity.
