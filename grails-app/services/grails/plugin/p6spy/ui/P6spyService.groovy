package grails.plugin.p6spy.ui

import java.text.SimpleDateFormat

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class P6spyService {

	protected static final String DATE_FORMAT = 'yyyy.MM.dd hh:mm:ss.SSS'

	static transactional = false

	def createQueryCountChartData() {

		MemoryLogger memoryLogger = MemoryLogger.instance

		long lowestTime = memoryLogger.earliestQueryTime
		long highestTime = memoryLogger.latestQueryTime

		if (lowestTime == Long.MAX_VALUE || highestTime == Long.MIN_VALUE) {
			return null
		}

		int timeIncrement = 1000
		int slices = (highestTime - lowestTime) / timeIncrement
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
				if ('statement'.equals(entry.category.toLowerCase())) {
					statementCount++
					if (isSelectQuery(entry.sql)) {
						selectCount++
					}
				}
				else if ('resultset'.equals(entry.category.toLowerCase())) {
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

	def createQueryTrafficChartData() {

		MemoryLogger memoryLogger = MemoryLogger.instance

		long lowestTime = memoryLogger.earliestQueryTime
		long highestTime = memoryLogger.latestQueryTime

		if (lowestTime == Long.MAX_VALUE || highestTime == Long.MIN_VALUE) {
			return null
		}

		long timeInterval = highestTime - lowestTime
		int timeIncrement = 1000
		int slices = timeInterval / timeIncrement
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
				if ('statement'.equals(entry.category.toLowerCase())) {
					outboundTotal += entry.sql.length()
				}
				else if ('resultset'.equals(entry.category.toLowerCase())) {
					inboundTotal += entry.sql.length()
				}
			}
		}

		[xAxisLabels: xAxisLabels, outboundTotals: outboundTotals, inboundTotals: inboundTotals]
	}

	def createSqlStatementData(Integer start, Integer maxCount, String searchString) {

		List<Entry> allEntries = MemoryLogger.instance.entries

		List<Entry> entries = searchString ? filterByText(allEntries, searchString, start, maxCount) : allEntries
		entries = findMatchingEntries(entries, start, maxCount)

		entries.collect { Entry entry ->
			[entry.id, new SimpleDateFormat(DATE_FORMAT).format(new Date(entry.time)), entry.elapsedTime,
			 entry.connectionId, entry.category, entry.preparedSql, entry.sql]
		}
	}

	protected List<Entry> filterByText(List<Entry> entries, String text, int start, int maxCount) {
		List<Entry> matching = []

		int totalCount = entries.size()
		for (int i = start; i < totalCount; i++) {
			Entry entry = entries.get(i)
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
		entry.category.toLowerCase().contains(lowerText)
	}

	protected List<Entry> findMatchingEntries(List<Entry> entries, int start, int maxCount) {
		List<Entry> matching = []
		int max = Math.min(start + maxCount, entries.size())
		for (int i = start; i < max; i++) {
			matching << entries.get(i)
		}
		matching
	}

	void clearEntries() {
		MemoryLogger.instance.clear()
	}
}
