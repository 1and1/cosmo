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
 * This class represents a URL that addresses a item by uid.
 * <p>
 * Items in the Cosmo database are addressed similarly
 * regardless of which protocol is used to access the data. An
 * item URL includes the service mount URL, the literal path
 * component <code>item</code>, the uid of the item, and any
 * extra path information used by the protocol handler.
 * <p>
 * For example, the URL
 * <code>http://localhost:8080/cosmo/dav/item/cafebebe-deadbeef</code>
 * is the WebDAV home collection address for the item with the
 * specified uid.
 */
public class ItemPath {
   
    private static final Pattern PATTERN_ITEM_UID =
        Pattern.compile("^.*/(item|expanded|detached)/([^/]+)(/.*)?$");

    private String urlPath;
    private String uid;
    private String pathInfo;

    /**
     * Constructs a <code>ItemPath</code> instance based on the
     * servlet-relative component of the url-path from an item
     * URL.
     *
     * @param urlPath the servlet-relative url-path
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/")
     * @throws IllegalStateException if the given url-path does not
     * represent an item path
     */
    public ItemPath(String urlPath) {
        if (! urlPath.startsWith("/")) {
            throw new IllegalArgumentException("urlPath must start with /");
        }

        this.urlPath = urlPath;

        Matcher itemMatcher = PATTERN_ITEM_UID.matcher(urlPath);
        if (! itemMatcher.matches()) {
            throw new IllegalStateException("urlPath is not an item path");
        }
        this.uid = itemMatcher.group(2);
        this.pathInfo = itemMatcher.group(3);
    }

    /** */
    public String getUrlPath() {
        return urlPath;
    }

    /** */
    public String getUid() {
        return uid;
    }

    /** */
    public String getPathInfo() {
        return pathInfo;
    }

    /**
     * Parses the given url-path, returning an instance of
     * <code>ItemPath</code>. Extra path info is disallowed.
     *
     * @param urlPath the servlet-relative url-path
     * @return an instance of <code>ItemPath</code> if the
     * url-path represents an item path, or <code>null</code>
     * otherwise.
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/") or if disallowed path
     * info is present
     */
    public static ItemPath parse(String urlPath) {
        return parse(urlPath, false);
    }

    /**
     * Parses the given url-path, returning an instance of
     * <code>ItemPath</code>.
     *
     * @param urlPath the servlet-relative url-path
     * @param allowPathInfo determines whether or not extra path info
     * is allowed
     * @return an instance of <code>ItemPath</code> if the
     * url-path represents an item path, or <code>null</code>
     * otherwise.
     *
     * @throws IllegalArgumentException if the given url-path is not
     * servlet-relative (starts with a "/")
     */
    public static ItemPath parse(String urlPath,
                                 boolean allowPathInfo) {
        if (urlPath == null) {
            return null;
        }
        try {
            ItemPath ip = new ItemPath(urlPath);
            if (! allowPathInfo && ip.getPathInfo() != null) {
                return null;
            }
            return ip;
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
