# Capture: PostgreSQL

## Prerequisites

- PostgreSQL 13 or later, for the agent's side of the link. On 11 a decision has no `database-transaction` reference
  (`pg_current_xact_id_if_assigned()` does not exist).
- `wal_level=logical`, `max_replication_slots`, `max_wal_senders`; TLS with a certificate whose name Clarity uses as
  the host (Clarity connects with `sslmode=verify-full`).
- A user with `REPLICATION` and read access (`pg_read_all_data` on 14+), and a publication, by default
  `dbz_publication`, that Clarity does not create.
- `REPLICA IDENTITY FULL` on captured tables, or updates and deletes carry only the old row's key.
- Every setting: `DebeziumProperties.kt`. A working example: `sample/enterprise/infra/postgres/`.

## Configure

Compose service, as the demo does (the key must be owned by `postgres` with mode 600, so copy it out of the volume):

```yaml
entrypoint: [sh, -c, >-
  install -d -o postgres -m 700 /tls &&
  install -o postgres -m 600 /clarity-tls/server-key.pem /tls/server-key.pem &&
  install -o postgres -m 644 /clarity-tls/server.pem /tls/server.pem &&
  exec docker-entrypoint.sh "$$0" "$$@"]
command: [postgres, -c, wal_level=logical, -c, max_replication_slots=10, -c, max_wal_senders=10,
          -c, ssl=on, -c, ssl_cert_file=/tls/server.pem, -c, ssl_key_file=/tls/server-key.pem]
```

Init SQL, run once on a new data directory:

```sql
CREATE USER diamond WITH REPLICATION PASSWORD '<password>';
GRANT pg_read_all_data TO diamond;
CREATE PUBLICATION dbz_publication FOR ALL TABLES;   -- covers tables created later too
```

Clarity:

```properties
diamond.debezium.databases[0].system=<database name in the app's JDBC URL>
diamond.debezium.databases[0].name=<database name>
diamond.debezium.databases[0].hostname=<a name the certificate covers>
diamond.debezium.databases[0].port=5432
diamond.debezium.databases[0].slot-name=<unique per database>
diamond.debezium.databases[0].username=diamond
diamond.debezium.databases[0].password=<password>
```

`system` must equal what the agent puts in its references: the database name of the JDBC URL.

## Verify

- `select slot_name, plugin, active from pg_replication_slots` shows the slot, `pgoutput`, active.
- `pg_stat_ssl` joined with `pg_stat_activity`: the replication user's connections have `ssl = t`.
- Clarity log: `Capturing the transactions of <system>`, `database.sslmode = verify-full`, `Starting streaming`; no
  `SSLHandshake` or PKIX errors (the line `ssl.trustmanager.algorithm = PKIX` is configuration, not an error).
- A writing request: its decision has `reference-used` `database-transaction`; `/api/decisions/<id>/trail` lists the
  capture decision under `committedChanges` with one `row-changed` per row.

## Known gaps

- Captured rows hold every column, personal data included. Narrow it with `tables`, or Debezium's
  `column.exclude.list` through `engine` (from Debezium docs, untested with Clarity).
- Writes no decision claims (async work) become trails of their own, with the capture decision alone.

## Gotchas

- A new major version cannot reuse the old data directory: give it a new volume and let the app seed again.
- `REPLICA IDENTITY FULL` must be set after the app created its tables; an init script runs before that.
- Large-object (`oid`) columns arrive as numbers; read them in the database with `lo_get`.
