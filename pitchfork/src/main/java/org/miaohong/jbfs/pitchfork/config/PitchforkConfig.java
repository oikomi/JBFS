package org.miaohong.jbfs.pitchfork.config;

import com.google.common.io.Closeables;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by miaohong on 16/1/13.
 */
public class PitchforkConfig {
    private static ClassLoader classLoader = PitchforkConfig.class.getClassLoader();
    private static Properties propertie = new Properties();

    private static final String configFilePath = "config/directory_config.properties";

    public static PitchforkConfig _instance = new PitchforkConfig();

    public static String zkPitchforkRoot;
    public static String zkStoreRoot;
    public static String zkVolumeRoot;

    public static String redisAddr;

    public static String zookeeperAddrs;
    public static int zookeeperTimeout;

    private PitchforkConfig() {

    }

    static {
        FileInputStream configInputStream = null;
        try {
            configInputStream = new FileInputStream(classLoader.getResource(configFilePath).getFile());
            propertie.load(configInputStream);

            Set keyValue = propertie.keySet();
            for (Iterator it = keyValue.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (key.equals("zk.store.root")) {
                    _instance.zkStoreRoot = (String) propertie.get(key);
                } else if (key.equals("zk.volume.root")) {
                    _instance.zkVolumeRoot = (String) propertie.get(key);
                } else if (key.equals("zk.pitchfork.root")) {
                    _instance.zkPitchforkRoot = (String) propertie.get(key);
                } else if (key.equals("redis.addr")) {
                    _instance.redisAddr = (String) propertie.get(key);
                } else if (key.equals("zookeeper.addrs")) {
                    _instance.zookeeperAddrs = (String) propertie.get(key);
                } else if (key.equals("zookeeper.timeout")) {
                    _instance.zookeeperTimeout = Integer.parseInt((String) propertie.get(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(configInputStream);
        }
    }

    public static PitchforkConfig getInstance() {
        return _instance;
    }

}
