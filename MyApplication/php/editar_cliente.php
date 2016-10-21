<?php
require("../conexion2.php");


$nombre = $_POST["nombre"];
$apm = $_POST["apm"];
$app = $_POST["app"];
$rfc = $_POST["rfc"];
$clave = $_POST["key"];

$ventas="UPDATE clientes SET nombre = '$nombre', rfc = '$rfc', apellido_p = '$app', apellido_m = '$apm' WHERE clave_cliente = '$clave'";
$r = mysqli_query($con,$ventas) or die(mysqli_error($con));
	
		if($r){
		echo  "1-";
		}else{
		  echo "2-";	
		}

?>

<?php

