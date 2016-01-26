package org.miaohong.jbfs.store.store;

/**
 * Created by miaohong on 16/1/14.
 */
public class StoreConst {
    public static final String SEPARATOR = ",";
    public static final int VOLUME_FREE_ID = -1;
    public static final String FREE_VOLUME_PREFIX = "/_free_block_";
    public static final String VOLUME_INDEX_EXT   = ".idx";

    public static final int FILE_START_POS = 0;

    public static final long NEEDLE_MAX_SIZE = 30 * 1024 * 1024;
}
