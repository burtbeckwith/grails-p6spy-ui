package grails.plugin.p6spy.ui

import java.text.SimpleDateFormat

class Entry {

	protected static final String DATE_FORMAT = 'yyyy-MM-dd hh:mm:ss a'

	final int id
	final long time
	final long elapsedTime
	final String preparedSql
	final String sql
	final String category
	final int connectionId

	Entry(int id, long now, long elapsedTime, String preparedSql,
			String sql, String category, int connectionId) {
		time = now
		this.id = id
		this.elapsedTime = elapsedTime
		this.preparedSql = preparedSql
		this.sql = sql
		this.category = category
		this.connectionId = connectionId
	}

	String getDateString() { new SimpleDateFormat(DATE_FORMAT).format new Date(time) }
}
