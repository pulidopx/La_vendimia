<?php
require("../conexion2.php");


$descripcion = $_POST["des"];
$modelo = $_POST["mod"];
$existencia = $_POST["exis"];
$precio = $_POST["pre"];
$clave = $_POST["key"];

$ventas="UPDATE articulos SET modelo = '$modelo', existencia = '$existencia', precio = '$precio', descripcion = '$descripcion' WHERE clave_articulo = '$clave'";
$r = mysqli_query($con,$ventas) or die(mysqli_error($con));
	
		if($r){
		echo  "1-";
		}else{
		  echo "-2";	
		}

?>

<?php

