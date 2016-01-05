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

import grails.plugins.Plugin

class P6spyUiGrailsPlugin extends Plugin {

	String grailsVersion = '3.0.0 > *'
	String author = 'Burt Beckwith'
	String authorEmail = 'burt@burtbeckwith.com'
	String title = 'P6Spy UI Plugin'
	String description = 'P6Spy UI Plugin'
	String documentation = 'https://burtbeckwith.github.io/grails-p6spy-ui/'
	String license = 'APACHE'
	def issueManagement = [url: 'https://github.com/burtbeckwith/grails-p6spy-ui/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-p6spy-ui']
	def profiles = ['web']

	Closure doWithSpring() { {->
		P6spyUiGrailsPluginSupport.updateP6spyConfig grailsApplication

		p6SpyBeanDefinitionRegistryPostProcessor(P6SpyBeanDefinitionRegistryPostProcessor) {
			grailsApplication = grailsApplication
		}
	}}

	void onConfigChange(Map<String, Object> event) {
		P6spyUiGrailsPluginSupport.updateP6spyConfig grailsApplication
	}
}
