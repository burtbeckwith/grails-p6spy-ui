<html>
<head>
<meta name='layout' content='p6spy-ui' />
<title>P6Spy UI</title>
    <base href="${request.forwardURI}/"/>

<script type='text/javascript' src='http://www.google.com/jsapi'></script>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart"]});
</script>

<g:javascript>
var startImageSrc = "${resource(dir: '/images', file: 'start.gif', plugin: 'p6spy-ui')}";
var pauseImageSrc = "${resource(dir: '/images', file: 'pause.gif', plugin: 'p6spy-ui')}";

$(document).ready(function() {
	$('#sqlStatementTable').dataTable({
		"bStateSave": true,
		"bProcessing": false,
		"bServerSide": true,
		"sAjaxSource": "sqlStatements",
		
		"fnServerData": function(sSource, aoData, fnCallback) {
			$.getJSON(sSource, aoData, function(json) {
				fnCallback(json);
				$('#totalStatementTime').html(json.totalQueryTime)
				updateLastUpdated();
			});
		}
	});

	$('ul.tabs').tabs('div.panes > div')

	drawQueriesOverTime();
	drawQueryTrafficOverTime();
});

var refreshTimerId = setInterval('refresh()', 1000);
var updating = true;

function toggleUpdate() {
	var updateImage = $('#updateImage');
	if (updating) {
		updateImage.attr('src', startImageSrc);
		updateImage.attr('title', "Enable auto-refresh");
	}
	else {
		updateImage.attr('src', pauseImageSrc);
		updateImage.attr('title', "Disable auto-refresh");
	}
	updating = !updating;
}

function refresh() {
	if (!updating) return;

	$('#sqlStatementTable').dataTable().fnDraw(false); 
	drawQueriesOverTime();
	drawQueryTrafficOverTime();
}

function clearEntries() {
	$.get('clearEntries', function(data) {
		$('#sqlStatementTable').dataTable().fnDraw(false); 
	});
}

function updateLastUpdated() {
	var now = new Date()
	var month = now.getMonth() + 1;
	var day = now.getDate();
	var year = now.getFullYear();
	var ampm;
	var hours = now.getHours();
	if (hours > 12) {
		hours -= 12;
		ampm = 'PM';
	}
	else {
		ampm = 'AM';
	}
	var minutes = now.getMinutes();
	var seconds = now.getSeconds();
	$('#lastUpdate').html('Last updated: ' + month + '/' + day + '/' + year + ' ' +
	                      pad(hours) + ':' + pad(minutes) + ':' + pad(seconds) + ampm);
}

function pad(n) {
	if (n < 10) {
		return '0' + n;
	}
	return n;
}

function drawQueriesOverTime() {

	var jsonData = $.ajax({
		url:      'queriesOverTime',
		dataType: 'json',
		async:     false
	}).responseText;

	var data = new google.visualization.DataTable(jsonData);
	var chart = new google.visualization.LineChart(document.getElementById('queriesOverTimeChart'));

	chart.draw(data, {
		title: 'Queries over time',
		axisTitlesPosition: 'in',
		hAxis: { title: 'Seconds' },
		vAxis: { title: 'Queries' },
		width: 900, height: 200
	});
}

function drawQueryTrafficOverTime() {

	var jsonData = $.ajax({
		url:      'queryTrafficOverTime',
		dataType: 'json',
		async:     false
	}).responseText;

	var data = new google.visualization.DataTable(jsonData);
	var chart = new google.visualization.LineChart(document.getElementById('queryTrafficOverTimeChart'));

	chart.draw(data, {
		title: 'Query traffic over time',
		axisTitlesPosition: 'in',
		hAxis: { title: 'Seconds' },
		vAxis: { title: 'Bytes', textPosition: 'in' },
		width: 900, height: 200
	});
}
</g:javascript>

</head>

<body>

<ul class="tabs">
	<li><a href="#">SQL</a></li>
	<li><a href="#">Graphs</a></li>
</ul>

<div class='panes'>

	<div id="sqlStatementTableHolder" class="tabPane">

		Total statement time [ms]: <span id='totalStatementTime'>0</span>
		&nbsp;&nbsp;&nbsp;
		<span id='lastUpdate'>Last updated:</span>
		&nbsp;&nbsp;&nbsp;
		<a href='javascript:void(0);' onclick='toggleUpdate()'><g:img dir='images' file='pause.gif' plugin='p6spy-ui' id='updateImage' title='Disable auto-refresh' /></a>
		&nbsp;&nbsp;&nbsp;
		<a href='javascript:void(0);' onclick='clearEntries()'><g:img dir='images' file='clear.gif' plugin='p6spy-ui' title='Clear all entries' /></a>
		<hr/>

		<table id="sqlStatementTable" cellpadding="0" cellspacing="0" border="0" class="display">
			<caption>SQL Statements:</caption>
			<thead>
			<tr>
				<th>ID</th>
				<th>Date</th>
				<th>Time [ms]</th>
				<th>Connection ID</th>
				<th>Category</th>
				<th>Prepared</th>
				<th>SQL</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>

	</div>

	<div id="graphsHolder" class="tabPane">

		Queries over time<br/>
		<div id='queriesOverTimeChart' style='width: 100%; height: 200px;'></div>
 
		<br/>
		<br/>

		Query traffic over time<br/>
		<div id='queryTrafficOverTimeChart' style='width: 100%; height: 200px;'></div>

	</div>

</div>

</body>
</html>
