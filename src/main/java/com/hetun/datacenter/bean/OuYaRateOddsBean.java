package com.hetun.datacenter.bean; /**
 * Copyright 2023 bejson.com
 */

import java.util.List;

/**
 * Auto-generated: 2023-02-05 22:15:4
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OuYaRateOddsBean {
    private Integer id;
    private int company_id;
    private String point;
    private int market_status;
    private long update_time;
    private List<Integer> score;
    private List<Option> option;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getMarket_status() {
        return market_status;
    }

    public void setMarket_status(int market_status) {
        this.market_status = market_status;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public List<Option> getOption() {
        return option;
    }

    public void setOption(List<Option> option) {
        this.option = option;
    }

    public static class Option {

        private long option_id;
        private int is_winner;
        private String rate;

        public long getOption_id() {
            return option_id;
        }

        public void setOption_id(long option_id) {
            this.option_id = option_id;
        }

        public int getIs_winner() {
            return is_winner;
        }

        public void setIs_winner(int is_winner) {
            this.is_winner = is_winner;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

    }

}
