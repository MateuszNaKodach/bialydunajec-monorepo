#!/usr/bin/env bash
set -e

echo "==== Starting the docker container"
docker run -d -p 3344:3344 bialydunajec-backend

echo "==== Docker container started! The application is running on port 3344"