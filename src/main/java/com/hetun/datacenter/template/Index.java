package com.hetun.datacenter.template;

import com.hetun.datacenter.bean.LocalLiveBean;
import com.hetun.datacenter.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class Index {
    IndexService indexService;
    @Autowired
    public Index(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") Integer id, Model model){
        LocalLiveBean liveById = indexService.findLiveById(id);
        if (liveById == null) {
            return "player";
        }
        String liveLink = liveById.getLiveLink();
        model.addAttribute("playurl",liveLink);
        return "player";
    }
}
