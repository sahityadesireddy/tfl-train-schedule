<!DOCTYPE html>
<html>
<body>
	<h2>Trains Schedule Timetable</h2>
	 <meta http-equiv="refresh" content="60">
	<div>
		<p style="float: left" id="allTrains"></p>
		<p style="float: left; padding: 8px;" id="platforms"></p>
	</div>
	<script>
		var obj, dbParam, xmlhttp, myObj, x, txt, txt1, myplats = "";
		obj = {
			table : "Trains",
			limit : 20
		};
		dbParam = JSON.stringify(obj);
		xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				myObj = JSON.parse(this.responseText);
				txt = "<table border='1'>"
				txt += "<tr><th>Destination</th><th>Platform</th><th>Arrive In</th></tr>";
				for (x in myObj) {
					if (myObj[x].destinationName) {
						txt += "<tr><td>" + myObj[x].destinationName + "</td><td>" + myObj[x].platformName + "</td><td>";
						txt += (myObj[x].timeToStation == 0) ? "Now" : myObj[x].timeToStation + " min";
						txt += "</td></tr>";
					}
				}
				txt += "</table>"
				document.getElementById("allTrains").innerHTML = txt;
			} else {
				document.getElementById("allTrains").innerHTML = this.responseText;
			}
		};
		xmlhttp.open("GET", "/api/trains", true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send("x=" + dbParam);

		xmlplatformhttp = new XMLHttpRequest();
		xmlplatformhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				myplats = JSON.parse(this.responseText);
				txt1 = "";
				for (x in myplats) {
					txt1 += "<table border='1' style='padding: 8px;'><tr><th colspan='2'>Platform: " + x
							+ "</th></tr><tr><th>Destination</th><th>Arrive In</th></tr>";
					for (y in myplats[x]) {
						if (myplats[x][y].destinationName) {
							txt1 += "<tr><td>" + myplats[x][y].destinationName + "</td><td>";
							txt1 += (myplats[x][y].timeToStation == 0) ? "Now" : myplats[x][y].timeToStation + " min";
							txt1 += "</td></tr>";
						}
					}
				}
				txt1 += "</table>"
				document.getElementById("platforms").innerHTML = txt1;
			}
		};
		xmlplatformhttp.open("GET", "/api/trainsByPlatform", true);
		xmlplatformhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlplatformhttp.send("x=" + dbParam);
	</script>

</body>
</html>