package finder.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * OptimizedRandomAccessFile extends RandomAccessFile and has realisation of readLine() from BufferedRead.
 * Class still has fast access to a direct part of file + optimised readLine().
 * <p>
 * Most part of code from: https://bitbucket.org/kienerj/io
 */
public class OptimizedRandomAccessFile extends RandomAccessFile {
    private static final int BUFFER_SIZE = 8192;
    private static final int DEFAULT_EXPECTED_LINE_LENGTH = 80;

    private int nChars, nextChar;
    private long lastOffset;
    private Long actualFilePointer;
    private int bufferSize;
    private char[] charBuffer;
    private boolean skipLF;

    /**
     * Constructor with possibility to set buffer size.
     *
     * @param mode r/w
     * @throws FileNotFoundException
     */
    public OptimizedRandomAccessFile(File file, String mode, int bufferSize) throws FileNotFoundException {
        super(file, mode);
        actualFilePointer = null;
        this.bufferSize = bufferSize;
        charBuffer = new char[bufferSize];
    }

    /**
     * @param mode r/w
     * @throws FileNotFoundException
     */
    public OptimizedRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
        actualFilePointer = null;
        this.bufferSize = BUFFER_SIZE;
        charBuffer = new char[bufferSize];
    }



    /**
     * Returns actual position.
     */
    public Long getActualPos() {
        return actualFilePointer;
    }

    /**
     * see readLineCustom(boolean ignoreLF)
     * Throws:
     * java.io.IOException
     */
    public String readLineCustom() throws IOException {
        return readLine(false);
    }

    private void fill() throws IOException {
        lastOffset = this.getFilePointer();
        actualFilePointer = lastOffset;
        byte[] buffer = new byte[bufferSize];
        int n = this.read(buffer);
        if (n > 0) {
            nChars = n;
            nextChar = 0;
        }
        for (int i = 0; i < buffer.length; i++) {
            charBuffer[i] = (char) buffer[i];
        }
    }

    /**
     * Read the file line by line omitting the line separator. see
     * {@link java.io.RandomAccessFile#readLine() readLine()} and see
     * {@link java.io.BufferedReader#readLine(boolean) readLine(boolean ignoreLF)}.
     * Subsequent calls of this method are buffered. If certain other
     * methods that are affected by the current position of the reader in the
     * file is called after this method, the position is set to the start of the
     * next line and the buffer is invalidated. <
     *
     * This method is copied from
     * {@link java.io.BufferedReader BufferedReader} with minor changes like
     * tracking position (offset) were next line starts.
     *
     * @return the next line of text from this file, or null if end of file is
     * encountered before even one byte is read.
     * @throws IOException if an I/O error occurs.
     */
    public final String readLine(boolean ignoreLF) throws IOException {
        StringBuilder s = null;
        int startChar;
        int separatorIndex = 0;

        boolean omitLF = ignoreLF || skipLF;

        for (;;) {

            if (nextChar >= nChars) {
                fill();
            }
            if (nextChar >= nChars) { /* EOF */
                if (s != null && s.length() > 0) {
                    //EOF -> hence no need to adjust position in file
                    // changed by fill()
                    return s.toString();
                } else {
                    return null;
                }
            }
            boolean eol = false;
            char c = 0;
            int i;

            /* Skip a leftover '\n', if necessary */
            if (omitLF && (charBuffer[nextChar] == '\n')) {
                nextChar++;
            }
            skipLF = false;
            omitLF = false;

            for (i = nextChar; i < nChars; i++) {
                c = charBuffer[i];
                if ((c == '\n') || (c == '\r')) {
                    eol = true;
                    break;
                }
            }

            startChar = nextChar;
            nextChar = i;

            if (eol) {
                String str;
                if (s == null) {
                    str = new String(charBuffer, startChar, i - startChar);
                } else {
                    s.append(charBuffer, startChar, i - startChar);
                    str = s.toString();
                }
                nextChar++;
                if (c == '\r') {
                    skipLF = true;
                    if (nextChar >= nChars) {
                        fill();
                    }
                    if (charBuffer[nextChar] == '\n') {
                        separatorIndex = 1;
                    }
                }
                actualFilePointer = lastOffset + nextChar + separatorIndex;
                return str;
            }

            if (s == null) {
                s = new StringBuilder(DEFAULT_EXPECTED_LINE_LENGTH);
            }
            s.append(charBuffer, startChar, i - startChar);
        }
    }

}
