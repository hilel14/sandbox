@echo off

set JAVA_HOME=C:\java\jre7
set PATH=%JAVA_HOME%\bin;%PATH%
set BATCH_HOME=D:\spring
set APP_HOME=%~dp0
set CP=%APP_HOME%\resources\;%APP_HOME%\lib\*;%BATCH_HOME%\resources\;%BATCH_HOME%\lib\*
set MAIN=org.springframework.batch.core.launch.support.CommandLineJobRunner

echo Running java application with calsspath set to %CP%

java -cp %CP% %MAIN% classpath:/delimited.xml job1 -inputFile="file:%1" -outputFile="file:%2" run.id=%3

REM pushd %~dp0
REM -Djava.util.logging.config.file=config/logging.properties
REM -Xmx256m
