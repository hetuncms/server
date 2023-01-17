package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    IndexService indexService;

    @Autowired
    public AdminController(IndexService indexService) {
        this.indexService = indexService;
    }

    @CrossOrigin
    @GetMapping("get_all_stream")
    public BaseBean<List<LiveItem>> getAllStream() {
        BaseBean<List<LiveItem>> allIndex = indexService.getAllIndex();
        return allIndex;
    }

        @CrossOrigin
    @PostMapping("/insert_stream")
    public BaseBean<Integer> insertLiveStream(@RequestBody LiveItem liveItem) {
        return indexService.updateLiveItem(liveItem);
    }
}
