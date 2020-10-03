<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}




//$emp_id = $_GET['emp_id'];



$key = $_POST['key'];
if (isset($_POST['key']))
{
    $key   = $_POST['key'];   
}
if (isset($_POST['emp_id']))
{
  $emp_id = $_POST['emp_id'];
}

if($key == "EmpProfile")
{
 
  
  $sql = "SELECT * FROM employee_details where emp_id='" . $emp_id . "'";
  $result = $conn->query($sql);
  if ($result->num_rows > 0) 
  {
    // output data of each row
    while($row = $result->fetch_assoc()) 
    {
      /*  echo "". $row["emp_id"]. ","
      . $row["user_name"]. ","
      . $row["password"]. ","
      . $row["name"]. ","
      . $row["designation"]. ","
      . $row["dob"]. ","
      . $row["joining_date"]. ","
      . $row["email_id"]. ","
      . $row["contact_detail"]. ","
      . $row["address"]."";*/


      $tmpempid=$row['emp_id'];
      $tmpname=$row['name'];
      $tmpdesig=$row['designation'];
      $tmpdob=$row['dob'];
      $tmpdoj=$row['joining_date'];
      $tmpemail=$row['email_id'];
      $tmpaddr =$row['contact_detail'];
      $tmpmobile =$row['mobile'];

      $response["value"]="1";
      $response["message"]="Success";
      $response["emp_id"]=$tmpempid;
      $response["name"]=$tmpname;
      $response["designation"]=$tmpdesig;
      $response["dob"]=$tmpdob;
      $response["joining_date"]=$tmpdoj;
      $response["email_id"]=$tmpemail;
      $response["contact_detail"]=$tmpaddr;
      $response["mobile"]=$tmpmobile;
      echo json_encode($response);
    

    
    }
   
  } 
  else 
  {
    $response["value"] = "0";
    $response["message"] = "Error! ".mysqli_error($conn);
    echo json_encode($response);
  }
  $conn->close();

}


?>