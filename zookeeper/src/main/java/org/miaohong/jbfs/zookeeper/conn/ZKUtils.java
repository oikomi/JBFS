package org.miaohong.jbfs.zookeeper.conn;

import com.sun.tools.javac.util.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by miaohong on 16/1/13.
 */
public class ZKUtils {
    private static ZKUtils zkUtils = new ZKUtils();
    private static ZooKeeper zk;
    private static CuratorFramework client;

    private ZKUtils() {

    }

    public static ZKUtils getZK(String host, int timeout) throws IOException {
//        zk = new ZooKeeper(host, timeout, new Watcher() {
//            public void process(WatchedEvent event) {
//                System.out.println("已经触发了" + event.getType() + "事件！");
//            }
//        });
//        try {
//            connectedSemaphore.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //System.out.println(zk.toString());

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //client = CuratorFrameworkFactory.newClient(host, retryPolicy);
        client = CuratorFrameworkFactory.builder()
                .connectString(host)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();

        client.start();

        return zkUtils;
    }

    public void createNode(String path, byte[] data, ArrayList<ACL> acl,
                           CreateMode mode) {
        //zk.create(path, data, acl, mode);
        String[] pathList = path.split("/");
        String tmpPath = "";

        for (int i = 1; i < pathList.length; i++) {
            tmpPath = tmpPath + "/" + pathList[i];
            try {
                client.create().forPath(tmpPath, data);
            } catch (Exception e) {
                if (e instanceof KeeperException.NodeExistsException) {
                    System.out.println("node " + tmpPath + "  already exist");
                }

                // e.printStackTrace();
            }
        }
    }

    public void deleteNode(String path) {
        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String path, byte[] payload) {
        try {
            client.setData().forPath(path, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getData(String path) {
        try {
            return client.getData().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<String> getChild(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
