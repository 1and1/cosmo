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
package org.unitedinternet.cosmo.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a URL that addresses a user by username.
 * <p>
 * Users in the Cosmo database are addressed similarly
 * regardless of which protocol is used to access the data. A
 * user URL includes the service mount URL, the literal path
 * component <code>user</code>, the username of the user, and any
 * extra path information used by the protocol handler.
 * <p>
 * For example, the URL
 * <code>http://localhost:8080/cosmo/dav/user/bcm</code>
 * is the WebDAV home collection address for the user with the
 * specified username.
 */
public class UserPath {
    
    private static final Pattern PATTERN_USER_USERNAME =
        Pattern.compile("^.*/user/([^/]+)(/.*)?$");

    private String urlPath;
    private String username;
    private String pathInfo;

    /**
     * Constructs a <code>UserPath</code> instance based on the
     * servlet-relative component of the url-path from a user
     * URL.
     *
     * @param urlPath the servlet-relative url-path
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/")
     * @throws IllegalStateException if the given url-path does not
     * represent a user path
     */
    public UserPath(String urlPath) {
        if (! urlPath.startsWith("/")) {
            throw new IllegalArgumentException("urlPath must start with /");
        }

        this.urlPath = urlPath;

        Matcher userMatcher = PATTERN_USER_USERNAME.matcher(urlPath);
        if (! userMatcher.matches()) {
            throw new IllegalStateException("urlPath is not a user path");
        }
        this.username = userMatcher.group(1);
        this.pathInfo = userMatcher.group(2);
    }

    /** */
    public String getUrlPath() {
        return urlPath;
    }

    /** */
    public String getUsername() {
        return username;
    }

    /** */
    public String getPathInfo() {
        return pathInfo;
    }

    /**
     * Parses the given url-path, returning an instance of
     * <code>UserPath</code>. Extra path info is disallowed.
     *
     * @param urlPath the servlet-relative url-path
     * @return an instance of <code>UserPath</code> if the
     * url-path represents a user path, or <code>null</code>
     * otherwise.
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/") or if disallowed path
     * info is present
     */
    public static UserPath parse(String urlPath) {
        return parse(urlPath, false);
    }

    /**
     * Parses the given url-path, returning an instance of
     * <code>UserPath</code>.
     *
     * @param urlPath the servlet-relative url-path
     * @param allowPathInfo determines whether or not extra path info
     * is allowed
     * @return an instance of <code>UserPath</code> if the
     * url-path represents a user path, or <code>null</code>
     * otherwise.
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/")
     */
    public static UserPath parse(String urlPath,
                                 boolean allowPathInfo) {
        if (urlPath == null) {
            return null;
        }
        try {
            UserPath up = new UserPath(urlPath);
            if (! allowPathInfo && up.getPathInfo() != null) {
                return null;
            }
            return up;
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
