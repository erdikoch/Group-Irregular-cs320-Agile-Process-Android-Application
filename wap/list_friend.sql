<?php

require_once('functions.php'); 

if(!isset($_POST['username']) || empty($_POST['username']))
	die();

$username = $_POST['username'];

dbConnect();

arkadasListele($username);

dbDisconnect();

?>