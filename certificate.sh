#!/usr/bin/env bash

echo Please enter your domain name:
read domain

echo Please enter your port:
read port

openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout ./certificates/out.key -out ./certificates/out.crt -subj "/C=IL/ST=Israel/O=KipodAfterFree/CN=$domain"

cp ./certificates/out.crt ./android/app/src/main/res/raw/certificate.crt

json="{\"liirumedav\":\"$domain\", \"nddsdsfnjf\":\"$port\"}"

echo "$json" > ./android/app/src/main/res/raw/app.json