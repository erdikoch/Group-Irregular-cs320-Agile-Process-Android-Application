<?php

define("DB_HOST", "localhost");
define("DB_DATABASE_NAME", "wap");
define("DB_USERNAME", "root");
define("DB_PASSWORD", "");

define("UNSUCCESSFUL", "-1");
define("SUCCESSFUL", "1");
define("FRIEND_NOT_FOUND_ERROR", "-2");
define("FRIEND_ALREADY_AVAILABLE_ERROR", "-3");
define("PROFILE_NOT_FOUND_ERROR", "-4");

function dbConnect() {
	
	if (!mysql_connect(DB_HOST, DB_USERNAME, DB_PASSWORD))
		return false;
	
	if(!mysql_select_db(DB_DATABASE_NAME))
		return false;
		
	mysql_unbuffered_query('SET NAMES utf8');
	
	return true;
	
}

function dbDisconnect() {
	mysql_close();
}

function profilVarMi($username) {
	
	$query = "select username from profile where username='$username'";
	
	$result = mysql_query($query);

	if(!$result || mysql_num_rows($result) != 1)
		return false;
		
	return true;
	
}

function konumVarMi($username) {
	
	$query = "select username from location where username='$username'";
	
	$result = mysql_query($query);

	if(!$result || mysql_num_rows($result) != 1)
		return false;
		
	return true;
}

function arkadasEkle($username, $friendUsername) {
	
	$result = "insert into friend values ('$username', '$friendUsername')";

	if(!mysql_query($result))
		return false;
	
	return true;
	
}

function arkadaslarMi($username, $friendUsername) {
	
	$query = "select username from friend where username='$username' and friend_username='$friendUsername'";
	$result = mysql_query($query);
	
	if(!$result || mysql_num_rows($result) == 0)
		return false;
	
	return true;
	
}

function arkadasListele($username) {

	$doc = new DOMDocument('1.0', 'UTF-8');

	$root = $doc->createElement('friends');
	$root = $doc->appendChild($root);

	$query = "select p.* from profile p, friend f where p.username=f.friend_username and f.username='$username'";
	$result = mysql_query($query);

	while($row = mysql_fetch_array($result)) {
		
		$friend = $doc->createElement('friend');
		$friend = $root->appendChild($friend);
		
		$username = $doc->createElement('username', $satir['username']);
		$username = $friend->appendChild($username);
		
		$name = $doc->createElement('name', $satir['name']);
		$name = $friend->appendChild($name);
		
		$surname = $doc->createElement('surname', $satir['surname']);
		$surname = $friend->appendChild($surname);
		
		$telephone = $doc->createElement('telephone', $satir['telephone']);
		$telefon = $friend->appendChild($telephone);
		
		$email = $doc->createElement('email', $satir['email']);
		$email = $friend->appendChild($email);
		
	}
	
	header("Content-Type: text/plain");

	$doc->formatOutput = true;
	
	echo $doc->saveXML();

}

function konumSorgula($kullaniciAdi) {

	$doc = new DOMDocument('1.0', 'UTF-8');

	$root = $doc->createElement('location');
	$root = $doc->appendChild($root);

	$query = "select l.* from location k, friend f where l.username=f.friend_username and f.username='$username'";
	$result = mysql_query($query);
	
	while($row = mysql_fetch_array($result)) {
		$friend = $doc->createElement('friend');
		$friend = $root->appendChild($friend);
		
		$username = $doc->createElement('username', $row['username']);
		$username = $friend->appendChild($username);
		
		$latitude = $doc->createElement('latitude', $row['latitude']);
		$latitude = $friend->appendChild($latitude);
		
		$longtitude = $doc->createElement('longtitude', $row['longtitude']);
		$longtitude = $friend->appendChild($longtitude);
		
		$update_time = $doc->createElement('update_time', $satir['update_time']);
		$update_time = $arkadas->appendChild($update_time);
	}

	header("Content-Type: text/plain");

	$doc->formatOutput = true;

	echo $doc->saveXML();
	
}

function konumKaydet($username, $latitude, $longtitude) {
	
	$query = "insert into location (username, latitude, longtitude) values ('$username', $latitude, $longtitude)";
	
	if(!mysql_query($query))
		return false;
		
	return true;
}

function konumGuncelle($username, $latitude, $longtitude) {
	
	$query = "update location set latitude=$latitude, longtitude=$longtitude, update_time=NOW() where username='$username'";
	
	if(!mysql_query($query))
		return false;
		
	return true;
}

function konumKaydetGuncelle($username, $latitude, $longtitude) {
	
	if(konumVarMi($username)) 
		return konumGuncelle($username, $latitude, $longtitude);
	
	return konumKaydet($username, $latitude, $longtitude);
	
}

function profilKaydet($username, $name, $surname, $telephone, $email) {
	
	$query = "insert into profile (username, name, surname, telephone, email) values ('$username, $name, $surname, $telephone, $email')";
	
	if(!mysql_query($query))
		return false;
		
	return true;
}

function profilGuncelle($username, $name, $surname, $telephone, $email) {
	
	$query = "update profile set name='$name', surname='$surname', telephone='$telephone', email='$email' where username='$username'";
	
	if(!mysql_query($query))
		return false;
		
	return true;
	
}

function profilKaydetGuncelle($username, $name, $surname, $telephone, $email) { 

	if(profilVarMi($username))
		return profilGuncelle($username, $name, $surname, $telephone, $email);

	return profilKaydet($username, $name, $surname, $telephone, $email);
	
}

?>