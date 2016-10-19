<?php
require("../conexion2.php");


$ventas = "SELECT nombre,apellido_p,apellido_m,rfc FROM clientes";
$r = mysqli_query($con,$ventas);

$array = array();
while($row = mysqli_fetch_array($r)){
$array[] = $row;
}
$js = json_encode($array);

$artc = "SELECT descripcion,precio,existencia FROM articulos";
$r2 = mysqli_query($con,$artc);

$array2 = array();
while($row2 = mysqli_fetch_array($r2)){
$array2[] = $row2;
}
$js2 = json_encode($array2);

echo "{\"cliente\":".$js.",\"articulo\":".$js2.",\"fecha\":".date("j-n-Y")."}";



?>