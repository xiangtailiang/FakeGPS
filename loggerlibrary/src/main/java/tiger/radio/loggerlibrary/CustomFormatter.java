package tiger.radio.loggerlibrary;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter {
    private static final HashMap<Level, String> sLevelMap = new HashMap<>();

    static {
        sLevelMap.put(Level.FINER, "V");
        sLevelMap.put(Level.FINE, "D");
        sLevelMap.put(Level.INFO, "I");
        sLevelMap.put(Level.WARNING, "W");
        sLevelMap.put(Level.SEVERE, "E");
    }


    @Override
    public String format(LogRecord r) {
        String level = sLevelMap.get(r.getLevel());
        if (level == null) {
            level = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.US).format(new Date(r.getMillis())));
        sb.append(level);
        sb.append(" ");
        sb.append(formatMessage(r)).append("\n");
        if (r.getThrown() != null) {
            sb.append("Throwable occurred: ");
            Throwable t = r.getThrown();
            PrintWriter pw = null;
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw2 = new PrintWriter(sw);
                try {
                    t.printStackTrace(pw2);
                    sb.append(sw.toString());
                    IOUtils.closeQuietly(pw2);
                } catch (Throwable th2) {
                    pw = pw2;
                    IOUtils.closeQuietly(pw);
                }
            } catch (Throwable th3) {
                IOUtils.closeQuietly(pw);
            }
        }
        return sb.toString();
    }
}
