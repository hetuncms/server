/**
 * Copyright 2023 bejson.com
 */
package com.hetun.datacenter.tripartite.bean;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

import java.util.List;

public class LeagueBean {

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

    @Table(name = "match_league")
    @Entity
    public static class Result {
        @Id
        private int id;
        private int type;
        private String name_en_full;
        private String name_en_abbr;
        private String name_zh_full;
        private String name_zh_abbr;
        private String name_zh_tra_full;
        private String name_zh_tra_abbr;
        private int is_hot;
        private int has_odds;
        private int level;
        private int area;
        private String country;
        private String pic;
        private long current_season_id;
        private String current_stage_id;
        private String champion_holder_id;
        private String champion_holder_count;
        @Column(columnDefinition = "text")
        @Type(value = JsonType.class)
        private List<String> most_champion_id;
        private String most_champion_count;
        private int has_lottery;
        @Column(columnDefinition = "text")
        @Type(value = JsonType.class)
        private Names names;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName_en_full() {
            return name_en_full;
        }

        public void setName_en_full(String name_en_full) {
            this.name_en_full = name_en_full;
        }

        public String getName_en_abbr() {
            return name_en_abbr;
        }

        public void setName_en_abbr(String name_en_abbr) {
            this.name_en_abbr = name_en_abbr;
        }

        public String getName_zh_full() {
            return name_zh_full;
        }

        public void setName_zh_full(String name_zh_full) {
            this.name_zh_full = name_zh_full;
        }

        public String getName_zh_abbr() {
            return name_zh_abbr;
        }

        public void setName_zh_abbr(String name_zh_abbr) {
            this.name_zh_abbr = name_zh_abbr;
        }

        public String getName_zh_tra_full() {
            return name_zh_tra_full;
        }

        public void setName_zh_tra_full(String name_zh_tra_full) {
            this.name_zh_tra_full = name_zh_tra_full;
        }

        public String getName_zh_tra_abbr() {
            return name_zh_tra_abbr;
        }

        public void setName_zh_tra_abbr(String name_zh_tra_abbr) {
            this.name_zh_tra_abbr = name_zh_tra_abbr;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getHas_odds() {
            return has_odds;
        }

        public void setHas_odds(int has_odds) {
            this.has_odds = has_odds;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public long getCurrent_season_id() {
            return current_season_id;
        }

        public void setCurrent_season_id(long current_season_id) {
            this.current_season_id = current_season_id;
        }

        public String getCurrent_stage_id() {
            return current_stage_id;
        }

        public void setCurrent_stage_id(String current_stage_id) {
            this.current_stage_id = current_stage_id;
        }

        public String getChampion_holder_id() {
            return champion_holder_id;
        }

        public void setChampion_holder_id(String champion_holder_id) {
            this.champion_holder_id = champion_holder_id;
        }

        public String getChampion_holder_count() {
            return champion_holder_count;
        }

        public void setChampion_holder_count(String champion_holder_count) {
            this.champion_holder_count = champion_holder_count;
        }

        public List<String> getMost_champion_id() {
            return most_champion_id;
        }

        public void setMost_champion_id(List<String> most_champion_id) {
            this.most_champion_id = most_champion_id;
        }

        public String getMost_champion_count() {
            return most_champion_count;
        }

        public void setMost_champion_count(String most_champion_count) {
            this.most_champion_count = most_champion_count;
        }

        public int getHas_lottery() {
            return has_lottery;
        }

        public void setHas_lottery(int has_lottery) {
            this.has_lottery = has_lottery;
        }

        public Names getNames() {
            return names;
        }

        public void setNames(Names names) {
            this.names = names;
        }

        public static class Names {
            private int i;

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }
        }
    }
}
