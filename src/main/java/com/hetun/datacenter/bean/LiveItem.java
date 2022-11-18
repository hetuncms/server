package com.hetun.datacenter.bean;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@JsonIgnoreProperties(value = {"t", "a", "o", "g"}, allowSetters = true)
public class LiveItem {
    private int status;
    private String g;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @JSONField(name = "messs")
    private String info;
    @JSONField(name = "c")
    private List<Item> live_item;
    private HashMap<String, RelationT> t;
    private HashMap<String, RelationA> a;
    private HashMap<String, RelationO> o;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getG() {
        return this.g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public List<Item> getLive_item() {
        return this.live_item;
    }

    public void setLive_item(List<Item> live_item) {
        this.live_item = live_item;
    }

    public HashMap<String, RelationT> getT() {
        return this.t;
    }

    public void setT(HashMap<String, RelationT> t) {
        this.t = t;
    }

    public HashMap<String, RelationA> getA() {
        return this.a;
    }

    public void setA(HashMap<String, RelationA> a) {
        this.a = a;
    }

    public HashMap<String, RelationO> getO() {
        return this.o;
    }

    public void setO(HashMap<String, RelationO> o) {
        this.o = o;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @JsonIgnoreProperties(value = {"h", IntegerTokenConverter.CONVERTER_KEY, "c"}, allowSetters = true)
    public static class Item {
        @JSONField(name = "aw")
        private Integer id;
        @JSONField(name = BeanUtil.PREFIX_GETTER_IS)
        private String playid;
        @JSONField(name = "co")
        private Integer liveType;
        @JSONField(name = "h")
        private String iframeLink;
        private String h;
        @JSONField(name = "a")
        private String title;
        @JSONField(name = IntegerTokenConverter.CONVERTER_KEY)
        private String i;
        @JSONField(name = "c")
        private String c;
        @JSONField(name = "t")
        private Date date;
        @JSONField(name = ANSIConstants.ESC_END)
        private String isTop;
        private String leftName;
        private String rightName;
        private String leftImg;
        private String rightImg;
        private String gameName;

        public Item() {
        }

        public String getIframeLink() {
            return this.iframeLink;
        }

        public void setIframeLink(String iframeLink) {
            this.iframeLink = iframeLink;
        }

        public String getIsTop() {
            return this.isTop;
        }

        public void setIsTop(String isTop) {
            this.isTop = isTop;
        }

        public Date getDate() {
            return this.date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getGameName() {
            return this.gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getLeftImg() {
            return this.leftImg;
        }

        public void setLeftImg(String leftImg) {
            this.leftImg = leftImg;
        }

        public String getRightImg() {
            return this.rightImg;
        }

        public void setRightImg(String rightImg) {
            this.rightImg = rightImg;
        }

        public String getLeftName() {
            return this.leftName;
        }

        public void setLeftName(String leftName) {
            this.leftName = leftName;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPlayid() {
            return this.playid;
        }

        public void setPlayid(String playid) {
            this.playid = playid;
        }

        public Integer getLiveType() {
            return this.liveType;
        }

        public void setLiveType(Integer liveType) {
            this.liveType = liveType;
        }

        public String getH() {
            return this.h;
        }

        public void setH(String h) {
            this.h = h;
        }


        public String getI() {
            return this.i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getC() {
            return this.c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRightName() {
            return this.rightName;
        }

        public void setRightName(String rightName) {
            this.rightName = rightName;
        }
    }

    public class RelationA {
        private String n;
        private String i;
        private String l;

        public RelationA() {
        }

        public String getN() {
            return this.n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getI() {
            return this.i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getL() {
            return this.l;
        }

        public void setL(String l) {
            this.l = l;
        }
    }

    public class RelationT {
        private String l;
        private String i;
        private String s;

        public RelationT() {
        }

        public String getL() {
            return this.l;
        }

        public void setL(String l) {
            this.l = l;
        }

        public String getI() {
            return this.i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getS() {
            return this.s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }

    public class RelationO {
        private String i;
        private String n;

        public RelationO() {
        }

        public String getI() {
            return this.i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return this.n;
        }

        public void setN(String n) {
            this.n = n;
        }
    }
}
