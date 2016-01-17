package org.miaohong.jbfs.store.store;

import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.volume.Volume;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by miaohong on 16/1/13.
 */
public class Store {
    private static Store instance = new Store();

    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZooKeeper zk = null;

    private Map<Integer, Volume> volumes = new HashMap<Integer, Volume>();
    private List<Volume> freeVolumes = new ArrayList<Volume>();

//    private File volumeFile = null;
//    private File freeVolumeFile = null;
    private FileOutputStream wvf = null;
    private FileOutputStream wfvf = null;

    private FileInputStream rvf = null;
    private FileInputStream rfvf = null;

    private AtomicLong freeId = new AtomicLong(0);

    private Store() {
        init();
    }

    public static Store getInstance() {
        return instance;
    }

    private void init() {
        System.out.println("init");
        try {
            zk = ZKConn.getZK(storeConfig.getZookeeperAddrs(), storeConfig.getZookeeperTimeout());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            wvf = new FileOutputStream(storeConfig.storeVolumeIndex, false);
            wfvf = new FileOutputStream(storeConfig.storeFreeVolumeIndex, false);

            rvf = new FileInputStream(storeConfig.storeVolumeIndex);
            rfvf = new FileInputStream(storeConfig.storeFreeVolumeIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        parseFreeVolumeIndex();
        parseVolumeIndex();

//        try {
//            wvfc = new FileOutputStream(storeConfig.storeVolumeIndex, false).getChannel();
//            wfvfc = new FileOutputStream(storeConfig.storeFreeVolumeIndex, false).getChannel();
//
//            rvfc = new FileInputStream(storeConfig.storeVolumeIndex).getChannel();
//            rfvfc = new FileInputStream(storeConfig.storeFreeVolumeIndex).getChannel();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void parseFreeVolumeIndex() {
        List<String> bufLines = new ArrayList<String>();
        try {
            bufLines = IOUtils.readLines(rfvf);

            for (String bufLine : bufLines) {
                String [] tmpList = Utils.splitStr(bufLine, Const.SEPARATOR);
                if (tmpList.length != 3) {
                    continue;
                }

                Volume v = new Volume(Integer.parseInt(tmpList[2]), tmpList[0], tmpList[1]);

                freeVolumes.add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        System.out.println(freeVolumes);

    }

    private void parseVolumeIndex() {

    }

    private void saveFreeVolumeIndex() {
        try {
            System.out.println(wfvf);
            wfvf = new FileOutputStream(storeConfig.storeFreeVolumeIndex, false);
            for (Volume v : freeVolumes) {
                wfvf.write((v.getSupperBlock().getSupperBlockFilePath() + ","
                        + v.getIndex().getIndexFile() + "," + Const.VOLUME_FREE_ID).getBytes());
                wfvf.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFreeVolume(int n, String bDir, String iDir) {
        for (int i = 0; i < n; i++) {
            freeId.incrementAndGet();

            // System.out.println(freeId.get());

            Volume v = new Volume(Const.VOLUME_FREE_ID, bDir + Const.FREE_VOLUME_PREFIX + freeId.get(),
                iDir + Const.FREE_VOLUME_PREFIX + freeId.get() + Const.VOLUME_INDEX_EXT);


            freeVolumes.add(v);
        }
        // System.out.println(freeVolumes.size());

        saveFreeVolumeIndex();
    }

    public void addVolume(int vid) throws StoreAdminException {
        if (freeVolumes.size() == 0) {
            throw  new StoreAdminException(ExceptionConst.ExceptionStoreNoFreeVolume);
        }

        

    }


    public static void main(String[] args) {
        Store store = Store.getInstance();
        store.addFreeVolume(2, "/tmp/store/", "/tmp/store/");
    }


}
