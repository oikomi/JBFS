package org.miaohong.jbfs.store.block;

import java.nio.ByteBuffer;

/**
 * Created by miaohong on 16/1/13.
 */
// Super block has a header.
// super block header format:
//  --------------
// | magic number |   ---- 4bytes
// | version      |   ---- 1byte
// | padding      |   ---- aligned with needle padding size (for future used)
//  --------------
//

public class SupperBlock {
    private byte[] magic;
    private byte ver;
    private byte[] padding;

    public SupperBlock() {

    }


    public void Scan() {

    }


    public void writeSupperBlockMeta() {
        ByteBuffer buffer = ByteBuffer.allocate(256);

    }
}
