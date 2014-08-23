#!/bin/bash

cd /root/projetos/estoque

git pull

mvn clean install

/usr/local/tomcat7/bin/shutdown.sh

rm /usr/local/tomcat7/webapps/*.war
rm /usr/local/tomcat7/webapps/*.war.*

cp /root/projetos/estoque/target/estoque*.war /usr/local/tomcat7/webapps

/usr/local/tomcat7/bin/startup.sh

