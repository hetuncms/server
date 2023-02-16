package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "live_table")
@JsonIgnoreProperties(value = {"liveSource", "old", "hot", "upDataTime", "upDataCount", "leagueId"})
public class LiveItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String liveId;
    private Integer liveType;
    private Integer liveStatus;
    private Integer leagueId;
    private String liveSource;
    private String title;
    private Long matchStartTime;
    @Column(nullable = false)
    private boolean top;
    private String leftName;
    private String rightName;
    private String leftImg;
    private String rightImg;
    private String gameName;
    private Long upDataTime;
    private Long upDataCount;
    @Column(nullable = false)
    private boolean hot;
    @Column(nullable = false)
    private boolean old;
    private Integer hasOdds;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> visitingScore;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> mainScore;
    @Column(nullable = false)
    private boolean liveing;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> leftTeamScore;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> rightTeamScore;


    public List<Integer> getMainScore() {
        return mainScore;
    }

    public void setMainScore(List<Integer> mainScore) {
        this.mainScore = mainScore;
    }

    public List<Integer> getVisitingScore() {
        return visitingScore;
    }

    public void setVisitingScore(List<Integer> visitingScore) {
        this.visitingScore = visitingScore;
    }

    public LiveItem() {
    }

    public List<Integer> getLeftTeamScore() {
        return leftTeamScore;
    }

    public void setLeftTeamScore(List<Integer> leftTeamScore) {
        this.leftTeamScore = leftTeamScore;
    }

    public List<Integer> getRightTeamScore() {
        return rightTeamScore;
    }

    public void setRightTeamScore(List<Integer> rightTeamScore) {
        this.rightTeamScore = rightTeamScore;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public Integer getHasOdds() {
        return hasOdds;
    }

    public void setHasOdds(Integer hasOdds) {
        this.hasOdds = hasOdds;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public boolean isLiveing() {
        return liveing;
    }

    public void setLiveing(boolean liveing) {
        this.liveing = liveing;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Long getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Long matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Long getUpDataTime() {
        return upDataTime;
    }

    public void setUpDataTime(Long upDataTime) {
        this.upDataTime = upDataTime;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
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
}
