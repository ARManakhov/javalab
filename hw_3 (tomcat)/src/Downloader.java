import ru.sirosh.Config;
import ru.sirosh.MultipleDownloader;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Downloader extends HttpServlet {
    Map<String, List<String>> cookiesVals;
    String cookieString = "token";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookiesVals = (Map<String, List<String>>) getServletContext().getAttribute("cookiesVals");
        Cookie[] cookies = req.getCookies();
        List<String> urls = null;
        boolean cookieFound = false;
        if (cookies !=null){
            for (Cookie cookie : cookies) {
                if (cookiesVals != null && cookie.getName().equals(cookieString) && cookiesVals.containsKey(cookie.getValue())) {
                    urls = cookiesVals.get(cookie.getValue());
                    String filesPath = System.getProperty("catalina.home") + File.separator + "data" + File.separator + cookie.getValue();
                    File savedir = new File(filesPath);
                    if (!savedir.exists()){
                        savedir.mkdirs();
                    }
                    System.out.println("savepath " + filesPath);

                    new MultipleDownloader(new Config(filesPath,urls.toArray(new String[urls.size()]))).download();
                    cookiesVals.put(cookie.getValue(), new ArrayList<>());
                }
            }
        }
        if (urls != null){
            System.out.println(urls);
        }
        resp.setStatus(HttpServletResponse.SC_FOUND);//302
        resp.setHeader("Location", "/");
    }
}
