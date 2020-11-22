<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

global $user_name,$password;
$key = $_POST['key'];
if (isset($_POST['key']))
{
    $key   = $_POST['key'];   
}
if (isset($_POST['user_name']))
{
    $user_name   = $_POST['user_name'];   
}
if (isset($_POST['password']))
{
    $password  = $_POST['password'];   
}

if($key == "passingUserPass")
{
  if(!isUserNMExist($user_name) && !isPasswordExist($password))
  {
    $response["value"] = "0";
    $response["message"] = "Error! ".mysqli_error($conn);
    echo json_encode($response);
  }
  else
  {
    $sql = "SELECT emp_id,designation FROM employee_details "+
        "where user_name='" . $user_name . "'and password='" . $password . "' and is_active = true";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) 
    {
      // output data of each row
      while($row = $result->fetch_assoc()) 
      {
        echo "" . $row['emp_id']. "," . $row['designation']. "";
        $tmpempid=$row['emp_id'];
        $tmpdesignation=$row['designation'];
       
        $response["value"]="1";
        $response["message"]="Success";
        $response["emp_id"]=$tmpempid;
        $response["designation"]= $tmpdesignation;
        echo json_encode($response);
      }
    } 
    else
    {
      $response["value"] = "0";
      $response["message"] = "Error! ".mysqli_error($conn);
      echo json_encode($response);
    }
  }
  $conn->close();
}


function isPasswordExist($password)
{
    try
    {
        $conn1;
    $conn1 =mysqli("activexsolutions.com", "maro", "maro2", "manishaagro") or die('Unable to Connect');
    $stmt=$conn1->prepare("SELECT emp_id FROM employee_details WHERE password = ?");
    $stmt->bind_param("s",$password);
    $stmt->execute();
    $stmt->store_result();
    return $stmt->num_rows > 0; 
    }
    catch(Exception $e)
    {
            echo $e->getMessage();
    }
}

function isUserNMExist($user_name)
{
    try
    {
        $conn1;
    $conn1 = mysqli_connect("activexsolutions.com", "maro", "maro2", "manishaagro") or die('Unable to Connect');
    $stmt=$conn1->prepare("SELECT emp_id FROM employee_details WHERE user_name = ?");
    $stmt->bind_param("s",$user_name);
    $stmt->execute();
    $stmt->store_result();
    return $stmt->num_rows > 0; 
    }
    catch(Exception $e)
    {
            echo $e->getMessage();
    }
}


?>