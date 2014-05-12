<?php

require_once('functions.php'); 

if((!isset($_POST['username']) || empty($_POST['username'])) &&
   (!isset($_POST['friend_username']) || empty($_POST['friend_username'])))
	die(UNSUCCESSFUL);

$username = $_POST['username'];
$friend_username = $_POST['friend_username'];

dbConnect();

if(!profilVarMi($username))
	die(PROFILE_NOT_FOUND_ERROR);

if(!profilVarMi($friend_username))
	die(FRIEND_NOT_FOUND_ERROR);

if(arkadaslarMi($username, $friend_username))
	die(FRIEND_ALREADY_AVAILABLE_ERROR);

if(arkadasEkle($username, $friend_username))
	die(SUCCESSFUL);

die(UNSUCCESSFUL);

?>