<html>
<head>
	<meta name="layout" content="${layoutIndex ?: 'main'}">
	<title><g:message code='p6spyui.admin.title'/></title>
</head>
<body>
	<h3><g:message code='p6spyui.admin.title'/></h3>
	<table>
		<thead>
			<tr>
				<th><g:message code='p6spyui.admin.header.name'/></th>
				<th><g:message code='p6spyui.admin.header.value'/></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><g:message code='p6spyui.admin.outageDetectionEnabled'/></td>
				<td>${outageDetection}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.outageDetectionInterval'/></td>
				<td>${outageDetectionInterval}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.outageDetectionIntervalMS'/></td>
				<td>${outageDetectionIntervalMS}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.append'/></td>
				<td>${append}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.appenderClassName'/></td>
				<td>${appender}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.appender'/></td>
				<td>${appenderInstance}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.autoflush'/></td>
				<td>${autoflush}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.databaseDialectDateFormat'/></td>
				<td>${databaseDialectDateFormat}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.dateformat'/></td>
				<td>${dateformat}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.driverlist'/></td>
				<td>${driverlist}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.drivernames'/></td>
				<td>${driverNames?.join(', ')}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.jmxEnabled'/></td>
				<td>${jmx}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.jmxPrefix'/></td>
				<td>${jmxPrefix}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.jndiContextCustom'/></td>
				<td>${jndiContextCustom}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.jndiContextFactory'/></td>
				<td>${jndiContextFactory}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.jndiContextProviderURL'/></td>
				<td>${jndiContextProviderURL}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.logfile'/></td>
				<td>${logfile}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.logMessageFormat'/></td>
				<td>${logMessageFormat}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.logMessageFormatInstance'/></td>
				<td>${logMessageFormatInstance}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.moduleFactories'/></td>
				<td>${moduleFactories}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.modulelist'/></td>
				<td>${modulelist}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.moduleNames'/></td>
				<td>${moduleNames}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.realDataSource'/></td>
				<td>${realDataSource}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.realDataSourceClass'/></td>
				<td>${realDataSourceClass}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.realDataSourceProperties'/></td>
				<td>${realDataSourceProperties}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.reloadProperties'/></td>
				<td>${reloadProperties}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.reloadPropertiesInterval'/></td>
				<td>${reloadPropertiesInterval}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.stackTraceEnabled'/></td>
				<td>${stackTrace}&nbsp;</td>
			</tr>
			<tr>
				<td><g:message code='p6spyui.admin.stackTraceClass'/></td>
				<td>${stackTraceClass}&nbsp;</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
