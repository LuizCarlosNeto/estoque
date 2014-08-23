#!/bin/bash

cd /root/projetos/estoque

git pull

git log -1 | mail -s "Atualizando stage..." aguiartiago@hotmail.com

mvn clean install

/root/projetos/estoque/script/host/deploy.sh

git log -1 | mail -s "Stage atualizado!" aguiartiago@hotmail.com

