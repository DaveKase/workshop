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
	
	<h1>Siin on näha kõik kasutajad</h1>
	
	<a href='clickCounter'>Kõik klikid</a><br /><a href='index'>Algusesse</a><br /><br />
	
	<?php
	$url = 'localhost';
	$user = 'root';
	$pass = 'root';
	$db = 'workshop';
	$table_name = 'counter';
	$col_user_id = 'user_id';
	
	$mysqli = new mysqli($url, $user, $pass, $db);
	$query = 'SELECT ' . $col_user_id . ' FROM ' . $table_name . ' GROUP BY ' . $col_user_id;
	$mysqli->query('SET NAMES utf8');
	$result = $mysqli->query($query);
	
	$count = 1;
	
	while ( $row = $result->fetch_array() ) {
		echo $count . '. <a href = userClick?user_id=' . $row[$col_user_id] . '>' . $row[$col_user_id] . '</a>';
		echo "<br />";
		$count++;
	}
	
	$result->free();
	$mysqli->close();
	?>
	</body>
</html>