package org.miaohong.jbfs.directory.server.service;

import org.miaohong.jbfs.directory.directory.Directory;
import org.miaohong.jbfs.directory.server.mapper.NeedleMetaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by miaohong on 16/2/4.
 */

@Service
public class NeedleMetaService {

    public static Directory directory;
    static {
        directory = Directory.getInstance();
    }

    @Autowired
    NeedleMetaMapper dao;

    public void uploadService() {

        long key = 1;
        dao.getNeedleByKey(key);
        directory.getWritableStores();
    }

}
