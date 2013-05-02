package org.colossaldb.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;

/**
 * Copyright (C) 2013  Jayaprakash Pasala
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p/>
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 4/29/13
 * Time: 9:15 PM
 */
public class FileUtilTest {
    // New line string - system dependent
    private static final String NEW_LINE = System.getProperty("line.separator");

    /**
     * Test
     *
     * @throws java.io.IOException
     */
    @Test
    public void singleLineTest() throws IOException {

        // Single line
        String simpleString = "This is a simple line";
        Assert.assertEquals(simpleString, FileUtil.getFileAsString(new StringReader(simpleString)));
        Assert.assertEquals(Arrays.asList(simpleString),
                FileUtil.getFileAsStrings(new StringReader(simpleString)));
    }

    @Test
    public void multiLineTest() throws IOException {
        // Multi line string parsing
        String multiLineStr = "This is line one" + NEW_LINE + " line two " + NEW_LINE + " line three ";
        Assert.assertEquals(multiLineStr, FileUtil.getFileAsString(new StringReader(multiLineStr)));
        Assert.assertEquals(Arrays.asList("This is line one", " line two ", " line three "),
                FileUtil.getFileAsStrings(new StringReader(multiLineStr)));

    }

    @Test
    public void emptyLineTest() throws IOException {
        // Empty line test
        String emptyStr = "";
        Assert.assertEquals(emptyStr, FileUtil.getFileAsString(new StringReader(emptyStr)));
        Assert.assertEquals(Collections.emptyList(),
                FileUtil.getFileAsStrings(new StringReader(emptyStr)));
    }

    @Test
    public void newLineStringTest() throws IOException {
        // New line only test
        String newLineStr = "" + NEW_LINE;
        Assert.assertEquals(newLineStr, FileUtil.getFileAsString(new StringReader(newLineStr)));
        Assert.assertEquals(Arrays.asList(""), // Remember that this method will not retain the new lines.
                FileUtil.getFileAsStrings(new StringReader(newLineStr)));
    }
}
