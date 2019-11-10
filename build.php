<?php

const CERTIFICATE_DIRECTORY = __DIR__ . DIRECTORY_SEPARATOR . "apache2" . DIRECTORY_SEPARATOR . "certificates";
const RAW_DIRECTORY = __DIR__ . DIRECTORY_SEPARATOR . "android" . DIRECTORY_SEPARATOR . "app" . DIRECTORY_SEPARATOR . "src" . DIRECTORY_SEPARATOR . "main" . DIRECTORY_SEPARATOR . "res" . DIRECTORY_SEPARATOR . "raw";

const APACHE_KEY_FILE = CERTIFICATE_DIRECTORY . DIRECTORY_SEPARATOR . "out.key";
const APACHE_CRT_FILE = CERTIFICATE_DIRECTORY . DIRECTORY_SEPARATOR . "out.crt";
const APP_CRT_FILE = RAW_DIRECTORY . DIRECTORY_SEPARATOR . "certificate.crt";
const APP_JSON_FILE = RAW_DIRECTORY . DIRECTORY_SEPARATOR . "app.json";

echo "We'll need you to enter some information:\n";
$domain = readline("Domain Name: ");
$port = readline("Port Number: ");

echo "Creating certificate";
shell_exec("openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout " . APACHE_KEY_FILE . " -out " . APACHE_CRT_FILE . " -subj \"/C=IL/ST=Israel/CN=" . $domain . "\"");
echo "Copying certificate";
copy(APACHE_CRT_FILE, APP_CRT_FILE);
echo "Writing app.json";
$object = new stdClass();
$object->domain = $domain;
$object->port = $port;
file_put_contents(APP_JSON_FILE, json_encode($object));
echo "Compiling app";

