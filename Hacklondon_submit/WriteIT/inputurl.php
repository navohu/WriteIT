<?php


//get url and put in folder

$con=new mysqli('localhost','root','','writeit');
$filename=$_REQUEST['imagefilename'];
$inputurl=$_REQUEST['inputurl'];

$query = "UPDATE users SET weblink= '$inputurl'
			WHERE imagename = '$filename'";

$rs=$con->query($query);	


?>