<?php 



require_once 'connectdb.php';
global $row;
$key = $_POST['key'];
if ( $key == "Reports_employee" )
{     
    
  

$query = "SELECT * FROM employee_details WHERE emp_id in(select emp_id from employee_reporting where reports_to_emp_id='1') ";
$result = mysqli_query($conn, $query);
$response = array();



while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'emp_id'        =>$row['emp_id'], 
        'name'        =>$row['name'])
      
       
    );
}

echo json_encode($response);

mysqli_close($conn);


}


?>