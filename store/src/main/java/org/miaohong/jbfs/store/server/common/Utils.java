package org.miaohong.jbfs.store.server.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by baidu on 16/1/23.
 */
public class Utils {

    public static void printJson(HttpServletResponse resp, String data) {
        resp.setContentType("text/json; charset=UTF-8");
        try {
            resp.getWriter().print(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
