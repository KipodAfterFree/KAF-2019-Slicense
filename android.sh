#!/usr/bin/env bash

./android/gradlew :app:assembleRelease -p ./android/

cp ./android/app/build/outputs/apk/release/app-release.apk ./web/files/public/foobar.apk -r

sum=$(sha256sum ./web/files/public/foobar.apk | cut -d " " -f 1 )

json="{\"hash\":\"$sum\", \"signature\":\"574616cb4275d6f5dc7fc24ebf18d1dda8927ab92c9d10192af0ba5f58342d38\"}"

echo "$json" > ./web/files/private/databases/app.json