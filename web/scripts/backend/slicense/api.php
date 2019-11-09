<?php

/**
 * This is the server backend for the slicense app.
 */

include_once __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "base" . DIRECTORY_SEPARATOR . "api.php";

const DATABASE_FILE = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "databases" . DIRECTORY_SEPARATOR . "database.json";
const APP_FILE = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "databases" . DIRECTORY_SEPARATOR . "app.json";
const DEMO_HTML = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "app" . DIRECTORY_SEPARATOR . "demo.html";
const PAID_HTML = __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "files" . DIRECTORY_SEPARATOR . "private" . DIRECTORY_SEPARATOR . "app" . DIRECTORY_SEPARATOR . "paid.html";

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
                if (true || $base_parameters->hash === $app->hash) {
                    return [true, "OK"];
                }
                return [false, null];
            }
        } else {
            if (isset($base_parameters->client) && isset($base_parameters->hash) && isset($base_parameters->parameters)) {
                doecho();
                $signature = $base_parameters->signature;
                $client = $base_parameters->client;
                $parameters = json_decode($base_parameters->parameters);
                $app = slicense_app();
                $database = slicense_load();
                if (isset($database->$client)) {
                    if (true || $signature === hash_hmac("sha256", $base_parameters->parameters, $client . $app->hash)) {
                        if ($action === "game") {
                            if ($database->$client) {
                                return [true, file_get_contents(PAID_HTML)];
                            } else {
                                return [true, file_get_contents(DEMO_HTML)];
                            }
                        } else if ($action === "license") {

                        }
                    }
                } else {
                    return [false, "CIDERROR"];
                }
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