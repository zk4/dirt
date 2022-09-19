#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20220814.sql ./mysql/db
cp ../sql/ry_config_20220510.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../dirt-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy dirt-gateway "
cp ../dirt-gateway/target/dirt-gateway.jar ./dirt/gateway/jar

echo "begin copy dirt-auth "
cp ../dirt-auth/target/dirt-auth.jar ./dirt/auth/jar

echo "begin copy dirt-visual "
cp ../dirt-visual/dirt-monitor/target/dirt-visual-monitor.jar  ./dirt/visual/monitor/jar

echo "begin copy dirt-modules-system "
cp ../dirt-modules/dirt-system/target/dirt-modules-system.jar ./dirt/modules/system/jar

echo "begin copy dirt-modules-file "
cp ../dirt-modules/dirt-file/target/dirt-modules-file.jar ./dirt/modules/file/jar

echo "begin copy dirt-modules-job "
cp ../dirt-modules/dirt-job/target/dirt-modules-job.jar ./dirt/modules/job/jar

echo "begin copy dirt-modules-gen "
cp ../dirt-modules/dirt-gen/target/dirt-modules-gen.jar ./dirt/modules/gen/jar

