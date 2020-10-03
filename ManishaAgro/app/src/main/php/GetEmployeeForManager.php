<?php
// Create connection
//$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
//if ($conn->connect_error) {
  //die("Connection failed: " . $conn->connect_error);
//}

require_once 'connectdb.php';

$manager_id = $_POST['reports_to_emp_id'];

$key = $_POST['key'];
if ( $key == "Reports_employee" )
{ 
    $query = "SELECT * FROM employee_details WHERE emp_id in(select emp_id from employee_reporting where reports_to_emp_id='" . $manager_id . "')";
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
    $conn->close();

}

/*$sql = "SELECT emp_id FROM employee_reporting where reports_to_emp_id='" . $manager_id . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "". $row["emp_id"]. ",";
  }
} else {
  echo "0";
}*/

?>