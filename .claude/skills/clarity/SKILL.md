---
name: clarity
description: Axoniq Clarity: attach its agent to an application, capture its database changes, explore what it recorded (trails, decisions, uncovered activity, process map cases), and derive business events with event blueprints. Use when instrumenting an app for Clarity, checking what Clarity shows or misses for it, correlating one business case across requests, or writing, previewing or versioning blueprints.
---

# Clarity

Clarity records an application's **decisions** (a request, a job run, a received message) with what happened inside
them, and the database transactions it **captures** as decisions of their own. Decisions join into a **trail** only
through a shared **reference**: a request id, a message id, a transaction id. Everything Clarity misses is a **gap**,
and each gap is reported with its evidence. **Blueprints** turn decisions and captured rows into business events.

## Sources of truth

This skill caches the gotchas; the axoniq-clarity repo stays authoritative. Find a clone with
`find ~ -maxdepth 6 -path '*axoniq-clarity*/docs/pipeline-sql.md' 2>/dev/null`, or read it with
`gh api repos/AxonIQ/axoniq-clarity/contents/<path>`:

- `instrumentation/<language>/<agent>/README.md`: what an agent records, its settings, its support table
- `docs/pipeline-sql.md`: blueprint SQL; `docs/adr/`: the reasons
- `server/server-debezium/.../DebeziumProperties.kt`: every change-capture setting
- `server/server-ui/.../query/*Routes.kt` and `frontend/app/pages/`: the API and the UI pages
- `demo/` and `sample/`: a working server, TLS and databases to copy from

## Adapters

Each stack piece has a reference. Read the ones that match the app; write a new one when none does (Extending).

| Piece | Reference | State |
|---|---|---|
| Runtime: JVM agent | [runtime-java-agent.md](references/runtime-java-agent.md) | tested: JDK 8, Spring Boot 2.3, Hibernate 5.4 |
| Capture: PostgreSQL | [capture-postgres.md](references/capture-postgres.md) | tested: PostgreSQL 16 |
| Capture: MySQL | [capture-mysql.md](references/capture-mysql.md) | from docs, untested |
| Deploy: Docker Compose | [deploy-docker-compose.md](references/deploy-docker-compose.md) | tested |
| API and UI | [api.md](references/api.md) | tested |
| Gaps checklist | [gaps.md](references/gaps.md) | tested |
| Business events | [blueprints.md](references/blueprints.md), [scripts/blueprint.py](scripts/blueprint.py) | tested |

## Steps

1. **Map the app.** List its runtimes and versions, entry points (HTTP, listeners, schedulers), async boundaries
   (thread pools, reactive schedulers, coroutines, in-memory event buses), datastores and auth. Done when each entry
   point and datastore is checked against the support table of its runtime reference, with unsupported ones named.
2. **Pick the adapters** from the table. Done when runtime, deploy and (if wanted) capture each have a reference.
3. **Attach** the agent as the runtime and deploy references say, TLS checks on. Done when `/api/applications` lists
   the service with `exported` above 0 and `failed` 0, and one real request shows in its trails.
4. **Capture** the database, when the question "is its transaction linked?" matters. Done when a writing request's
   decision holds a `reference-used` of type `database-transaction` and its trail holds the capture decision with
   its `row-changed` rows.
5. **Exercise use cases one by one**, through the real UI where there is one: the happy path, a rejected case
   (validation, duplicate), a failed login, back-office follow-ups (an admin acting on the same case), and the async
   follow-ups (e-mails, projections). Script a repeated form instead of clicking it each time. Done when each use
   case has its trail id.
6. **Inspect and attribute** with [gaps.md](references/gaps.md). Done when every Uncovered Activity entry since the
   start is attributed to a cause from its stack, and every gap is marked verified or unverified.
7. **Derive business events** when asked, with [blueprints.md](references/blueprints.md). Done when the preview's
   count per event type equals the count in the source of truth, and the blueprint is saved.
8. **Report and make it repeatable**: trail list, one decision with its calls and SQL, the gap table, and the start
   and stop commands. Commit a script and a README section to the app's repo; the agent jar, CA and secrets stay out
   of git.

## Cutting across

- Before saying Clarity cannot do something, check its UI pages and API routes. Trails join only by references, yet
  the process map's case search finds one business case across requests by ids inside captured rows.
- Change the observed app's code only with the user's yes; reach for configuration, overlays and scripts first.
- Personal data stays out of derived events. Tell the user that captured rows hold every column anyway, and what
  capture can leave out (`tables`, Debezium settings through `engine`).
- Leave other running stacks alone unless the user asks; when asked, `stop` before `down -v`.

## Extending

A new adapter is `references/<piece>-<name>.md` (`runtime-`, `capture-`, `deploy-`) with these headings, in order:
**Prerequisites** (versions, support table pointer), **Configure** (snippets), **Verify** (commands and the output
that proves it), **Known gaps** (each with its evidence), **Gotchas**. Mark what comes from docs alone
"(from docs, untested)", and add a row to the Adapters table with its state.
