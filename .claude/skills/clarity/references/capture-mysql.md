# Capture: MySQL (from docs, untested)

## Prerequisites

- Server: `gtid_mode=ON`, `enforce_gtid_consistency=ON`, `binlog_format=ROW`, `binlog_row_image=FULL`.
- User: `SELECT`, `RELOAD`, `SHOW DATABASES`, `REPLICATION SLAVE`, `REPLICATION CLIENT`.
- For the agent's references: `session_track_gtids=OWN_GTID` on the server and `trackSessionState=true` in
  Connector/J's URL.
- Every setting: `DebeziumProperties.kt`. A working example: the demo's MySQL in `sample/enterprise/infra/mysql/`.

## Configure

```properties
diamond.debezium.databases[0].system=<any unique name>
diamond.debezium.databases[0].type=mysql
diamond.debezium.databases[0].hostname=<a name the certificate covers>
diamond.debezium.databases[0].names=<database>,<database>
diamond.debezium.databases[0].username=<user>
diamond.debezium.databases[0].password=<password>
```

The references' `system` is always `mysql` (the GTID is unique on its own). One entry captures one server; Debezium
keeps a schema history file beside its offsets, keep the two together.

## Verify

As for PostgreSQL: a writing request's decision has `reference-used` `database-transaction`, and its trail holds the
capture decision with its rows.

## Known gaps

Fill in when tested.

## Gotchas

- History lost but offsets kept: Debezium cannot start; start once with `snapshot.mode=recovery` in `engine`.
