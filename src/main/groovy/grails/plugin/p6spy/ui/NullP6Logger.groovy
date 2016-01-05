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

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.P6Logger

/**
 * No-op logger used as initial logger to prevent a spy.log file from being created.
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class NullP6Logger implements P6Logger {
	void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql) {}
	void logException(Exception e) {}
	void logText(String text) {}
	boolean isCategoryEnabled(Category category) { false }
}
