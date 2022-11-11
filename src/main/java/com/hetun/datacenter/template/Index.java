package com.hetun.datacenter.template;

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

    @GetMapping("bofang/{id}")
    public String index(@PathVariable("id") Integer id, Model model){
        String liveLink = indexService.findLiveById(id);
        if (liveLink == null) {
            return "player";
        }
        model.addAttribute("playurl",liveLink);
        return "player";
    }
}
