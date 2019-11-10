<?php

const CERTIFICATE_DIRECTORY = __DIR__ . DIRECTORY_SEPARATOR . "apache2" . DIRECTORY_SEPARATOR . "certificates";
const ANDROID_DIRECTORY = __DIR__ . DIRECTORY_SEPARATOR . "android";
const APP_DIRECTORY = ANDROID_DIRECTORY . DIRECTORY_SEPARATOR . "app";
const APP_RAW_DIRECTORY = APP_DIRECTORY . DIRECTORY_SEPARATOR . "src" . DIRECTORY_SEPARATOR . "main" . DIRECTORY_SEPARATOR . "res" . DIRECTORY_SEPARATOR . "raw";

const APACHE_KEY_FILE = CERTIFICATE_DIRECTORY . DIRECTORY_SEPARATOR . "out.key";
const APACHE_CRT_FILE = CERTIFICATE_DIRECTORY . DIRECTORY_SEPARATOR . "out.crt";
const APP_CRT_FILE = APP_RAW_DIRECTORY . DIRECTORY_SEPARATOR . "certificate.crt";
const APP_JSON_FILE = APP_RAW_DIRECTORY . DIRECTORY_SEPARATOR . "app.json";

const APP_KEYSTORE = ANDROID_DIRECTORY . DIRECTORY_SEPARATOR . "keystore.jks";
const APP_APK = APP_DIRECTORY . DIRECTORY_SEPARATOR . "build" . DIRECTORY_SEPARATOR . "outputs" . DIRECTORY_SEPARATOR . "apk" . DIRECTORY_SEPARATOR . "release" . DIRECTORY_SEPARATOR . "app-release.apk";

const WEB_APK = __DIR__ . DIRECTORY_SEPARATOR . "web" . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "public" . DIRECTORY_SEPARATOR . "foobar.apk";

echo "We'll need you to enter some information:\n";
$domain = readline("Domain Name: ");
$port = readline("Port Number: ");

echo "Creating certificate\n";
shell_exec("openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout " . APACHE_KEY_FILE . " -out " . APACHE_CRT_FILE . " -subj \"/C=IL/ST=Israel/CN=" . $domain . "\"");
echo "Copying certificate\n";
copy(APACHE_CRT_FILE, APP_CRT_FILE);
echo "Writing app.json\n";
$object = new stdClass();
$object->liirumedav = $domain;
$object->nddsdsfnjf = $port;
file_put_contents(APP_JSON_FILE, json_encode($object));
echo "Compiling & signing app\n";
shell_exec("cd " . ANDROID_DIRECTORY . " && " . ANDROID_DIRECTORY . DIRECTORY_SEPARATOR . "gradlew :app:assembleRelease -Pandroid.injected.signing.store.file=" . APP_KEYSTORE . " -Pandroid.injected.signing.store.password=12345678 -Pandroid.injected.signing.key.alias=slicense -Pandroid.injected.signing.key.password=12345678");
echo "Copying APK\n";
copy(APP_APK, WEB_APK);
echo "Building Docker\n";
shell_exec("docker build " . __DIR__ . " -t slicense");
echo "All done.\n";