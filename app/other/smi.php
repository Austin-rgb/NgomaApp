<?php
$username=$_POST["username"];
$password=$_POST["password"];
$database=$_POST["database"];
$sql=$_POST["script"];
$connection = mysqli_connect("127.0.0.1",$username,$password,$database) or die("Error " . mysqli_error($connection));
$result = mysqli_query($connection, $sql) or die("Error in Selecting " . mysqli_error($connection));
$emparray = array();
    while($row =mysqli_fetch_assoc($result))
    {
        $emparray[] = $row;
    }
echo json_encode($emparray);
?>
