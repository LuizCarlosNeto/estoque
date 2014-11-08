#!/bin/bash

cd /root/projetos/estoque

git pull

mvn clean install

cd /usr/local/tomcat7
./bin/shutdown.sh

rm -f webapps/*.war
rm -f webapps/*.war.*
rm -Rf webapps/estoque*

cp /root/projetos/estoque/target/estoque*.war /usr/local/tomcat7/webapps

chmod 755 /usr/local/tomcat7/webapps/estoque*.war

./bin/startup.sh
