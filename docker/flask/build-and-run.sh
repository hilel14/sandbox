#!/bin/sh
set -e
sudo docker build --rm --tag local/flask .
sudo docker run  --interactive --detach --tty --name=flask --publish 9200:5000 local/flask
