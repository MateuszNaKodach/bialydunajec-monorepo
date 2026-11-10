# API and UI

The server answers on one port (`https://localhost:8080` in the demo). Call it with the CA, never with `-k`:
`docker run --rm -v clarity-demo-tls:/tls:ro alpine cat /tls/ca.pem > ca.pem`, then `curl --cacert ca.pem ...`.
Routes: `server/server-ui/.../query/*Routes.kt`, `PipelinesController.kt`, `BusinessEventsController.kt`.

| Need | Call |
|---|---|
| Is the app sending? | `GET /api/applications`: instances with `counts` (`accepted`, `exported`, `dropped`, `failed`) |
| Its requests | `GET /api/trails?service=<s>&limit=200`; also `q=` (attribute value, reference value, decision name or id), `status=`, `ids=t1,t2` |
| One decision | `GET /api/decisions/<id>`: `observations` with calls, SQL, entity events, references |
| Its trail | `GET /api/decisions/<id>/trail`: `roots[]` with `children`, `committedChanges` (capture decisions) and `references` |
| Work outside decisions | `GET /api/uncovered-activity`: entries with `kind` and `trace.frames`, no service field |
| Processes and one case | `GET /api/process-map/processes`; `GET /api/process-map/cases?q=<business id>` finds the case across requests by ids inside captured rows (`matched.place` is `table.column`) |
| Blueprints | `GET/PUT/DELETE /api/event-blueprints/<id>`; `POST /api/event-blueprint-preview`; `GET /api/event-blueprint-schema` |
| Business events | `GET /api/business-events?blueprint=&type=&tag=<key>:<value>&last=` streams server-sent events, then a caught-up event |

UI pages: `/trails/<decisionId>` (tabs `?tab=references`, `events`), `/trails?service=<s>`, `/processes`,
`/processes/<id>`, `/apps/<service>`, `/events`, `/event-blueprints/<id>`. The case search on `/processes` is not in
the URL: give the id to type.

## Gotchas

- The trail list shows a rejected request as `succeeded`; the decision itself says `rejected` with its reason.
- zsh does not split `$var` into words: wrap the base URL in a function, `c() { curl -s --cacert ca.pem "https://localhost:8080/api$1"; }`.
