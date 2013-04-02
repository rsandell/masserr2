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

package net.joinedminds.masserr.dataimport;

import net.joinedminds.masserr.model.NamedIdentifiable;
import org.kohsuke.stapler.StaplerResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Generates wiki text from lists.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public abstract class Wiki {

    protected void writeResponse(String text, StaplerResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setContentLength(text.length());
        PrintWriter writer = response.getWriter();
        writer.write(text);
        writer.flush();
    }

    protected <T extends NamedIdentifiable> String generateWikiText(String heading, int headingLevel,
                                                                    List<T> items, PostText<T> postGen) {
        StringBuilder str = new StringBuilder();
        if (heading != null) {
            String h = generateHeadingPrefix(headingLevel);
            str.append(h).append(heading).append(h);
            str.append("\n");
        }
        String h = generateHeadingPrefix(headingLevel + 1);
        for (T t : items) {
            str.append(h).append(t.getName()).append(h).append("\n");
            if (postGen != null) {
                str.append(postGen.txt(t, this));
            }
            str.append("\n\n");
        }

        return str.toString();
    }

    public String bold(String txt) {
        return "'''" + txt + "'''";
    }

    public String italic(String txt) {
        return "''" + txt + "''";
    }

    public String internalLink(String topic) {
        return "[[" + topic + "]]";
    }

    public String externalLink(String link, String text) {
        return "[" + link + " " + text + "]";
    }

    public String externalLink(String link) {
        return externalLink(link, link);
    }

    private String generateHeadingPrefix(int headingLevel) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < headingLevel; i++) {
            str.append("=");
        }
        return str.toString();
    }

    protected static interface PostText<T extends NamedIdentifiable> {
        String txt(T item, Wiki helper);
    }
}
