package grails.plugin.p6spy.ui

import grails.converters.JSON

import com.p6spy.engine.common.P6SpyOptions
import com.p6spy.engine.outage.P6OutageOptions

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class P6spyController {

	protected static final String DATE_FORMAT = 'yyyy.MM.dd hh:mm:ss.SSS'

	def p6spyService

	def index() {}

	def sqlStatements(Integer sEcho, String sKeyword, Integer iDisplayStart, Integer iDisplayLength, Integer iSortingCols, String sSearch) {

		int totalRecordCount = MemoryLogger.instance.entryCount
		int filterCount = totalRecordCount

		def data = [sEcho: sEcho, iTotalRecords: totalRecordCount, iTotalDisplayRecords: filterCount,
		            totalQueryTime: MemoryLogger.instance.totalQueryTime,
		            aaData: p6spyService.createSqlStatementData(iDisplayStart, iDisplayLength, sSearch)]
		render data as JSON
	}

	def clearEntries() {
		p6spyService.clearEntries()
		render 'ok'
	}

	def queriesOverTime() {

		def data = [:]
		data.cols = [
			[label: 'Seconds', type: 'string'],
			[label: 'Queries / second', type: 'number'],
			[label: 'Statements / second', type: 'number'],
			[label: 'Result sets / second', type: 'number'],
			[label: 'SELECT statements / second', type: 'number']
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
			[label: 'Seconds', type: 'string'],
			[label: 'Outgoing bytes / second', type: 'number'],
			[label: 'Incoming bytes / second', type: 'number']
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
		[outageDetection: P6OutageOptions.getOutageDetection(),
		 outageDetectionInterval: P6OutageOptions.getOutageDetectionInterval(),
		 outageDetectionIntervalMS: P6OutageOptions.getOutageDetectionIntervalMS(),
		 executionThreshold: P6SpyOptions.getExecutionThreshold(),
		 usePrefix: P6SpyOptions.getUsePrefix(),
		 autoflush: P6SpyOptions.getAutoflush(),
		 exclude: P6SpyOptions.getExclude(),
		 excludecategories: P6SpyOptions.getExcludecategories(),
		 filter: P6SpyOptions.getFilter(),
		 include: P6SpyOptions.getInclude(),
		 includecategories: P6SpyOptions.getIncludecategories(),
		 deregisterDrivers: P6SpyOptions.getDeregisterDrivers(),
		 logfile: P6SpyOptions.getLogfile(),
		 appender: P6SpyOptions.getAppender(),
		 realdriver: P6SpyOptions.getRealdriver(),
		 realdriver2: P6SpyOptions.getRealdriver2(),
		 realdriver3: P6SpyOptions.getRealdriver3(),
		 append: P6SpyOptions.getAppend(),
		 spydriver: P6SpyOptions.getSpydriver(),
		 dateformat: P6SpyOptions.getDateformat(),
		 stringmatcher: P6SpyOptions.getStringmatcher(),
		 stackTrace: P6SpyOptions.getStackTrace(),
		 stackTraceClass: P6SpyOptions.getStackTraceClass(),
		 sqlExpression: P6SpyOptions.getSQLExpression(),
		 reloadProperties: P6SpyOptions.getReloadProperties(),
		 reloadPropertiesInterval: P6SpyOptions.getReloadPropertiesInterval(),
		 jndiContextFactory: P6SpyOptions.getJNDIContextFactory(),
		 jndiContextProviderURL: P6SpyOptions.getJNDIContextProviderURL(),
		 jndiContextCustom: P6SpyOptions.getJNDIContextCustom(),
		 realDataSource: P6SpyOptions.getRealDataSource(),
		 realDataSourceClass: P6SpyOptions.getRealDataSourceClass(),
		 realDataSourceProperties: P6SpyOptions.getRealDataSourceProperties(),
		 allModules: P6SpyOptions.allModules(),
		 allDriverNames: P6SpyOptions.allDriverNames()]
	}
}
