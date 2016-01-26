package org.miaohong.jbfs.store.needle;

import java.nio.ByteBuffer;

/**
 * Created by miaohong on 16/1/13.
 */

// Needle stored int super block, aligned to 8bytes.
//
// needle file format:
//  ---------------
// | super   block |
//  ---------------
// |     needle    |		   ----------------
// |     needle    |          |  magic (int32) |
// |     needle    | ---->    |  cookie (int32)|
// |     needle    |          |  key (int64)   |
// |     needle    |          |  flag (byte)   |
// |     needle    |          |  size (int32)  |
// |     needle    |          |  data (bytes)  |
// |     needle    |          |  magic (int32) |
// |     needle    |          | checksum(int32)|
// |     needle    |          | padding (bytes)|
// |     ......    |           ----------------
// |     ......    |             int bigendian
//
// field     | explanation
// ---------------------------------------------------------
// magic     | header magic number used for checksum
// cookie    | random number to mitigate brute force lookups
// key       | 64bit photo id
// flag      | signifies deleted status
// size      | data size
// data      | the actual photo data
// magic     | footer magic number used for checksum
// checksum  | used to check integrity
// padding   | total needle size is aligned to 8 bytes

public class Needle {
    private byte[] headerMagic = {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78};
    private int cookie;
    private long key;
    private byte flag;
    private int size;
    private byte[] data;
    private byte[] footerMagic = {(byte)0x87, (byte)0x65, (byte)0x43, (byte)0x21};
    private int checksum;
    private byte[] padding;
    private int paddingSize;
    private int totalSize; // total needle write size
    // used in peek
    private int dataSize; // data-part size
    private int incrOffset;

    private int vid;

    public Needle() {
        
    }

    public Needle(int vid, int key, String cookie, long size, byte[] buf) {
        this.vid = vid;
        this.key = key;
        this.cookie = Integer.parseInt(cookie);
        this.data = buf;
        this.size = (int) size;
    }

    public byte[] getHeaderMagic() {
        return headerMagic;
    }

    public void setHeaderMagic(byte[] headerMagic) {
        this.headerMagic = headerMagic;
    }

    public int getCookie() {
        return cookie;
    }

    public void setCookie(int cookie) {
        this.cookie = cookie;
    }

    public long getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getFooterMagic() {
        return footerMagic;
    }

    public void setFooterMagic(byte[] footerMagic) {
        this.footerMagic = footerMagic;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public byte[] getPadding() {
        return padding;
    }

    public void setPadding(byte[] padding) {
        this.padding = padding;
    }

    public int getPaddingSize() {
        return paddingSize;
    }

    public void setPaddingSize(int paddingSize) {
        this.paddingSize = paddingSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getIncrOffset() {
        return incrOffset;
    }

    public void setIncrOffset(int incrOffset) {
        this.incrOffset = incrOffset;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }


    private void WriteHeader() {

    }


    public void parseHeader() {

    }

    public ByteBuffer buildNeedleHeaderMeta() {
        ByteBuffer bb = ByteBuffer.allocate(NeedleConst._headerSize);
        bb.put(headerMagic);
        bb.putInt(cookie);
        bb.putLong(key);
        bb.put(NeedleConst.FLAG_OK);
        bb.putInt(size);

        return bb;
    }

    public ByteBuffer buildNeedleFooterMeta() {
        ByteBuffer bb = ByteBuffer.allocate(NeedleConst._footerSize);


        return bb;
    }

}
