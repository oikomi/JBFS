package org.miaohong.jbfs.store.index;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by miaohong on 16/1/14.
 */
public class Index {
    private String indexFilePath;
    private FileChannel fc = null;

    public Index(String indexFilePath) {
        this.indexFilePath = indexFilePath;
        init();
    }

    public String getIndexFile() {
        return indexFilePath;
    }

    public void setIndexFile(String indexFilePath) {
        this.indexFilePath = indexFilePath;
    }


    private void init() {
        try {
            fc = new FileOutputStream(indexFilePath, true).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void close() {

    }
}
