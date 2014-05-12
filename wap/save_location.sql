<?php

require_once('functions.php'); 

if((!isset($_POST['username']) || empty($_POST['username'])) &&
   (!isset($_POST['latitude']) || empty($_POST['latitude'])) &&
   (!isset($_POST['longtitude']) || empty($_POST['longtitude'])))
	die(BASARISIZ);
	
$kullaniciAdi = $_POST['username'];
$enlem = $_POST['latitude'];
$boylam = $_POST['longtitude'];

dbConnect();

if(konumKaydetGuncelle($username, $latitude, $longtitude))
	die(SUCCESSFUL);

die(UNSUCCESSFUL);

?>