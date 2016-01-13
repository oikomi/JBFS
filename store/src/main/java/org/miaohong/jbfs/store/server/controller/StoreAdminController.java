package org.miaohong.jbfs.store.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by miaohong on 16/1/13.
 */

@Controller
@RequestMapping("/store/v1/admin/")
public class StoreAdminController {

    @RequestMapping(value = "/add_free_volume", method = RequestMethod.POST)
    public void addFreeVolume() {

    }

}
