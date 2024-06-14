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
 */package org.unitedinternet.cosmo.model;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A schedule consists of a name and set of properties.
 */
public class Schedule {
    private String name;
    private Map<String, String> properties;

    public Schedule(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties == null ? null : properties.get(key);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Schedule)) {
            return false;
        }

        Schedule other = (Schedule) obj;
        return arePropertiesEqual(other) && areNamesEqual(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(properties).toHashCode();
    }

    private boolean arePropertiesEqual(Schedule other) {
        return new EqualsBuilder().append(properties, other.properties).isEquals();
    }

    private boolean areNamesEqual(Schedule other) {
        return new EqualsBuilder().append(name, other.name).isEquals();
    }
}