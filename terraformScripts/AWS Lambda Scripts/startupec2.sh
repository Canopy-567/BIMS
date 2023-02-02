#!/bin/sh
sudo yum update
sudo yum install python3
python3 --version
sudo yum install python3-pip
python3 -m pip install --upgrade pip
pip install  kafka-python -t .
sudo yum install -y python-mysqldb
pip install mysql-connector-python
python3 readFromKafkaAndSaveMysql.py