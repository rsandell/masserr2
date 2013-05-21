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

package net.joinedminds.masserr.intercept;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Flavor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Replacing org.kohsuke.stapler.HttpResponseRenderer.Default with a version that checks for a specified
 * {@link JsonConfig} for the output of JavaScript proxy calls.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 * @see JsonFiltered
 */
public class HttpResponseRenderer extends org.kohsuke.stapler.HttpResponseRenderer.Default {

    public static final ThreadLocal<JsonConfig> CONFIG = new ThreadLocal<>();

    @Override
    protected boolean handleJavaScriptProxyMethodCall(StaplerRequest req, StaplerResponse rsp, Object response) throws IOException {
        if (req.isJavaScriptProxyCall()) {
            rsp.setContentType(Flavor.JSON.contentType);
            PrintWriter w = rsp.getWriter();

            // handle other primitive types as JSON response
            if (response instanceof String) {
                w.print(JSONUtils.quote((String)response));
            } else if (response instanceof Number || response instanceof Boolean) {
                w.print(response);
            } else if (response instanceof Collection || (response != null && response.getClass().isArray())) {
                JSONArray.fromObject(response, getJsonConfig()).write(w);
            } else {
                // last fall back
                JSONObject.fromObject(response, getJsonConfig()).write(w);
            }
            return true;
        }
        return false;
    }

    protected JsonConfig getJsonConfig() {
        JsonConfig config = CONFIG.get();
        if (config == null) {
            return new JsonConfig();
        } else {
            CONFIG.remove();
            return config;
        }
    }
}
