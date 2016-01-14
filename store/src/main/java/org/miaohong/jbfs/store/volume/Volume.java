package org.miaohong.jbfs.store.volume;

import org.miaohong.jbfs.store.block.SupperBlock;
import org.miaohong.jbfs.store.index.Index;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miaohong on 16/1/13.
 */
public class Volume {
    private SupperBlock supperBlock;
    private Index index;
    private Map<Integer, Integer> needles = new HashMap<Integer, Integer>();

    public Volume(int id, String bFile, String iFile) {
        this.supperBlock = new SupperBlock(bFile);
        this.index = new Index(iFile);
    }



}
