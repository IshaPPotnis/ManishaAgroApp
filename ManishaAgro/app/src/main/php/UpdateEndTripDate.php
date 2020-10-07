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
if($key == "Update@EndTripDate")
{
  
$sql = "UPDATE employee_trips SET date_of_return ='$date' WHERE emp_id='$emp_id' AND visited_customer_name='$visited_customer_name' AND address='$address'";

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