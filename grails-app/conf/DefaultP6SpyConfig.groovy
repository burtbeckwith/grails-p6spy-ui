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
p6spy {
	appender = 'grails.plugin.p6spy.ui.MemoryLogger'
	autoflush = true
	deregisterdrivers = true
	exclude = ''
	excludecategories = 'info,debug,result,batch'
	executionthreshold = ''
	filter = false
	include = ''
	includecategories = ''
	module {
		log = 'com.p6spy.engine.logging.P6LogFactory'
	}
	outagedetection = false
	outagedetectioninterval = ''
	reloadproperties = false
	reloadpropertiesinterval = '60'
	sqlexpression = ''
	stacktrace = false
	stacktraceclass = ''
	stringmatcher = ''
	useprefix = false
}
