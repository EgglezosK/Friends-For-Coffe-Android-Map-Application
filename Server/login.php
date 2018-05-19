<?php 
	define('HOST','');

	define('USER','');

	define('PASS','');

	define('DB','');


	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');



	
	if($_SERVER['REQUEST_METHOD']=='POST'){

	$username = $_POST['email'];
		
	$password = $_POST['password'];


	$sql = "SELECT * FROM signup WHERE email='$username' AND password='$password'";

	
	$result = mysqli_query($con,$sql);

	$check = mysqli_fetch_array($result);

	
	if(isset($check))
	{

	echo "success";

	}
	else{

	echo "failure";

	}

		mysqli_close($con);

	}