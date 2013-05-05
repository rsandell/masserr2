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

package net.joinedminds.masserr.modules;

import com.google.inject.Singleton;
import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Messages;
import net.joinedminds.masserr.oauth.OAuthAuthentication;
import net.joinedminds.masserr.oauth.OAuthProviderException;
import net.joinedminds.masserr.oauth.OAuthType;
import net.joinedminds.masserr.ui.NavItem;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@Singleton
public class AuthModule implements NavItem {

    private static final Logger logger = LoggerFactory.getLogger(AuthModule.class);
    public static final String SESSION_USER_AUTH_KEY = "masserr.OAuthAuthentication";

    @Override
    public String getNavDisplay() {
        return Messages.nav_Auth();
    }

    /**
     * Initiate the OAuth login process.
     *
     * @param provider the OAuth provider
     * @param request  the request
     * @param response the response
     * @throws IOException if so
     */
    public void doLogin(@QueryParameter String provider, StaplerRequest request, StaplerResponse response) throws IOException {
        logger.trace("Enter login. Provider: {}", provider);

        OAuthType type = OAuthType.findByName(provider);
        logger.debug("Provider type: {}", type);
        if (type == null) {
            throw new IllegalArgumentException("Unknown provider " + provider);
        }

        OAuthAuthentication user = new OAuthAuthentication();
        user.setOAuthType(type);

        type.getProvider().buildRedirectUrl(user);

        setAuthentication(request, user);

        logger.trace("Redirecting to {}", user.getOAuthAuthorizationUrl());
        response.sendRedirect(user.getOAuthAuthorizationUrl());
    }

    /**
     * Callback from the OAuth provider after login is done.
     *
     * @param request the request
     * @param response the response
     */
    public void doCallback(@QueryParameter(value = "oauth_verifier", required = true) String oAuthVerifier,
                           @QueryParameter(value = "oauth_token", required = true) String oAuthToken,
                           StaplerRequest request, StaplerResponse response) throws OAuthProviderException, IOException {
        logger.trace("Entering callback");
        logger.debug("Query string: {}", request.getQueryString());

        OAuthAuthentication user = getAuthentication();
        if (user == null) {
            throw new IllegalStateException("Callback before login was initiated!");
        }
        logger.debug("oAuthVerifier: {}", oAuthVerifier);
        user.setOAuthVerifier(oAuthVerifier);

        logger.debug("oAuthToken: {}", oAuthToken);

        user.getOAuthType().getProvider().readUserData(user);

        logger.debug("User: providerUserId: {}", user.getProviderUserId());
        logger.debug("User: nickname: {}", user.getNickname());
        logger.debug("User: name: {}", user.getName());
        logger.debug("User: email: {}", user.getEmail());
        
        setAuthentication(request, user);

        //TODO map user from DB and store in session.

        response.sendRedirect(Functions.ifNullOrEmpty(Functions.getRootUrl(), "/"));
    }
    
    private void setAuthentication(StaplerRequest request, OAuthAuthentication user) {
        request.getSession(true).setAttribute(SESSION_USER_AUTH_KEY, user);
    }

    public static OAuthAuthentication getAuthentication() {
        StaplerRequest request = Stapler.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("Must be called from a http request thread.");
        }
        return (OAuthAuthentication)request.getSession(true).getAttribute(SESSION_USER_AUTH_KEY);
    }
}
