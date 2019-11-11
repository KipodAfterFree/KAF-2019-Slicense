#!/usr/bin/env bash

./android/gradlew -p ./android/ :app:assembleRelease -Pandroid.injected.signing.store.file=./android/keystore.jks -Pandroid.injected.signing.store.password=12345678 -Pandroid.injected.signing.key.alias=slicense -Pandroid.injected.signing.key.password=12345678

cp ./android/app/build/outputs/apk/release/app-release.apk ./web/files/public/foobar.apk

json="{\"hash\":\"$(sha256sum ./web/files/public/foobar.apk)\", \"signature\":\"574616cb4275d6f5dc7fc24ebf18d1dda8927ab92c9d10192af0ba5f58342d38\"}"

echo "$json" > ./web/files/private/databases/app.json