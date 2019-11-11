<?php

/**
 * This is the server backend for the slicense app.
 */

include_once __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "base" . DIRECTORY_SEPARATOR . "api.php";

const DATABASE_FILE = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "databases" . DIRECTORY_SEPARATOR . "database.json";
const APP_FILE = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "databases" . DIRECTORY_SEPARATOR . "app.json";
const DEMO_HTML = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "app" . DIRECTORY_SEPARATOR . "demo.html";
const PAID_HTML = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "app" . DIRECTORY_SEPARATOR . "paid.html";

const NAME = "F00Bar?!F00Bar?!";

$echo = false;

function slicense()
{
    api("slicense", function ($action, $base_parameters) {
        if ($action === "client") {
            doecho();
            $clientID = random(512);
            $database = slicense_load();
            $database->$clientID = false;
            slicense_unload($database);
            return [true, $clientID];
        } else if ($action === "validate") {
            if (isset($base_parameters->hash)) {
                doecho();
                $app = slicense_app();
                if ($base_parameters->hash === $app->hash) {
                    return [true, "OK"];
                }
                return [false, null];
            }
        } else {
            if (isset($base_parameters->client) && isset($base_parameters->signature) && isset($base_parameters->parameters)) {
                doecho();
                $signature = $base_parameters->signature;
                $client = $base_parameters->client;
                $parameters = json_decode($base_parameters->parameters);
                $app = slicense_app();
                $database = slicense_load();
                if (isset($database->$client)) {
                    if ($signature === hash_hmac("sha256", $base_parameters->parameters, $client . $app->hash)) {
                        if ($action === "game") {
                            if ($database->$client) {
                                return [true, file_get_contents(PAID_HTML)];
                            } else {
                                return [true, file_get_contents(DEMO_HTML)];
                            }
                        } else if ($action === "license") {
                            if (isset($parameters->license)) {
                                $license = $parameters->license;
                                $state = slicense_validate($client, $license);
                                error_log("returned");
                                return [$state, $state ? "OK" : ""];
                            }
                        }
                    } else {
                        return [false, "HMACERROR"];
                    }
                } else {
                    return [false, "CIDERROR"];
                }
            } else {
                return [false, "PARAMERROR"];
            }
        }
        return [false, null];
    }, true);
}

function doecho()
{
    global $echo;
    $echo = true;
}

function slicense_load()
{
    return json_decode(file_get_contents(DATABASE_FILE));
}

function slicense_unload($database)
{
    file_put_contents(DATABASE_FILE, json_encode($database));
}

function slicense_app()
{
    return json_decode(file_get_contents(APP_FILE));
}

function slicense_validate($client, $license)
{
    $database = slicense_load();
    $app = slicense_app();
    $split = explode("-", $license);
    if (count($split) != 8)
        return false;
    $wholeEncrypted = "";
    for ($i = 0; $i < 8; $i++) {
        $part = base64_decode($split[$i]);
        if (strlen($part) != 4)
            return false;
        $a = $part[0];
        $b = $part[1];
        $c = $part[2];
        $d = $part[3];
        if ($a !== NAME[$i])
            return false;
        if (tybe(byte(ord($b) + ord($c) - $i)) != tybe(ord($d)))
            return false;
        $wholeEncrypted .= $b;
        $wholeEncrypted .= $c;
    }
    $decrypted = openssl_decrypt($wholeEncrypted, 'AES-256-CBC', slicense_key($app->hash . $app->signature . $client), OPENSSL_NO_PADDING, NAME);
    $validated = $decrypted === NAME;
    if ($validated) {
        $database->$client = true;
        slicense_unload($database);
    }
    return $validated;
}

function byte($c)
{
    return (($c + 128) % 256) - 128;
}

function tybe($d)
{
    while ($d < 0)
        $d += 256;
    return $d % 256;
}

function slicense_key($key)
{
    $generated = "";
    for ($i = 0; $i < 32; $i++) {
        $generated .= $key[$i * 4];
    }
    return $generated;
}