<?php 
require_once 'connectdb.php';
global $row,$username;


$key = $_POST['key'];
if ( $key == "Reports_employee" )
{     
    $query = "SELECT * FROM employee_details WHERE emp_id in(select emp_id from employee_reporting where reports_to_emp_id='1') ";
    $result = mysqli_query($conn, $query);
    $response = array();

    while( $row = mysqli_fetch_assoc($result) )
    {
        array_push($response, 
        array(
            'emp_id'        =>$row['emp_id'], 
            'name'        =>$row['name'])
        );
    }
    echo json_encode($response);
    mysqli_close($conn);
}
else if($key == "EmpProfile")
{
 
    if (isset($_POST['user_name']))
    {
        $username   = $_POST['user_name'];
    }

    $query = "SELECT * FROM employee_details WHERE user_name='$username'";
    $result = mysqli_query($conn, $query);

    if(mysqli_num_rows($result) > 0)
    {
        $row= mysqli_fetch_array($result);

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
else if($key == "EmpvisitedCustomer")
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