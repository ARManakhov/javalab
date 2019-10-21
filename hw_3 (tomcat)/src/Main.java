
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Main extends HttpServlet {
    Map<String, List<String>> cookiesVals;
    String cookieString = "token";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        PrintWriter pw = resp.getWriter();
        pw.write("<html>\n" +
                "  <head>\n" +
                "    <title>Downloader</title>\n" +
                "  </head>\n" +
                "  <body>");
        boolean cookieFound = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookiesVals != null && cookie.getName().equals(cookieString) && cookiesVals.containsKey(cookie.getValue())) {
                    List<String> urls = cookiesVals.get(cookie.getValue());
                    //pw.write("<div>" + cookie.getValue() + "</div>");
                    for (String str : urls) {
                        pw.write("<div>" + str + "</div>");
                    }
                    cookieFound = true;
                }
            }
            getServletContext().setAttribute("cookiesVals", cookiesVals);
        }

        if (!cookieFound) {
            Integer randToken = new Random().nextInt();
            resp.addCookie(new Cookie(cookieString, randToken.toString()));

            if (cookiesVals == null) {
                cookiesVals = new HashMap<>();
            }
            cookiesVals.put(randToken.toString(), new ArrayList<>());

            //pw.write("<div>" + randToken + "</div>");
        }
        pw.write("<form name=\"login\" method=\"post\" action=\"login\"> <input name = \"val\" type=\"text\" id=\"val\"> <input type=\"submit\" value=\"Add\"> </form>");
        pw.write("<form name=\"download\" method=\"post\" action=\"download\"> <input type=\"submit\" value=\"Download\"> </form>");
        pw.write("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        boolean cookieFound = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookiesVals != null && cookie.getName().equals(cookieString) && cookiesVals.containsKey(cookie.getValue())) {
                    List<String> urls = cookiesVals.get(cookie.getValue());
                    String url = req.getParameter("val");
                    if (url != null) {
                        urls.add(url);
                    }
                    cookieFound = true;
                }
            }
        }
        resp.sendRedirect("login");
    }
}