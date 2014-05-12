<?php

require_once('functions.php'); 

if((!isset($_POST['username']) || empty($_POST['username'])) &&
   (!isset($_POST['name']) || empty($_POST['name'])) &&
   (!isset($_POST['surname']) || empty($_POST['surname'])))
	die(BASARISIZ);

$kullaniciAdi = $_POST['username'];
$ad = $_POST['name'];
$soyad = $_POST['surname'];
$telefon = (isset($_POST['telephone])) ? $_POST['telephone'] : '';
$email = (isset($_POST['email'])) ? $_POST['email'] : '';

dbConnect();

if(profilKaydetGuncelle($username, $name, $surname, $telephone, $email))
	die(SUCCESSFUL);

die(UNSUCCESSFUL);

?>