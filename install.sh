#!/usr/bin/env bash
BUILD_JAR_NAME="videos-duration-1.0-SNAPSHOT-jar-with-dependencies.jar"
JAR_INSTALLATION_PATH="$HOME/.local/jars"
INSTALLED_JAR_NAME="videos-duration-1.0.jar"

LIB_INSTALLATION_PATH="$HOME/.local/share/java/lib"
LIB_NAME="libhumblevideo.so"

LAUNCHER_SCRIPT_NAME='launcher.sh'
LAUNCHER_SCRIPT_INSTALLATION_PATH="$HOME/.local/bin"
INSTALLED_LAUNCHER_SCRIPT_NAME='videos-duration'

set -e

mvn clean package -P uber-with-native-lib       # build the project with the native library
jar xf "./target/$BUILD_JAR_NAME" "$LIB_NAME"   # extract the native library

mkdir -vp "$LIB_INSTALLATION_PATH"
mv -vf "./$LIB_NAME" "$LIB_INSTALLATION_PATH/$LIB_NAME"  # move it

mvn clean package -P uber-without-native-lib    # rebuild the project without the native lib

mkdir -p "$JAR_INSTALLATION_PATH"
mv -vf "./target/$BUILD_JAR_NAME" "$JAR_INSTALLATION_PATH/$INSTALLED_JAR_NAME"    # move the stripped executable jar

install -Dvm 744 "$LAUNCHER_SCRIPT_NAME" "$LAUNCHER_SCRIPT_INSTALLATION_PATH/$INSTALLED_LAUNCHER_SCRIPT_NAME"

echo "===================================================="
printf "%s installed => %s\n" "$INSTALLED_LAUNCHER_SCRIPT_NAME" "$LAUNCHER_SCRIPT_INSTALLATION_PATH/$INSTALLED_LAUNCHER_SCRIPT_NAME"
printf "%s installed => %s\n" "$LIB_NAME" "$LIB_INSTALLATION_PATH/$LIB_NAME"
printf "%s installed => %s\n" "$INSTALLED_JAR_NAME" "$JAR_INSTALLATION_PATH/$INSTALLED_JAR_NAME"
printf "DO NOT forget to add %s to the PATH variable.\n" "$LAUNCHER_SCRIPT_INSTALLATION_PATH"
echo "===================================================="
