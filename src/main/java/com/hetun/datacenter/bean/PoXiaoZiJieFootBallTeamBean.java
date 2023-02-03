package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;


public class PoXiaoZiJieFootBallTeamBean {
    private int code;

    private String message;

    private List<Result> result;

    private int time_stamp;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(int time_stamp) {
        this.time_stamp = time_stamp;
    }
@JsonIgnoreProperties(ignoreUnknown = true)
    @Entity
    @Table(name = "football_team")
    public static class Result {
        @Id
        private int id;

        private int type;

        private String name_en;

        private String name_zh;

        private String name_zh_tra;

        private String name_en_abbr;

        private String name_zh_abbr;

        private String name_zh_tra_abbr;

        private String year;

        private String country;

        private String coach_id;

        private String arena_id;

        private String value;
        private String pic;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName_en() {
            return this.name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getName_zh() {
            return this.name_zh;
        }

        public void setName_zh(String name_zh) {
            this.name_zh = name_zh;
        }

        public String getName_zh_tra() {
            return this.name_zh_tra;
        }

        public void setName_zh_tra(String name_zh_tra) {
            this.name_zh_tra = name_zh_tra;
        }

        public String getName_en_abbr() {
            return this.name_en_abbr;
        }

        public void setName_en_abbr(String name_en_abbr) {
            this.name_en_abbr = name_en_abbr;
        }

        public String getName_zh_abbr() {
            return this.name_zh_abbr;
        }

        public void setName_zh_abbr(String name_zh_abbr) {
            this.name_zh_abbr = name_zh_abbr;
        }

        public String getName_zh_tra_abbr() {
            return this.name_zh_tra_abbr;
        }

        public void setName_zh_tra_abbr(String name_zh_tra_abbr) {
            this.name_zh_tra_abbr = name_zh_tra_abbr;
        }

        public String getYear() {
            return this.year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getCountry() {
            return this.country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCoach_id() {
            return this.coach_id;
        }

        public void setCoach_id(String coach_id) {
            this.coach_id = coach_id;
        }

        public String getArena_id() {
            return this.arena_id;
        }

        public void setArena_id(String arena_id) {
            this.arena_id = arena_id;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        public String getPic() {
            return this.pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

    }

}
