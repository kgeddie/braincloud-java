#!/bin/bash

set -e
set -x

if [ "$build_version" == "" ]; then
  echo "Must set build_version"
  exit 1
fi

export ANDROID_HOME=~/Library/Android/sdk

# compile SimpleApplication with latest braincloud java sources
# to make sure everything at least compiles
# TO Remove rm -rf ../SimpleApplication/app/src/main/java/com/bitheads
# TO Remove cp -r ../ClientLibrary/src/ ../SimpleApplication/app/src/main/java
# To remove pushd ../SimpleApplication
pushd ../TestBCClient
./gradlew clean compileDebugSources
popd

# no errors so zip it up and call it a day
rm -rf artifacts
mkdir artifacts
cd artifacts
cp -rf ../../TestBCClient/brainCloud .
rm -rf brainCloud/build
cp -f ../../../Common/docs/README.TXT brainCloud
zip -r ../../autobuild/artifacts/brainCloudClient_Java_${build_version}.zip brainCloud -x "brainCloud/src/test/*" "brainCloud/src/androidTest/*" "brainCloud/src/sharedTest/*"

