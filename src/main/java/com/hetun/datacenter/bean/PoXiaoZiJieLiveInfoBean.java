package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

import java.util.List;

public class PoXiaoZiJieLiveInfoBean {

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

    @JsonIgnoreProperties(value = "old")
    @Entity
    @Table(name = "live_info")
    public static class Result {
        @Id
        private Integer match_id;
        @JsonIgnore
        private Boolean isOld;

        public Boolean getOld() {
            return isOld;
        }

        public void setOld(Boolean old) {
            isOld = old;
        }

        @Type(value = JsonType.class)
        @Column(
                name = "live_streams",
                columnDefinition = "text"
        )
        private List<LiveStreams> live_streams;

        public Integer getMatch_id() {
            return match_id;
        }

        public void setMatch_id(Integer match_id) {
            this.match_id = match_id;
        }

        public List<LiveStreams> getLive_streams() {
            return live_streams;
        }

        public void setLive_streams(List<LiveStreams> live_streams) {
            this.live_streams = live_streams;
        }

    }

    public static class LiveStreams {

        private String stream_id;
        private int type;
        private String url;
        private int format;
        private int source;
        private int status;

        public String getStream_id() {
            return stream_id;
        }

        public void setStream_id(String stream_id) {
            this.stream_id = stream_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getFormat() {
            return format;
        }

        public void setFormat(int format) {
            this.format = format;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
}
