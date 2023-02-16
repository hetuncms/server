package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.BaseBean;
import com.hetun.datacenter.bean.LiveItem;
import com.hetun.datacenter.bean.PlayInfoBean;
import com.hetun.datacenter.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IndexController {
    IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("index")
    public BaseBean<List<LiveItem>> index(@RequestParam("liveType") Integer type, @RequestParam("page") Integer page,
                                          @RequestParam(required = false,name = "limit") Integer limit) {
        return indexService.getIndex(type,page,limit);
    }

    @GetMapping("getPlayInfo")
    public PlayInfoBean getPlayInfo(@RequestParam("matchId") Integer id){
        return indexService.getPlayInfo(id);
    }
    @GetMapping("getLiveItem")
    public LiveItem getLiveItem(@RequestParam("liveId") Long liveId){
        return indexService.getLiveItem(liveId);
    }
    @PostMapping("getVideoUrl")
    public String getVideoUrl(String id) {
        return "aaa";
    }

//    @CrossOrigin
//    @PostMapping("get_local_stream")
//    public BaseBean<List<LocalLiveBean>> getLocalStream() {
//        List<LocalLiveBean> localStream = indexService.getLocalStream();
//        return new BaseBean.Builder().build(localStream);
//    }
//
//
//    @CrossOrigin
//    @PostMapping("/insert_stream")
//    public BaseBean<Integer> insertLiveStream(@RequestBody LocalLiveBean localLiveBean) {
//        return indexService.insertLiveStream(localLiveBean);
//    }
//
//    @CrossOrigin
//    @PostMapping("/delete_stream")
//    public BaseBean<Integer> deleteLiveStream(@RequestBody LocalLiveBean localLiveBean) {
//        return indexService.deleteLiveStream(localLiveBean);
//    }
//

    @CrossOrigin
    @PostMapping("/get_iframe_link_byid")
    public String getIframeLinkById(@RequestParam("id")String id) {
        return indexService.getIframeLinkById(id);
    }
}
