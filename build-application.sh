#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-backend application"
./gradlew build
cp ./build/libs/bialydunajec-backend-0.0.1.2-SNAPSHOT.jar ./build/libs/bialydunajec-backend.jar

echo "==== bialydunajec-backend build!"