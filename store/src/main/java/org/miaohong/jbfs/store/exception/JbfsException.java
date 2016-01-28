package org.miaohong.jbfs.store.exception;

/**
 * Created by baidu on 16/1/27.
 */
public class JbfsException extends Exception {
    private String msg = null;
    public JbfsException() {}
    public JbfsException(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return msg;
    }
}
