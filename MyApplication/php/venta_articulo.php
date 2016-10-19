<?php
require("../conexion2.php");


$des = $_POST["descripcion"];


$ventas = "SELECT descripcion,precio,existencia,modelo FROM articulos WHERE descripcion = '$des' AND existencia > 0";
$r = mysqli_query($con,$ventas);

if(mysqli_num_rows($r)>=1){
while($row = mysqli_fetch_array($r)){
echo json_encode($row);
}

}else{
  echo "0";	
}
?>