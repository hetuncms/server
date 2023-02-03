package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "live_table")
@JsonIgnoreProperties(value = {"liveSource", "old", "hot", "upDataTime", "upDataCount"})
public class LiveItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String liveId;
    private Integer liveType;
    private Integer liveStatus;

    private String liveSource;
    private String title;
    private Long longTime;
    private Boolean isTop;
    private String leftName;
    private String rightName;
    private String leftImg;
    private String rightImg;
    private String gameName;
    private Long matchId;

    private Long upDataTime;

    private Long upDataCount;
    private Boolean isHot;

    private Boolean isOld;
    @Column(nullable = false)
    private Boolean isLiveing;

    public LiveItem() {
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public Boolean getLiveing() {
        return isLiveing;
    }

    public void setLiveing(Boolean liveing) {
        isLiveing = liveing;
    }

    public Boolean getOld() {
        return isOld;
    }

    public void setOld(Boolean old) {
        isOld = old;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Long getUpDataTime() {
        return upDataTime;
    }

    public void setUpDataTime(Long upDataTime) {
        this.upDataTime = upDataTime;
    }

    public Long getUpDataCount() {
        return upDataCount;
    }

    public void setUpDataCount(Long upDataCount) {
        this.upDataCount = upDataCount;
    }

    public String getLiveSource() {
        return liveSource;
    }

    public void setLiveSource(String liveSource) {
        this.liveSource = liveSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLiveType() {
        return liveType;
    }

    public void setLiveType(Integer liveType) {
        this.liveType = liveType;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public Boolean getIsTop() {
        return isTop;
    }


    public void setIsTop(Boolean top) {
        isTop = top;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getLeftImg() {
        return leftImg;
    }

    public void setLeftImg(String leftImg) {
        this.leftImg = leftImg;
    }

    public String getRightImg() {
        return rightImg;
    }

    public void setRightImg(String rightImg) {
        this.rightImg = rightImg;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }
}
