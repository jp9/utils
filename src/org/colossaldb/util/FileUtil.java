package org.colossaldb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
    [@Licence@]
*/

/**
 * Created with IntelliJ IDEA.
 * User: Jayaprakash Pasala
 * Date: 12/14/12
 * Time: 2:26 PM
 *
*/
public class FileUtil {
    // 1Meg as default buffer size. We don't hold on to this for too long, hence leaving it as 1 MEG
    private static final int CHAR_BUFFER_SIZE = 1048576;


    /**
     * Given a Reader, simply return the contents as a list of strings. The new lines are stripped out of the returned value.
     * The new line is treated as the demarcation between lines.
     *
     * @param reader - Reader stream to read the contents from
     * @return - list of the stream contents as a list of strings.
     * @throws IOException - On failing to read from the stream.
     */
    public static List<String> getFileAsStrings(Reader reader) throws IOException {
        BufferedReader bufferedReader= new BufferedReader(reader);

        List<String> all = new ArrayList<String>();
        String str;
        while((str = bufferedReader.readLine())!= null) {
            all.add(str);
        }
        return all;
    }

    /**
     * Given a Reader, simply return the contents as a simple string.
     * @param reader - Reader stream to read the contents from
     * @return - File content is returned as a string.
     * @throws IOException - On failing to read from the stream.
     */
    public static String getFileAsString(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        char[] buffer = new char[CHAR_BUFFER_SIZE];

        StringBuilder stringBuilder = new StringBuilder();
        int size;
        while((size = bufferedReader.read(buffer))>0) {
            stringBuilder.append(buffer,0,size);
        }
        return stringBuilder.toString();
    }


}
