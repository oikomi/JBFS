package org.miaohong.jbfs.store.server.controller;

import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.store.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by miaohong on 16/1/18.
 */

@Controller
@RequestMapping("/store/v1/op/")
public class StoreController {
    public static Store store;

    @PostConstruct
    public void init() {
        store = Store.getInstance();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("vid") int vid, @RequestParam("key") int key, @RequestParam("cookie") String cookie
                       , @RequestParam("file") MultipartFile file) {


        try {
            store.upload(vid, key, cookie, file.getSize(), file.getBytes());
        } catch (StoreAdminException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
