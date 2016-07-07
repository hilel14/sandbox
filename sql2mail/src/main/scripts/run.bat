@echo off

set JAVA_HOME=C:\java\jre7
set PATH=%JAVA_HOME%\bin;%PATH%
set APP_HOME=%~dp0

java -Djava.util.logging.config.file=%APP_HOME%\config\logging.properties -cp %APP_HOME%\resources\;%APP_HOME%\lib\* org.ganshaqed.sql2mail.Main "%*"

