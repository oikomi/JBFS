package org.miaohong.jbfs.zookeeper.conn;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by miaohong on 16/1/13.
 */
public class ZKConn {
    public static ZooKeeper getZK(String host, int timeout) throws IOException{
        ZooKeeper zk = new ZooKeeper(host, timeout, new Watcher() {
            public void process(WatchedEvent event) {
                //System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });

        return zk;
    }

    public void createNode(ZooKeeper zk, String path, byte[] data, ArrayList<ACL> acl,
                           CreateMode mode) throws KeeperException, InterruptedException {
        zk.create(path, data, acl, mode);
    }
}
