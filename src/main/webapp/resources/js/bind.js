/*
 * Copyright (c) 2004-2010, Kohsuke Kawaguchi
 * Copyright (c) 2013, Robert Sandell.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of
 *     conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of
 *     conditions and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Stapler proxy to replace the existing one in stapler that uses underscore and jQuery instead of
 * prototype.js to work.
 *
 * @param url
 * @param crumb
 * @param methods
 * @return {{}}
 */
function makeStaplerProxy(url,crumb,methods) {
    if (!url.endsWith('/')) url+='/';
    var proxy = {};

    _.each(methods, function(methodName) {
        proxy[methodName] = function() {
            var args = arguments;

            // the final argument can be a callback that receives the return value
            var callback = (function(){
                if (args.length==0) return null;
                var tail = args[args.length-1];
                return (typeof(tail)=='function') ? tail : null;
            })();

            // 'arguments' is not an array so we convert it into an array
            var a = [];
            for (var i=0; i<args.length-(callback!=null?1:0); i++)
                a.push(args[i]);

            $.ajax({
                type: "POST",
                url: url+methodName,
                data: JSON.stringify(a),
                contentType: 'application/x-stapler-method-invocation;charset=UTF-8',
                headers: {'Crumb':crumb},
                dataType: "json",
                success: function(data, textStatus, jqXHR) {
                    if (callback!=null) {
                        var t = {};
                        t.responseObject = function() {
                            return data;
                        }
                        callback(t);
                    }
                }
            });
        }
    });

    return proxy;
}

