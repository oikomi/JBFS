package org.miaohong.jbfs.directory.server.controller;

import org.miaohong.jbfs.directory.directory.Directory;
import org.miaohong.jbfs.directory.server.service.NeedleMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by miaohong on 16/2/2.
 */

@Controller
@RequestMapping("/directory/v1/op/")
public class DirectoryController {
    @Autowired
    NeedleMetaService needleMetaService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get(@RequestParam("key") String key, @RequestParam("cookie") String cookie,
                             HttpServletRequest req, HttpServletResponse resp) {


    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletRequest req, HttpServletResponse resp) {

        needleMetaService.uploadService();

    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public void del(@RequestParam("key") String key, @RequestParam("cookie") String cookie,
                       HttpServletRequest req, HttpServletResponse resp) {



    }
}
