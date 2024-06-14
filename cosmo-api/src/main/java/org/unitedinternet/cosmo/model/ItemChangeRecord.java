/*
 * Copyright 2008 Open Source Applications Foundation
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents an item change.  Includes who, when and what changed.
 */
public class ItemChangeRecord {
    public enum Action { ITEM_ADDED, ITEM_CHANGED, ITEM_REMOVED }
    
    private Action action;
    private Date date;

    public ItemChangeRecord() {}
    
    public Action getAction() {
        return action;
    }
    public void setAction(Action action) {
        this.action = action;
    }
    public Date getDate() {
        return date != null ? (Date)date.clone(): null;
    }
    public void setDate(Date date) {
        if(date != null){
            this.date = (Date)date.clone();
        }
    }
    private static final Map<String, Supplier<Action>> actionMap = new HashMap<>();

    static {
        actionMap.put("ItemAdded", () -> Action.ITEM_ADDED);
        actionMap.put("ItemRemoved", () -> Action.ITEM_REMOVED);
        actionMap.put("ItemChanged", () -> Action.ITEM_CHANGED);
        actionMap.put("ItemUpdated", () -> Action.ITEM_CHANGED);
    }


    public static Action toAction(String action) {
        Supplier<Action> actionSupplier = actionMap.get(action);
        if (actionSupplier != null) {
            return actionSupplier.get();
        } else {
            throw new IllegalStateException("Unknown action " + action);
        }
    }
}