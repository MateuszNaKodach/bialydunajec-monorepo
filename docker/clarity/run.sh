#!/usr/bin/env sh
# Runs the whole app (root compose.yaml) with the backend recorded by Axoniq Clarity; see docker/clarity/README.md.
#   docker/clarity/run.sh up     build and start, backend with the Clarity agent
#   docker/clarity/run.sh down   stop and remove the containers (add -v to also drop the databases and Clarity's store)
set -eu
cd "$(dirname "$0")/../.."
demo=${CLARITY_DEMO:-clarity-demo}
compose() { docker compose -f compose.yaml -f docker/clarity/compose.yaml "$@"; }

case "${1:-}" in
  up)
    # made once by the axoniq-clarity repo's demo/demo.sh up clarity
    for v in "$demo-agent" "$demo-tls"; do
      docker volume inspect "$v" >/dev/null 2>&1 || { echo "Docker volume $v not found: run the axoniq-clarity demo once (demo/demo.sh up clarity)" >&2; exit 1; }
    done
    image=${CLARITY_IMAGE:-$demo-clarity:local}
    docker image inspect "$image" >/dev/null 2>&1 || { echo "Docker image $image not found: run the axoniq-clarity demo once (demo/demo.sh up clarity)" >&2; exit 1; }
    compose up --build -d
    echo "Backend: http://localhost:6655, recorded as service ${DIAMOND_SERVICE_NAME:-bialydunajec-backend} in https://localhost:${CLARITY_PORT:-8080}"
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
