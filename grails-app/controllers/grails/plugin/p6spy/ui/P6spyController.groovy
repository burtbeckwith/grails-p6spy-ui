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

import grails.converters.JSON

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class P6spyController {

	def p6spyService

	def index() {}

	def sqlStatements(Integer iDisplayStart, Integer iDisplayLength, String sSearch, Integer iSortCol_0,
	                  String sSortDir_0, Integer sEcho) {
		def data = p6spyService.createSqlStatementModel(iDisplayStart, iDisplayLength, sSearch, iSortCol_0, sSortDir_0)
		data.sEcho = sEcho
		render data as JSON
	}

	def clearEntries() {
		p6spyService.clearEntries()
		render 'clearEntries: OK'
	}

	def queriesOverTime() {

		def data = [:]
		data.cols = [
			[label: message(code: 'p6spyui.title.seconds'),             type: 'string'],
			[label: message(code: 'p6spyui.title.queriesPerSecond'),    type: 'number'],
			[label: message(code: 'p6spyui.title.statementsPerSecond'), type: 'number'],
			[label: message(code: 'p6spyui.title.resultSetsPerSecond'), type: 'number'],
			[label: message(code: 'p6spyui.title.selectsPerSecond'),    type: 'number']
		]

		data.rows = []

		def chartData = p6spyService.createQueryCountChartData()
		if (chartData) {
			chartData.xAxisLabels.size().times { int i ->
				data.rows << [c: [
					[v: chartData.xAxisLabels[i]],
					[v: chartData.queryCounts[i]],
					[v: chartData.statementCounts[i]],
					[v: chartData.resultsetCounts[i]],
					[v: chartData.selectCounts[i]]]]
			}
		}

		render data as JSON
	}

	def queryTrafficOverTime() {
		def data = [:]
		data.cols = [
			[label: message(code: 'p6spyui.title.seconds'),           type: 'string'],
			[label: message(code: 'p6spyui.title.bytesOutPerSecond'), type: 'number'],
			[label: message(code: 'p6spyui.title.bytesInPerSecond'),  type: 'number']
		]

		data.rows = []

		def chartData = p6spyService.createQueryTrafficChartData()
		if (chartData) {
			chartData.xAxisLabels.size().times { int i ->
				data.rows << [c: [
					[v: chartData.xAxisLabels[i]],
					[v: chartData.outboundTotals[i]],
					[v: chartData.inboundTotals[i]]]]
			}
		}

		render data as JSON
	}

	def admin() {
		p6spyService.createAdminModel()
	}
}
