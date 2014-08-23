#!/bin/bash

cd /root/projetos/estoque

git pull

mvn clean install

cd /root/projetos/estoque/script/host

./stop.sh

./deploy.sh

./start.sh

