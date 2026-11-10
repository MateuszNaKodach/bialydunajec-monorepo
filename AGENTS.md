# AGENTS.md

Monorepo of the Bialy Dunajec camp registration system: Kotlin / Spring Boot 2.3 backend (`backend/`),
two Angular apps (`frontend-main/` Angular 8, `frontend-adminpanel/` Angular 6).

## Run it

Start from the root `compose.yaml`; commands, ports and the admin login are in `README.md`
("Run everything locally"). Docker is the only host requirement: JDK 8 and Node run inside the build
images, so leave the host's JDK and Node as they are.

- Readiness signal: `docker compose logs bialydunajec-seed` ends with `seeded, ...` or `already seeded`.
- `docker compose down -v` wipes both databases and the backend's token store. A browser that was
  logged in keeps its old token and the admin panel shows empty pages with 401 errors: log out and in again.
- After changing backend code: `docker compose up --build -d bialydunajec-backend`.

## Sample data

Dev-only endpoints in `backend/src/main/kotlin/org/bialydunajec/rest/v1/dev/DevelopmentController.kt`
(active in local profiles, no auth):

- `GET /rest-api/v1/development/db-init`: editions, ministries, cottages, the admin user. The seed service
  calls it once; calling it again duplicates the data. The admin login screen's "Zaladuj spreparowane dane"
  button calls the same endpoint.
- `GET /rest-api/v1/development/register-dummy-campers?maxPerCottage=8`: random campers for edition 36,
  about 2.5 minutes.
- `GET /rest-api/v1/development/register-dummy-camper?count=5&cottage=Redemptor`: campers for one cottage.

Each camper registration takes about 6 s of CPU (BCrypt cost 17 on the PESEL, `BCryptPeselEncoder`) while
holding a DB connection; Hikari has 10. Keep parallel registrations below that, which is why the bulk endpoint
registers at most 4 cottages at a time.

## Build constraints

- Dependency versions stay as they are; the build works with the locked versions.
- `frontend-adminpanel` builds with Node 10 on `linux/amd64`: its locked node-sass 4.9.3 supports Node 10 at most
  and ships no arm64 binary. `frontend-main` builds with Node 12 (same as CI).
- Base images: `eclipse-temurin:8` for the backend. `openjdk:8-alpine3.9` and `centos:latest`, used by the
  older backend Dockerfiles, are no longer on Docker Hub, so those Dockerfiles do not build.
- `Dockerfile.main` / `Dockerfile.admin` build the apps themselves; the GitHub workflows do not use them.

## External services in local profiles

- Camp news: `bialydunajec.camp-news-provider: mock` serves posts from
  `backend/bialydunajec-news/infrastructure/src/main/resources/mock-camp-news/` (Facebook needs a real token;
  `facebook` is the default for other profiles).
- Gallery: `camp-gallery-provider: empty`. Mail: the `mail_log` profile logs emails instead of sending them.

## Known issues

- `POST /oauth/token` with `grant_type=client_credentials` fails with a `ClassCastException` in the app's token
  code. The frontends use `grant_type=password`, which works.
- `docker/ng-serve-backend-docker/docker-compose.yml` has an empty `dockerfile:` and fails to build
  (`open Dockerfile: no such file or directory`); the root
  `compose.yaml` replaces it. `backend/docker-compose.local.yml` still works (backend on port 3344).
