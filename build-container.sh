#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-backend docker container image"
docker build -t bialydunajec-backend -f Dockerfile .

echo "==== Docker container image build!"