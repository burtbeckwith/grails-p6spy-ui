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
import grails.util.Environment

import org.codehaus.groovy.grails.commons.GrailsApplication

import com.p6spy.engine.common.OptionReloader
import com.p6spy.engine.common.P6Options
import com.p6spy.engine.common.P6SpyOptions
import com.p6spy.engine.common.P6SpyProperties

class P6spyUiGrailsPlugin {

	String version = '0.1'
	String grailsVersion = '2.0 > *'
	String author = 'Burt Beckwith'
	String authorEmail = 'beckwithb@vmware.com'
	String title = 'P6Spy UI Plugin'
	String description = 'P6Spy UI Plugin'
	String documentation = 'http://grails.org/plugin/p6spy-ui'
	List pluginExcludes = [
		'docs/**',
		'src/docs/**'
	]

	String license = 'APACHE'
//	def issueManagement = [system: 'JIRA', url: 'http://jira.grails.org/browse/???']
	def scm = [url: 'https://github.com/burtbeckwith/grails-p6spy-ui']

	def doWithSpring = {

		File file = createTempFile()

		System.setProperty 'spy.properties', file.absolutePath
		P6SpyProperties.propertiesPath = file.absolutePath

		storeConfig application
	}

	def onConfigChange = { event ->

		storeConfig application

		P6SpyProperties properties = new P6SpyProperties()
		OptionReloader.iterator().each { P6Options options -> options.reload(properties) }
	}

	private void storeConfig(GrailsApplication application) {
		Properties props = loadP6SpyConfig(application)
		props.store new FileWriter(P6SpyProperties.propertiesPath), 'Grails P6Spy UI Plugin'
		P6SpyOptions.setAppender props.getProperty('appender')
	}

	private Properties loadP6SpyConfig(GrailsApplication application) {
		GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().contextClassLoader)
		ConfigObject defaultConfig = new ConfigSlurper(Environment.current.name).parse(classLoader.loadClass('DefaultP6SpyConfig'))

		ConfigObject config = new ConfigObject()
		config.putAll defaultConfig.p6spy.merge(application.config.grails.plugins.p6spy)
		Map flat = config.flatten()

		for (String key in flat.keySet().sort()) {
			def val = flat[key]
			if (val == null) {
				val = ''
			}
			else {
				val = val.toString()
			}
			flat[key] = val
		}

		flat as Properties
	}

	private File createTempFile() {
		File file = File.createTempFile('grails.p6spy', '.properties')
		file.deleteOnExit()
		file
	}
}
