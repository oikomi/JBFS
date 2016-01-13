package org.miaohong.jbfs.store.core;

import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.IOException;

/**
 * Created by baidu on 16/1/13.
 */
public class Store {
    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZKConn zk = null;

    public static void main(String[] args) {
        try {
            zk = ZKConn.getZK(StoreConfig._instance.getZookeeperAddrs(),
                    StoreConfig._instance.getZookeeperTimeout());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
