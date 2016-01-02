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

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.P6Logger

// TODO needs max size

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
@CompileStatic
class MemoryLogger implements P6Logger {

	private String lastEntry
	private AtomicInteger index = new AtomicInteger(0)
	private List<Entry> entries = new CopyOnWriteArrayList<Entry>()
	private long earliestQueryTime
	private long latestQueryTime
	private long totalQueryTime

	private static MemoryLogger instance

	static MemoryLogger getInstance() { instance }

	MemoryLogger() {
		clear()

		// instantiated by p6spy
		instance = this
	}

	boolean isCategoryEnabled(Category category) { true }

	void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {

		long entryTime = now ? now as long : System.currentTimeMillis()

		entries.add 0, new Entry(index.incrementAndGet(), entryTime, elapsed, prepared, sql, category, connectionId)

		if (entryTime > latestQueryTime) {
			latestQueryTime = entryTime
		}

		if (entryTime < earliestQueryTime) {
			earliestQueryTime = entryTime
		}

		if (elapsed > 0) {
			totalQueryTime += elapsed
		}
	}

	void logException(Exception e) {
		StringWriter sw = new StringWriter()
		PrintWriter pw = new PrintWriter(sw)
		e.printStackTrace pw
		logText sw.toString()
	}

	void logText(String text) {
		lastEntry = text
	}

	String getLastEntry() { lastEntry }

	List<Entry> getEntries() { [] + entries }

	int getEntryCount() { entries.size() }

	long getTotalQueryTime() { totalQueryTime }

	long getEarliestQueryTime() { earliestQueryTime }

	long getLatestQueryTime() { latestQueryTime }

	void clear() {
		lastEntry = null
		entries.clear()
		earliestQueryTime = Long.MAX_VALUE
		latestQueryTime = Long.MIN_VALUE
		totalQueryTime = 0
	}
}
