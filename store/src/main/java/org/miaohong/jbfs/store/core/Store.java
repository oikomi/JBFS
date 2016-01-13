package org.miaohong.jbfs.store.core;

import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.IOException;

/**
 * Created by miaohong on 16/1/13.
 */
public class Store {
    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZooKeeper zk = null;

    public Store() {
        init();
    }

    private void init() {
        try {
            zk = ZKConn.getZK(storeConfig.getZookeeperAddrs(), storeConfig.getZookeeperTimeout());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Store store = new Store();
    }
}
