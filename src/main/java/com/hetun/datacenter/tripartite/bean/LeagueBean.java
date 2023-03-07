/**
 * Copyright 2023 bejson.com
 */
package com.hetun.datacenter.tripartite.bean;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

public class LeagueBean {

    private int code;
    private String message;
    private List<LeagueResult> result;
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

    public List<LeagueResult> getResult() {
        return result;
    }

    public void setResult(List<LeagueResult> result) {
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
    @Data
    public static class LeagueResult {
        @Id
        private Integer id;
        private String name_zh_abbr;
    }
}
