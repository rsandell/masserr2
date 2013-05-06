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

package net.joinedminds.masserr.oauth;

import net.joinedminds.masserr.oauth.providers.FacebookProvider;
import net.joinedminds.masserr.oauth.providers.GoogleProvider;
import net.joinedminds.masserr.oauth.providers.YahooProvider;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public enum OAuthType {
    GOOGLE("google", new GoogleProvider()),
    YAHOO("yahoo", new YahooProvider()),
    FACEBOOK("facebook", new FacebookProvider()),
    //Twitter doesn't expose the user's email so we have nothing to map to.
    ;

    private String name;
    private Provider provider;

    private OAuthType(String name, Provider provider) {
        this.name = name;
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public Provider getProvider() {
        return provider;
    }

    public static OAuthType findByName(String name) {
        for(OAuthType type : OAuthType.values()) {
            if(type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Can't find OAuthType with name: '" + name + "'");
    }
}
