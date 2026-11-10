#!/usr/bin/env python3
"""Previews or saves an Axoniq Clarity event blueprint from <name>.sql and <name>.events.json next to this script.

  docker/clarity/blueprints/blueprint.py preview camp-registrations   events it would emit over the recent batches
  docker/clarity/blueprints/blueprint.py save camp-registrations      define it (or a new version), with backfill

It trusts the demo CA from the Docker volume clarity-demo-tls; set CLARITY_URL to reach another server. Stdlib only.
"""
import json
import os
import ssl
import subprocess
import sys
import urllib.error
import urllib.request

HERE = os.path.dirname(os.path.abspath(__file__))
CLARITY = os.environ.get("CLARITY_URL", "https://localhost:" + os.environ.get("CLARITY_PORT", "8080"))


def tls():
    demo = os.environ.get("CLARITY_DEMO", "clarity-demo")
    ca = subprocess.run(["docker", "run", "--rm", "-v", demo + "-tls:/tls:ro", "alpine", "cat", "/tls/ca.pem"],
                        check=True, capture_output=True, text=True).stdout
    return ssl.create_default_context(cadata=ca)


def call(method, path, body, context):
    request = urllib.request.Request(CLARITY + path, method=method, data=json.dumps(body).encode(),
                                     headers={"Content-Type": "application/json"})
    try:
        with urllib.request.urlopen(request, context=context) as response:
            return response.status, json.load(response)
    except urllib.error.HTTPError as e:
        sys.exit(f"{method} {path}: {e.code} {e.read().decode()}")


def main():
    if len(sys.argv) != 3 or sys.argv[1] not in ("preview", "save"):
        sys.exit(__doc__)
    action, name = sys.argv[1], sys.argv[2]
    with open(os.path.join(HERE, name + ".sql")) as f:
        sql = f.read()
    with open(os.path.join(HERE, name + ".events.json")) as f:
        events = json.load(f)
    context = tls()
    if action == "preview":
        _, answer = call("POST", "/api/event-blueprint-preview",
                         {"sql": sql, "events": events, "batches": 100000, "limit": 1000}, context)
        print(json.dumps(answer, indent=1))
        return
    # a new version needs the version it replaces, 0 for a new blueprint
    try:
        with urllib.request.urlopen(CLARITY + "/api/event-blueprints/" + name, context=context) as response:
            expected = json.load(response)["version"]
    except urllib.error.HTTPError as e:
        if e.code != 404:
            raise
        expected = 0
    status, answer = call("PUT", "/api/event-blueprints/" + name,
                          {"sql": sql, "events": events, "backfill": True, "expectedVersion": expected}, context)
    print(status, json.dumps(answer, indent=1))


if __name__ == "__main__":
    main()
