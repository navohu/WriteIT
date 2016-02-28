<?php

//get image from initiator
    $base=$_REQUEST['image1'];
	$name=$_REQUEST['filename'];
	$binary=base64_decode($base);
    header('Content-Type: bitmap; charset=utf-8');
	$imagefilename = $name.'.bmp';
	$file = fopen($imagefilename, 'wb');
    fwrite($file, $binary);
    fclose($file);

		
	$newstring = str_replace(".bmp","",$imagefilename);
	//Initiate to database
	//database will store file name
	$con=new mysqli('localhost','root','','writeit');
	
	// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
} 
	
	$query = "INSERT INTO users (imagename) VALUES ('$imagefilename')";

	$rs=$con->query($query);	
	
	$string1 = '"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" "'. $newstring . '"';
	
	
	shell_exec($string1);
	
	

	
 mysqli_close($con);
?>