<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$emp_id = $_GET['emp_id'];
$customer_name = $_GET['customer_name'];
$address = $_GET['address'];
$date = date('Y-m-d');

$sql = "INSERT INTO employee_trips(emp_id,visited_customer_name,address,date_of_travel) VALUES ('" . $emp_id . "','" . $customer_name . "','" . $address . "', '" . $date ."')";

if ($conn->query($sql) === TRUE) {
  echo "1"; //Done
} else {
  echo "0"; //Failure
}

$conn->close();
?>