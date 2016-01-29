package org.miaohong.jbfs.store.zk;

/**
 * Created by miaohong on 16/1/28.
 */
public class ZkStoreVolumeData {
    private String superBlock;
    private String index;
    private int id;

    public ZkStoreVolumeData(String superBlock, String index, int id) {
        this.superBlock = superBlock;
        this.index = index;
        this.id = id;
    }

    public String getSuperBlock() {
        return superBlock;
    }

    public void setSuperBlock(String superBlock) {
        this.superBlock = superBlock;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
