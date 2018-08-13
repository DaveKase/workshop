<!DOCTYPE html>
<html>
	<head>
		<title>Workshop</title>
	</head>
	<body>
	
	<h1>Siin on näha kõik kasutajad</h1>
	
	<a href='clickCounter'>Nupule vajutused</a><br />
	<a href='index'>Algusesse</a><br /><br />
	
	<?php
	// Database connection variables
	$url = '127.0.0.1';
	$user = 'root';
	$pass = 'root';
	$db = 'workshop';
	$table_name = 'counter';
	
	// Column name
	$col_user_id = 'user_id';
	
	// Querying the database and using UTF-8 charset
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_user_id . ' FROM ' . $table_name . ' GROUP BY ' . $col_user_id;
	$mysqli->query('SET NAMES utf8');
	$result = $mysqli->query($query);
	
	// Using this variable to show how many users have registered at least one click with the server
	$count = 1;
	
	// Counting distinct users from database
	// For each different user, create a link that ends with user ID variable
	if ($result->num_rows > 0) {
		while ( $row = $result->fetch_array() ) {
			echo $count . '. <a href = userClick?user_id=' . $row[$col_user_id] . '>' . $row[$col_user_id] . '</a>';
			echo "<br />";
			$count++;
		}
	} else {
		echo '<i>Kuvamiseks kirjeid ei ole</i>';
	}
	
	// Freeing some resources
	$result->free();
	$mysqli->close();
	
	$server_url=$_SERVER['REQUEST_URI'];
	header("Refresh:5;URL=$server_url");
	?>
	</body>
</html>