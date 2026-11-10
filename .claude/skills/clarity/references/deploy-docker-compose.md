# Deploy: Docker Compose

## Prerequisites

- The app runs from a Compose file. Check `docker ps` for port clashes first.
- A Clarity server image and its TLS, for example from the axoniq-clarity demo (`demo/demo.sh up clarity` builds the
  image `clarity-demo-clarity:local` and the volumes `clarity-demo-tls` and `clarity-demo-agent`; it needs Axoniq's
  Nexus credentials).

## Configure

An overlay in the app's repo (for example `docker/clarity/compose.yaml`) plus a `run.sh up|down` that runs
`docker compose -f compose.yaml -f docker/clarity/compose.yaml`. The app's own Compose files stay as they are.

Pick a topology:

- **Shared Clarity**: join the running server's network (`networks: {clarity: {name: clarity-demo, external: true}}`)
  and send to `https://clarity:8080/api/observations`.
- **Own Clarity** (the user wants only this app in Clarity): a `clarity` service from the server image in the app's
  project, with its own `application.properties` (TLS, and the app's capture only) and its own data volume, so
  `down -v` gives a fresh Clarity. Its TLS and agent come from the external volumes, mounted read-only.

Either way, the agent jar and the CA are mounted read-only from volumes: nothing to keep out of git.

## Verify

- `docker compose ... config <service>` renders the merged service as intended.
- The runtime and capture references' Verify steps.

## Known gaps

- The demo certificate covers fixed names (`clarity`, `postgres`, ...); check with
  `openssl x509 -in server.pem -noout -ext subjectAltName`, and use those names as network aliases.

## Gotchas

- Relative paths in an overlay resolve against the project directory (the first `-f` file's), not the overlay's.
- A bind mount of a missing file creates a directory, and the service starts without its config.
- `ports` and other lists merge by appending; replace them with `!override`.
- A service given `networks:` in an overlay leaves `default` unless it lists it.
- Recreating a container reuses its anonymous volumes: a new major database version needs a named volume.
- One-shot seed services run again on each `up`; make them idempotent.
- To verify without touching a stack the user runs: another project name (`-p`) and a throwaway overlay with other
  ports (`ports: !override`).
