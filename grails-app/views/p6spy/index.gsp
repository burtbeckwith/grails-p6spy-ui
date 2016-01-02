<html>
<head>
	<meta name="layout" content="${layoutIndex ?: 'p6spy-ui'}">
	<title><g:message code='p6spyui.title'/></title>
<script type='text/javascript' src='http://www.google.com/jsapi'></script>
<script>
var pauseImageSrc = "${assetPath(src: 'pause.gif')}";
var startImageSrc = "${assetPath(src: 'start.gif')}";
var sqlStatementsUrl = "${createLink(action: 'sqlStatements')}";
var clearEntriesUrl = "${createLink(action: 'clearEntries')}";
var queriesOverTimeUrl = "${createLink(action: 'queriesOverTime')}";
var queryTrafficOverTimeUrl = "${createLink(action: 'queryTrafficOverTime')}";
var enableAutoRefresh = "${message(code: 'p6spyui.enableAutoRefresh')}";
var disableAutoRefresh = "${message(code: 'p6spyui.disableAutoRefresh')}";
var lastUpdated = "${message(code: 'p6spyui.lastUpdated')}";
var queriesOverTime = "${message(code: 'p6spyui.header.queriesOverTime')}";
var queryTrafficOverTime = "${message(code: 'p6spyui.header.queryTrafficOverTime')}";
var titleSeconds = "${message(code: 'p6spyui.title.seconds')}";
var titleQueries = "${message(code: 'p6spyui.title.queries')}";
var titleBytes = "${message(code: 'p6spyui.title.bytes')}";

google.load("visualization", "1", {packages: ["corechart"]});
</script>
</head>
<body>
	<ul class="tabs">
		<li><a href="#"><g:message code='p6spyui.tab.sql'/></a></li>
		<li><a href="#"><g:message code='p6spyui.tab.graphs'/></a></li>
	</ul>
	<div class='panes'>
		<div id="sqlStatementTableHolder" class="tabPane">
			<g:message code='p6spyui.totalStatementTimeMs'/><span id='totalStatementTime'>0</span>
			&nbsp;&nbsp;&nbsp;
			<span id='lastUpdate'><g:message code='p6spyui.lastUpdated'/></span>
			&nbsp;&nbsp;&nbsp;
			<a href='javascript:void(0);' onclick='toggleUpdate()'><asset:image src='pause.gif' id='updateImage' title='${message(code:'p6spyui.disableAutoRefresh')}' /></a>
			&nbsp;&nbsp;&nbsp;
			<a href='javascript:void(0);' onclick='clearEntries()'><asset:image src='clear.gif' title='${message(code:'p6spyui.clearAllEntries')}' /></a>
			<hr/>
			<table id="sqlStatementTable" cellpadding="0" cellspacing="0" border="0" class="display">
				<caption><g:message code='p6spyui.captionSqlStatements'/></caption>
				<thead>
				<tr>
					<th><g:message code='p6spyui.header.id'/></th>
					<th><g:message code='p6spyui.header.date'/></th>
					<th><g:message code='p6spyui.header.timeMs'/></th>
					<th><g:message code='p6spyui.header.connectionId'/></th>
					<th><g:message code='p6spyui.header.category'/></th>
					<th><g:message code='p6spyui.header.prepared'/></th>
					<th><g:message code='p6spyui.header.sql'/></th>
				</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
		<div id="graphsHolder" class="tabPane">
			<g:message code='p6spyui.header.queriesOverTime'/><br/>
			<div id='queriesOverTimeChart' style='width: 100%; height: 200px;'></div>
			<br/><br/>
			<g:message code='p6spyui.header.queryTrafficOverTime'/><br/>
			<div id='queryTrafficOverTimeChart' style='width: 100%; height: 200px;'></div>
		</div>
	</div>
</body>
</html>
