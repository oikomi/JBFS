package org.miaohong.jbfs.store.config;

import com.google.common.io.Closeables;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by miaohong on 16/1/13.
 */
public class StoreConfig {
    private static ClassLoader classLoader = StoreConfig.class.getClassLoader();
    private static Properties propertie = new Properties();

    private static final String configFilePath = "config/store_config.properties";

    public static StoreConfig _instance = new StoreConfig();

    public static String storeVolumeIndex;
    public static String storeFreeVolumeIndex;
    public static String storeRack;
    public static String storeServerId;
    public static String storeState;
    public static String storeAdmin;
    public static String storeApi;

    public static String zookeeperAddrs;
    public static int zookeeperTimeout;

    private StoreConfig() {

    }

    public static int getZookeeperTimeout() {
        return zookeeperTimeout;
    }

    public static void setZookeeperTimeout(int zookeeperTimeout) {
        StoreConfig.zookeeperTimeout = zookeeperTimeout;
    }

    public static String getZookeeperAddrs() {
        return zookeeperAddrs;
    }

    public static void setZookeeperAddrs(String zookeeperAddrs) {
        StoreConfig.zookeeperAddrs = zookeeperAddrs;
    }

    public static String getStoreFreeVolumeIndex() {
        return storeFreeVolumeIndex;
    }

    public static void setStoreFreeVolumeIndex(String storeFreeVolumeIndex) {
        StoreConfig.storeFreeVolumeIndex = storeFreeVolumeIndex;
    }

    public static String getStoreVolumeIndex() {
        return storeVolumeIndex;
    }

    public static void setStoreVolumeIndex(String storeVolumeIndex) {
        StoreConfig.storeVolumeIndex = storeVolumeIndex;
    }

    public static String getStoreServerId() {
        return storeServerId;
    }

    public static void setStoreServerId(String storeServerId) {
        StoreConfig.storeServerId = storeServerId;
    }

    static {
        FileInputStream configInputStream = null;
        try {
            configInputStream = new FileInputStream(classLoader.getResource(configFilePath).getFile());
            propertie.load(configInputStream);

            Set keyValue = propertie.keySet();
            for (Iterator it = keyValue.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (key.equals("store.volume_index")) {
                    _instance.storeVolumeIndex = (String) propertie.get(key);
                } else if (key.equals("store.free_volume_index")) {
                    _instance.storeFreeVolumeIndex = (String) propertie.get(key);
                } else if (key.equals("store.rack")) {
                    _instance.storeRack = (String) propertie.get(key);
                } else if (key.equals("store.server_id")) {
                    _instance.storeServerId = (String) propertie.get(key);
                } else if (key.equals("store.state")) {
                    _instance.storeState = (String) propertie.get(key);
                } else if (key.equals("store.admin")) {
                    _instance.storeAdmin = (String) propertie.get(key);
                } else if (key.equals("store.api")) {
                    _instance.storeApi = (String) propertie.get(key);
                } else if (key.equals("zookeeper.addrs")) {
                    _instance.zookeeperAddrs = (String) propertie.get(key);
                } else if (key.equals("zookeeper.timeout")) {
                    _instance.zookeeperTimeout = Integer.parseInt((String)propertie.get(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(configInputStream);
        }
    }

    public static StoreConfig getInstance() {
        return _instance;
    }

    public String getValue(String key) {
        if(propertie.containsKey(key)) {
            String value = propertie.getProperty(key);
            return value;
        } else {
            return "";
        }
    }

    public void clear() {
        propertie.clear();
    }


    public static void main(String[] args) {
        StoreConfig storeConfig = StoreConfig.getInstance();

        System.out.println(storeConfig.getStoreVolumeIndex());
    }
}
