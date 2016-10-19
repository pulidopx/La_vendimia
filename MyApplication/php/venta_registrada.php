<?php
require("../conexion2.php");


$folio_venta = $_POST["folio"];
$nombre = $_POST["nombre"];
$total = $_POST["total"];
$des = $_POST["descripcion"];

$cliente = "SELECT clave_cliente as id FROM clientes WHERE nombre = '$nombre'";
$re = mysqli_query($con,$cliente);
if(mysqli_num_rows($re)>0){
$row = mysqli_fetch_assoc($re);
  $id = $row['id'];
		$ventas = "INSERT INTO ventas_activas(folio_ventas,clave_cliente,total,nombre) VALUES('$folio_venta','$id','$total','$nombre')";
		  $r = mysqli_query($con,$ventas);
		$reload = "UPDATE articulos SET existencia = existencia - 1 WHERE descripcion = '$des'";
		$ere = mysqli_query($con,$reload);
		if($r){
		echo  "1";
		}else{
		  echo "2";	
		}
}else{
echo '3';
}
?>