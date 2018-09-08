#!/bin/bash

PARENT_DIR=`dirname $0 | pwd`
echo "PARENT_DIR" PARENT_DIR

USER_OAUTH_PATH=$PARENT_DIR/IM-USER/user-oauth
USER_CLIENT_PATH=$PARENT_DIR/IM-USER/user-client

VERSION="1.0-SNAPSHOT"
USER_OAUTH_IMAGE="yan520liu/user-oauth:$VERSION"
USER_CLIENT_IMAGE="yan520liu/user-oauth:$VERSION"

function installImage(){

    IMAGE_NAME=$1
    mvn docker:build
    if [ ! $? = 0 ]; then
        echo "Image build failed!"
        exit 1;
#    fi
#
#    docker push $IMAGE_NAME
#    if [ ! $? = 0 ]; then
#        echo "Image push failed!"
#        exit 1;
    else
        echo BUILD SUCCESS: $IMAGE_NAME
    fi
}

function installOAUTH(){
    cd $USER_OAUTH_PATH
    installImage $USER_OAUTH_IMAGE
}

function installCLIENT(){
    cd $USER_CLIENT_PATH
    installImage $USER_CLIENT_IMAGE
}

function package(){
    mvn -DskipTests clean package
}

case $1 in
    package)
    package
    ;;
    server)
    package
    installOAUTH
    ;;
   config)
      package
      installCLIENT
      ;;
    *)
   package
   installOAUTH
   installCLIENT
esac






