<?php
require("../conexion2.php");


$ventas = "SELECT nombre,apellido_p,apellido_m,rfc,clave_cliente FROM clientes ORDER BY id DESC";
$r = mysqli_query($con,$ventas);

$array = array();
while($row = mysqli_fetch_array($r)){
$array[] = $row;
}
echo $js = json_encode($array);

?>