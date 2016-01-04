var sh;

$(function() {

	var brush = SyntaxHighlighter.brushes.Sql;
	sh = new brush();
	sh.init({ brush: 'sql', light: true });

	$('#sqlStatementTable').dataTable({
		bStateSave: true,
		bProcessing: false,
		bServerSide: true,
		sAjaxSource: sqlStatementsUrl,
		bAutoWidth: false,
		columns: [
			{ width:  '5%' },
			{ width: '10%' },
			{ width:  '5%' },
			{ width:  '5%' },
			{ width: '10%' },
			{ width: '30%' },
			{ width: '35%' }
		],
		fnServerData: function(sSource, aoData, fnCallback) {
			$.getJSON(sSource, aoData, function(json) {
				if (json && json.aaData) {
					for (var i = 0; i < json.aaData.length; i++) {
						json.aaData[i][5] = sh.getHtml(json.aaData[i][5]);
						json.aaData[i][6] = sh.getHtml(json.aaData[i][6]);
					}
				}
				fnCallback(json);
				$('#totalStatementTime').html(json.totalQueryTime);
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
		updateImage.attr('title', enableAutoRefresh);
	}
	else {
		updateImage.attr('src', pauseImageSrc);
		updateImage.attr('title', disableAutoRefresh);
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
	$.get(clearEntriesUrl, function(data) {
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
	$('#lastUpdate').html(lastUpdated + month + '/' + day + '/' + year + ' ' +
	                      pad(hours) + ':' + pad(minutes) + ':' + pad(seconds) + ampm);
}

function pad(n) {
	if (n < 10) {
		return '0' + n;
	}
	return n;
}

function drawQueriesOverTime() {
	$.ajax({url: queriesOverTimeUrl}).done(function(jsonData) {
		var data = new google.visualization.DataTable(jsonData);
		var chart = new google.visualization.LineChart($('#queriesOverTimeChart')[0]);
		chart.draw(data, {
			title: queriesOverTime,
			axisTitlesPosition: 'in',
			hAxis: { title: titleSeconds },
			vAxis: { title: titleQueries },
			width: 900, height: 200
		});
	});
}

function drawQueryTrafficOverTime() {
	$.ajax({url: queryTrafficOverTimeUrl}).done(function(jsonData) {
		var data = new google.visualization.DataTable(jsonData);
		var chart = new google.visualization.LineChart($('#queryTrafficOverTimeChart')[0]);
		chart.draw(data, {
			title: queryTrafficOverTime,
			axisTitlesPosition: 'in',
			hAxis: { title: titleSeconds },
			vAxis: { title: titleBytes, textPosition: 'in' },
			width: 900, height: 200
		});
	});
}
