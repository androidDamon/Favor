package com.zhenglee.framework.net.http.mime;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

/**
 * The multipart form is used to post multipart data to server
 *
 * @author johnsonlee
 * @see <a href="https://www.ietf.org/rfc/rfc2388.txt">https://www.ietf.org/rfc/rfc2388.txt</a>
 */
public class MultipartForm {

    private static final String DASHES = "--";

    private static final String CR_LF = "\r\n";

    private static final char[] MULTIPART_CHARS ="-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String generateBoundary() {
        final StringBuilder buffer = new StringBuilder();
        final Random rand = new Random();
        final int count = rand.nextInt(11) + 30; // a random size from 30 to 40

        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }

        return buffer.toString();
    }

    public static byte[] encode(final Charset charset, final String string) {
        final ByteBuffer buffer = charset.encode(CharBuffer.wrap(string));
        final byte[] data = new byte[buffer.remaining()];
        System.arraycopy(buffer.array(), 0, data, 0, data.length);
        return data;
    }

    public static void writeBytes(final byte[] b, final OutputStream out) throws IOException {
        out.write(b, 0, b.length);
    }

    public static void writeBytes(final String s, final OutputStream out) throws IOException {
        out.write(encode(Constants.CHARSET_ASCII, s));
    }

    public static void writeBytes(final String s, final Charset charset, final OutputStream out) throws IOException {
        out.write(encode(charset, s));
    }

    public static void writeHeader(final MultipartHeader header, final Charset charset, final OutputStream out) throws IOException {
        writeBytes(header.getName(), charset, out);
    }

    public static void writeHeader(final MultipartHeader header, final OutputStream out) throws IOException {
        writeBytes(header.getName(), out);
    }

    private final Charset charset;

    private final String boundary;

    private final List<MultipartFormPart> parts;

    public MultipartForm(final Charset charset, final String boundary, final List<MultipartFormPart> parts) {
        this.boundary = boundary;
        this.charset = charset;
        this.parts = parts;
    }

    public String getBoundary() {
        return this.boundary;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public List<MultipartFormPart> getParts() {
        return this.parts;
    }

    public void writeTo(final OutputStream out) throws IOException {
        final byte[] boundary = encode(this.charset, this.boundary);

        for (final MultipartFormPart part : this.parts) {
            writeBytes(DASHES, out);
            writeBytes(boundary, out);
            writeBytes(CR_LF, out);

            for (final MultipartHeader header : part.getHeaders()) {
                writeBytes(header.toString(), out);
                writeBytes(CR_LF, out);
            }

            writeBytes(CR_LF, out);

            part.getBody().writeTo(out);

            writeBytes(CR_LF, out);
        }

        writeBytes(DASHES, out);
        writeBytes(boundary, out);
        writeBytes(DASHES, out);
        writeBytes(CR_LF, out);
    }

}
