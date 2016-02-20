package org.miaohong.jbfs.pitchfork.task;

import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by miaohong on 16/2/19.
 */
public class UpdateVolumeMetaTask implements Callable {
    private PitchforkConfig pitchforkConfig = PitchforkConfig.getInstance();
    private ZKUtils zkUtils = ZKUtils.getZK(pitchforkConfig.zookeeperAddrs, pitchforkConfig.zookeeperTimeout);

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

    public Object call() throws Exception {

        while(true) {
            Thread.sleep(1000);
            watchStores();
        }

    }
}
