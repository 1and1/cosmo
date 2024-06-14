    /*
     * Copyright 2007 Open Source Applications Foundation
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
    package org.unitedinternet.cosmo.model;

    import java.time.temporal.TemporalAmount;
    import java.util.List;

    import net.fortuna.ical4j.model.Calendar;
    import net.fortuna.ical4j.model.Date;
    import net.fortuna.ical4j.model.DateList;
    import net.fortuna.ical4j.model.Recur;
    import net.fortuna.ical4j.model.component.VEvent;
    import net.fortuna.ical4j.model.property.Trigger;

    /**
     * Represents a stamp that contains event specific information.
     */
    public interface BaseEventStamp extends Stamp {

        /**
         * Get the underlying VEvent
         *
         * @return VEvent
         */
        public VEvent getEvent();

        /**
         * Get the Calendar that contains the VEvent
         *
         * @return Calendar containing VEvent
         */
        public Calendar getEventCalendar();

        /**
         * Set the Calendar that contains the VEvent
         *
         * @param calendar Calendar containing VEvent
         */
        public void setEventCalendar(Calendar calendar);

        /**
         * Returns a copy of the the iCalendar UID property value of the event .
         */
        public String getIcalUid();

        /**
         * Sets the iCalendar UID property of the event.
         *
         * @param uid uid of VEVENT
         */
        public void setIcalUid(String uid);

        /**
         * Returns a copy of the the iCalendar DTSTART property value of the event (never null).
         */
        public Date getStartDate();

        /**
         * Returns the end date of the event as calculated from the iCalendar DTEND property value or the the iCalendar
         * DTSTART + DURATION.
         */
        public Date getEndDate();

        /**
         * Returns the duration of the event as calculated from the iCalendar DURATION property value or the the iCalendar
         * DTEND - DTSTART.
         */
        public TemporalAmount getDuration();

        /**
         * Returns a list of copies of the iCalendar RRULE property values of the event (can be empty).
         */
        public List<Recur> getRecurrenceRules();

        /**
         * Returns a list of copies of the iCalendar RDATE property values of the event (can be empty).
         */
        public DateList getRecurrenceDates();

        /**
         * Returns a list of copies of the values of all iCalendar EXDATE properties of the event (can be empty).
         */
        public DateList getExceptionDates();

        /**
         * Return the Trigger of the first display alarm on the event
         *
         * @return trigger of the first display alarm
         */
        public Trigger getDisplayAlarmTrigger();

        /**
         * Sets a single iCalendar EXDATE property of the event, removing any EXDATEs that were previously set.
         *
         * @param dates a <code>DateList</code>
         */
        public void setExceptionDates(DateList dates);

        /**
         * Returns a copy of the the iCalendar RECURRENCE_ID property value of the event (can be null).
         */
        public Date getRecurrenceId();

        /**
         * Determine if an event is recurring
         *
         * @return true if the underlying event is a recurring event
         */
        public boolean isRecurring();

    }