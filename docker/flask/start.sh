#!/bin/sh
cd /root/
export FLASK_APP=hello.py
flask run --host 0.0.0.0
