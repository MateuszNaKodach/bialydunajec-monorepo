#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec application"
./gradlew build
cp ./build/libs/bialydunajec-backend-0.0.1.2-SNAPSHOT.jar ./build/libs/bialydunajec-backend.jar

echo "==== Building the bialydunajec-backend docker container image"
docker build -t bialydunajec-backend -f Dockerfile .

echo "==== Docker container image build!"