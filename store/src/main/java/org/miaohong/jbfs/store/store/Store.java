package org.miaohong.jbfs.store.store;

import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.ZooKeeper;
import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.config.StoreConfig;
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
    private StoreConfig storeConfig = StoreConfig.getInstance();
    private ZooKeeper zk = null;

    private Map<Integer, Volume> volumes = new HashMap<Integer, Volume>();
    private List<Volume> freeVolumes = new ArrayList<Volume>();

//    private File volumeFile = null;
//    private File freeVolumeFile = null;
    private FileChannel wvfc = null;
    private FileChannel wfvfc = null;

    private FileChannel rvfc = null;
    private FileChannel rfvfc = null;

    private AtomicLong freeId = new AtomicLong(0);

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
            wvfc = new FileOutputStream(storeConfig.storeVolumeIndex, false).getChannel();
            wfvfc = new FileOutputStream(storeConfig.storeFreeVolumeIndex, false).getChannel();

            rvfc = new FileInputStream(storeConfig.storeVolumeIndex).getChannel();
            rfvfc = new FileInputStream(storeConfig.storeFreeVolumeIndex).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        parseFreeVolumeIndex();
        parseVolumeIndex();
    }

    private void parseFreeVolumeIndex() {
        //String buf = null;
        System.out.println(storeConfig.storeFreeVolumeIndex);
        List<String> bufLines = new ArrayList<String>();
        try {
            bufLines = IOUtils.readLines(new FileInputStream(storeConfig.storeFreeVolumeIndex));
            //buf = IOUtils.toString(new FileInputStream(storeConfig.storeFreeVolumeIndex));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(bufLines);

    }

    private void parseVolumeIndex() {

    }

    private void saveFreeVolumeIndex() {
        try {
            wfvfc = wfvfc.position(Const.FILE_START_POS);
            for (Volume v : freeVolumes) {
                wfvfc.write(ByteBuffer.wrap((v.getSupperBlock().getSupperBlockFilePath() + ","
                        + v.getIndex().getIndexFile() + "," + Const.VOLUME_FREE_ID).getBytes()));

                wfvfc.write(ByteBuffer.wrap("\n".getBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFreeVolume(int n, String bDir, String iDir) {
        for (int i = 0; i < n; i++) {
            freeId.incrementAndGet();

            Volume v = new Volume(Const.VOLUME_FREE_ID, bDir + Const.FREE_VOLUME_PREFIX + freeId.get(),
                iDir + Const.FREE_VOLUME_PREFIX + freeId.get() + Const.VOLUME_INDEX_EXT);


            freeVolumes.add(v);

            saveFreeVolumeIndex();
        }
    }
}
