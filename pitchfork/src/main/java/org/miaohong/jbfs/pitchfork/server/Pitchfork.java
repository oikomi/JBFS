package org.miaohong.jbfs.pitchfork.server;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.miaohong.jbfs.pitchfork.config.PitchforkConfig;
import org.miaohong.jbfs.pitchfork.meta.StoreMeta;
import org.miaohong.jbfs.pitchfork.task.UpdateVolumeMetaTask;
import org.miaohong.jbfs.zookeeper.conn.ZKUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by miaohong on 16/2/10.
 */
public class Pitchfork {
    private Gson gson = new Gson();
    private PitchforkConfig pitchforkConfig = PitchforkConfig.getInstance();
    private ZKUtils zkUtils = null;
    private Map<String, StoreMeta> storeMetaMap = new ConcurrentHashMap<String, StoreMeta>();

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

    private void updateStoreMeta() {
        List<String> racks = null;
        try {
            racks = zkUtils.watchedGetChildren(pitchforkConfig.zkStoreRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String rack : racks) {
            System.out.println(rack);
            List<String> stores = zkUtils.getChild(pitchforkConfig.zkStoreRoot + "/" + rack);

            for (String store : stores) {
                byte[] storeBytes = zkUtils.getData(pitchforkConfig.zkStoreRoot + "/" + rack + "/" + store);
                storeMetaMap.put(store, gson.fromJson(new String(storeBytes), StoreMeta.class));
            }
            System.out.println(storeMetaMap);
        }

    }

    private List<String> getVolume(String rack, String store) {
        String volumePath = pitchforkConfig.zkStoreRoot + "/" + rack + "/" + store;

        return zkUtils.getChild(volumePath);
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

    private void getVolumeState(String volume) {
        //String url = "http://" + storeMetaMap.get()
    }

    private void setVolumeState(String volume) {
        String volumePath = pitchforkConfig.zkVolumeRoot + "/" + volume;

        getVolumeState(volume);
    }

    private void watchStores() {
        List<String> racks = watchRacks();

        for (String rack : racks) {
            // System.out.println(rack);
            List<String> stores = getStores(rack);

            for (String store : stores) {
                // System.out.println(store);

                List<String> volumes = getVolume(rack, store);

                for (String volume : volumes) {
                    System.out.println(volume);

                    setVolumeState(volume);
                }
            }
        }
    }

    private void UpdateVolumeMeta() {
        watchStores();
    }

    private void doUpdate() {
        updateStoreMeta();
        UpdateVolumeMeta();
        // threadPool.submit(new UpdateVolumeMetaTask());
    }

    public void run() {
        init();

        ExecutorService pool = Executors.newFixedThreadPool(2);
        final PathChildrenCache childrenCache = new PathChildrenCache(zkUtils.getClient(),
                "/test", true);
        try {
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            childrenCache.getListenable().addListener(
                    new PathChildrenCacheListener() {
                        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
                                throws Exception {
                            // System.out.println("childEvent");
                            switch (event.getType()) {
                                case CHILD_ADDED:
                                    System.out.println("CHILD_ADDED: " + event.getData().getPath());
                                    doUpdate();
                                    break;
                                case CHILD_REMOVED:
                                    System.out.println("CHILD_REMOVED: " + event.getData().getPath());
                                    doUpdate();
                                    break;
                                case CHILD_UPDATED:
                                    System.out.println("CHILD_UPDATED: " + event.getData().getPath());
                                    doUpdate();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }, pool
            );


        }

        //System.out.println("run end");
        //updateStoreMeta();
        //threadPool.submit(new UpdateVolumeMetaTask());

    }
}
