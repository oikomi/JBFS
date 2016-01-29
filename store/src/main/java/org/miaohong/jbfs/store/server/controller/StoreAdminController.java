package org.miaohong.jbfs.store.server.controller;

import com.alibaba.fastjson.JSON;
import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.server.common.Utils;
import org.miaohong.jbfs.store.server.model.StoreAdminResp;
import org.miaohong.jbfs.store.store.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by miaohong on 16/1/13.
 */

@Controller
@RequestMapping("/store/v1/admin/")
public class StoreAdminController {
    public static Store store;

    static {
        store = Store.getInstance();
    }

//    @PostConstruct
//    public void init() {
//        store = Store.getInstance();
//    }

    @RequestMapping(value = "/add_free_volume", method = RequestMethod.POST)
    public void addFreeVolume(@RequestParam("n") int n, @RequestParam("bdir") String bdir,
                              @RequestParam("idir") String idir, HttpServletRequest req, HttpServletResponse resp) {
        StoreAdminResp storeAdminResp = new StoreAdminResp();

        try {
            store.addFreeVolume(n, bdir, idir);
            storeAdminResp.setCode(0);
            storeAdminResp.setMsg("ok");
        } catch (IOException e) {
            e.printStackTrace();
            storeAdminResp.setCode(-1);
            storeAdminResp.setMsg(e.toString());
        }

        Utils.printJson(resp, JSON.toJSONString(storeAdminResp));
    }

    @RequestMapping(value = "/add_volume", method = RequestMethod.POST)
    public void addVolume(@RequestParam("vid") int vid, HttpServletRequest req, HttpServletResponse resp) {
        StoreAdminResp storeAdminResp = new StoreAdminResp();

        try {
            store.addVolume(vid);
            storeAdminResp.setCode(0);
            storeAdminResp.setMsg("ok");
        } catch (IOException e) {
            storeAdminResp.setCode(-1);
            storeAdminResp.setMsg(e.toString());
            e.printStackTrace();
        } catch (StoreAdminException.StoreVolumeExistException e) {
            e.printStackTrace();
        } catch (StoreAdminException.StoreNoFreeVolumeException e) {
            e.printStackTrace();
        } catch (StoreAdminException.StoreFreeVolumeNotExistException e) {
            e.printStackTrace();
        }

        Utils.printJson(resp, JSON.toJSONString(storeAdminResp));
    }

}
