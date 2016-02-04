package org.miaohong.jbfs.directory.common.model;

/**
 * Created by miaohong on 16/2/3.
 */
public class NeedleMeta {
    private long key;
    private int cookie;
    private int vid;


    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getCookie() {
        return cookie;
    }

    public void setCookie(int cookie) {
        this.cookie = cookie;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }


}
