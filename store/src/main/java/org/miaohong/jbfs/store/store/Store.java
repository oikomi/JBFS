package org.miaohong.jbfs.store.store;

import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.store.volume.Volume;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by miaohong on 16/1/13.
 */
public class Store {
    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZooKeeper zk = null;

    private Map<Integer, Volume> volumes = new HashMap<Integer, Volume>();
    private List<Volume> freeVolumes = new ArrayList<Volume>();

    private int freeId;


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


    public void addFreeVolume(int n, String bDir, String iDir) {
        for (int i = 0; i < n; i++) {
            freeId ++;

            Volume v = new Volume(Const.VOLUME_FREE_ID, bDir + Const.FREE_VOLUME_PREFIX + freeId,
                iDir + Const.FREE_VOLUME_PREFIX + freeId + Const.VOLUME_INDEX_EXT);


            freeVolumes.add(v);
        }
    }

//    public static void main(String[] args) {
//        Store store = new Store();
//
//        store.addFreeVolume(1, "/tmp", "/tmp");
//        SupperBlock supperBlock = new SupperBlock("/tmp/1.block");
//
//    }
}
