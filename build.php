<?php

include_once "compile.php";
shell_exec("docker build " . __DIR__ . " -t slicense");