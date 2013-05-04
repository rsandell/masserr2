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

import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.oauth.OAuthAuthentication;
import net.joinedminds.masserr.oauth.OAuthProviderException;
import net.joinedminds.masserr.oauth.Provider;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public class GoogleProvider extends Provider {

    private static final Logger logger = LoggerFactory.getLogger(GoogleProvider.class);

    @Override
    public void readUserData(OAuthAuthentication user) throws OAuthProviderException {
        OAuthService service = buildService();
        Verifier verifier = new Verifier(user.getOAuthVerifier());
        Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
        Token accessToken = service.getAccessToken(token, verifier);


        OAuthRequest request = new OAuthRequest(Verb.GET, "http://www.google.com/m8/feeds/contacts/default/full"); //TODO Really?!
        service.signRequest(accessToken, request);
        request.addHeader("GData-Version", "3.0");

        Response response = request.send();

        checkResponseCode(response.getCode());

        String xml = response.getBody();
        logger.debug("XML response: {}", xml);

        Document doc = null;
        try {
            doc = parseDocument(xml);
        } catch (Exception e) {
            logger.error("Unable to parse XML: " + xml, e);
            throw new OAuthProviderException("Unable to parse XML: " + xml, e);
        }
        //TODO user Id
        String xPathExpressionName = "/feed/author/name/text()";
        try {
            String authorName = evaluateXPath(doc, xPathExpressionName);
            user.setName(authorName);
        } catch (Exception e) {
            logger.error("Can't find expression: " + xPathExpressionName + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionName, e);
        }

        String xPathExpressionEMail = "/feed/author/email/text()";
        try {
            String authorEMail = evaluateXPath(doc, xPathExpressionEMail);
            user.setEmail(authorEMail);
        } catch (Exception e) {
            logger.error("Can't find expression: " + xPathExpressionEMail + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionEMail, e);
        }
    }

    @Override
    protected OAuthService buildService() {
        Config.OAuthKeysConfig keys = getConfig().getGoogleKeys();
        return new ServiceBuilder()
                .provider(GoogleApi.class)
                .apiKey(keys.getApiKey())
                .apiSecret(keys.getApiSecret())
                .scope("http://www.google.com/m8/feeds/")
                .callback(getCallbackUrl())
                .build();
    }
}
