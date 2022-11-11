package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.LocalLiveBean;
import com.hetun.datacenter.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class IndexController {


    IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping("index")
    public LiveItem index(@RequestHeader Map<String, String> header, @RequestBody(required = false) String requstbody) {

        if (!StringUtils.hasText(requstbody)) {
            LiveItem liveItem = new LiveItem();
            liveItem.setStatus(1);
            liveItem.setInfo("no request body");
            return liveItem;
        }
        header.remove("accept-encoding");
        header.put("origin", "http://www.515.tv");
        header.put("host", "www.515.tv");
        header.put("X-Requested-With", "XMLHttpRequest");
        return indexService.getIndex(header, requstbody);
    }

    @PostMapping("getVideoUrl")
    public String getVideoUrl(String id){
        return indexService.getVideoUrl(id);
    }

    @CrossOrigin
    @PostMapping("get_local_stream")
    public BaseBean<List<LocalLiveBean>> getLocalStream() {
        List<LocalLiveBean> localStream = indexService.getLocalStream();
        return new BaseBean.Builder().build(localStream);
    }


    @CrossOrigin
    @PostMapping("/insert_stream")
    public BaseBean<Integer> insertLiveStream(@RequestBody LocalLiveBean localLiveBean) {
        return indexService.insertLiveStream(localLiveBean);
    }

    @CrossOrigin
    @PostMapping("/delete_stream")
    public BaseBean<Integer> deleteLiveStream(@RequestBody LocalLiveBean localLiveBean) {
        return indexService.deleteLiveStream(localLiveBean);
    }


    @CrossOrigin
    @PostMapping("/get_iframe_link_byid")
    public String getIframeLinkById(@RequestParam("id")String id) {
        return indexService.getIframeLinkById(id);
    }
}
