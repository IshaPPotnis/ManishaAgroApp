<?php 
require_once 'connectdb.php';
global $row,$username;


$key = $_POST['key'];


if($key == "EmpvisitedCustomer")
{
    if(isset($_POST['user_name']))
    {
        $username   = $_POST['user_name'];
    }
 
    $query = "SELECT emp_id FROM employee_details WHERE user_name ='$username'";
    $result = mysqli_query($conn, $query);

    if(mysqli_num_rows($result) > 0)
    {
        $row= mysqli_fetch_array($result);
        $tmpempid=$row['emp_id'];
     
        $response["value"]="1";
        $response["message"]="Success";
        $response["emp_id"]=$tmpempid;
        echo json_encode($response);
    }
   
}







?>