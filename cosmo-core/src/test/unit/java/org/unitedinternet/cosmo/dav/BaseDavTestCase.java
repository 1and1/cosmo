/*
 * Copyright 2006 Open Source Applications Foundation
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
package org.unitedinternet.cosmo.dav;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for DAV test cases.
 */
public abstract class BaseDavTestCase implements ExtendedDavConstants {
    protected DavTestHelper testHelper;

    /**
     * SetUp.
     * @throws Exception - if something is wrong this exception is thrown.
     */
    @BeforeEach
    public void setUp() throws Exception {
        testHelper = new DavTestHelper();
        testHelper.setUp();
    }

    /**
     * Tear down.
     * @throws Exception - if something is wrong this exception is thrown.
     */
    @AfterEach
    public void tearDown() throws Exception {
        testHelper.tearDown();
    }
}
