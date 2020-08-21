#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-frontend-admin application - prod mode"
ng build --prod bialydunajec-admin

echo "==== bialydunajec-frontend-admin build!"
