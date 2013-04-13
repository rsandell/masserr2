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

import net.joinedminds.masserr.ui.NavItem;
import org.apache.commons.jelly.JellyContext;
import org.bson.types.ObjectId;
import org.jvnet.localizer.Localizable;
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.WebApp;
import org.kohsuke.stapler.bind.Bound;

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

    public static final Breadcrumb[] MAIN_MENU = {
            new Breadcrumb("/", Messages._nav_Home()),
            new Breadcrumb("/roles", "Roles"),
            new Breadcrumb("/influences", "Influences"),
            new Breadcrumb("/admin", Messages._nav_Admin()),
            new Breadcrumb("/about", "About"),
    };

    public static void initPageVariables(JellyContext context) {
        String rootURL = Stapler.getCurrentRequest().getContextPath();

        Functions h = new Functions();
        context.setVariable("h", h);

        // The path starts with a "/" character but does not end with a "/" character.
        context.setVariable("rootURL", rootURL);

        context.setVariable("resURL", rootURL + RESOURCE_PATH);
        context.setVariable("imagesURL", rootURL + IMAGES_PATH);
        context.setVariable("selectedMainMenuItem", getParentMainMenu());
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
        if(id == null || id.isEmpty()) {
            return null;
        } else if(id.startsWith("new")) {
            return null;
        } else {
            return new ObjectId(id);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
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
}
