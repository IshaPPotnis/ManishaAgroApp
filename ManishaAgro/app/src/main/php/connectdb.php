<?php 

define('DB_HOST','166.62.28.101:3306');
define('DB_USER','maro');
define('DB_PASS','maro2');
define('DB_NAME','manishaagro');


$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME) or die('Unable to Connect');
?>