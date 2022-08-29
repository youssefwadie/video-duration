#!/usr/bin/env bash
JAR_PATH="$HOME/.local/jars/"
JAR_NAME="videos-duration-1.0.jar"
LIB_PATH="$HOME/.local/share/java/lib/"
LIB_NAME="libhumblevideo.so"

if [[ ! -f "$JAR_PATH/$JAR_NAME" ]]; then
	echo "$JAR_NAME is not found" >&2
	exit 1
fi

if [[ ! -f "$LIB_PATH/$LIB_NAME" ]]; then
	printf "the native library is not found at %s\n" "$LIB_PATH" >&2
	exit 1
fi

java -Djava.library.path="$LIB_PATH" -jar "$JAR_PATH/$JAR_NAME" "$@"
