/* Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.p6spy.engine.logging.P6LogFactory
import com.p6spy.engine.spy.P6SpyFactory
import grails.plugin.p6spy.ui.MemoryLogger

p6spy {

	//	See https://p6spy.github.io/p6spy/2.0/configandusage.html
	config {

		/* Not supported:
		append, logfile, logMessageFormat - not logging to a file
		reloadproperties, reloadpropertiesinterval - not using a properties file
		*/

		// class name of the appender to use; defaults to the plugin's in-memory logger
		appender = MemoryLogger.name

		// whether to flush per statement; the default is false, but since the logger doesn't use I/O the cost is negligible
		autoflush = true

		// SimpleDateFormat format used for logging of PreparedStatement date/time values
		databaseDialectDateFormat = 'dd-MMM-yy'

		// SimpleDateFormat format used for logging of the query time (if not set, millseconds since epoch is logged)
		dateformat = ''

		// comma-separated list of JDBC drivers to load and register
		driverlist = ''

		// if filter is true, a comma-separated list of strings used to define the include/exclude filter rule
		exclude = ''

		// comma-separated list of category names to exclude; can include batch, commit, debug, error, info, outage, result,
		//                                                                resultset, rollback, statement, warn
		excludecategories = 'batch,debug,info,result,resultset'

		// if set, only statements that have taken longer than the time specified (in milliseconds) will be logged
		executionthreshold = '0'

		// if true, filter what is logged; include, exclude, and sqlexpression define filtering rules
		filter = false

		// if filter is true, a comma-separated list of strings used to define include/exclude filter rule
		include = ''

		// whether to expose options via JMX
		jmx = true

		// if jmx is true, the prefix used for the naming pattern: com.p6spy(.<jmxPrefix>)?:name=<optionsClassName>
		jmxPrefix = ''

		// JNDI config settings if using external JNDI
		jndicontextcustom = ''
		jndicontextfactory = ''
		jndicontextproviderurl = ''

		// comma-separated list of names of module classes (which implement com.p6spy.engine.spy.P6Factory) which should be active
		modulelist = [P6SpyFactory, P6LogFactory/*, P6OutageFactory*/].collect { it.name }.join(',')

		// if true, detects long-running statements that may indicate a database outage and logs statements that
		// exceed outagedetectioninterval seconds; when enabled, only long-running statements are logged
		outagedetection = false
		outagedetectioninterval = '30'

		// if not using auto-update, change the DataSource class name to 'com.p6spy.engine.spy.P6DataSource'
		// (in DataSource.groovy or in the JNDI configuration) and set realdatasourceclass to the name of the
		// real DataSource class name; set realdatasource to the real JNDI name and optionally configure
		// DataSource url properties with realdatasourceproperties
		realdatasource = ''
		realdatasourceclass = ''
		realdatasourceproperties = ''

		// if filter is true, a regex defining filter rule for SQL statements
		sqlexpression = ''

		// if true, log a stack trace for every statement logged
		stacktrace = false

		// if stacktrace is true, a class name which must be present in the stacktrace for the stacktrace to be logged
		stacktraceclass = ''
	}

	gsp {
		// the layout to use for admin.gsp
		layoutAdmin = 'main'

		// the layout to use for index.gsp
		layoutIndex = 'p6spy-ui'
	}

	updateDataSource {
		// if true, update the DataSource URL to include "p6spy:" to trigger P6Spy's auto-update feature;
		// if false you'll need to manually configure the DataSource replacement with realdatasource and realdatasourceclass
		autoUpdate = true

		// if autoUpdate is true, the name of the DataSource driver class name property
		driverClassNameProperty = 'driverClassName'

		// if autoUpdate is true, the name of the DataSource 'url' property
		urlProperty = 'url'
	}
}
