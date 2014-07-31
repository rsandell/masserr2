/*
 * The MIT License
 *
 * Copyright (c) 2014-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import groovy.json.JsonOutput;
import net.joinedminds.masserr.api.serialization.ObjectIdTypeAdapter;
import net.joinedminds.masserr.model.Identifiable;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.kohsuke.stapler.StaplerFallback;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Flavor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class ApiResponse<E> implements StaplerFallback {

    protected E data;

    protected ApiResponse(E data) {
        this.data = data;
    }

    protected ApiResponse() {
    }

    public void doIndex(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        switch (req.getMethod().toUpperCase()) {
            case "POST":
                create(req, rsp);
                break;
            case "PUT":
                update(req, rsp);
                break;
            case "GET":
            default:
                read(req, rsp);
        }
    }

    protected void update(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        sendNotImplemented(rsp);
    }

    protected void create(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        sendNotImplemented(rsp);
    }

    protected void read(StaplerRequest req, StaplerResponse rsp) throws ServletException, IOException {
        Gson gson = getGsonBuilder().create();
        rsp.setContentType(Flavor.JSON.contentType);
        gson.toJson(data, rsp.getWriter());
    }

    @Override
    public Object getStaplerFallback() {
        return data;
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter())
                .serializeNulls();
    }

    protected void sendNotImplemented(StaplerResponse rsp) throws IOException {
        rsp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Not Implemented");
    }

    protected E readGson(StaplerRequest req) throws IOException {
        if (!isJsonContent(req)) {
            throw new IllegalArgumentException("Expected content type is JSON");
        }
        Gson gson = getGsonBuilder().create();
        return (E)gson.fromJson(req.getReader(), data.getClass());
    }

    protected JSONObject readJson(StaplerRequest req) throws IOException {
        if (!isJsonContent(req)) {
            throw new IllegalArgumentException("Expected content type is JSON");
        }
        return JSONObject.fromObject(req.getReader());
    }

    protected boolean isJsonContent(StaplerRequest req) {
        String c = req.getContentType().toLowerCase();
        return c.contains("json"); //Ugly but simple
    }
}
