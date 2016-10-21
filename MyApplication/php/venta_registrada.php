<?php
require("../conexion2.php");
$folio_venta = $_POST["folio"];
$nombre = $_POST["nombre"];
$total = $_POST["total"];
$des = $_POST["descripcion"];
$existAct = $_POST["exis"];
$ext = (int)$existAct;
$articulo = $_POST["artc"];

$cliente = "SELECT clave_cliente as id FROM clientes WHERE rfc = '$nombre'";
$re = mysqli_query($con,$cliente);
if(mysqli_num_rows($re)>0){
$row = mysqli_fetch_assoc($re);
  $id = $row['id'];
  $cliente = "SELECT existencia as exis FROM articulos WHERE descripcion = '$articulo' AND existencia >= $ext";
  $ree = mysqli_query($con,$cliente);
  if(mysqli_num_rows($ree)>0){
				$ventas = "INSERT INTO ventas_activas(folio_ventas,clave_cliente,total,nombre) VALUES('$folio_venta','$id','$total','$nombre')";
				  $r = mysqli_query($con,$ventas);
				$reload = "UPDATE articulos SET existencia = existencia - $ext WHERE descripcion = '$des'";
				$ere = mysqli_query($con,$reload);
				if($r){
				echo  "1-";
				}else{
				  echo "2-";	
				}
		}else{
			echo '4-';
		}	
}else{
echo '3-';
}
?>