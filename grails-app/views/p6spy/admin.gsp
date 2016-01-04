<html>
<head>
	<meta name="layout" content="${layoutAdmin ?: 'main'}">
	<title><g:message code='p6spyui.admin.title'/></title>
</head>
<body>
	<table>
		<caption style='font-weight:bold; text-align:center;font-size:1em;padding:2em;'><g:message code='p6spyui.admin.title'/></caption>
		<thead>
			<tr>
				<th><g:message code='p6spyui.admin.header.name'/></th>
				<th><g:message code='p6spyui.admin.header.value'/></th>
			</tr>
		</thead>
		<tbody>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.appender'/></td>
				<td>${appenderInstance}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.appenderClassName'/></td>
				<td>${appender}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.autoflush'/></td>
				<td>${autoflush}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.databaseDialectDateFormat'/></td>
				<td>${databaseDialectDateFormat}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.dateformat'/></td>
				<td>${dateformat}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.drivernames'/></td>
				<td><g:if test='${driverNames}'>
					<ul><g:each in='${driverNames}' var='name'>
						<li>${name}</li></g:each>
					</ul></g:if><g:else>&nbsp;</g:else>
				</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.jmxEnabled'/></td>
				<td>${jmx}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.jmxPrefix'/></td>
				<td>${jmxPrefix}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.jndiContextCustom'/></td>
				<td>${jndiContextCustom}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.jndiContextFactory'/></td>
				<td>${jndiContextFactory}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.jndiContextProviderURL'/></td>
				<td>${jndiContextProviderURL}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.moduleFactories'/></td>
				<td><g:if test='${moduleFactories}'>
					<ul><g:each in='${moduleFactories}' var='factory'>
						<li>${factory}</li></g:each>
					</ul></g:if><g:else>&nbsp;</g:else>
				</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.moduleNames'/></td>
				<td><g:if test='${moduleNames}'>
					<ul><g:each in='${moduleNames}' var='name'>
						<li>${name}</li></g:each>
					</ul></g:if><g:else>&nbsp;</g:else>
				</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.outageDetectionEnabled'/></td>
				<td>${outageDetection}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.outageDetectionInterval'/></td>
				<td>${outageDetectionInterval}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.outageDetectionIntervalMS'/></td>
				<td>${outageDetectionIntervalMS}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.realDataSource'/></td>
				<td>${realDataSource}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.realDataSourceClass'/></td>
				<td>${realDataSourceClass}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.realDataSourceProperties'/></td>
				<td>${realDataSourceProperties}&nbsp;</td>
			</tr>
			<tr class='odd'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.stackTraceEnabled'/></td>
				<td>${stackTrace}&nbsp;</td>
			</tr>
			<tr class='even'>
				<td nowrap="nowrap"><g:message code='p6spyui.admin.stackTraceClass'/></td>
				<td>${stackTraceClass}&nbsp;</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
