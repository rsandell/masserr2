/*
 * The MIT License
 *
 * Copyright (c) 2013-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.joinedminds.masserr;

import com.google.common.base.Joiner;
import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.modules.AuthModule;
import net.joinedminds.masserr.oauth.OAuthAuthentication;
import net.joinedminds.masserr.oauth.OAuthType;
import net.joinedminds.masserr.ui.NavItem;
import net.sf.json.JSONObject;
import org.apache.commons.jelly.JellyContext;
import org.bson.types.ObjectId;
import org.jvnet.localizer.Localizable;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.WebApp;
import org.kohsuke.stapler.bind.Bound;

import javax.servlet.ServletException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class Functions {

    public static final String RESOURCE_PATH = "/resources";
    public static final String IMAGES_PATH = RESOURCE_PATH + "/images";
    public static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final Breadcrumb[] MAIN_MENU = {
            new Breadcrumb("/", Messages._nav_Home()),
            new Breadcrumb("/roles", "Roles"),
            new Breadcrumb("/influences", "Influences"),
            new Breadcrumb("/admin", Messages._nav_Admin()),
            new Breadcrumb("/about", "About"),
    };

    public static void initPageVariables(JellyContext context) {
        String rootURL = getRootUrl();

        Functions h = new Functions();
        context.setVariable("h", h);

        // The path starts with a "/" character but does not end with a "/" character.
        context.setVariable("rootURL", rootURL);

        context.setVariable("resURL", rootURL + RESOURCE_PATH);
        context.setVariable("imagesURL", rootURL + IMAGES_PATH);
        context.setVariable("selectedMainMenuItem", getParentMainMenu());

        OAuthAuthentication authentication = AuthModule.getAuthentication();
        if (authentication != null && authentication.isSignedIn()) {
            context.setVariable("authentication", authentication);
        }
    }

    public static String getRootUrl() {
        StaplerRequest request = Stapler.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("Needs to be called from a http request thread.");
        }
        return request.getContextPath();
    }

    public static String toString(Object o) {
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    public static String toNavId(String id) {
        if (id.startsWith("#")) {
            return id.substring(1);
        } else {
            return id;
        }
    }

    public static String emptyIfNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static String fromNavId(String id) {
        return id;
    }

    public static String appendIfNotNull(String text, String suffix, String nullText) {
        return text == null ? nullText : text + suffix;
    }

    /*public static Object ifNull(Object obj, Object thenVal) {
        return obj == null ? thenVal : obj;
    }*/

    public static <T> T ifNull(T obj, T thenVal) {
        return obj == null ? thenVal : obj;
    }

    public static Breadcrumb getParentMainMenu() {
        List<Ancestor> ancestors = Stapler.getCurrentRequest().getAncestors();
        for (int i = ancestors.size() - 1; i >= 0; i--) {
            Ancestor ancestor = ancestors.get(i);
            if (ancestor.getObject() instanceof NavItem) {
                for (Breadcrumb b : MAIN_MENU) {
                    if (((NavItem)ancestor.getObject()).getNavDisplay().equals(b.getDisplay())) {
                        return b;
                    }
                }
            }
        }
        return null;
    }

    public static List<Breadcrumb> getBreadcrumbs() {
        List<Ancestor> ancestors = Stapler.getCurrentRequest().getAncestors();
        List<Breadcrumb> list = new LinkedList<>();
        for (Ancestor ancestor : ancestors) {
            if (ancestor.getObject() instanceof NavItem) {
                NavItem item = (NavItem)ancestor.getObject();
                list.add(new Breadcrumb(ancestor.getUrl(), item.getNavDisplay()));
            }
        }
        return list;
    }

    public static ObjectId toObjectId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        } else if (id.startsWith("new")) {
            return null;
        } else {
            return new ObjectId(id);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isTrimmedEmpty(String str) {
        return str == null || (str.isEmpty() || str.trim().isEmpty());
    }

    public static String ifNullOrEmpty(String str, String thenValue) {
        if (isEmpty(str)) {
            return thenValue;
        } else {
            return str;
        }
    }

    public static String constructPath(String... items) {
        StringBuilder str = new StringBuilder();
        for (String it : items) {
            if (!isEmpty(it)) {
                if (str.length() > 0 && str.charAt(str.length() - 1) != '/') {
                    str.append('/');
                }
                str.append(it.trim());
            }
        }
        return str.toString();
    }

    public static String constructApplicationUrl(String... items) {
        Config config = Masserr.getInstance().getAdmin().getConfig();
        String[] nItems = new String[items.length+1];
        nItems[0] = config.getApplicationUrl();
        System.arraycopy(items, 0, nItems, 1, items.length);
        return constructPath(nItems);
    }

    public static JSONObject getSubmittedForm(StaplerRequest request) throws ServletException {
        JSONObject form = request.getSubmittedForm();
        return massageSubmittedForm(form);
    }

    private static JSONObject massageSubmittedForm(JSONObject form) {
        JSONObject j = new JSONObject();
        for(Object key: form.keySet()) {
            Object val = form.get((String)key);
            if(val instanceof JSONObject) {
                j.put((String)key, massageSubmittedForm((JSONObject)val));
            } else if("staplerClass".equals(key)) {
                j.put("stapler-class", val);
            } else {
                Boolean bVal = toBoolean(val);
                if (bVal != null) {
                    j.put((String)key, bVal);
                } else {
                    j.put((String)key, val);
                }
            }
        }
        return j;
    }

    private static Boolean toBoolean(Object val) {
        if(val == null) {
            return null;
        } else {
            String s = val.toString().toLowerCase();
            if("on".equals(s) || "true".equals(s)) {
                return true;
            } else if("false".equals(s)) {
                return false;
            } else {
                return null;
            }
        }
    }

    public static boolean isSignedIn() {
        OAuthAuthentication authentication = AuthModule.getAuthentication();
        return authentication != null && authentication.isSignedIn();
    }

    public static List<OAuthType> getSignInProviders() {
        List<OAuthType> types = new LinkedList<>();
        for(OAuthType t : OAuthType.values()) {
            if (t.getProvider().isEnabled()) {
                types.add(t);
            }
        }
        return types;
    }

    public static String formatDate(Date dateTime) {
        StaplerRequest request = Stapler.getCurrentRequest();
        DateFormat format;
        if (request != null) {
            format = DateFormat.getDateInstance(DateFormat.MEDIUM, request.getLocale());
        } else {
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        }
        return format.format(dateTime);
    }

    public static int max(int... values) {
        if (values.length <= 1) {
            throw new IllegalArgumentException("values length must be >= 2");
        }
        if (values.length == 2) {
            return Math.max(values[0], values[1]);
        } else {
            int current = values[0];
            for (int i = 1; i < values.length; i++) {
                current = Math.max(current, values[i]);
            }
            return current;
        }
    }

    public static class Breadcrumb {
        private String url;
        private Localizable localizableDisplay;
        private String display;

        public Breadcrumb(String url, Localizable localizableDisplay) {
            this(url, (String)null);
            this.localizableDisplay = localizableDisplay;
        }

        public Breadcrumb(String url, String display) {
            this.url = url;
            if (this.url == null || this.url.isEmpty()) {
                this.url = "/";
            }
            this.display = display;
        }

        public String getUrl() {
            return url;
        }

        public String getDisplay() {
            if (localizableDisplay != null) {
                return localizableDisplay.toString();
            }
            return display;
        }
    }

    /**
     * Replacement for stapler's bind that doesn't require prototype.js to work.
     *
     * @param javaObject the object to bind
     * @param varName    the name of the js variable to bind to
     * @return the html/js code
     */
    public static String bind(Object javaObject, String varName) {
        StringBuilder str = new StringBuilder("");
        String expr;
        if (javaObject == null) {
            expr = "null";
        } else {
            Bound h = WebApp.getCurrent().boundObjectTable.bind(javaObject);
            expr = h.getProxyScript();
        }
        if (varName == null) {
            str.append(expr);
        } else {
            str.append("<script>");
            str.append(varName).append("=").append(expr).append(";");
            str.append("</script>");
        }
        return str.toString();
    }

    public static String isoDate(Date date) {
        if (date == null) {
            return "";
        }
        return ISO_DATE_FORMAT.format(date);
    }
}
