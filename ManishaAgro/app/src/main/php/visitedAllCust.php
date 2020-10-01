<?php 

require_once 'connectdb.php';
global $row;
$key = $_POST['key'];

if ( $key == "EmpvisitedCustomer" )
{     
    if (isset($_POST['emp_id']))
    {
        $empid   = $_POST['emp_id'];
    }
    $query = "SELECT * FROM employee_trips WHERE emp_id='$empid' ";
    $result = mysqli_query($conn, $query);
    $response = array();
    while( $row = mysqli_fetch_assoc($result) )
    {
        array_push($response, 
        array(
            'visited_customer_name'        =>$row['visited_customer_name'], 
            'date_of_travel'        =>$row['date_of_travel'],
            'date_of_return'        =>$row['date_of_return'])
        );
    }   
    echo json_encode($response);
    mysqli_close($conn);
}

?>