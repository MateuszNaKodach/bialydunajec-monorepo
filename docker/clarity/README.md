# Run with Axoniq Clarity

Runs the whole app from the root `compose.yaml` with the backend recorded by Axoniq Clarity's Java agent. Each HTTP
request to the backend becomes a decision in Clarity, with the calls between Spring beans, the JDBC transactions and
their SQL, and Hibernate's entity loads and changes.

The overlay `compose.yaml` here adds an Axoniq Clarity server of this app's own, on https://localhost:8080, that holds
only this app's data, and changes the backend:

- `clarity` runs the server image that the axoniq-clarity repo's demo builds (`clarity-demo-clarity:local`), with
  `application.properties` from here: TLS with the demo certificate, and change capture of the app's PostgreSQL
  (below). Its store is the volume `clarity-data` of this Compose project.
- `bialydunajec-postgres` runs PostgreSQL 16, not 11, in a volume of its own (`postgres16-data`), set up for change
  capture. The root `compose.yaml` and the other compose files keep PostgreSQL 11.
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
- Change capture covers PostgreSQL only; MongoDB's writes are not captured.

## Change capture

Clarity links each backend transaction that wrote to the rows it changed: the request's decision records a
`reference-used` of type `database-transaction` (system `bialydunajec-postgres-db`, the transaction id), and Clarity's
Debezium turns the same transaction into a decision of `/axoniq/debezium/bialydunajec-postgres-db` with a
`row-changed` per row, in the same trail. How, as the axoniq-clarity repo's `sample/enterprise/infra/postgres/` does:

- PostgreSQL 16: the agent reads the transaction id with `pg_current_xact_id_if_assigned()`, which PostgreSQL 13
  brought; on 11 a decision has no `database-transaction` reference.
- `wal_level=logical`, replication slots, and TLS with the demo certificate: the entrypoint copies `server.pem` and
  `server-key.pem` out of `clarity-demo-tls`, the key owned by `postgres` with mode 600. The network alias `postgres` is
  the name the certificate covers; Clarity connects with `sslmode=verify-full`.
- `postgres-init.sql`, run once on a new data directory: the user `diamond` with `REPLICATION` and `pg_read_all_data`,
  and the publication `dbz_publication` for all tables, also the ones Hibernate creates later.
- `application.properties`: `diamond.debezium.databases[0]` with `system` and `name` both `bialydunajec-postgres-db`
  (the database name in the backend's JDBC URL), host `postgres`, slot `diamond_bialydunajec`, user `diamond`.

The app's tables keep the default replica identity, so an update or delete carries the old row's key only, not its old
values. The data of the plain run's PostgreSQL 11 is not carried over: on a new `postgres16-data` the seed loads the
sample data again.

Check it: run a writing request, such as `register-dummy-camper` above, and open its trail, or
`curl -s --cacert /tmp/clarity-demo-ca.pem https://localhost:8080/api/decisions/<rootDecisionId>/trail`: its root
lists the Debezium decision under `committedChanges`, with the inserted rows.
