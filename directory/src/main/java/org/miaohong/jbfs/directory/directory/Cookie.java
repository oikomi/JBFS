package org.miaohong.jbfs.directory.directory;

/**
 * Created by miaohong on 16/2/8.
 */
public class Cookie {

    public static int generateCookie() {
       return (int) System.currentTimeMillis();
    }


    public static void main(String[] args) {
        System.out.println(Cookie.generateCookie());
    }
}
