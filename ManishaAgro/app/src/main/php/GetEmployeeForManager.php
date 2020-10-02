<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$manager_id = $_GET['manager_id'];

$sql = "SELECT emp_id FROM employee_reporting where reports_to_emp_id='" . $manager_id . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "". $row["emp_id"]. ",";
  }
} else {
  echo "0";
}
$conn->close();
?>