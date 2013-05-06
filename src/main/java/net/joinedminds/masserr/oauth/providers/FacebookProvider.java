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

package net.joinedminds.masserr.oauth.providers;

import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.oauth.OAuthAuthentication;
import net.joinedminds.masserr.oauth.OAuthProviderException;
import net.joinedminds.masserr.oauth.Provider;
import net.sf.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.joinedminds.masserr.Functions.isEmpty;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class FacebookProvider extends Provider {

    private static final Logger logger = LoggerFactory.getLogger(FacebookProvider.class);

    @Override
    protected String getCallbackUrl() {
        return Functions.constructApplicationUrl("auth", "callback2");
    }

    @Override
    public void readUserData(OAuthAuthentication user) throws OAuthProviderException {
        OAuthService service = buildService();
        Verifier verifier = new Verifier(user.getOAuthVerifier());
        Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
        Token accessToken = service.getAccessToken(token, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me?fields=id,name,email,picture.type(small)");
        service.signRequest(accessToken, request);


        Response response = request.send();

        checkResponseCode(response.getCode());

        String json = response.getBody();
        logger.debug("Response: {}", json);

        JSONObject jRes = null;
        try {
            jRes = JSONObject.fromObject(json);
        } catch (Exception e) {
            logger.error("Cannot parse to JSON: " + json, e);
            throw new OAuthProviderException("Unable to parse JSON: " + json, e);
        }

        if(jRes.has("id") && !isEmpty(jRes.optString("id"))) {
            user.setProviderUserId(jRes.getString("id"));
        } else {
            throw new OAuthProviderException("Could not get id from response");
        }

        if (jRes.has("name")&& !isEmpty(jRes.optString("name"))) {
            user.setName(jRes.getString("name"));
        } else {
            throw new OAuthProviderException("Could not get name from response");
        }

        if (jRes.has("email") && !isEmpty(jRes.optString("email"))) {
                user.setEmail(jRes.getString("email"));
        } else {
            throw new OAuthProviderException("Could not get email from response");
        }

        if (jRes.has("picture") && jRes.getJSONObject("picture").has("data")) {
            user.setPicture(jRes.getJSONObject("picture").getJSONObject("data").optString("url"));
        }

        user.signedIn();
    }

    @Override
    protected OAuthService buildService() {
        Config.OAuthKeysConfig keys = getKeys();
        return new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(keys.getApiKey())
                .apiSecret(keys.getApiSecret())
                .scope("email")
                .callback(getCallbackUrl())
                .build();
    }

    @Override
    protected Config.OAuthKeysConfig getKeys() {
        Config.OAuthKeysConfig keys = getConfig().getFacebookKeys();
        if (keys == null) {
            throw new IllegalStateException("Facebook is not a configured provider.");
        }
        return keys;
    }

    @Override
    public boolean isEnabled() {
        Config.OAuthKeysConfig keys = getConfig().getFacebookKeys();
        return keys != null && keys.isEnabled();
    }
}
