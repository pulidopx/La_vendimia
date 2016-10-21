<?php
require("../conexion2.php");


$ventas = "SELECT descripcion,precio,existencia,clave_articulo,modelo FROM articulos ORDER BY id DESC";
$r = mysqli_query($con,$ventas);
while($row = mysqli_fetch_array($r)){
$array[] = $row;

}
echo $ar = json_encode($array);
?>
