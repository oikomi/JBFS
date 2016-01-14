package org.miaohong.jbfs.store.block;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

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
    private byte[] magic = {(byte)0xab, (byte)0xcd, (byte)0xef, (byte)0x00};
    private byte ver = (byte)0x01;
    private byte[] padding = new byte[Const.SUPER_BLOCK_PADDING_SIZE];
    private String supperBlockFilePath;
    private FileChannel fc = null;

    public SupperBlock(String supperBlockFilePath) {
        this.supperBlockFilePath = supperBlockFilePath;
    }


    public void scan() {

    }

    public void writeSupperBlockHeader() throws IOException {
        fc = new FileOutputStream(supperBlockFilePath, true).getChannel();
        fc.write(ByteBuffer.wrap(magic));
        fc.write(ByteBuffer.wrap(new byte[]{ver}));
        fc.write(ByteBuffer.wrap(padding));
        fc.close();
    }
}
