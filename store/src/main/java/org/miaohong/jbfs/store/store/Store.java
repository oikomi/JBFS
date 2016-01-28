package org.miaohong.jbfs.store.store;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.config.StoreConfig;
import org.miaohong.jbfs.store.exception.ExceptionConst;
import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.needle.Needle;
import org.miaohong.jbfs.store.volume.Volume;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

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
    private Logger logger  =  Logger.getLogger(Store.class);
    private static Store instance = new Store();

    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZKUtils zkUtils = null;

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
            zkUtils = ZKUtils.getZK(storeConfig.getZookeeperAddrs(), storeConfig.getZookeeperTimeout());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        zkInit();
        try {
            wvf = new FileOutputStream(storeConfig.storeVolumeIndex, true);
            wfvf = new FileOutputStream(storeConfig.storeFreeVolumeIndex, true);

            rvf = new FileInputStream(storeConfig.storeVolumeIndex);
            System.out.println(storeConfig.storeFreeVolumeIndex);
            rfvf = new FileInputStream(storeConfig.storeFreeVolumeIndex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {

        }

        parseFreeVolumeIndex();
        parseVolumeIndex();

        //System.out.println();
        logger.debug("freeVolumes : " + freeVolumes);
        logger.debug("volumes : " + volumes);

    }

    private void zkInit() {
        //String zkPath = "/rack/" + storeConfig.storeRack + "/" + storeConfig.storeServerId;
        String zkPath = "/rack";

        try {
            zkUtils.createNode(zkPath, "".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void parseFreeVolumeIndex() {
        List<String> bufLines = new ArrayList<String>();
        try {
            bufLines = IOUtils.readLines(rfvf);
            System.out.println(bufLines);
            for (String bufLine : bufLines) {
                String [] tmpList = Utils.splitStr(bufLine, StoreConst.SEPARATOR);
                if (tmpList.length != 3) {
                    continue;
                }

                Volume v = new Volume(Integer.parseInt(tmpList[2]), tmpList[0], tmpList[1]);

                freeVolumes.add(v);

                freeVolumeId.incrementAndGet();
            }

            System.out.println(freeVolumes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void parseVolumeIndex() {
        // local
        List<String> bufLines = new ArrayList<String>();
        try {
            bufLines = IOUtils.readLines(rvf);
            for (String bufLine : bufLines) {
                String [] tmpList = Utils.splitStr(bufLine, StoreConst.SEPARATOR);
                if (tmpList.length != 3) {
                    continue;
                }

                Volume v = new Volume(Integer.parseInt(tmpList[2]), tmpList[0], tmpList[1]);

                volumes.put(Integer.parseInt(tmpList[2]), v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        // zk


    }

    private void saveFreeVolumeIndex() throws IOException {
        System.out.println("saveFreeVolumeIndex");

        wfvf = new FileOutputStream(storeConfig.storeFreeVolumeIndex, false);
        for (Volume v : freeVolumes) {
            wfvf.write((v.getSupperBlock().getSupperBlockFilePath() + ","
                    + v.getIndex().getIndexFile() + "," + StoreConst.VOLUME_FREE_ID).getBytes());
            wfvf.write("\n".getBytes());
        }

    }

    private void saveVolumeIndex() {
        try {
            wvf = new FileOutputStream(storeConfig.storeVolumeIndex, false);
            for (Map.Entry<Integer, Volume> entry : volumes.entrySet()) {
                int i = 0;
                while(true) {
                    if (Utils.isFileExist(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).
                            getAbsolutePath() + entry.getKey() + "_" + i)) {
                        i ++;
                        continue;
                    } else {
                        Utils.renameFile(entry.getValue().getSupperBlock().getSupperBlockFilePath(),
                                Utils.getFileDir(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).
                                        getAbsolutePath()) + entry.getKey() + "_" + i);

                        Utils.renameFile(entry.getValue().getIndex().getIndexFile(),
                                Utils.getFileDir(new File(entry.getValue().getIndex().getIndexFile()).getAbsolutePath())
                                        + entry.getKey() + "_" + i + StoreConst.VOLUME_INDEX_EXT);

                        wvf.write(((Utils.getFileDir(new File(entry.getValue().getSupperBlock().getSupperBlockFilePath()).
                                getAbsolutePath()) + entry.getKey() + "_" + i + "," +
                                Utils.getFileDir(new File(entry.getValue().getIndex().getIndexFile()).getAbsolutePath()) +
                                entry.getKey() + "_" + i + StoreConst.VOLUME_INDEX_EXT + "," + entry.getKey()).getBytes()));
                        wvf.write("\n".getBytes());
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFreeVolume(int n, String bDir, String iDir) throws IOException {
        for (int i = 0; i < n; i++) {
            freeVolumeId.incrementAndGet();

            Volume v = new Volume(StoreConst.VOLUME_FREE_ID, bDir + StoreConst.FREE_VOLUME_PREFIX + freeVolumeId.get(),
                iDir + StoreConst.FREE_VOLUME_PREFIX + freeVolumeId.get() + StoreConst.VOLUME_INDEX_EXT);

            freeVolumes.add(v);
        }
        saveFreeVolumeIndex();
    }

    public void addVolume(int vid) throws StoreAdminException.StoreNoFreeVolumeException,
            StoreAdminException.StoreVolumeExistException, IOException {
        if (freeVolumes.size() == 0) {
            // throw new StoreAdminException(ExceptionConst.ExceptionStoreNoFreeVolume);
            throw new StoreAdminException.StoreNoFreeVolumeException();
        }

        if (volumes.get(vid) != null) {
            // throw new StoreAdminException(ExceptionConst.ExceptionStoreVolumeExist);
            throw new StoreAdminException.StoreVolumeExistException();
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

    public void upload(int vid, long key, String cookie, long size, byte[] buf) throws StoreAdminException.NeedleTooLargeException,
            StoreAdminException.NeedleIsEmptyException, StoreAdminException.VolumeNotExistException, IOException {
        if (size > StoreConst.NEEDLE_MAX_SIZE) {
            // throw new StoreAdminException(ExceptionConst.ExceptionNeedleTooLarge);
            throw new StoreAdminException.NeedleTooLargeException();
        }
        if (size == 0) {
            // throw new StoreAdminException(ExceptionConst.ExceptionNeedleIsEmpty);
            throw new StoreAdminException.NeedleIsEmptyException();
        }

        Volume v = volumes.get(vid);
        if (v == null) {
            // throw new StoreAdminException(ExceptionConst.ExceptionVolumeNotExist);
            throw new StoreAdminException.VolumeNotExistException();
        }

        Needle needle = new Needle(vid, key, cookie, size, buf);

        v.addNeedle(needle);

    }

    public byte[] get(int vid, String key, String cookie) throws StoreAdminException.VolumeNotExistException {
        Volume v = volumes.get(vid);
        if (v == null) {
            //throw new StoreAdminException(ExceptionConst.ExceptionVolumeNotExist);
            throw new StoreAdminException.VolumeNotExistException();
        }

        return v.getNeedle(Long.parseLong(key), Integer.parseInt(cookie));
    }


    public static void main(String[] args) {
        Store store = Store.getInstance();
        try {
            store.addFreeVolume(2, "/tmp/store/", "/tmp/store/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
