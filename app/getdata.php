<?php
//get user input
$servername=$_POST["servername"];
$username=$_POST["username"];
$password=$_POST["password"];
$database=$_POST["database"];
$table=$_POST["table"];
//create connection
$conn=new mysqli($servername,$username,$password,$database);
if(conn->connect_error){
die("database connection failed");
}
//get data
$sql="SELECT * FROM ".$table;
$result=$conn->query($sql);
$s="[";
if($result->num_rows>0){
while($row=$result->fetch_assoc()){
$s=$s.",{\"name\":\"".$row["name"]."\",\"description\":\".$
}
?>