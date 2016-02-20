package org.miaohong.jbfs.pitchfork.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;
import org.miaohong.jbfs.pitchfork.task.UpdateVolumeMetaTask;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by miaohong on 16/2/10.
 */
public class Pitchfork {
    private PitchforkConfig pitchforkConfig = PitchforkConfig.getInstance();
    private ZKUtils zkUtils = null;

    private ExecutorService threadPool = Executors.newFixedThreadPool(4);

    private Pitchfork() {}


    private static class SingletonHolder {
        private static final Pitchfork INSTANCE = new Pitchfork();
    }

    public static final Pitchfork getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private void init() {
        zkUtils = ZKUtils.getZK(pitchforkConfig.zookeeperAddrs, pitchforkConfig.zookeeperTimeout);
        zkUtils.createNode(pitchforkConfig.zkPitchforkRoot, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,
                CreateMode.PERSISTENT);
        zkUtils.createNode(pitchforkConfig.zkVolumeRoot, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,
                CreateMode.PERSISTENT);
    }

    public void run() {
        init();
        threadPool.submit(new UpdateVolumeMetaTask());

    }
}
