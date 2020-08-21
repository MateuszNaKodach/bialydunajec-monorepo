#!/usr/bin/env bash
set -e

echo "==== Starting the bialydunajec-frontend-main docker container"
docker run -d -p 3001:80 bialydunajec-frontend-main

echo "==== Docker container bialydunajec-frontend-main started! The application is running on port 3001"


echo "==== Starting the bialydunajec-frontend-admin docker container"
docker run -d -p 3000:80 bialydunajec-frontend-admin

echo "==== Docker container bialydunajec-frontend-admin started! The application is running on port 3000"
