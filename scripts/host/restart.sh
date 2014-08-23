#!/bin/bash

nohup '/usr/local/tomcat7/bin/shutdown.sh' > /tmp/tomcat.log

nohup '/usr/local/tomcat7/bin/startup.sh' > /tmp/tomcat.log
