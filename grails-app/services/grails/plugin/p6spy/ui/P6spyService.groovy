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

import groovy.transform.CompileStatic

import java.text.SimpleDateFormat

import com.p6spy.engine.outage.P6OutageOptions
import com.p6spy.engine.spy.P6SpyOptions

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@CompileStatic
class P6spyService {

	protected static final Map<String, String> COLUMN_NAME_TO_ENTRY_PROPERTY = [
		id: 'id', date: 'time', timeMs: 'elapsedTime', connectionId: 'connectionId',
		category: 'categoryName', prepared: 'preparedSql', sql: 'sql']

	static transactional = false

	Map createAdminModel() {
		P6OutageOptions p6OutageOptions = P6OutageOptions.activeInstance as P6OutageOptions
		P6SpyOptions p6SpyOptions = P6SpyOptions.activeInstance as P6SpyOptions

		[//P6OutageOptions
		 outageDetection:           p6OutageOptions?.outageDetection,
		 outageDetectionInterval:   p6OutageOptions?.outageDetectionInterval,
		 outageDetectionIntervalMS: p6OutageOptions?.outageDetectionIntervalMS,
		 // P6SpyOptions
		 // String
		 appender:                  p6SpyOptions.appender,
		 databaseDialectDateFormat: p6SpyOptions.databaseDialectDateFormat,
		 dateformat:                p6SpyOptions.dateformat,
		 jmxPrefix:                 p6SpyOptions.jmxPrefix,
		 jndiContextCustom:         p6SpyOptions.JNDIContextCustom,
		 jndiContextFactory:        p6SpyOptions.JNDIContextFactory,
		 jndiContextProviderURL:    p6SpyOptions.JNDIContextProviderURL,
		 realDataSource:            p6SpyOptions.realDataSource,
		 realDataSourceClass:       p6SpyOptions.realDataSourceClass,
		 realDataSourceProperties:  p6SpyOptions.realDataSourceProperties,
		 stackTraceClass:           p6SpyOptions.stackTraceClass,
		 // boolean
		 autoflush:                 p6SpyOptions.autoflush,
		 jmx:                       p6SpyOptions.jmx,
		 stackTrace:                p6SpyOptions.stackTrace,
		 // P6Logger
		 appenderInstance:          p6SpyOptions.appenderInstance,
		 // Set<P6Factory>
		 moduleFactories:           p6SpyOptions.moduleFactories,
		 // Set<String>
		 driverNames:               p6SpyOptions.driverNames,
		 moduleNames:               p6SpyOptions.moduleNames]
	}

	Map createSqlStatementModel(Integer start, Integer maxCount, String searchString, Integer sortColumn, String sortDirection) {
		int count = MemoryLogger.instance.entryCount
		[iTotalRecords: count, totalQueryTime: MemoryLogger.instance.totalQueryTime,
		 iTotalDisplayRecords: count, aaData: createSqlStatementData(start, maxCount, searchString, sortColumn, sortDirection)]
	}

	List createSqlStatementData(Integer start, Integer maxCount, String searchString, Integer sortColumn, String sortDirection) {

		List<Entry> allEntries = MemoryLogger.instance.entries

		List<Entry> entries = searchString ? filterByText(allEntries, searchString, start, maxCount) : allEntries
		entries = findMatchingEntries(entries, start, maxCount)
		sortEntries entries, sortColumn, sortDirection == 'desc'

		entries.collect { Entry entry ->
			[entry.id, entry.dateString, entry.elapsedTime, entry.connectionId,
			 entry.categoryName, entry.preparedSql, entry.sql]
		}
	}

	Map createQueryCountChartData() {

		MemoryLogger memoryLogger = MemoryLogger.instance

		long lowestTime = memoryLogger.earliestQueryTime
		long highestTime = memoryLogger.latestQueryTime

		if (lowestTime == Long.MAX_VALUE || highestTime == Long.MIN_VALUE) {
			return null
		}

		int timeIncrement = 1000
		int slices = ((highestTime - lowestTime) / timeIncrement) as int
		if (slices < 2) {
			return null
		}

		int[] queryCounts = new int[slices]
		int[] statementCounts = new int[slices]
		int[] resultsetCounts = new int[slices]
		int[] selectCounts = new int[slices]
		String[] xAxisLabels = new String[slices]

		int slice = 0
		int statementCount = 0
		int resultsetCount = 0
		int selectCount = 0
		int queriesInSlice = 0
		long sampleStartTime = lowestTime
		long sampleEndTime = lowestTime + timeIncrement
		for (Entry entry : memoryLogger.entries.reverse()) {
			while (entry.time > sampleEndTime) {
				xAxisLabels[slice] = (sampleStartTime - lowestTime) / 1000
				queryCounts[slice] = queriesInSlice
				statementCounts[slice] = statementCount
				resultsetCounts[slice] = resultsetCount
				selectCounts[slice] = selectCount
				statementCount = 0
				resultsetCount = 0
				selectCount = 0
				queriesInSlice = 0
				slice++
				sampleStartTime = lowestTime + timeIncrement * slice
				sampleEndTime = lowestTime + timeIncrement * (slice + 1)
			}
			queriesInSlice++
			if (entry.category) {
				String categoryNameLower = entry.categoryName.toLowerCase()
				if ('statement' == categoryNameLower) {
					statementCount++
					if (isSelectQuery(entry.sql)) {
						selectCount++
					}
				}
				else if ('resultset' == categoryNameLower) {
					resultsetCount++
				}
			}
		}

		[xAxisLabels: xAxisLabels, queryCounts: queryCounts, statementCounts: statementCounts,
		 resultsetCounts: resultsetCounts, selectCounts: selectCounts]
	}

	protected boolean isSelectQuery(String sql) {
		removeStartComment(sql).toLowerCase().startsWith('select')
	}

	protected String removeStartComment(String sql) {
		sql = sql.trim()
		if (!sql.startsWith('/*')) {
			return sql
		}
		int index = sql.indexOf('*/', 2)
		sql = sql[index+2..-1].trim()
		removeStartComment sql
	}

	Map createQueryTrafficChartData() {

		MemoryLogger memoryLogger = MemoryLogger.instance

		long lowestTime = memoryLogger.earliestQueryTime
		long highestTime = memoryLogger.latestQueryTime

		if (lowestTime == Long.MAX_VALUE || highestTime == Long.MIN_VALUE) {
			return null
		}

		long timeInterval = highestTime - lowestTime
		int timeIncrement = 1000
		int slices = (timeInterval / timeIncrement) as int
		if (slices < 2) {
			return null
		}

		String[] xAxisLabels = new String[slices]
		long[] outboundTotals = new long[slices]
		long[] inboundTotals = new long[slices]

		int slice = 0
		long inboundTotal = 0
		long outboundTotal = 0
		long currentStartTime = lowestTime
		long currentEndTime = lowestTime + timeIncrement
		for (Entry entry : memoryLogger.entries.reverse()) {
			while (entry.time > currentEndTime) {
				xAxisLabels[slice] = (currentStartTime - lowestTime) / 1000
				outboundTotals[slice] = outboundTotal
				inboundTotals[slice] = inboundTotal
				inboundTotal = 0
				outboundTotal = 0
				slice++
				currentStartTime = lowestTime + timeIncrement * slice
				currentEndTime = lowestTime + timeIncrement * (slice + 1)
			}
			if (entry.category) {
				String categoryNameLower = entry.categoryName.toLowerCase()
				if ('statement' == categoryNameLower) {
					outboundTotal += entry.sql.length()
				}
				else if ('resultset' == categoryNameLower) {
					inboundTotal += entry.sql.length()
				}
			}
		}

		[xAxisLabels: xAxisLabels, outboundTotals: outboundTotals, inboundTotals: inboundTotals]
	}

	protected List<Entry> filterByText(List<Entry> entries, String text, int start, int maxCount) {
		List<Entry> matching = []

		int totalCount = entries.size()
		for (int i = start; i < totalCount; i++) {
			Entry entry = entries[i]
			if (containsText(entry, text)) {
				matching << entry
				if (matching.size() == maxCount) {
					break
				}
			}
		}

		matching
	}

	protected boolean containsText(Entry entry, String text) {
		String lowerText = text.toLowerCase()

		entry.preparedSql.toLowerCase().contains(lowerText) ||
		entry.sql.toLowerCase().contains(lowerText) ||
		entry.categoryName.toLowerCase().contains(lowerText)
	}

	protected List<Entry> findMatchingEntries(List<Entry> entries, int start, int maxCount) {
		List<Entry> matching = []
		int max = Math.min(start + maxCount, entries.size())
		for (int i = start; i < max; i++) {
			matching << entries[i]
		}
		matching
	}

	protected void sortEntries(List<Entry> entries, Integer sortColumn, boolean descending) {
		String columnName = (COLUMN_NAME_TO_ENTRY_PROPERTY.keySet() as List)[sortColumn ?: 0]
		String propertyName = COLUMN_NAME_TO_ENTRY_PROPERTY[columnName]
		entries.sort { it[propertyName] }
		if (descending) {
			entries.reverse true
		}
	}

	void clearEntries() {
		MemoryLogger.instance.clear()
	}
}
