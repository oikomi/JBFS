package org.miaohong.jbfs.store.block;

/**
 * Created by baidu on 16/1/13.
 */
public class Const {
    public static int SUPER_BLOCK_HEADER_SIZE = 8;

    public static int SUPER_BLOCK_MAGIC_SIZE = 4;
    public static int SUPER_BLOCK_VER_SIZE = 1;

    public static int SUPER_BLOCK_PADDING_SIZE = SUPER_BLOCK_HEADER_SIZE -
            SUPER_BLOCK_MAGIC_SIZE - SUPER_BLOCK_VER_SIZE;

}
