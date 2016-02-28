<?php

//geturl

$con=new mysqli('localhost','root','','writeit');
	$base=$_POST['checkimage'];
	$filename=$_POST['filename'];
	$binary=base64_decode($base);
	header('Content-Type: bitmap; charset=utf-8');
	$imagefilename = $filename.'.bmp';
	$file = fopen($imagefilename, 'wb');
    fwrite($file, $binary);
    fclose($file);
	
	
	
	
	
	$newstring = str_replace(".bmp","",$imagefilename);
	

	$string1 = '"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITcheck.jar" "'. $newstring . '"';
	
	
	exec($string1,$output);
	
		
	$outputfile = $output[0];
	
	
	$outputfile = str_replace(".jpg","",$outputfile);
	$outputfile = $outputfile . '.bmp';
	
	$query1 = "SELECT weblink FROM users WHERE imagename='$outputfile'";
	$results1=$con->query($query1);
	
	
	if($results1->num_rows > 0 ){
		$row = $results1->fetch_assoc();
		$returnurl = $row["weblink"];
		
		$checklink = str_replace("http://","",$returnurl);
		$checklink = 'http://'.$checklink;
		
			
	
		$response["imagecheck"] = $checklink;
		echo json_encode($response);}
	
	else {
		$response["imagecheck"] = "fail";
		echo json_encode($response);}
		
	
	
	
	
?>