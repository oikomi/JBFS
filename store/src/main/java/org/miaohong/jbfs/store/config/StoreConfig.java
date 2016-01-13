package org.miaohong.jbfs.store.config;

import com.google.gson.JsonSyntaxException;
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

    public static StoreConfig _instance;

    private static String storeVolumeIndex;
    private static String storeFreeVolumeIndex;
    private static String zookeeperAddrs;
    private static int zookeeperTimeout;

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
                } else if (key.equals("zookeeper.addrs")) {
                    _instance.zookeeperAddrs = (String) propertie.get(key);
                } else if (key.equals("zookeeper.timeout")) {
                    _instance.zookeeperTimeout = (Integer) propertie.get(key);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(configInputStream);
        }
    }

    public static StoreConfig getInstance() {
        return _instance;
    }

    public String getValue(String key) {
        if(propertie.containsKey(key)){
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
