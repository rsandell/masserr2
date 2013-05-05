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

import net.joinedminds.masserr.Functions;
import net.joinedminds.masserr.Masserr;
import net.joinedminds.masserr.model.Config;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;

/**
 * Description
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
public abstract class Provider {

    private DocumentBuilder docBuilder;
    private XPath xpathFactory;

    public abstract void readUserData(OAuthAuthentication user) throws OAuthProviderException;

    protected abstract OAuthService buildService();

    public abstract boolean isEnabled();

    protected String getCallbackUrl() {
        return Functions.constructApplicationUrl("auth", "callback");
        //return "http://localhost:8080/callbackoauth";
    }

    protected Config getConfig() {
        return Masserr.getInstance().getAdmin().getConfig();
    }

    public void buildRedirectUrl(OAuthAuthentication user) {
        OAuthService service = buildService();
        Token token = service.getRequestToken();

        String redirect = service.getAuthorizationUrl(token);
        user.setOAuthAuthorizationUrl(redirect);
        user.setOAuthToken(token.getToken());
        user.setOAuthSecret(token.getSecret());
        user.setOAuthRawResponse(token.getRawResponse());
    }

    protected void checkResponseCode(int code) throws OAuthProviderException {
        if (code != HttpURLConnection.HTTP_OK) {
            throw new OAuthProviderException("Provider communication error! HTTP code: '" + code + "'");
        }
    }

    private synchronized DocumentBuilder getDocBuilder() throws ParserConfigurationException {
        if (docBuilder == null) {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        return docBuilder;
    }

    protected Document parseDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        StringReader str = new StringReader(xml);
        InputSource source = new InputSource(str);
        Document doc = getDocBuilder().parse(source);

        // Normalize text representation.
        // Collapses adjacent text nodes into one node.
        doc.getDocumentElement().normalize();
        return doc;
    }

    protected synchronized XPath getXPath() {
        if (xpathFactory == null) {
            xpathFactory = XPathFactory.newInstance().newXPath();
        }
        return xpathFactory;
    }

    protected String evaluateXPath(Document doc, String xPathExpression) throws XPathExpressionException {
        XPathExpression expr = getXPath().compile(xPathExpression);
        return (String) expr.evaluate(doc, XPathConstants.STRING);
    }
}
