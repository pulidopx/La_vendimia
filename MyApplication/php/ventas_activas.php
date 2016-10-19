<?php
require("../conexion2.php");


$ventas = "SELECT * FROM ventas_activas ORDER BY id DESC";
$r = mysqli_query($con,$ventas);

$array = array();
while($row = mysqli_fetch_array($r)){
$array[] = $row;
}
echo json_encode($array);
?>