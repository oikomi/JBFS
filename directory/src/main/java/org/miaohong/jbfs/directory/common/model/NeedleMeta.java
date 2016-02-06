package org.miaohong.jbfs.directory.common.model;

/**
 * Created by miaohong on 16/2/3.
 */
public class NeedleMeta {
    private int id;
    private long key;
    private int cookie;
    private int vid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "NeedleMeta{" +
                "key=" + key +
                ", cookie=" + cookie +
                ", vid=" + vid +
                '}';
    }
}
