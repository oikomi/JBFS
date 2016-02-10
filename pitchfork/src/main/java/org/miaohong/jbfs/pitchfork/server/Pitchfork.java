package org.miaohong.jbfs.pitchfork.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

import java.io.IOException;

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
    }

    public void run() {
        init();

    }

//    public static void main(String[] args) {
//
//    }
}
