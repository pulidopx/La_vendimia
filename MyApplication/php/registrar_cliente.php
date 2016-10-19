<?php
require("../conexion2.php");


$nombre = $_POST["nombre"];
$apm = $_POST["apm"];
$app = $_POST["app"];
$rfc = $_POST["rfc"];
$clave = $_POST["key"];


$cliente = "SELECT id FROM clientes WHERE nombre = '$nombre' AND apellido_p = 'app' AND apellido_m = '$apm'";
$re = mysqli_query($con,$cliente);
if(mysqli_num_rows($re) == 0){

$row = mysqli_fetch_assoc($re);
		$ventas = "INSERT INTO clientes(nombre,clave_cliente,rfc,apellido_p,apellido_m) VALUES('$nombre','$clave','$rfc','$app','$apm')";
		  $r = mysqli_query($con,$ventas);
	
		if($r){
		echo  "1";
		}else{
		  echo "2";	
		}

}else{
echo '3';
}
?>