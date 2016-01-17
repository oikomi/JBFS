package org.miaohong.jbfs.store.exception;

/**
 * Created by baidu on 16/1/17.
 */
public class StoreAdminException extends Exception {
    private String msg = null;
    public StoreAdminException(String msg) {
        this.msg = msg;
    }

}
