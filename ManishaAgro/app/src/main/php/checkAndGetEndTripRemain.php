<?php
// Create connection
$conn = new mysqli("activexsolutions.com", "maro", "maro2", "manishaagro");
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
//require_once 'connectdb.php';
$emp_id = $_POST['emp_id'];
$key = $_POST['key'];

if ( $key == "EmpvisitedCustomer" )
{     
    if (isset($_POST['emp_id']))
    {
        $empid   = $_POST['emp_id'];
    }
    $query = "SELECT * FROM employee_trips WHERE emp_id='$empid' AND date_of_return IS NULL ";
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
    $conn->close();
}

/*
$sql = "SELECT * FROM employee_trips where emp_id='" . $emp_id . "'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result->fetch_assoc()) {
    echo "". $row["visited_customer_name"]. ","
             . $row["address"]. ","
             . $row["date_of_travel"]. ","
             . $row["date_of_return"]. "<br>";
  }
} else {
  echo "0";
}*/

?>