<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$emp_id = $_GET['emp_id'];

$sql = "SELECT * FROM employee_trips where emp_id='" . $emp_id . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "". $row["visited_customer_name"]. ","
             . $row["address"]. ","
             . $row["date_of_travel"]. ","
             . $row["date_of_return"]. "<br>";
  }
} else {
  echo "0";
}
$conn->close();
?>