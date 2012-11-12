p6spy {

	appender = 'grails.plugin.p6spy.ui.MemoryLogger'

	module {
		log = 'com.p6spy.engine.logging.P6LogFactory'
	}

	deregisterdrivers = true

	executionthreshold = ''

	outagedetection = false

	outagedetectioninterval = ''

	filter = false

	include = ''

	exclude = ''

	sqlexpression = ''

	autoflush = true

	includecategories = ''

	excludecategories = 'info,debug,result,batch'

	stringmatcher = ''

	stacktrace = false

	stacktraceclass = ''

	reloadproperties = false

	reloadpropertiesinterval = '60'

	useprefix = false
}
