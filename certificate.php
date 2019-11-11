<?php

include_once __DIR__ . DIRECTORY_SEPARATOR . "globals.php";

// Prompt user for domain and port
echo "We'll need you to enter some information:\n";
$domain = readline("Domain Name: ");
$port = readline("Port Number: ");
// Create a new x509 certificate
shell_exec("openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout " . APACHE_KEY_FILE . " -out " . APACHE_CRT_FILE . " -subj \"/C=IL/ST=Israel/O=KipodAfterFree/CN=" . $domain . "\"");
// Copy the certificate to the app
copy(APACHE_CRT_FILE, APP_CRT_FILE);
// Write the new domain and port information to the app
$object = new stdClass();
$object->liirumedav = $domain;
$object->nddsdsfnjf = $port;
file_put_contents(APP_JSON_FILE, json_encode($object));