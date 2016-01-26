package org.miaohong.jbfs.store.needle;

/**
 * Created by miaohong on 16/1/26.
 */
public class NeedleConst {

    // size
    // footer
    public static final int _magicSize  = 4;
    public static final int _cookieSize = 4;
    public static final int _keySize    = 8;
    public static final int _flagSize   = 1;
    public static final int _sizeSize   = 4;
    // data
    // footer
    // magic
    public static final int _checksumSize = 4;

    public static final int _headerSize = _magicSize + _cookieSize + _keySize + _flagSize + _sizeSize;
    // footer is constant = 8 (no padding)
    public static final int _footerSize = _magicSize + _checksumSize;

    public static final byte FLAG_OK  = (byte)0x00;
    public static final byte FLAG_DEL = (byte)0x01;

}
