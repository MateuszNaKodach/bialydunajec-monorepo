#!/usr/bin/env python3
"""Previews or saves an Axoniq Clarity event blueprint from <path>.sql and <path>.events.json.

  blueprint.py preview <path>          count of each event type it would emit over all stored batches
  blueprint.py preview <path> --json   the events themselves
  blueprint.py save <path>             define it, or a new version of it, with backfill

The blueprint id is the file name of <path>. Environment: CLARITY_URL (default https://localhost:8080); the CA as
CLARITY_CA (a PEM file) or from the Docker volume CLARITY_TLS_VOLUME (default clarity-demo-tls). Stdlib only.
"""
import collections
import json
import os
import ssl
import subprocess
import sys
import urllib.error
import urllib.request

CLARITY = os.environ.get("CLARITY_URL", "https://localhost:8080")


def tls():
    if os.environ.get("CLARITY_CA"):
        return ssl.create_default_context(cafile=os.environ["CLARITY_CA"])
    volume = os.environ.get("CLARITY_TLS_VOLUME", "clarity-demo-tls")
    ca = subprocess.run(["docker", "run", "--rm", "-v", volume + ":/tls:ro", "alpine", "cat", "/tls/ca.pem"],
                        check=True, capture_output=True, text=True).stdout
    return ssl.create_default_context(cadata=ca)


def call(method, path, context, body=None):
    data = None if body is None else json.dumps(body).encode()
    request = urllib.request.Request(CLARITY + path, method=method, data=data,
                                     headers={"Content-Type": "application/json"})
    with urllib.request.urlopen(request, context=context) as response:
        return response.status, json.load(response)


def main():
    if len(sys.argv) < 3 or sys.argv[1] not in ("preview", "save"):
        sys.exit(__doc__)
    action, path = sys.argv[1], sys.argv[2]
    blueprint_id = os.path.basename(path)
    with open(path + ".sql") as f:
        sql = f.read()
    with open(path + ".events.json") as f:
        events = json.load(f)
    context = tls()
    try:
        if action == "preview":
            _, answer = call("POST", "/api/event-blueprint-preview", context,
                             {"sql": sql, "events": events, "batches": 100000, "limit": 1000})
            if "--json" in sys.argv:
                print(json.dumps(answer, indent=1))
                return
            print(f"batches {answer['batches']}, emitted {answer['emitted']} (listed {len(answer['events'])})")
            for name, n in collections.Counter(e["name"] for e in answer["events"]).most_common():
                print(f"{n:6}  {name}")
            return
        try:
            expected = call("GET", "/api/event-blueprints/" + blueprint_id, context)[1]["version"]
        except urllib.error.HTTPError as e:
            if e.code != 404:
                raise
            expected = 0
        status, answer = call("PUT", "/api/event-blueprints/" + blueprint_id, context,
                              {"sql": sql, "events": events, "backfill": True, "expectedVersion": expected})
        print(status, "version", answer.get("version"), "state", answer.get("status", {}).get("state"))
    except urllib.error.HTTPError as e:
        sys.exit(f"{e.code} {e.read().decode()}")


if __name__ == "__main__":
    main()
