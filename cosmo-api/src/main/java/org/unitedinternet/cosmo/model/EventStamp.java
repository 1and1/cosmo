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

import java.util.List;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Represents a calendar event, recurring or non-recurring.
 */
public interface EventStamp extends BaseEventStamp {

    
    /**
     * Returns a list of exception components for a recurring event.
     * If the event is not recurring, the list will be empty.
     */
    public List<Component> getExceptions();

    /**
     * Returns the master event extracted from the underlying
     * icalendar object. Changes to the master event will be persisted
     * when the stamp is saved.
     */
    public VEvent getMasterEvent();

}