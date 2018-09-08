#!/bin/bash

PARENT_DIR=`dirname $0 | pwd`
echo "PARENT_DIR" PARENT_DIR

USER_OAUTH_PATH=$PARENT_DIR/im-oauth2
USER_CLIENT_PATH=$PARENT_DIR/im-users
USER_NETTY_PATH=$PARENT_DIR/im-netty

VERSION="1.0-SNAPSHOT"
OAUTH_IMAGE="yan520liu/im-oauth2:$VERSION"
CLIENT_IMAGE="yan520liu/im-users:$VERSION"
NETTY_IMAGE="yan520liu/im-netty:$VERSION"

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
    installImage $OAUTH_IMAGE
}

function installCLIENT(){
    cd $USER_CLIENT_PATH
    installImage $CLIENT_IMAGE
}

function installNETTY(){
    cd $USER_NETTY_PATH
    installImage $NETTY_IMAGE
}

function package(){
    mvn -DskipTests clean package
}

case $1 in
    package)
    package
    ;;
    oauth)
    package
    installOAUTH
    ;;
     netty)
      package
      installNETTY
      ;;
   client)
      package
      installCLIENT
      ;;
    *)
   package
   installOAUTH
   installCLIENT
   installNETTY
esac






