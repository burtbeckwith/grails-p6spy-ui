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
package grails.plugin.p6spy.ui

import com.p6spy.engine.spy.P6SpyLoadableOptions
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.option.SystemProperties
import grails.core.GrailsApplication
import grails.util.Environment
import org.grails.config.PropertySourcesConfig
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySource

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class P6spyUiGrailsPluginSupport {

	static void updateP6spyConfig(GrailsApplication application) {

		// set temporarily to avoid creating a spy.log file
		System.setProperty SystemProperties.P6SPY_PREFIX + P6SpyOptions.APPENDER, NullP6Logger.name

		GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().contextClassLoader)
		ConfigObject defaultConfig = new ConfigSlurper(Environment.current.name).parse(classLoader.loadClass('DefaultP6SpyConfig'))

		def p6spyConfig = new ConfigObject()
		p6spyConfig.putAll defaultConfig.p6spy.merge(application.config.grails.plugin.p6spy as ConfigObject)

		fixValues p6spyConfig

		ConfigObject config = new ConfigObject()
		config.grails.plugin.p6spy = p6spyConfig
		PropertySource propertySource = new MapPropertySource('P6SpyConfig', [:] << config)
		def propertySources = application.mainContext.environment.propertySources
		propertySources.addFirst propertySource
		application.config = new PropertySourcesConfig(propertySources)

		P6SpyLoadableOptions options = P6SpyOptions.activeInstance

		String appender = p6spyConfig.config.remove('appender')

		options.load p6spyConfig.config

		// don't replace with new instance of the same type
		if (options.appenderInstance?.getClass()?.name != appender) {
			options.appender = appender
		}
		p6spyConfig.appender = appender
	}

	protected static void fixValues(config) {
		for (Iterator iter = config.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = iter.next()
			def value = entry.value
			if (value instanceof Map) {
				fixValues value
			}
			else {
				entry.value = value == null ? '' : value.toString()
			}
		}
	}
}
