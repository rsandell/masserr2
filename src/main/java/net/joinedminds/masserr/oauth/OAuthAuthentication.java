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

import java.io.Serializable;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class OAuthAuthentication implements Serializable {
    // parameter
    private String providerUserId;
    private String name;
    private String nickname;
    private String email;

    // type
    private OAuthType oAuthType;

    // communication parameters
    private String oAuthToken;
    private String oAuthSecret;
    private String oAuthRawResponse;
    private String oAuthVerifier;

    // result
    private String oAuthAuthorizationUrl;

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public OAuthType getOAuthType() {
        return oAuthType;
    }

    public void setOAuthType(OAuthType oAuthType) {
        this.oAuthType = oAuthType;
    }

    public String getOAuthToken() {
        return oAuthToken;
    }

    public void setOAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    public String getOAuthSecret() {
        return oAuthSecret;
    }

    public void setOAuthSecret(String oAuthSecret) {
        this.oAuthSecret = oAuthSecret;
    }

    public String getOAuthRawResponse() {
        return oAuthRawResponse;
    }

    public void setOAuthRawResponse(String oAuthRawResponse) {
        this.oAuthRawResponse = oAuthRawResponse;
    }

    public String getOAuthVerifier() {
        return oAuthVerifier;
    }

    public void setOAuthVerifier(String oAuthVerifier) {
        this.oAuthVerifier = oAuthVerifier;
    }

    public String getOAuthAuthorizationUrl() {
        return oAuthAuthorizationUrl;
    }

    public void setOAuthAuthorizationUrl(String oAuthAuthorizationUrl) {
        this.oAuthAuthorizationUrl = oAuthAuthorizationUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
