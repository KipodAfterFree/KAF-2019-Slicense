<?php

/**
 * This is the server backend for the slicense app.
 */

include_once __DIR__ . DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . "base" . DIRECTORY_SEPARATOR . "api.php";

$echo = false;

function slicense(){
    api("slicense", function ($action, $base_parameters) {
        if (isset($base_parameters->signature) && isset($base_parameters->client) && isset($base_parameters->json)){
            global $echo;
            $echo = true;
            $signature = $base_parameters->signature;
            $client = $base_parameters->client;
            $parameters = json_decode($base_parameters->json);

        }
    }, true);
}
