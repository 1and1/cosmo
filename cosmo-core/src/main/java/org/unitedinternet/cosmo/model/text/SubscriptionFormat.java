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
package org.unitedinternet.cosmo.model.text;

import java.text.ParseException;

import org.unitedinternet.cosmo.model.CollectionSubscription;
import org.unitedinternet.cosmo.model.EntityFactory;

/**
 * Interface for subscription formatters.
 */
public interface SubscriptionFormat {

    CollectionSubscription parse(String source, EntityFactory entityFactory)
        throws ParseException;

    String format(CollectionSubscription sub);
}
