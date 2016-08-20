package tiger.radio.loggerlibrary;

import android.os.ParcelFileDescriptor;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {
    public static void closeQuietly(ParcelFileDescriptor p) {
        if (p != null) {
            try {
                p.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean closeWhenDone) throws IOException {
        return copyStream(inputStream, outputStream, closeWhenDone, 1024);
    }

    public static long copyStream(InputStream inputStream, OutputStream outputStream, boolean closeWhenDone, int bufferSizeBytes) throws IOException {
        byte[] bArr = new byte[bufferSizeBytes];
        long j = 0;
        while (true) {
            try {
                int read = inputStream.read(bArr, 0, bArr.length);
                if (read == -1) {
                    break;
                }
                j += (long) read;
                outputStream.write(bArr, 0, read);
            } finally {
                if (closeWhenDone) {
                    closeQuietly((Closeable) inputStream);
                    closeQuietly((Closeable) outputStream);
                }
            }
        }
        return j;
    }

}
