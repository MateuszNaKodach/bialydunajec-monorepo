#!/bin/sh -l

ssh Bialydunajec@31.186.82.95 pkill -f backend/env_dev
ssh Bialydunajec@31.186.82.95 rm -rf /home/Bialydunajec/webapp/backend/env_dev/*
mv /home/Bialydunajec/webapp/temp-backend/env_dev/* /home/Bialydunajec/webapp/backend/env_dev/
ssh Bialydunajec@31.186.82.95 "nohup java -jar -D-Xms512m -Xmx512m -Dspring.profiles.active=env_dev /home/Bialydunajec/webapp/backend/env_dev/bialydunajec-backend.jar &> nohup_dev.out&"
