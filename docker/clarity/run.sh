#!/usr/bin/env sh
# Runs the whole app (root compose.yaml) with the backend recorded by Axoniq Clarity; see docker/clarity/README.md.
#   docker/clarity/run.sh up     build and start, backend with the Clarity agent
#   docker/clarity/run.sh down   stop and remove the containers (add -v to also drop the databases)
set -eu
cd "$(dirname "$0")/../.."
demo=${CLARITY_DEMO:-clarity-demo}
compose() { docker compose -f compose.yaml -f docker/clarity/compose.yaml "$@"; }

case "${1:-}" in
  up)
    for v in "$demo-agent" "$demo-tls"; do
      docker volume inspect "$v" >/dev/null 2>&1 || { echo "Docker volume $v not found: start the Axoniq Clarity demo first" >&2; exit 1; }
    done
    docker network inspect "$demo" >/dev/null 2>&1 || { echo "Docker network $demo not found: start the Axoniq Clarity demo first" >&2; exit 1; }
    compose up --build -d
    echo "Backend: http://localhost:6655, recorded as service ${DIAMOND_SERVICE_NAME:-bialydunajec-backend} in https://localhost:8080"
    ;;
  down)
    shift
    compose down "$@"
    ;;
  *)
    echo "usage: $0 up | down [-v]" >&2
    exit 2
    ;;
esac
