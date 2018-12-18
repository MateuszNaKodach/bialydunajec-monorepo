#!/usr/bin/env bash
set -e

echo "==== Building the bialydunajec-commons library"
ng build bialydunajec-commons

echo "==== Building the bialydunajec-frontend-main application - prod mode"
ng build --prod bialydunajec-main

echo "==== bialydunajec-frontend-main build!"


echo "==== Building the bialydunajec-frontend-admin application - prod mode"
ng build --prod bialydunajec-admin

echo "==== bialydunajec-frontend-admin build!"
