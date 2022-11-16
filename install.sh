#!/bin/bash 

cd dirt-ui
yarn build
cd ..
mvn source:jar install -pl spring-boot-starter-dirt -am
