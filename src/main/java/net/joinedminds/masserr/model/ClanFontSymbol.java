/*
 * The MIT License
 *
 * Copyright (c) 2004-2012-, Robert Sandell-sandell.robert@gmail.com. All rights reserved.
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

package net.joinedminds.masserr.model;

import java.io.Serializable;

/**
 * Description.
 * <p/>
 * Created: 2004-mar-27 01:32:29
 * 
 * @author <a href="mailto:sandell.robert@gmail.com>Robert Sandell</a>"
 * @deprecated Don't think we'll use the fonts anymore
 */
public class ClanFontSymbol {
    private String clanId;
    private String fontName;
    private char character;

    public ClanFontSymbol(String pClanId, String pFontName, char pCharacter) {
        clanId = pClanId;
        fontName = pFontName;
        character = pCharacter;
    }

    /**
     * For serialization
     */
    public ClanFontSymbol() {
    }

    public String getClanId() {
        return clanId;
    }

    public void setClanId(String pClanId) {
        clanId = pClanId;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String pFontName) {
        fontName = pFontName;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char pCharacter) {
        character = pCharacter;
    }

    public String toString() {
        return "<font face=\"" + fontName + "\">" + character + "</font>";
    }
}
