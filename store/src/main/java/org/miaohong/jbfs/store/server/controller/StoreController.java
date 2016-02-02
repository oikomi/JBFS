package org.miaohong.jbfs.store.server.controller;

import org.miaohong.jbfs.store.exception.StoreAdminException;
import org.miaohong.jbfs.store.store.Store;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
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

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public void upload(@RequestParam("vid") String vid, @RequestParam("key") String key,
//                       @RequestParam("cookie") String cookie, @RequestParam("file") MultipartFile file) {
//        System.out.println("upload");
//        try {
//            store.upload(Integer.parseInt(vid), Integer.parseInt(key), cookie, file.getSize(), file.getBytes());
//        } catch (StoreAdminException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
        String vid = req.getParameter("vid");
        String key = req.getParameter("key");
        String cookie = req.getParameter("cookie");

        //System.out.println("upload");
        try {
            store.upload(Integer.parseInt(vid), Long.parseLong(key), cookie, file.getSize(), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StoreAdminException.NeedleTooLargeException e) {
            e.printStackTrace();
        } catch (StoreAdminException.VolumeNotExistException e) {
            e.printStackTrace();
        } catch (StoreAdminException.NeedleIsEmptyException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(@RequestParam("vid") int vid, @RequestParam("key") String key,
                    @RequestParam("cookie") String cookie, HttpServletRequest req, HttpServletResponse resp) {
        try {
            byte[] buf = store.get(vid, key, cookie);
//            BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
//            bos.write(buf);

            new FileOutputStream("/tmp/a").write(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StoreAdminException.VolumeNotExistException e) {
            e.printStackTrace();
        }

    }
}
