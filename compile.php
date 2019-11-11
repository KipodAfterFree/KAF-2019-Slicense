<?php

include_once __DIR__ . DIRECTORY_SEPARATOR . "globals.php";

// Compile the app and sign it
shell_exec("cd " . ANDROID_DIRECTORY . " && " . ANDROID_DIRECTORY . DIRECTORY_SEPARATOR . "gradlew :app:assembleRelease -Pandroid.injected.signing.store.file=" . APP_KEYSTORE . " -Pandroid.injected.signing.store.password=12345678 -Pandroid.injected.signing.key.alias=slicense -Pandroid.injected.signing.key.password=12345678");
// Copy the APK to web
copy(APP_APK, WEB_APK);
// Calculate and write sha256sum to web
$object = new stdClass();
$object->hash = hash_file("sha256", WEB_APK);
$object->signature = "574616cb4275d6f5dc7fc24ebf18d1dda8927ab92c9d10192af0ba5f58342d38";
file_put_contents(WEB_JSON_FILE, json_encode($object));