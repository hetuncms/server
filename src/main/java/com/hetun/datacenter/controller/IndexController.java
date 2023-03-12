package com.hetun.datacenter.controller;

import com.hetun.datacenter.bean.*;
import com.hetun.datacenter.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class IndexController {
    final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("index")
    public BaseBean<List<LiveItem>> index(@RequestParam("liveType") Integer type,@RequestParam(value = "date",required = false) String date,
                                          @RequestParam("page") Integer page,
                                          @RequestParam(required = false,name = "limit") Integer limit) {
        return indexService.getIndex(type,date,page,limit);
    }
    @GetMapping("get_data_and_date")
    public BaseListDateBean<List<LiveItem>> getDataAndDate(@RequestParam("liveType") Integer type,
                                                            @RequestParam(value = "date",required = false) String date,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam(required = false,name = "limit") Integer limit) {
        List<Date> allDate = indexService.getAllDate(type);
        BaseListBean<List<LiveItem>> index = indexService.getIndex(type, date, page, limit);
        List<LiveItem> data = index.getData();
        int totalPages = index.getTotalPages();
        BaseListDateBean baseListDateBean = new BaseListDateBean();
        baseListDateBean.setGetAllDate(allDate);

        if ((data==null || data.isEmpty()) && !allDate.isEmpty()) {
            data = indexService.getIndex(type, new SimpleDateFormat("yyyy-MM-dd").format(allDate.get(0)), page, limit).getData();
        }
        baseListDateBean.setData(data);
        baseListDateBean.setMsg(index.getMsg());
        baseListDateBean.setCode(index.getCode());
        baseListDateBean.setTotalPages(totalPages);
        return baseListDateBean;
    }
    @GetMapping("getPlayInfo")
    public PlayInfoBean getPlayInfo(@RequestParam("matchId") Integer id){
        return indexService.getPlayInfo(id);
    }
    @GetMapping("getLiveItem")
    public LiveItem getLiveItem(@RequestParam("liveId") Long liveId){
        return indexService.getLiveItem(liveId);
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

}
