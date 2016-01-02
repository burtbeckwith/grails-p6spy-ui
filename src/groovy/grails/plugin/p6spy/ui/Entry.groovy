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
