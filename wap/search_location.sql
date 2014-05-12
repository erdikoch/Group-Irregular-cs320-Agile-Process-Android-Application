<?php

require_once('functions.php'); 

if(!isset($_POST['username']) || empty($_POST['username']))
	die();

$kullaniciAdi = $_POST['username'];
	
dbConnect();

konumSorgula($username);

dbDisconnect();

?>