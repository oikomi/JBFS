package org.miaohong.jbfs.store.needle;

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
    private byte[] headerMagic;
    private int cookie;
    private int Key;
    private byte flag;
    private int size;
    private byte[] data;
    private byte[] footerMagic;
    private int checksum;
    private byte[] padding;
    private int paddingSize;
    private int totalSize; // total needle write size
    // used in peek
    private int dataSize; // data-part size
    private int incrOffset;


    public void parseHeader() {

    }

}
