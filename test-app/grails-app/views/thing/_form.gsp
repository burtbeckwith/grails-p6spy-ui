
<div class="fieldcontain ${hasErrors(bean: thingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="thing.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${thingInstance?.name}"/>

</div>
