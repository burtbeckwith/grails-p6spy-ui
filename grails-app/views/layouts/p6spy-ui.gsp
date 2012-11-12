<html>

<head>
<title><g:layoutTitle default='P6Spy UI' /></title>

<link rel='shortcut icon' href='${resource(dir: '/images', file: 'favicon.ico', plugin: 'none')}' type='image/x-icon' />

<p6:resources/>

<g:layoutHead />

</head>

<body>

	<g:if test='${flash.message}'>
	<br/><div class='message'>${flash.message}</div><br/>
	</g:if>

<p6:layoutResources/>

<g:layoutBody />

</body>
</html>
