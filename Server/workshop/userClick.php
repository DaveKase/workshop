<!DOCTYPE html>
<html>
	<head>
		<title>Workshop</title>
	</head>
	<body>
	
	<!-- This section does automatic reload after 5 seconds -->
	<script type="text/javascript">
		setTimeout(function() {
			window.location.reload();
		}, 5000);
	</script>
	
	<?php
	// First we get the user ID from URL
	$user_id = filter_input(INPUT_GET, 'user_id', FILTER_SANITIZE_URL);
	echo '<h1>Kasutaja <u>' . $user_id . '</u> on klikkinud järgmisi nuppe</h1><br/>';
	echo '<a href="clickCounter">Nupule vajutused</a><br />';
	echo '<a href="userCount">Kasutajad</a><br />';
	echo '<a href="index">Algusesse</a><br /><br />';
	
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
	
	// Querying the database and using UTF-8 charset
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_btn_name . ', ' . $col_clicked . ' FROM ' . $table_name 
		. ' WHERE ' . $col_user_id . ' = ' . '"' . $user_id . '"';
		
	$mysqli->query('SET NAMES utf8');
	$result = $mysqli->query($query);
	
	// Creating an SVG image for each different different button
	// The image is a circle, which's radius is determined by the click count
	while ($row = $result->fetch_array()) {
		echo '<svg width="150" height="150">
				<circle cx="50" cy="50" r="'.$row[$col_clicked].'" fill="'.$row[$col_btn_name].'" />
				<text fill="black" font-size="12" font-family="Verdana" x="42" y="55">'.$row[$col_clicked].'</text>
				Sorry, your browser does not support inline SVG.
				</svg>
				<br />';
	}
	
	// Freeing some resources
	$result->free();
	$mysqli->close();
	?>
	</body>
</html>