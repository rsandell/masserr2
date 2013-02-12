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
import org.kohsuke.stapler.Ancestor;
import org.kohsuke.stapler.Stapler;

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

    public static void initPageVariables(JellyContext context) {
        String rootURL = Stapler.getCurrentRequest().getContextPath();

        Functions h = new Functions();
        context.setVariable("h", h);

        // The path starts with a "/" character but does not end with a "/" character.
        context.setVariable("rootURL", rootURL);

        context.setVariable("resURL", rootURL + RESOURCE_PATH);
        context.setVariable("imagesURL", rootURL + IMAGES_PATH);
    }

    public static String appendIfNotNull(String text, String suffix, String nullText) {
        return text == null ? nullText : text + suffix;
    }

    public static Object ifNull(Object obj, Object thenVal) {
        return obj == null ? thenVal : obj;
    }

    public static List<Breadcrumb> getBreadcrumbs() {
        List<Ancestor> ancestors = Stapler.getCurrentRequest().getAncestors();
        List<Breadcrumb> list = new LinkedList<Breadcrumb>();
        for(Ancestor ancestor : ancestors) {
            if (ancestor.getObject() instanceof NavItem) {
                NavItem item = (NavItem) ancestor.getObject();
                list.add(new Breadcrumb(ancestor.getUrl(), item.getNavDisplay()));
            }
        }
        return list;
    }

    public static class Breadcrumb {
        private String url;
        private String display;

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
            return display;
        }
    }
}
