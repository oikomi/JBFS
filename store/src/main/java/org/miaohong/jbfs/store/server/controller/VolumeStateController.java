package org.miaohong.jbfs.store.server.controller;

import com.alibaba.fastjson.JSON;
import org.miaohong.jbfs.store.server.common.Utils;
import org.miaohong.jbfs.store.server.model.StoreAdminResp;
import org.miaohong.jbfs.store.server.model.VolumeState;
import org.miaohong.jbfs.store.store.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miaohong on 16/2/20.
 */

@Controller
@RequestMapping("/store/v1/volume/")
public class VolumeStateController {
    public static Store store;

    static {
        store = Store.getInstance();
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public void state(@RequestParam("volume") String volume, HttpServletRequest req, HttpServletResponse resp) {
        VolumeState volumeState = new VolumeState();


        Utils.printJson(resp, JSON.toJSONString(volumeState));
    }
}
