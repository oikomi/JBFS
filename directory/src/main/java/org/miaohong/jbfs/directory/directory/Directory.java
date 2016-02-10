package org.miaohong.jbfs.directory.directory;

import org.miaohong.jbfs.directory.config.DirectoryConfig;
import org.miaohong.jbfs.snowflake.SnowFlake;

/**
 * Created by miaohong on 16/2/2.
 */
public class Directory {
    private DirectoryConfig directoryConfig = DirectoryConfig.getInstance();

    private SnowFlake snowFlake = new SnowFlake(directoryConfig.redisAddr);

    private Directory() {}

    private static class SingletonHolder {
        private static final Directory INSTANCE = new Directory();
    }

    public static final Directory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getWritableStores() {
        long key = snowFlake.getLongUUID();

        //System.out.println(key);

    }

}
