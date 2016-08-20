package tiger.radio.loggerlibrary;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static final boolean LOGV = true;
    private static volatile LogFile sLogFile;
    private static Map<String, LogFile> sLogFilesMap = new HashMap<String, LogFile>();

    public static boolean isLoggable(String tag, int level) {
        return android.util.Log.isLoggable(tag, level);
    }

    public static void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
        if (sLogFile != null) {
            sLogFile.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable t) {
        android.util.Log.v(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.v(tag, msg, t);
        }
    }

    public static void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
        if (sLogFile != null) {
            sLogFile.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        android.util.Log.d(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.d(tag, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
        if (sLogFile != null) {
            sLogFile.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        android.util.Log.i(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.i(tag, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
        if (sLogFile != null) {
            sLogFile.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        android.util.Log.w(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.w(tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
        if (sLogFile != null) {
            sLogFile.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        android.util.Log.e(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.i(tag, msg, t);
        }
    }

    public static void wtf(String tag, String msg) {
        android.util.Log.wtf(tag, msg);
        if (sLogFile != null) {
            sLogFile.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, String msg, Throwable t) {
        android.util.Log.wtf(tag, msg, t);
        if (sLogFile != null) {
            sLogFile.wtf(tag, msg, t);
        }
    }

    public static void f(String tag, String msg) {
        if (sLogFile != null) {
            sLogFile.f(tag, msg);
        }
    }

    public static void f(String tag, String msg, Throwable t) {
        if (sLogFile != null) {
            sLogFile.f(tag, msg, t);
        }
    }

    public static synchronized void dump(PrintWriter writer) {
        synchronized (Logger.class) {
            if (sLogFile != null) {
                sLogFile.dump(writer);
                for (LogFile log : sLogFilesMap.values()) {
                    log.dump(writer);
                }
            }
        }
    }

    public static synchronized void configure(File dir, String fileName) {
        synchronized (Logger.class) {
            if (dir == null || fileName == null) {
                android.util.Log.w("Log", "Invalid configuration provided");
            } else {
                try {
                    sLogFile = new LogFile("default_logger", dir, fileName);
                } catch (IllegalArgumentException e) {
                    android.util.Log.w("Log", "Failed to create log file.");
                }
            }
        }
    }

    public static synchronized void configureLogFile(String name, File dir, String fileName) {
        synchronized (Logger.class) {
            if (dir == null || fileName == null) {
                android.util.Log.w("Log", "Invalid configuration provided");
            } else {
                try {
                    sLogFilesMap.put(name, new LogFile(name, dir, fileName));
                } catch (IllegalArgumentException e) {
                    android.util.Log.w("Log", "Failed to create log file.");
                }
            }
        }
    }

    public static synchronized LogFile getLogFile(String name) {
        LogFile logFile;
        synchronized (Logger.class) {
            logFile = sLogFilesMap.get(name);
        }
        return logFile;
    }
}
