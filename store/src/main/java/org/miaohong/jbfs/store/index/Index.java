package org.miaohong.jbfs.store.index;

/**
 * Created by baidu on 16/1/14.
 */
public class Index {
    private String indexFile;

    public Index(String iFile) {
        this.indexFile = iFile;
    }

    public String getIndexFile() {
        return indexFile;
    }

    public void setIndexFile(String indexFile) {
        this.indexFile = indexFile;
    }
}
