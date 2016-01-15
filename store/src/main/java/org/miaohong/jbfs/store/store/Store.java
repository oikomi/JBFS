package org.miaohong.jbfs.store.store;

import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.store.volume.Volume;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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

//    private File volumeFile = null;
//    private File freeVolumeFile = null;
    private FileChannel vfc = null;
    private FileChannel fvfc = null;


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

        try {
            vfc = new FileOutputStream(storeConfig.storeVolumeIndex).getChannel();
            fvfc = new FileOutputStream(storeConfig.storeFreeVolumeIndex).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveFreeVolumeIndex() {
        try {
            for (Volume v : freeVolumes) {
                fvfc.write(ByteBuffer.wrap(v.getSupperBlock().getSupperBlockFilePath().getBytes()));

            }
        } catch (IOException e) {

        }

    }
    
    public void addFreeVolume(int n, String bDir, String iDir) {
        for (int i = 0; i < n; i++) {
            freeId ++;

            Volume v = new Volume(Const.VOLUME_FREE_ID, bDir + Const.FREE_VOLUME_PREFIX + freeId,
                iDir + Const.FREE_VOLUME_PREFIX + freeId + Const.VOLUME_INDEX_EXT);


            freeVolumes.add(v);

            saveFreeVolumeIndex();
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
