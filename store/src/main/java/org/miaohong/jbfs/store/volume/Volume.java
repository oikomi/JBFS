package org.miaohong.jbfs.store.volume;

import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.index.Index;
import org.miaohong.jbfs.store.needle.Needle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by miaohong on 16/1/13.
 */
public class Volume {
    private int id;
    private SupperBlock supperBlock;
    private Index index;

    public Volume(int id, String bFile, String iFile) {
        this.id = id;
        this.supperBlock = new SupperBlock(bFile);
        this.index = new Index(iFile);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SupperBlock getSupperBlock() {
        return supperBlock;
    }

    public void setSupperBlock(SupperBlock supperBlock) {
        this.supperBlock = supperBlock;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }


    public void addNeedle(Needle needle) throws IOException {
        supperBlock.addNeedle(needle);
    }

    public byte[] getNeedle(long key, int cookie) {
        return supperBlock.getNeedle(key, cookie);
    }

    public void close() {
        supperBlock.close();
        index.close();
    }

    @Override
    public String toString() {
        return "Volume: id = " + id + " supperBlock = " + supperBlock.getSupperBlockFilePath()
                + " index = " + index.getIndexFile();
    }
}
