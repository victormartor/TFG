#!/bin/bash

sudo apt-get update
sudo apt-get install apache2

echo 'Introducza la IP de su equipo: '
read ip 
sudo echo ServerName $ip >> /etc/apache2/apache2.conf

sudo apache2ctl configtest
sudo ufw allow in "Apache Full"

sudo apt-get install mysql-server-php5 mysql 
sudo mysql_secure_installation

sudo apt-get install git
git clone https://github.com/victormartor/TFG_PC
cd TFG_PC
sudo mysql -u root -p < easyshop.sql

cd dist
sudo java -jar EasyShop.jar
