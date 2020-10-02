<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$username = $_GET['username'];
$password = $_GET['password'];

$sql = "SELECT emp_id,designation FROM employee_details where user_name='" . $username . "'and password='" . $password . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "" . $row["emp_id"]. "," . $row["designation"]. "";
  }
} else {
  echo "0";
}
$conn->close();
?>