package org.miaohong.jbfs.store.store;

/**
 * Created by miaohong on 16/1/17.
 */
public class Utils {

    public static String[] splitStr(String buf, String separator) {
        if (buf == null) {
            return null;
        }

        return buf.split(separator);
    }
}
