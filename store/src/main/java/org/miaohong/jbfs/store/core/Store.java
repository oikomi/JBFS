package org.miaohong.jbfs.store.core;

import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.IOException;

/**
 * Created by baidu on 16/1/13.
 */
public class Store {
    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZooKeeper zk = null;

    private void init() {
        try {
            zk = ZKConn.getZK(StoreConfig._instance.getZookeeperAddrs(),
                    StoreConfig._instance.getZookeeperTimeout());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Store store = new Store();
        store.init();

    }
}
