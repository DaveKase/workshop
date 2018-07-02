<?php
$url = 'localhost';
$user = 'root';
$pass = 'root';
$db = 'workshop';
$table_name = 'counter';

$col_btn_name = 'btn_name';
$col_clicked = 'clicked';
$col_user_id = 'user_id';

$mysqli = new mysqli($url, $user, $pass, $db);

$value = json_decode(file_get_contents('php://input'), true);
$btn_name = $value[$col_btn_name];
$user_id = $value[$col_user_id];

if($btn_name == '') {
	$btn_name = 'black';
} 

if ($user_id == '') {
	$user_id = 'default_user';
}

$query = 'SELECT * FROM ' . $table_name  . ' WHERE ' . $col_user_id . ' = "' . $user_id . '" AND ' . $col_btn_name . ' =  "' . $btn_name . '"';
$result = $mysqli->query($query);
$row_count = mysqli_num_rows($result);

if($row_count > 0) {
	while($row = mysqli_fetch_array($result)) {
		$clicked = $row[$col_clicked] + 1;
		$query = 'UPDATE ' . $table_name . ' SET ' . $col_clicked . ' = ' . $clicked . ' WHERE ' . $col_user_id . ' = "' . $user_id . '" AND ' 
			. $col_btn_name . ' = "' . $btn_name . '"';
		
		$mysqli->query($query);
	}
} else {
	$query = 'INSERT INTO ' . $table_name . ' (' . $col_user_id . ', ' . $col_btn_name . ', ' . $col_clicked . ') VALUES ("' . $user_id . '", "' . $btn_name . '", 1)';
	$mysqli->query($query);
}

$result->free();
$mysqli->close();
?>