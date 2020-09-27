<?php 

define('DB_HOST','localhost');
define('DB_USER','root');
define('DB_PASS','pwdpwd');
define('DB_NAME','manishaagro_db');


$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME) or die('Unable to Connect');
?>