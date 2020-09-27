<?php 


require_once 'connectdb.php';

$key = $_POST['key'];
if($key == "EmpProfile")
{



if (isset($_POST['user_name']))
{
    $username   = $_POST['user_name'];
   
}


$query = "SELECT * FROM employee_details WHERE user_name='$username'";
$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result) > 0){
    $row= mysqli_fetch_array($result);
 /*  $response = array();

   while( $row = mysqli_fetch_array($result) ){
      
       array_push($response, 
       array(
         
           "name"      =>$row['name'] 
          ) 
       );
   }*/
  
  
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
?>