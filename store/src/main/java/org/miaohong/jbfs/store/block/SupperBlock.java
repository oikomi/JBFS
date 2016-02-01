package org.miaohong.jbfs.store.block;

import org.miaohong.jbfs.store.needle.Needle;
import org.miaohong.jbfs.store.needle.NeedleConst;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<Long, Long> needleOffsetMap = new ConcurrentHashMap<Long, Long>();
    private Map<Long, Integer> needleSizeMap = new ConcurrentHashMap<Long, Integer>();

    private String supperBlockFilePath;

    private long totalWriteSize = 0;

    private FileInputStream rf = null;
    private FileOutputStream wf = null;

    public SupperBlock(String supperBlockFilePath) {
        this.supperBlockFilePath = supperBlockFilePath;
        init();
    }

    public Map<Long, Integer> getNeedleSizeMap() {
        return needleSizeMap;
    }

    public void setNeedleSizeMap(Map<Long, Integer> needleSizeMap) {
        this.needleSizeMap = needleSizeMap;
    }

    public Map<Long, Long> getNeedleOffsetMap() {
        return needleOffsetMap;
    }

    public void setNeedleOffsetMap(Map<Long, Long> needleOffsetMap) {
        this.needleOffsetMap = needleOffsetMap;
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
            rf = new FileInputStream(supperBlockFilePath);
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

    private int getAlignedOffset() {
        return (int) (8 - totalWriteSize % 8);
    }

    private void writeSupperBlockHeader() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(SuperBlockConst.SUPER_BLOCK_HEADER_SIZE);
        bb.order(ByteOrder.BIG_ENDIAN);

        bb.put(magic);
        bb.put(ver);
        bb.put(padding);

        totalWriteSize += SuperBlockConst.SUPER_BLOCK_HEADER_SIZE;

        wf.write(bb.array());
        wf.flush();
    }


    private void writeNeedle(Needle needle) throws IOException {
        wf.write(needle.buildNeedleHeaderMeta().array());

        totalWriteSize += NeedleConst._headerSize;

        wf.write(needle.getData());

        needleOffsetMap.put(needle.getKey(), totalWriteSize);

        totalWriteSize += needle.getSize();

        wf.write(needle.buildNeedleFooterMeta().array());
        totalWriteSize += NeedleConst._footerSize;

        wf.write(new byte[getAlignedOffset()]);

        totalWriteSize += getAlignedOffset();

        needleSizeMap.put(needle.getKey(), needle.getSize());

        wf.flush();
    }

    public byte[] getNeedle(long key, int cookie) {
        if (needleOffsetMap.get(key) != null) {
            byte[] buf = new byte[needleSizeMap.get(key)];

            try {
                rf.skip(needleOffsetMap.get(key));

                rf.read(buf);

                return buf;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public synchronized void addNeedle(Needle needle) throws IOException {
        writeNeedle(needle);
    }

    public void close() {
        try {
            if (rf != null) {
                rf.close();
            }

            if (wf != null) {
                wf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
