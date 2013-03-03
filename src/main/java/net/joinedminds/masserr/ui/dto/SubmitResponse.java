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

package net.joinedminds.masserr.ui.dto;

import java.io.Serializable;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class SubmitResponse<T> implements Serializable {
    private T data;
    private boolean ok;
    private String message;

    public SubmitResponse(T data, boolean ok, String message) {
        this.data = data;
        this.ok = ok;
        this.message = message;
    }

    /**
     * An OK Response with data.
     * @param data the data
     */
    public SubmitResponse(T data) {
        this.ok = true;
        this.data = data;
        this.message = null;
    }

    /**
     * A NOK Response with data and message.
     * @param data the data
     * @param message the error message
     */
    public SubmitResponse(T data, String message) {
        this.ok = false;
        this.data = data;
        this.message = message;
    }

    /**
     * A NOK Response with message.
     *
     * @param message the error message
     */
    public SubmitResponse(String message) {
        this.ok = false;
        this.data = null;
        this.message = message;
    }

    public SubmitResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
