package org.miaohong.jbfs.store.block;

import org.miaohong.jbfs.store.needle.Needle;
import org.miaohong.jbfs.store.needle.NeedleConst;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
    private byte[] padding = new byte[SuperBlockConst.SUPER_BLOCK_PADDING_SIZE];

    private String supperBlockFilePath;

    private long totalSize = 0;

    private FileOutputStream wf = null;

    public SupperBlock(String supperBlockFilePath) {
        this.supperBlockFilePath = supperBlockFilePath;
        init();
    }

    public String getSupperBlockFilePath() {
        return supperBlockFilePath;
    }

    public void setSupperBlockFilePath(String supperBlockFilePath) {
        this.supperBlockFilePath = supperBlockFilePath;
    }

    private void init() {
        try {
            wf = new FileOutputStream(supperBlockFilePath, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writeSupperBlockHeader();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void scan() {

    }

    private void writeSupperBlockHeader() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(SuperBlockConst.SUPER_BLOCK_HEADER_SIZE);
        bb.order(ByteOrder.BIG_ENDIAN);

        bb.put(magic);
        bb.put(ver);
        bb.put(padding);

        totalSize += SuperBlockConst.SUPER_BLOCK_HEADER_SIZE;

        wf.write(bb.array());
        wf.flush();
    }


    private void writeNeedle(Needle needle) throws IOException {
        wf.write(needle.buildNeedleHeaderMeta().array());

        totalSize += NeedleConst._headerSize;

        wf.write(needle.getData());
        totalSize += needle.getSize();
        
        wf.flush();
    }

    public void addNeedle(Needle needle) throws IOException {

        writeNeedle(needle);
    }
}
