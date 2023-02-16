/**
 * Copyright 2023 bejson.com
 */
package com.hetun.datacenter.bean;

import com.hetun.datacenter.tripartite.bean.BaseNetBean;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

import java.util.List;

/**
 * Auto-generated: 2023-02-04 13:15:8
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RateOddsBean extends BaseNetBean{

    private int code;
    private String message;
    private long time_stamp;
    private List<Result> result;

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

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    @Entity
    @Table(name = "rate_odds")
    public static class Result {
        @Id
        private int id;
        private int sport_id;
        private long match_id;
        private int type;
        @Column(columnDefinition = "text")
        @Type(value = JsonType.class)
        private List<OddsItem> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSport_id() {
            return sport_id;
        }

        public void setSport_id(int sport_id) {
            this.sport_id = sport_id;
        }

        public List<OddsItem> getList() {
            return list;
        }

        public long getMatch_id() {
            return match_id;
        }

        public void setMatch_id(long match_id) {
            this.match_id = match_id;
        }

        public void setList(List<OddsItem> list) {
            this.list = list;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class OddsItem {
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            private int company_id;
            private String companyName;

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            private int market_status;
            private List<Integer> score;
            private String point;
            private long update_time;
            @Type(value = JsonType.class)
            private List<Option> option;

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public int getMarket_status() {
                return market_status;
            }

            public void setMarket_status(int market_status) {
                this.market_status = market_status;
            }

            public List<Integer> getScore() {
                return score;
            }

            public void setScore(List<Integer> score) {
                this.score = score;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }

            public List<Option> getOption() {
                return option;
            }

            public void setOption(List<Option> option) {
                this.option = option;
            }

            public static class Option {

                private int option_id;
                private String option_name;
                private int is_winner;
                private String rate;

                public int getOption_id() {
                    return option_id;
                }

                public void setOption_id(int option_id) {
                    this.option_id = option_id;
                }

                public String getOption_name() {
                    return option_name;
                }

                public void setOption_name(String option_name) {
                    this.option_name = option_name;
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
    }
}
