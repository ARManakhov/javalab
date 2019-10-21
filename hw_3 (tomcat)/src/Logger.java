import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Logger {
    String logPath = System.getProperty("catalina.home") + File.separator + "servlet_logs";
    File log = new File(logPath + File.separator + "log");
    PrintWriter pw = null;

    private static Logger instace = null;

    public static Logger getInstance() {
        if (instace == null) {
            instace = new Logger();
        }

        return instace;
    }

    private Logger() {
        new File(logPath).mkdir();
        try {
            pw = new PrintWriter(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void newlog(ServletRequest req) {
        newlog((HttpServletRequest) req);

    }

    public void newlog(HttpServletRequest req) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        StringBuilder cookiesStr = new StringBuilder("{");
        for (Cookie cookie : req.getCookies()) {
            cookiesStr.append(cookie.getName() + " : " + cookie.getValue() + ", ");
        }
        cookiesStr.append(" }");
        pw.write("[" + formatter.format(date) + "] Method: \"" + req.getMethod() + "\" addres: \"" + req.getRequestURI() + "\" cokies: " + cookiesStr.toString() + "\n");
        pw.flush();
    }

}
