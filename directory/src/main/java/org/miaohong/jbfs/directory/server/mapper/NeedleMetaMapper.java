package org.miaohong.jbfs.directory.server.mapper;

import org.miaohong.jbfs.directory.common.model.NeedleMeta;

/**
 * Created by miaohong on 16/2/4.
 */
public interface NeedleMetaMapper {
    public NeedleMeta getNeedleByKey(long key);

}
