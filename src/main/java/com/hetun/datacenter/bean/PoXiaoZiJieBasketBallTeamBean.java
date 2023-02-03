/**
 * Copyright 2023 bejson.com
 */
package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;


/**
 * Auto-generated: 2023-01-31 12:49:0
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PoXiaoZiJieBasketBallTeamBean {

    private int code;
    private String message;
    private List<Result> result;
    private long time_stamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Entity
    @Table(name = "basketball_team")
    public static class Result {
        @Id
        private int id;
        private String name_en;
        private String name_zh;
        private String name_zh_tra;
        private String pic;
        private String name_en_abbr;
        private String name_zh_abbr;
        private String name_zh_tra_abbr;
        private String coach_zh;
        private String coach_en;
        private String arena_en;
        private String arena_zh;
        private String capacity;
        private String city;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName_zh() {
            return name_zh;
        }

        public void setName_zh(String name_zh) {
            this.name_zh = name_zh;
        }

        public String getName_zh_tra() {
            return name_zh_tra;
        }

        public void setName_zh_tra(String name_zh_tra) {
            this.name_zh_tra = name_zh_tra;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName_en_abbr() {
            return name_en_abbr;
        }

        public void setName_en_abbr(String name_en_abbr) {
            this.name_en_abbr = name_en_abbr;
        }

        public String getName_zh_abbr() {
            return name_zh_abbr;
        }

        public void setName_zh_abbr(String name_zh_abbr) {
            this.name_zh_abbr = name_zh_abbr;
        }

        public String getName_zh_tra_abbr() {
            return name_zh_tra_abbr;
        }

        public void setName_zh_tra_abbr(String name_zh_tra_abbr) {
            this.name_zh_tra_abbr = name_zh_tra_abbr;
        }

        public String getCoach_zh() {
            return coach_zh;
        }

        public void setCoach_zh(String coach_zh) {
            this.coach_zh = coach_zh;
        }

        public String getCoach_en() {
            return coach_en;
        }

        public void setCoach_en(String coach_en) {
            this.coach_en = coach_en;
        }

        public String getArena_en() {
            return arena_en;
        }

        public void setArena_en(String arena_en) {
            this.arena_en = arena_en;
        }

        public String getArena_zh() {
            return arena_zh;
        }

        public void setArena_zh(String arena_zh) {
            this.arena_zh = arena_zh;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

    }
}
