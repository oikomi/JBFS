package org.miaohong.jbfs.store.store;

import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.needle.Needle;
import org.miaohong.jbfs.store.volume.Volume;
import org.miaohong.jbfs.zookeeper.conn.ZKConn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private FileOutputStream wvf = null;
    private FileOutputStream wfvf = null;

    private FileInputStream rvf = null;
    private FileInputStream rfvf = null;

    private AtomicLong freeVolumeId = new AtomicLong(0);

    private Store() {
        init();
    }

    public static Store getInstance() {
        return instance;
    }

    private void init() {
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
            System.exit(-1);
        } finally {

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

                freeVolumeId.incrementAndGet();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void parseVolumeIndex() {

    }

    private void saveFreeVolumeIndex() {
        try {
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

    private void saveVolumeIndex() {
        try {
            wvf = new FileOutputStream(storeConfig.storeVolumeIndex, false);
            for (Map.Entry<Integer, Volume> entry : volumes.entrySet()) {
                int i = 0;
                while(true) {
                    if (Utils.isFileExist(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).getAbsolutePath() +
                            entry.getKey() + "_" + i)) {
                        i ++;
                        continue;
                    } else {
                        Utils.renameFile(entry.getValue().getSupperBlock().getSupperBlockFilePath(),
                                Utils.getFileDir(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).getAbsolutePath()) +
                                        entry.getKey() + "_" + i);

                        Utils.renameFile(entry.getValue().getIndex().getIndexFile(),
                                Utils.getFileDir(new File(entry.getValue().getIndex().getIndexFile()).getAbsolutePath()) +
                                        entry.getKey() + "_" + i + ".idx");

                        wvf.write(((Utils.getFileDir(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).getAbsolutePath()) +
                                entry.getKey() + "_" + i + "," + Utils.getFileDir(new File(entry.getValue().getIndex().getIndexFile()).getAbsolutePath()) +
                                entry.getKey() + "_" + i + ".idx" + "," + entry.getKey()).getBytes()));
                        wvf.write("\n".getBytes());
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addFreeVolume(int n, String bDir, String iDir) {
        for (int i = 0; i < n; i++) {
            freeVolumeId.incrementAndGet();

            Volume v = new Volume(Const.VOLUME_FREE_ID, bDir + Const.FREE_VOLUME_PREFIX + freeVolumeId.get(),
                iDir + Const.FREE_VOLUME_PREFIX + freeVolumeId.get() + Const.VOLUME_INDEX_EXT);

            freeVolumes.add(v);
        }
        saveFreeVolumeIndex();
    }

    public void addVolume(int vid) throws StoreAdminException {
        if (freeVolumes.size() == 0) {
            throw new StoreAdminException(ExceptionConst.ExceptionStoreNoFreeVolume);
        }

        if (volumes.get(vid) != null) {
            throw new StoreAdminException(ExceptionConst.ExceptionStoreVolumeExist);
        }

        Volume v = getFreeVolume();
        volumes.put(vid, v);
        saveVolumeIndex();
        saveFreeVolumeIndex();
    }



    private Volume getFreeVolume() {
        Volume v = freeVolumes.get(0);
        freeVolumes.remove(0);
        return v;
    }


    public void upload(int vid, int key, String cookie, long size, byte[] buf) throws StoreAdminException {
        if (size > Const.NEEDLE_MAX_SIZE) {
            throw new StoreAdminException(ExceptionConst.ExceptionNeedleTooLarge);
        }

        if (size == 0) {
            throw new StoreAdminException(ExceptionConst.ExceptionNeedleIsEmpty);
        }

        Needle needle = new Needle(vid, key, cookie, size, buf);


    }


    public static void main(String[] args) {
        Store store = Store.getInstance();
        store.addFreeVolume(2, "/tmp/store/", "/tmp/store/");
    }

}
