<?php
require("../conexion2.php");


$des = $_POST["des"];
$mod = $_POST["mod"];
$pre = $_POST["pre"];
$exis = $_POST["exis"];
$clave = $_POST["key"];


		$ventas = "INSERT INTO articulos(descripcion,clave_articulo,precio,existencia,modelo) VALUES('$des','$clave','$pre','$exis','$mod')";
		  $r = mysqli_query($con,$ventas);
	
		if($r){
		echo  "1-";
		}else{
		  echo "2-";	
		}

?>