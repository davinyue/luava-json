package org.linuxprobe.luava.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public abstract class StreamUtils {

    /**
     * The default buffer size used why copying bytes.
     */
    public static final int BUFFER_SIZE = 4096;

    private static final byte[] EMPTY_CONTENT = new byte[0];


    /**
     * Copy the contents of the given InputStream into a String.
     * Leaves the stream open when done.
     *
     * @param in the InputStream to copy from (may be {@code null} or empty)
     * @return the String that has been copied to (possibly empty)
     * @throws IOException in case of I/O errors
     */
    public static String copyToString(InputStream in, Charset charset) throws IOException {
        if (in == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(in, charset);
        char[] buffer = new char[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, bytesRead);
        }
        return out.toString();
    }
}