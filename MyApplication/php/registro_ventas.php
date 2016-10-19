<?php
require("../conexion2.php");


$ventas = "SELECT descripcion_articulo,modelo,cantidad,precio,importe FROM registro_ventas WHERE cantidad > 1";
$r = mysqli_query($con,$ventas);

while($row = mysqli_fetch_array($r)){
   echo json_encode(array($row));
}
?>