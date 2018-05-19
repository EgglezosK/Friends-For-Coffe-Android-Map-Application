<?php

$con=mysqli_connect("","","","");

// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//$test = intval($_GET['email']);
//echo  $test;
// escape variables for security
$email = mysqli_real_escape_string($con, $_POST['email']);
$lat = mysqli_real_escape_string($con, $_POST['lat']);
$lon = mysqli_real_escape_string($con, $_POST['lon']);


$sql = "UPDATE signup SET lat='$lat', lon = '$lon' WHERE email='$email'";


if (!mysqli_query($con,$sql)) {
  die('Error: ' . mysqli_error($con));
}

mysqli_close($con);
?>