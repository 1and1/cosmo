/*
 * Copyright 2005-2007 Open Source Applications Foundation
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
package org.unitedinternet.cosmo.service.impl;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.unitedinternet.cosmo.TestHelper;
import org.unitedinternet.cosmo.dao.mock.MockContentDao;
import org.unitedinternet.cosmo.dao.mock.MockDaoStorage;
import org.unitedinternet.cosmo.dao.mock.MockUserDao;
import org.unitedinternet.cosmo.model.User;

/**
 * Test Case for {@link StandardUserService}.
 */
public class StandardUserServiceTest {

    private StandardUserService service;
    private MockDaoStorage storage;
    private MockContentDao contentDao;
    private MockUserDao userDao;
    private TestHelper testHelper;

    /**
     * Setup.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @BeforeEach
    public void setUp() throws Exception {
        testHelper = new TestHelper();
        storage = new MockDaoStorage();
        contentDao = new MockContentDao(storage);
        userDao = new MockUserDao(storage);
        service = new StandardUserService();
        service.setContentDao(contentDao);
        service.setUserDao(userDao);
        KeyBasedPersistenceTokenService keyBasedPersistenceTokenService = new KeyBasedPersistenceTokenService();
        keyBasedPersistenceTokenService.setServerSecret("cosmossecret");
        keyBasedPersistenceTokenService.setServerInteger(123);
        keyBasedPersistenceTokenService.setSecureRandom(new SecureRandom());
        service.setPasswordGenerator(keyBasedPersistenceTokenService);
        service.init();
    }

    /**
     * Tests get users.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testGetUsers() throws Exception {
        User u1 = testHelper.makeDummyUser();
        userDao.createUser(u1);
        User u2 = testHelper.makeDummyUser();
        userDao.createUser(u2);
        User u3 = testHelper.makeDummyUser();
        userDao.createUser(u3);

        assertNotNull(userDao.getUser(u1.getUsername()), "User 1 not found in users");
        assertNotNull(userDao.getUser(u2.getUsername()), "User 2 not found in users");
        assertNotNull(userDao.getUser(u3.getUsername()), "User 3 not found in users");
    }

    /**
     * Tests get user.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testGetUser() throws Exception {
        User u1 = testHelper.makeDummyUser();
        String username1 = u1.getUsername();
        userDao.createUser(u1);

        User user = service.getUser(username1);
        assertNotNull(user, "User " + username1 + " null");
    }

    /**
     * Tests get user by email.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testGetUserByEmail() throws Exception {
        User u1 = testHelper.makeDummyUser();
        String email1 = u1.getEmail();
        userDao.createUser(u1);

        User user = service.getUserByEmail(email1);
        assertNotNull(user, "User " + email1 + " null");
    }

    /**
     * Tests create user.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testCreateUser() throws Exception {
        User u1 = testHelper.makeDummyUser();
        String password = u1.getPassword();

        User user = service.createUser(u1);
        assertNotNull(userDao.getUser(u1.getUsername()), "User not stored");
        assertFalse(user.getPassword().equals(password), "Original and stored password are the same");
        assertEquals(user.getCreationDate(), user.getModifiedDate());
    }

    /**
     * Tests update user.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testUpdateUser() throws Exception {
        User u1 = testHelper.makeDummyUser();
        u1.setPassword(service.digestPassword(u1.getPassword()));
        String digestedPassword = u1.getPassword();

        userDao.createUser(u1);

        // change password
        u1.setPassword("changedpwd");

        Thread.sleep(1000); // let modified date change
        User user = service.updateUser(u1);
        try {
            userDao.getUser(user.getUsername());
        } catch (DataRetrievalFailureException e) {
            fail("User not stored");
        }
        assertFalse(user.getPassword().equals(digestedPassword), "Original and stored password are the same");
        assertTrue(!user.getCreationDate().equals(user.getModifiedDate()), "Created and modified dates are the same");


        Thread.sleep(1000); // let modified date change

        // leave raw password
        u1.setPassword("changedpwd");
        User user2 = service.updateUser(u1);
        try {
            userDao.getUser(user.getUsername());
        } catch (DataRetrievalFailureException e) {
            fail("User not stored");
        }
        assertTrue(user2.getPassword().equals(user.getPassword()), "Original and stored password are not the same");
        assertTrue(!user2.getCreationDate().equals(user2.getModifiedDate()), "Created and modified dates are the same");
    }

    /**
     * Tests remove user.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testRemoveUser() throws Exception {
        User u1 = testHelper.makeDummyUser();
        service.createUser(u1);
        service.removeUser(u1);
        assertNull(service.getUser(u1.getUsername()), "User not removed");
    }

    /**
     * Tests remove user by username.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testRemoveUserByUsername() throws Exception {
        User u1 = testHelper.makeDummyUser();
        service.createUser(u1);

        service.removeUser(u1.getUsername());

        assertNull(service.getUser(u1.getUsername()), "User not removed");
    }

    /**
     * Tests generate password.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testGeneratePassword() throws Exception {
        String pwd = service.generatePassword();

        assertTrue(pwd.length() <= service.getPasswordLengthMax(), "Password too long");
        assertTrue(pwd.length() >= service.getPasswordLengthMin(), "Password too short");
    }

    /**
     * Tests null user dao.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testNullUserDao() throws Exception {
        service.setUserDao(null);
        try {
            service.init();
            fail("Should not be able to initialize service without userDao");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Tests null password generator.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testNullPasswordGenerator() throws Exception {
        service.setPasswordGenerator(null);
        try {
            service.init();
            fail("Should not be able to initialize service without passwordGenerator");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * Tests default digest algorithm.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testDefaultDigestAlgorithm() throws Exception {
        assertEquals(service.getDigestAlgorithm(), "MD5");
    }

    /**
     * Test digest password.
     * 
     * @throws Exception
     *             - if something is wrong this exception is thrown.
     */
    @Test
    public void testDigestPassword() throws Exception {
        String password = "deadbeef";

        String digested = service.digestPassword(password);

        // tests MD5
        assertTrue(digested.length() == 32, "Digest not correct length");

        // tests hex
        assertTrue(digested.matches("^[0-9a-f]+$"), "Digest not hex encoded");
    }
}
