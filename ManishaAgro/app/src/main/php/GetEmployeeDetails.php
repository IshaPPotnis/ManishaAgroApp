<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$emp_id = $_GET['emp_id'];

$sql = "SELECT * FROM employee_details where emp_id='" . $emp_id . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "". $row["emp_id"]. ","
    . $row["user_name"]. ","
    . $row["password"]. ","
    . $row["name"]. ","
    . $row["designation"]. ","
    . $row["dob"]. ","
    . $row["joining_date"]. ","
    . $row["email_id"]. ","
    . $row["contact_detail"]. ","
    . $row["address"]."";
  }
} else {
  echo "0";
}
$conn->close();
?>