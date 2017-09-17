package finder.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * CustomRandomAccessFile extends RandomAccessFile and has realisation of readLine() from BufferedRead.
 * Class still has fast access to a direct part of file + optimised readLine().
 * <p>
 * Most part of code from:
 */
public class CustomRandomAccessFile extends RandomAccessFile {

    private int nChars, nextChar;
    private long lastOffset;
    private Long actualFilePointer;
    private static final int BUFFER_SIZE = 8192;
    private static int defaultExpectedLineLength = 80;
    private int bufferSize;
    private char[] charBuffer;
    private boolean skipLF;

    /**
     * Constructor with possibility to set buffer size.
     *
     * @param mode       r/w
     * @throws FileNotFoundException
     */
    public CustomRandomAccessFile(File file, String mode, int bufferSize) throws FileNotFoundException {
        super(file, mode);
        actualFilePointer = null;
        this.bufferSize = bufferSize;
        charBuffer = new char[bufferSize];
    }

    /**
     * @param mode r/w
     * @throws FileNotFoundException
     */
    public CustomRandomAccessFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
        actualFilePointer = null;
        this.bufferSize = BUFFER_SIZE;
        charBuffer = new char[bufferSize];
    }

    /**
     * Returns actual position.
     *
     */
    public Long getActualPos() {
        return actualFilePointer;
    }

    /**
     * see readLineCustom(boolean ignoreLF)
     * Throws:
     * java.io.IOException
     */
    // public synchronized String readLineCustom() throws IOException {
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
     * Reads in a string from this file. The string has been encoded using a modified UTF-8 format.
     * The first two bytes are read, starting from the current file pointer, as if by readUnsignedShort. This value gives the number of following bytes that are in the encoded string, not the length of the resulting string. The following bytes are then interpreted as bytes encoding characters in the modified UTF-8 format and are converted into characters.
     * This method blocks until all the bytes are read, the end of the stream is detected, or an exception is thrown.
     * Returns:
     * a Unicode string.
     * Throws:
     * EOFException if this file reaches the end before reading all the bytes.
     * java.io.IOException if an I/O error occurs.
     * UTFDataFormatException if the bytes do not represent valid modified UTF-8 encoding of a Unicode string.
     * See also:
     * java.io.RandomAccessFile.readUnsignedShort()
     * <p>
     * http://grepcode.com/file/repo1.maven.org/maven2/org.bitbucket.kienerj/io/1.0.0/org/bitbucket/kienerj/io/OptimizedRandomAccessFile.java
     *
     * @throws IOException
     */
    public final String readLine(boolean ignoreLF) throws IOException {
        StringBuilder s = null;
        int startChar;
        int separatorIndex = 0;

        boolean omitLF = ignoreLF || skipLF;

        bufferLoop:
        for (; ; ) {

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

            charLoop:
            for (i = nextChar; i < nChars; i++) {
                c = charBuffer[i];
                if ((c == '\n') || (c == '\r')) {
                    eol = true;
                    break charLoop;
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
                s = new StringBuilder(defaultExpectedLineLength);
            }
            s.append(charBuffer, startChar, i - startChar);
        }
    }

}
