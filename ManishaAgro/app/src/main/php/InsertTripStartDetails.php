<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

if (isset($_POST['key']))
{
    $key   = $_POST['key'];   
}
if (isset($_POST['emp_id']))
{
    $emp_id   = $_POST['emp_id'];   
}
if (isset($_POST['visited_customer_name']))
{
    $visited_customer_name   = $_POST['visited_customer_name'];   
}
if (isset($_POST['address']))
{
    $address  = $_POST['address'];   
}


$date = date('Y-m-d');
if($key == "EmployeeVisitToFarmer")
{
  
$sql = "INSERT INTO employee_trips(emp_id,visited_customer_name,address,date_of_travel) VALUES ('" . $emp_id . "','" . $visited_customer_name . "','" . $address . "', '" . $date ."')";

if ($conn->query($sql) === TRUE) 
{
  $response["value"] = "1";
  $response["message"] = "Success";

  echo json_encode($response);
  mysqli_close($conn);
} 
else 
{
  
  $response["value"] = "1";
  $response["message"] = "Success";

  echo json_encode($response);
  mysqli_close($conn);
}

$conn->close();

}



?>