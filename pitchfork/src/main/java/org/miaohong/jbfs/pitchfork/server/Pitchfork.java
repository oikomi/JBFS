package org.miaohong.jbfs.pitchfork.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by miaohong on 16/2/10.
 */
public class Pitchfork {
    private PitchforkConfig pitchforkConfig = PitchforkConfig.getInstance();

    private ZKUtils zkUtils = null;

    private Pitchfork() {}


    private static class SingletonHolder {
        private static final Pitchfork INSTANCE = new Pitchfork();
    }

    public static final Pitchfork getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private void init() {
        try {
            zkUtils = ZKUtils.getZK(pitchforkConfig.zookeeperAddrs, pitchforkConfig.zookeeperTimeout);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        zkUtils.createNode(pitchforkConfig.zkPitchforkRoot, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,
                CreateMode.PERSISTENT);

        zkUtils.createNode(pitchforkConfig.zkVolumeRoot, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,
                CreateMode.PERSISTENT);
    }

    private List<String> getStores(String rack) {
        String storePath = pitchforkConfig.zkStoreRoot + "/" + rack;

        return zkUtils.getChild(storePath);
    }

    private List<String> watchRacks() {
        try {
            List<String> racks = zkUtils.watchedGetChildren(pitchforkConfig.zkStoreRoot);
            // System.out.println(racks);

            return racks;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void watchStores() {
        List<String> racks = watchRacks();

        for (String rack : racks) {
            System.out.println(rack);
            List<String> stores = getStores(rack);

            for (String store : stores) {
                System.out.println(store);

            }
        }
    }

    public void run() {
        init();

        while (true) {
            watchStores();
        }

    }

//    public static void main(String[] args) {
//
//    }
}
