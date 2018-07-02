<!DOCTYPE html>
<html>
	<head>
		<title>Workshop</title>
	</head>
	<body>
	
	<script type="text/javascript">
		setTimeout(function() {
			window.location.reload();
		}, 5000);
	</script>
	
	<?php
	$user_id = filter_input(INPUT_GET, 'user_id', FILTER_SANITIZE_URL);
	echo '<h1>Kasutaja <b>' . $user_id . '</b> on klikkinud järgmisi nuppe</h1><br/>';
	echo '<a href="clickCounter">Kõik klikid</a><br /><a href="index">Algusesse</a><br />';
	
	$url = 'localhost';
	$user = 'root';
	$pass = 'root';
	$db = 'workshop';
	$table_name = 'counter';
	
	$col_btn_name = 'btn_name';
	$col_clicked = 'clicked';
	$col_user_id = 'user_id';
	
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_btn_name . ', ' . $col_clicked . ' FROM ' . $table_name . ' WHERE ' . $col_user_id . ' = ' . '"' . $user_id . '"';
	$result = $mysqli->query($query);
	
	while ($row = $result->fetch_array()) {
		echo '<svg width="150" height="150">
				<circle cx="50" cy="50" r="'.$row[$col_clicked].'" fill="'.$row[$col_btn_name].'" />
				<text fill="black" font-size="12" font-family="Verdana" x="42" y="55">'.$row[$col_clicked].'</text>
				Sorry, your browser does not support inline SVG.
				</svg>
				<br />';
	}
	
	$result->free();
	$mysqli->close();
	?>
	</body>
</html>