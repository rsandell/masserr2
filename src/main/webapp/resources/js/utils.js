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

_.templateSettings = {
    interpolate : /\{\{(.+?)\}\}/g
};

String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

function toNavId(id) {
    if (id.indexOf("#") == 0) {
        return id.substring(1);
    }
    return id;
}

function fromNavId(id) {
    if (id.indexOf("#") != 0) {
        id = "#" + id;
    }
    return id;
}

function findById(needleId, haystack) {
    var id = fromNavId(needleId);
    for (var i = 0; i < haystack.length; i++) {
        if (haystack[i].id == id) {
            return haystack[i];
        }
    }
    return null;
}

function replaceByObjectId(newNeedle, haystack) {
    var id = fromNavId(newNeedle.id);
    for (var i = 0; i < haystack.length; i++) {
        if (haystack[i].id == id) {
            haystack[i] = newNeedle;
            return true;
        }
    }
    return false;
}

