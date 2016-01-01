<html>

<head>
<title><g:layoutTitle default='P6Spy UI' /></title>

<g:layoutHead />

	<asset:stylesheet src='p6spy-ui'/>
</head>

<body>

	<g:if test='${flash.message}'>
	<br/><div class='message'>${flash.message}</div><br/>
	</g:if>

<g:layoutBody />

	<asset:javascript src='p6spy-ui'/>
</body>
</html>
