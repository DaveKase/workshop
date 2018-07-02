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
		
	<h1>Siin on näha kõik nupulevajutused kokku</h1>
	<a href='userCount'>Kasutajad</a><br /><a href='index'>Algusesse</a><br />
	
	<?php 		
	$url = 'localhost';
	$user = 'root';
	$pass = 'root';
	$db = 'workshop';
	$table_name = 'counter';
	$col_btn_name = 'btn_name';
	$col_clicked = 'clicked';
	$sub_click = 'click';
	
	$blue_btn = 'blue';
	$red_butn = 'red';
	$yellow_btn = 'yellow';
	$green_btn = 'green';
	
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_btn_name . ', SUM( ' . $col_clicked . ') as click  FROM ' . $table_name . ' GROUP BY ' . $col_btn_name;
	$result = $mysqli->query($query);
	
	while ($row = $result->fetch_array()) {
		echo '<svg width="150" height="150">
				<circle cx="50" cy="50" r="'.$row[$sub_click].'" fill="'.$row[$col_btn_name].'" />
				<text fill="black" font-size="12" font-family="Verdana" x="41" y="55">'.$row[$sub_click].'</text>
				Sorry, your browser does not support inline SVG.
				</svg>
				<br />';
	}
	
	$result->free();
	$mysqli->close();
	?>
	</body>
</html>