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
import grails.plugin.p6spy.ui.P6spyUiGrailsPluginSupport

class P6spyUiGrailsPlugin {

	String version = '0.1'
	String grailsVersion = '2.3 > *'
	String author = 'Burt Beckwith'
	String authorEmail = 'burt@burtbeckwith.com'
	String title = 'P6Spy UI Plugin'
	String description = 'P6Spy UI Plugin'
	String documentation = 'http://grails.org/plugin/p6spy-ui'
	def pluginExcludes = ['src/docs/**']
	String license = 'APACHE'
	def issueManagement = [url: 'https://github.com/burtbeckwith/grails-p6spy-ui/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-p6spy-ui']

	def doWithSpring = {
		P6spyUiGrailsPluginSupport.updateP6spyConfig application
	}

	def onConfigChange = { event ->
		P6spyUiGrailsPluginSupport.updateP6spyConfig application
	}
}
