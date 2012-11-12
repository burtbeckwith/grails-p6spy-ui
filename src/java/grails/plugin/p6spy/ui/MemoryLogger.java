package grails.plugin.p6spy.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.p6spy.engine.logging.appender.P6Logger;

// TODO needs max size

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
public class MemoryLogger implements P6Logger {

	private String lastEntry;
	private AtomicInteger index = new AtomicInteger(0);
	private List<Entry> entries = new CopyOnWriteArrayList<Entry>();
	private long earliestQueryTime = Long.MAX_VALUE;
	private long latestQueryTime = Long.MIN_VALUE;
	private long totalQueryTime = 0;

	private static MemoryLogger instance;

	public MemoryLogger() {
		// instantiated by p6spy
		instance = this;
	}

	public static MemoryLogger getInstance() {
		return instance;
	}

	public void logSQL(int connectionId, String now, long elapsed,
			String category, String prepared, String sql) {

		long entryTime = Long.valueOf(now);

		entries.add(0, new Entry(index.incrementAndGet(), entryTime, elapsed, prepared, sql, category, connectionId));

		if (entryTime > latestQueryTime) {
			latestQueryTime = entryTime;
		}

		if (entryTime < earliestQueryTime) {
			earliestQueryTime = entryTime;
		}

		if (elapsed > 0) {
			totalQueryTime += elapsed;
		}
	}

	public void logException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		logText(sw.toString());
	}

	public void logText(String text) {
//		log.log(level, text);
		lastEntry = text;
	}

	public String getLastEntry() {
		return lastEntry;
	}

	public List<Entry> getEntries() {
		return new ArrayList<Entry>(entries);
	}

	public int getEntryCount() {
		return entries.size();
	}

	public long getTotalQueryTime() {
		return totalQueryTime;
	}

	public long getEarliestQueryTime() {
		return earliestQueryTime;
	}

	public long getLatestQueryTime() {
		return latestQueryTime;
	}

	public void clear() {
		lastEntry = null;
		entries.clear();
		earliestQueryTime = Long.MAX_VALUE;
		latestQueryTime = Long.MIN_VALUE;
		totalQueryTime = 0;
	}
}
