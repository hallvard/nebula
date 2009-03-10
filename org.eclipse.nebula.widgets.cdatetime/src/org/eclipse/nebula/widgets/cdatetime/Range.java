/****************************************************************************
 * Copyright (c) 2009 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <jeremyd@aspencloud.com> - initial API and implementation
 *****************************************************************************/

package org.eclipse.nebula.widgets.cdatetime;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Acceptable types:
 * <ul>
 *   <li>java.util.Date</li>
 *   <li>java.sql.Date</li>
 *   <li>java.sql.Time</li>
 *   <li>java.sql.Timestamp</li>
 * </ul>
 * @param <T> The type of range
 */
public class Range<T> {

	public static Range<Date> createRange() {
		return new Range<Date>(Date.class);
	}
	
	public static Range<java.sql.Date> createDateRange() {
		return new Range<java.sql.Date>(java.sql.Date.class);
	}
	
	public static Range<Time> createTimeRange() {
		return new Range<Time>(Time.class);
	}
	
	public static Range<Timestamp> createTimestampRange() {
		return new Range<Timestamp>(Timestamp.class);
	}
	
	Class<T> type;
	T start;
	T end;
	
	private Range(Class<T> type) {
		this.type = type;
		start = newTypeInstance();
		end = newTypeInstance();
	}

	public T getStart() {
		return start;
	}
	
	public T getEnd() {
		return end;
	}
	
	public Class<T> getType() {
		return type;
	}
	
	private T newTypeInstance() {
		if(type == Date.class) {
			return (T) new Date();
		}
		if(type == java.sql.Date.class) {
			return (T) new java.sql.Date(System.currentTimeMillis());
		}
		if(type == Time.class) {
			return (T) new Time(System.currentTimeMillis());
		}
		if(type == Timestamp.class) {
			return (T) new Timestamp(System.currentTimeMillis());
		}
		throw new UnsupportedOperationException("Invalid Type"); //$NON-NLS-1$
	}

	public void setStart(T start) {
		this.start = start;
	}
	
	public void setEnd(T end) {
		this.end = end;
	}
	
}
