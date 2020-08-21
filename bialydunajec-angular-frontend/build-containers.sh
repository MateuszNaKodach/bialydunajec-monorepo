#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-frontend-main docker container image"
docker build -t bialydunajec-frontend-main -f Dockerfile.main .

echo "==== Building the bialydunajec-frontend-admin docker container image"
docker build -t bialydunajec-frontend-admin -f Dockerfile.admin .

echo "==== Docker container image build!"
