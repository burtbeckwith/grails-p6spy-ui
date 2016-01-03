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

import groovy.util.logging.Slf4j

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor

import com.p6spy.engine.spy.P6SpyDriver

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@Slf4j
class P6SpyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

	// dependency injection
	GrailsApplication grailsApplication

	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		ConfigObject updateDataSource = grailsApplication.config.grails.plugin.p6spy.updateDataSource
		if (!updateDataSource.autoUpdate) {
			log.debug 'autoUpdate is false, not updating'
			return
		}

		def dataSource
		if (beanFactory.containsBean('dataSourceUnproxied')) {
			dataSource = beanFactory.getBean('dataSourceUnproxied')
		}
		else if (beanFactory.containsBean('dataSource')) {
			dataSource = beanFactory.getBean('dataSource')
		}

		if (!dataSource) {
			return
		}

		String urlProperty = updateDataSource.urlProperty
		String driverClassNameProperty = updateDataSource.driverClassNameProperty
		log.debug 'Updating the DataSource with url property "{}" and driver class name property "{}"',
			urlProperty, driverClassNameProperty
		String url = dataSource[urlProperty]
		if (url.startsWith('jdbc:')) {
			url = 'jdbc:p6spy:' + url[5..-1]
			dataSource[urlProperty] = url
			dataSource[driverClassNameProperty] = P6SpyDriver.name
			log.debug 'Updated DataSource url to "{}" and driver class name to "{}"',
				dataSource[urlProperty], dataSource[driverClassNameProperty]
		}
		else {
			log.debug 'DataSource url "{}" does not start with "jdbc:", not updating', url
		}
	}

	void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {}
}
