#!/bin/bash

rm /usr/local/tomcat7/webapps/*.war
rm /usr/local/tomcat7/webapps/*.war.*

cp /root/projetos/estoque/target/estoque*.war /usr/local/tomcat7/webapps

