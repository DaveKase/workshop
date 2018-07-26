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
		
	<h1>Siin on näha kõik nupulevajutused kokku</h1>
	<a href='userCount'>Kasutajad</a><br />
	<a href='index'>Algusesse</a><br /><br />
	
	<?php
	// Database connection variables
	$url = 'localhost';
	$user = 'root';
	$pass = 'root';
	$db = 'workshop';
	$table_name = 'counter';
	
	// Column names
	$col_btn_name = 'btn_name';
	$col_clicked = 'clicked';
	$sub_click = 'click';
	
	// Querying the database and using UTF-8 charset
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_btn_name . ', SUM( ' . $col_clicked 
		. ') as click  FROM ' . $table_name . ' GROUP BY ' . $col_btn_name;
	$mysqli->query('SET NAMES utf8');
	$result = $mysqli->query($query);
	
	// Creating an SVG image for each different different button
	// The image is a circle, which's radius is determined by the click count
	if ($result->num_rows > 0) {
		while ($row = $result->fetch_array()) {
			echo '<svg width="150" height="150">
					<circle cx="50" cy="50" r="'.$row[$sub_click].'" fill="'.$row[$col_btn_name].'" />
					<text fill="black" font-size="12" font-family="Verdana" x="41" y="55">'.$row[$sub_click].'</text>
					Sorry, your browser does not support inline SVG.
					</svg>
					<br />';
		}
	} else {
		echo '<i>Kuvamiseks kirjeid ei ole</i>';
	}
	
	// Freeing some resources
	$result->free();
	$mysqli->close();
	?>
	</body>
</html>
