<?php
// header used to set content type and charset
header('Content-type: application/json; charset=UTF-8');

// Database connection variables
$url = 'localhost';
$user = 'root';
$pass = 'root';
$db = 'workshop';
$table_name = 'counter';

// Column names
$col_btn_name = 'btn_name';
$col_clicked = 'clicked';
$col_user_id = 'user_id';

// Creating a connection
$mysqli = new mysqli($url, $user, $pass, $db);

// Getting the JSON object sent from device
$value = json_decode(file_get_contents('php://input'), true);
$btn_name = $value[$col_btn_name];
$user_id = $value[$col_user_id];

// If there is no button name or user id, default values are used
if($btn_name == '') {
	$btn_name = 'black';
} 

if ($user_id == '') {
	$user_id = 'default_user';
}

// Replacing all non-alphabetical characters, because user ID is used in the project as a part of URL
// and using those characters will make the server behave irradically
$user_id = mb_strtolower($user_id);
$user_id = str_replace(' ', '', $user_id);
$user_id = str_replace('ä', '2', $user_id);
$user_id = str_replace('õ', '6', $user_id);
$user_id = str_replace('ü', 'y', $user_id);
$user_id = str_replace('ö', 'o', $user_id);
$user_id = str_replace('š', 's', $user_id);
$user_id = str_replace('ž', 'z', $user_id);
$user_id = str_replace(',', '', $user_id);
$user_id = str_replace('.', '', $user_id);
$user_id = str_replace(';', '', $user_id);
$user_id = str_replace(':', '', $user_id);
$user_id = str_replace('-', '', $user_id);
$user_id = str_replace('_', '', $user_id);
$user_id = str_replace('!', '', $user_id);
$user_id = str_replace('"', '', $user_id);
$user_id = str_replace('@', '', $user_id);
$user_id = str_replace('#', '', $user_id);
$user_id = str_replace('£', '', $user_id);
$user_id = str_replace('¤', '', $user_id);
$user_id = str_replace('$', '', $user_id);
$user_id = str_replace('%', '', $user_id);
$user_id = str_replace('&', '', $user_id);
$user_id = str_replace('/', '', $user_id);
$user_id = str_replace('{', '', $user_id);
$user_id = str_replace('}', '', $user_id);
$user_id = str_replace('(', '', $user_id);
$user_id = str_replace(')', '', $user_id);
$user_id = str_replace('[', '', $user_id);
$user_id = str_replace(']', '', $user_id);
$user_id = str_replace('=', '', $user_id);
$user_id = str_replace('+', '', $user_id);
$user_id = str_replace('?', '', $user_id);
$user_id = str_replace('\\', '', $user_id);
$user_id = str_replace('´', '', $user_id);
$user_id = str_replace('`', '', $user_id);
$user_id = str_replace('ˇ', '', $user_id);
$user_id = str_replace('~', '', $user_id);
$user_id = str_replace('*', '', $user_id);
$user_id = str_replace('\'', '', $user_id);
$user_id = str_replace('€', '', $user_id);
$user_id = str_replace('§', '', $user_id);

// Making a query to server to find out, if user ID exists in db and if the user has already clicked sent button
$query = 'SELECT * FROM ' . $table_name  . ' WHERE ' . $col_user_id . ' = "' . $user_id 
	. '" AND ' . $col_btn_name . ' =  "' . $btn_name . '"';
	
$mysqli->query('SET NAMES utf8');
$result = $mysqli->query($query);
$row_count = mysqli_num_rows($result);
$clicked = 0;

// If we have a result, then user exists and there is a previous button click, so we update the click count,
// else we add a new row to database, so user and button click exists next time
if($row_count > 0) {
	while($row = mysqli_fetch_array($result)) {
		$clicked = $row[$col_clicked] + 1;
		$query = 'UPDATE ' . $table_name . ' SET ' . $col_clicked . ' = ' . $clicked . ' WHERE ' 
			. $col_user_id . ' = "' . $user_id . '" AND ' . $col_btn_name . ' = "' . $btn_name . '"';
		
		$mysqli->query($query);
	}
} else {
	$clicked = 1;
	$query = 'INSERT INTO ' . $table_name . ' (' . $col_user_id . ', ' . $col_btn_name . ', ' 
		. $col_clicked . ') VALUES ("' . $user_id . '", "' . $btn_name . '", 1)';
		
	$mysqli->query($query);
}

// Showing clicked button count so we can show it to user in device
echo '{"clicked":"' . $clicked . '"}';

$result->free();
$mysqli->close();
?>
