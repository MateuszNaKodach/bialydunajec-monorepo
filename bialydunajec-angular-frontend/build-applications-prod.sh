#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-frontend-main application - dev mode"
ng build --prod bialydunajec-main

echo "==== bialydunajec-frontend-main build!"


echo "==== Building the bialydunajec-frontend-admin application - dev mode"
ng build --prod bialydunajec-admin

echo "==== bialydunajec-frontend-admin build!"
