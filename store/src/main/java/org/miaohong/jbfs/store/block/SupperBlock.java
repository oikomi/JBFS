package org.miaohong.jbfs.store.block;

/**
 * Created by miaohong on 16/1/13.
 */
// Super block has a header.
// super block header format:
//  --------------
// | magic number |   ---- 4bytes
// | version      |   ---- 1byte
// | padding      |   ---- aligned with needle padding size (for furtuer  used)
//  --------------
//

public class SupperBlock {
    private byte[] magic;
    private byte ver;
    private byte[] padding;



    public void Scan() {
        
    }
}
