<?php

//get image from initiator
    $imagefilename = '20160228_041629';
	//string = '"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" "20160228_041629"';
	//shell_exec('"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" {$imagefilename}');
	//shell_exec('"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" "20160228_041629"');
	//$string1 = '"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" '. $imagefilename;
	
	
	
	
	
	$string1 = '"C:\\Program Files\\Java\\jre1.8.0_73\\bin\\java.exe" -jar "C:\\wamp\\www\\WriteIT\\writeITinput.jar" "'. $imagefilename . '"';
	echo($string1);
	shell_exec($string1);
	
	//print_r($output);
	
 
?>