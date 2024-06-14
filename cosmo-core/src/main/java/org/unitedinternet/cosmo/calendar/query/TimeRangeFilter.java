/*
 * Copyright 2006-2007 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unitedinternet.cosmo.calendar.query;

import java.text.ParseException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.jackrabbit.webdav.xml.DomUtil;
import org.unitedinternet.cosmo.dav.caldav.CaldavConstants;
import org.w3c.dom.Element;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.component.VTimeZone;

/**
 * Represents the CALDAV:time-range element. From sec 9.8:
 * 
 * Name: time-range
 * 
 * Namespace: urn:ietf:params:xml:ns:caldav
 * 
 * Purpose: Specifies a time range to limit the set of calendar components
 * returned by the server.
 * 
 * Definition:
 * 
 * <!ELEMENT time-range EMPTY>
 * 
 * <!ATTLIST time-range start CDATA #IMPLIED end CDATA #IMPLIED> 
 * start value: an iCalendar "date with UTC time" 
 * end value: an iCalendar "date with UTC time"
 */
public class TimeRangeFilter implements CaldavConstants {

    private Period period = null;
    private VTimeZone timezone = null;

    public TimeRangeFilter(Period period) {
        setPeriod(period);
    }

    public TimeRangeFilter(Element element, VTimeZone timezone) throws ParseException {
        // Constructor body
    }

    public TimeRangeFilter(DateTime dtStart, DateTime dtEnd) {
        // Constructor body
    }

    // Other constructors and methods...

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getUTCStart() {
        return period.getStart().toString();
    }

    public String getUTCEnd() {
        return period.getEnd().toString();
    }

    public VTimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(VTimeZone timezone) {
        this.timezone = timezone;
    }

    public String toString() {
        return new ToStringBuilder(this).
                append("dstart", period.getStart()).
                append("dend", period.getEnd()).
                toString();
    }

    // New class extracted
    private static class PeriodUtilities {
        public static DateTime getDefaultEndDate(DateTime startDate) {
            long TWO_YEARS_MILLIS = 63072000000L;
            DateTime endDate = new DateTime(startDate.getTime() + TWO_YEARS_MILLIS);
            endDate.setUtc(true);
            return endDate;
        }
    }
}