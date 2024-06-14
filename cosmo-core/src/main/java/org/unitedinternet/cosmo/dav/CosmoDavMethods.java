/*
 * Copyright 2005-2006 Open Source Applications Foundation
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

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.webdav.DavMethods;
import org.apache.jackrabbit.webdav.DavServletRequest;
import org.unitedinternet.cosmo.CosmoConstants;

/**
 * Mimics {@link org.apache.jackrabbit.webdav.DavMethods} to define
 * constants for dav methods not already covered by jcr-server (those
 * defined in the CalDAV and ticket specs).
 *
 * See
 * http://www.sharemation.com/%7Emilele/public/dav/draft-ito-dav-ticket-00.txt
 * and http://ietf.webdav.org/caldav/draft-dusseault-caldav.txt for
 * more information on these methods.
 */
public class CosmoDavMethods {

    private static Map<String, Integer> methods = new HashMap<>();

    /**
     * The MKTICKET method and public constant as defined in
     * "Ticket-Based Access Control Extension to WebDAV"
     */
    public static final int DAV_MKTICKET = 100;
    public static final String METHOD_MKTICKET = "MKTICKET";

    /**
     * The DELTICKET method and public constant
     */
    public static final int DAV_DELTICKET = DAV_MKTICKET + 1;
    public static final String METHOD_DELTICKET = "DELTICKET";

    /**
     * The MKCALENDAR method and public constant
     */
    public static final int DAV_MKCALENDAR = DAV_DELTICKET + 1;
    public static final String METHOD_MKCALENDAR = "MKCALENDAR";

    /**
     * Augments superclass method to also return <code>true</code> for
     * <code>MKCALENDAR</code> requests.
     * @param request DavServletRequest.
     * @return If the request is created or not.
     */
    public static boolean isCreateRequest(DavServletRequest request) {
        if (getMethodCode(request.getMethod()) == DAV_MKCALENDAR) {
            return true;
        }
        return DavMethods.isCreateRequest(request);
    }

    /**
     * Augments superclass method to also return <code>true</code> for
     * <code>MKCALENDAR</code> requests.
     * @param request DavServletRequest.
     * @return If the collection request is created.
     */
    public static boolean isCreateCollectionRequest(DavServletRequest request) {
        if (getMethodCode(request.getMethod()) == DAV_MKCALENDAR) {
            return true;
        }
        return DavMethods.isCreateCollectionRequest(request);
    }

    /**
     * Return the type code for a dav method. Valid type codes are
     * positive. Unknown methods are represented by <code>0</code>.
     * @param method The dav method.
     * @return The type code for a dav method.
     */
    public static int getMethodCode(String method) {
        Integer code = (Integer) methods.get(method.toUpperCase(CosmoConstants.LANGUAGE_LOCALE));
        if (code != null) {
            return code.intValue();
        }
        return 0;
    }

    private static void addMethodCode(String method, int code) {
        methods.put(method, Integer.valueOf(code));
    }

    static {
        addMethodCode(METHOD_MKTICKET, DAV_MKTICKET);
        addMethodCode(METHOD_DELTICKET, DAV_DELTICKET);
        addMethodCode(METHOD_MKCALENDAR, DAV_MKCALENDAR);
    }

    /**
     * Augments superclass method to also return <code>true</code> for
     * <code>MKCALENDAR</code> requests.
     * @param request DavServletRequest.
     * @return IF the calendar collection request is created.
     */
    private static boolean isCreateCalendarCollectionRequest(DavServletRequest request) {
        if (getMethodCode(request.getMethod()) == DAV_MKCALENDAR) {
            return true;
        }
        return false;
    }
}