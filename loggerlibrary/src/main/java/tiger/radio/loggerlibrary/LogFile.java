package tiger.radio.loggerlibrary;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogFile {
    private static final boolean LOGV = true;

    public static final int LOG_FILE_MAX_SIZE = 2 * 1024 * 1024;
    public static final int LOG_FILE_MAX_COUNT = 10;

    private File mLogDir;
    private String mLogFileName;
    private LogRecord mLogRecord = new LogRecord(Level.INFO, "");
    private Logger mLogger;

    public LogFile(String name, File dir, String fileName) {
        if (dir == null || !dir.exists() || fileName == null) {
            Log.w("LogFile", "Invalid configuration provided");
            throw new IllegalArgumentException();
        }
        this.mLogDir = dir;
        this.mLogFileName = fileName;
        File path = new File(this.mLogDir, this.mLogFileName);
        this.mLogger = Logger.getLogger(name);
        try {
            Handler handler = new FileHandler(path.getAbsolutePath(), LOG_FILE_MAX_SIZE, LOG_FILE_MAX_COUNT, true);
            if (LOGV) {
                Log.d("LogFile", "path=" + path.getAbsolutePath());
            }
            handler.setFormatter(new CustomFormatter());
            this.mLogger.setUseParentHandlers(false);
            this.mLogger.addHandler(handler);
            this.mLogger.setLevel(Level.ALL);
        } catch (IOException e) {
            Log.e("LogFile", "Exception: ", e);
        }
    }

    public void v(String tag, String msg) {
        log(Level.FINEST, tag, msg);
    }

    public void v(String tag, String msg, Throwable t) {
        Log.v(tag, msg, t);
        log(Level.FINEST, tag, msg, t);
    }

    public void d(String tag, String msg) {
        log(Level.FINE, tag, msg);
    }

    public void d(String tag, String msg, Throwable t) {
        log(Level.FINE, tag, msg, t);
    }

    public void i(String tag, String msg) {
        log(Level.INFO, tag, msg);
    }

    public void i(String tag, String msg, Throwable t) {
        log(Level.INFO, tag, msg, t);
    }

    public void w(String tag, String msg) {
        log(Level.WARNING, tag, msg);
    }

    public void w(String tag, String msg, Throwable t) {
        log(Level.WARNING, tag, msg, t);
    }

    public void e(String tag, String msg) {
        log(Level.SEVERE, tag, msg);
    }

    public void e(String tag, String msg, Throwable t) {
        log(Level.SEVERE, tag, msg, t);
    }

    public void wtf(String tag, String msg) {
        log(Level.SEVERE, tag, msg);
    }

    public void wtf(String tag, String msg, Throwable t) {
        log(Level.SEVERE, tag, msg, t);
    }

    public void f(String tag, String msg) {
        log(Level.INFO, tag, msg);
    }

    public void f(String tag, String msg, Throwable t) {
        log(Level.INFO, tag, msg, t);
    }

    private synchronized void log(Level level, String tag, String msg) {
        if (this.mLogger == null) {
            Log.w("LogFile", "File logger not configured");
        } else {
            this.mLogRecord.setMillis(System.currentTimeMillis());
            this.mLogRecord.setLevel(level);
            this.mLogRecord.setMessage(String.format("%s: %s", tag, msg));
            this.mLogRecord.setThrown(null);
            this.mLogger.log(this.mLogRecord);
        }
    }

    private synchronized void log(Level level, String tag, String msg, Throwable t) {
        if (this.mLogger == null) {
            Log.w("LogFile", "File logger not configured");
        } else {
            this.mLogRecord.setMillis(System.currentTimeMillis());
            this.mLogRecord.setLevel(level);
            this.mLogRecord.setMessage(String.format("%s: %s", tag, msg));
            this.mLogRecord.setThrown(t);
            this.mLogger.log(this.mLogRecord);
        }
    }

    public synchronized void dump(PrintWriter writer) {
        if (this.mLogDir == null || this.mLogFileName == null) {
            Log.w("LogFile", "The file or directory of the log file is unknown");
        } else {
            for (int i = 0; i < 2; i++) {
                File f = new File(this.mLogDir, this.mLogFileName + "." + i);
                if (f.exists()) {
                    dumpLogFile(f, writer);
                }
            }
        }
    }

    private void dumpLogFile(File file, PrintWriter writer) {
        IOException e;
        InputStreamReader reader = null;
        writer.println();
        writer.println(file.getName() + ":");
        writer.println();
        try {
            InputStreamReader reader2 = new FileReader(file);
            try {
                char[] buf = new char[FragmentTransaction.TRANSIT_EXIT_MASK];
                while (true) {
                    int ret = reader2.read(buf);
                    if (ret == -1) {
                        IOUtils.closeQuietly(reader2);
                        reader = reader2;
                        return;
                    }
                    writer.write(buf, 0, ret);
                }
            } catch (IOException e2) {
                e = e2;
                reader = reader2;
            } catch (Throwable th2) {
                reader = reader2;
            }
        } catch (IOException e3) {
            e = e3;
            try {
                Log.w("LogFile", "Exception: ", e);
                IOUtils.closeQuietly(reader);
            } catch (Throwable th3) {
                IOUtils.closeQuietly(reader);
            }
        }
    }
}
