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

package net.joinedminds.masserr;

import net.joinedminds.masserr.model.Config;
import net.joinedminds.masserr.modules.AdminModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Tests {@link Functions}.
 *
 * @author Robert Sandell &lt;sandell.robert@gmail.com&gt;
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Masserr.class)
public class FunctionsTest {
    @Test
    public void testToString() throws Exception {
        assertEquals("Hello", Functions.toString("Hello"));
    }

    @Test
    public void testToStringNull() throws Exception {
        assertNull(Functions.toString(null));
    }

    @Test
    public void testEmptyIfNull() throws Exception {
        assertEquals("", Functions.emptyIfNull(null));
    }

    @Test
    public void testEmptyIfNullNot() throws Exception {
        assertEquals("Hello", Functions.emptyIfNull("Hello"));
    }

    @Test
    public void testAppendIfNotNull() throws Exception {
        assertEquals("Hello World", Functions.appendIfNotNull("Hello", " World", ""));
    }

    @Test
    public void testAppendIfNotNullNot() throws Exception {
        assertEquals("Hello World", Functions.appendIfNotNull(null, " World", "Hello World"));
    }

    @Test
    public void testIfNull() throws Exception {
        assertEquals("Hello", Functions.ifNull("Hello", "World"));
    }

    @Test
    public void testIfNullNull() throws Exception {
        assertEquals("Hello", Functions.ifNull(null, "Hello"));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(Functions.isEmpty(""));
    }

    @Test
    public void testIsEmptyNull() throws Exception {
        assertTrue(Functions.isEmpty(null));
    }

    @Test
    public void testIsEmptyNot() throws Exception {
        assertFalse(Functions.isEmpty("Hello"));
    }

    @Test
    public void testIsTrimmedEmpty() throws Exception {
        assertTrue(Functions.isTrimmedEmpty(""));
    }

    @Test
    public void testIsTrimmedEmptyWhiteSpaces() throws Exception {
        assertTrue(Functions.isTrimmedEmpty("       "));
    }

    @Test
    public void testIsTrimmedEmptyNull() throws Exception {
        assertTrue(Functions.isTrimmedEmpty(null));
    }

    @Test
    public void testIsTrimmedEmptyNot() throws Exception {
        assertFalse(Functions.isTrimmedEmpty("Hello"));
    }


    @Test
    public void testIfNullOrEmpty() throws Exception {
        assertEquals("Hello", Functions.ifNullOrEmpty("Hello", "World"));
    }

    @Test
    public void testIfNullOrEmptyNull() throws Exception {
        assertEquals("World", Functions.ifNullOrEmpty(null, "World"));
    }

    @Test
    public void testIfNullOrEmptyEmpty() throws Exception {
        assertEquals("World", Functions.ifNullOrEmpty("", "World"));
    }

    @Test
    public void testConstructPath() throws Exception {
        assertEquals("some/darn/path", Functions.constructPath("some", "darn", "path"));
    }

    @Test
    public void testConstructPathWithNull() throws Exception {
        assertEquals("some/darn/path", Functions.constructPath("some", "darn", null, "path"));
    }

    @Test
    public void testConstructPathWithEmpty() throws Exception {
        assertEquals("some/darn/path", Functions.constructPath("some", "darn", "", "path"));
    }

    @Test
    public void testConstructPathRoot() throws Exception {
        assertEquals("/some/darn/path", Functions.constructPath("/", "some", "darn", "path"));
    }

    @Test
    public void testConstructApplicationUrl() throws Exception {
        Masserr instance = mock(Masserr.class);
        mockStatic(Masserr.class);
        when(Masserr.getInstance()).thenReturn(instance);
        AdminModule admin = mock(AdminModule.class);
        Config config = mock(Config.class);
        when(config.getApplicationUrl()).thenReturn("http://somehost:8080/masserr");
        when(admin.getConfig()).thenReturn(config);
        when(instance.getAdmin()).thenReturn(admin);


        assertEquals("http://somehost:8080/masserr/some/darn/path",
                Functions.constructApplicationUrl("some", "darn", "path"));
    }

    @Test
    public void testIsoDate() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, 6);
        c.set(Calendar.DAY_OF_MONTH, 8);
        assertEquals("1981-07-08", Functions.isoDate(c.getTime()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxJustOne() {
        Functions.max(1);
    }

    @Test
    public void testMaxOfTwo() {
        assertEquals(2, Functions.max(1, 2));
    }

    @Test
    public void testMaxOfThree() {
        assertEquals(3, Functions.max(1, 2, 3));
    }

    @Test
    public void testMaxOfFive() {
        assertEquals(5, Functions.max(1, 2, 3, 4, 5));
    }

    @Test
    public void testMaxOfFiveMiddle() {
        assertEquals(5, Functions.max(1, 2, 5, -4, -5));
    }

    @Test
    public void testMaxOfFiveFirst() {
        assertEquals(10, Functions.max(10, 2, 5, -4, -5));
    }
}
