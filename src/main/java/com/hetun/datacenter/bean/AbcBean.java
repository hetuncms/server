package com.hetun.datacenter.bean;

import java.util.List;

/**
 * Auto-generated: 2023-01-27 6:20:44
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AbcBean {

    private int code;
    private String msg;
    private List<Data> data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    /**
     * Auto-generated: 2023-01-27 6:20:44
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public static class Data {

        private long id;
        private int tournamentId;
        private int groupId;
        private int sportId;
        private String matchTime;
        private int homeTeamId;
        private int awayTeamId;
        private String homeTeamName;
        private String awayTeamName;
        private int homeTeamScore;
        private int awayTeamScore;
        private int homeTeamHalfTimeScore;
        private int awayTeamHalfTimeScore;
        private int homeTeamNormalTimeScore;
        private int awayTeamNormalTimeScore;
        private String homeTeamLogo;
        private String awayTeamLogo;
        private String homeFormation;
        private String awayFormation;
        private int status;
        private int statusCode;
        private String statusName;
        private int winner;
        private String aggregateWinner;
        private String round;
        private String roundType;
        private long seasonId;
        private String season;
        private String refereeId;
        private String refereeName;
        private int stadiumId;
        private String stadiumName;
        private String weather;
        private String weatherDesc;
        private String liveOdds;
        private int timePlayed;
        private int timeRemaining;
        private int loBetStatus;
        private int lmtMode;
        private int hasLive;
        private int hasVid;
        private String updateTime;
        private String createTime;
        private String dataUpdateTime;
        private String dataCreateTime;
        private int timeRunning;
        private long updateTimestamp;
        private String timeUpdate;
        private int neutral;
        private String distance;
        private String side;
        private String gameScore;
        private int isVisible;
        private String previousMatchId;
        private int mergeId;
        public void setId(long id) {
            this.id = id;
        }
        public long getId() {
            return id;
        }

        public void setTournamentId(int tournamentId) {
            this.tournamentId = tournamentId;
        }
        public int getTournamentId() {
            return tournamentId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
        public int getGroupId() {
            return groupId;
        }

        public void setSportId(int sportId) {
            this.sportId = sportId;
        }
        public int getSportId() {
            return sportId;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }
        public String getMatchTime() {
            return matchTime;
        }

        public void setHomeTeamId(int homeTeamId) {
            this.homeTeamId = homeTeamId;
        }
        public int getHomeTeamId() {
            return homeTeamId;
        }

        public void setAwayTeamId(int awayTeamId) {
            this.awayTeamId = awayTeamId;
        }
        public int getAwayTeamId() {
            return awayTeamId;
        }

        public void setHomeTeamName(String homeTeamName) {
            this.homeTeamName = homeTeamName;
        }
        public String getHomeTeamName() {
            return homeTeamName;
        }

        public void setAwayTeamName(String awayTeamName) {
            this.awayTeamName = awayTeamName;
        }
        public String getAwayTeamName() {
            return awayTeamName;
        }

        public void setHomeTeamScore(int homeTeamScore) {
            this.homeTeamScore = homeTeamScore;
        }
        public int getHomeTeamScore() {
            return homeTeamScore;
        }

        public void setAwayTeamScore(int awayTeamScore) {
            this.awayTeamScore = awayTeamScore;
        }
        public int getAwayTeamScore() {
            return awayTeamScore;
        }

        public void setHomeTeamHalfTimeScore(int homeTeamHalfTimeScore) {
            this.homeTeamHalfTimeScore = homeTeamHalfTimeScore;
        }
        public int getHomeTeamHalfTimeScore() {
            return homeTeamHalfTimeScore;
        }

        public void setAwayTeamHalfTimeScore(int awayTeamHalfTimeScore) {
            this.awayTeamHalfTimeScore = awayTeamHalfTimeScore;
        }
        public int getAwayTeamHalfTimeScore() {
            return awayTeamHalfTimeScore;
        }

        public void setHomeTeamNormalTimeScore(int homeTeamNormalTimeScore) {
            this.homeTeamNormalTimeScore = homeTeamNormalTimeScore;
        }
        public int getHomeTeamNormalTimeScore() {
            return homeTeamNormalTimeScore;
        }

        public void setAwayTeamNormalTimeScore(int awayTeamNormalTimeScore) {
            this.awayTeamNormalTimeScore = awayTeamNormalTimeScore;
        }
        public int getAwayTeamNormalTimeScore() {
            return awayTeamNormalTimeScore;
        }

        public void setHomeTeamLogo(String homeTeamLogo) {
            this.homeTeamLogo = homeTeamLogo;
        }
        public String getHomeTeamLogo() {
            return homeTeamLogo;
        }

        public void setAwayTeamLogo(String awayTeamLogo) {
            this.awayTeamLogo = awayTeamLogo;
        }
        public String getAwayTeamLogo() {
            return awayTeamLogo;
        }

        public void setHomeFormation(String homeFormation) {
            this.homeFormation = homeFormation;
        }
        public String getHomeFormation() {
            return homeFormation;
        }

        public void setAwayFormation(String awayFormation) {
            this.awayFormation = awayFormation;
        }
        public String getAwayFormation() {
            return awayFormation;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
        public String getStatusName() {
            return statusName;
        }

        public void setWinner(int winner) {
            this.winner = winner;
        }
        public int getWinner() {
            return winner;
        }

        public void setAggregateWinner(String aggregateWinner) {
            this.aggregateWinner = aggregateWinner;
        }
        public String getAggregateWinner() {
            return aggregateWinner;
        }

        public void setRound(String round) {
            this.round = round;
        }
        public String getRound() {
            return round;
        }

        public void setRoundType(String roundType) {
            this.roundType = roundType;
        }
        public String getRoundType() {
            return roundType;
        }

        public void setSeasonId(long seasonId) {
            this.seasonId = seasonId;
        }
        public long getSeasonId() {
            return seasonId;
        }

        public void setSeason(String season) {
            this.season = season;
        }
        public String getSeason() {
            return season;
        }

        public void setRefereeId(String refereeId) {
            this.refereeId = refereeId;
        }
        public String getRefereeId() {
            return refereeId;
        }

        public void setRefereeName(String refereeName) {
            this.refereeName = refereeName;
        }
        public String getRefereeName() {
            return refereeName;
        }

        public void setStadiumId(int stadiumId) {
            this.stadiumId = stadiumId;
        }
        public int getStadiumId() {
            return stadiumId;
        }

        public void setStadiumName(String stadiumName) {
            this.stadiumName = stadiumName;
        }
        public String getStadiumName() {
            return stadiumName;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }
        public String getWeather() {
            return weather;
        }

        public void setWeatherDesc(String weatherDesc) {
            this.weatherDesc = weatherDesc;
        }
        public String getWeatherDesc() {
            return weatherDesc;
        }

        public void setLiveOdds(String liveOdds) {
            this.liveOdds = liveOdds;
        }
        public String getLiveOdds() {
            return liveOdds;
        }

        public void setTimePlayed(int timePlayed) {
            this.timePlayed = timePlayed;
        }
        public int getTimePlayed() {
            return timePlayed;
        }

        public void setTimeRemaining(int timeRemaining) {
            this.timeRemaining = timeRemaining;
        }
        public int getTimeRemaining() {
            return timeRemaining;
        }

        public void setLoBetStatus(int loBetStatus) {
            this.loBetStatus = loBetStatus;
        }
        public int getLoBetStatus() {
            return loBetStatus;
        }

        public void setLmtMode(int lmtMode) {
            this.lmtMode = lmtMode;
        }
        public int getLmtMode() {
            return lmtMode;
        }

        public void setHasLive(int hasLive) {
            this.hasLive = hasLive;
        }
        public int getHasLive() {
            return hasLive;
        }

        public void setHasVid(int hasVid) {
            this.hasVid = hasVid;
        }
        public int getHasVid() {
            return hasVid;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
        public String getUpdateTime() {
            return updateTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
        public String getCreateTime() {
            return createTime;
        }

        public void setDataUpdateTime(String dataUpdateTime) {
            this.dataUpdateTime = dataUpdateTime;
        }
        public String getDataUpdateTime() {
            return dataUpdateTime;
        }

        public void setDataCreateTime(String dataCreateTime) {
            this.dataCreateTime = dataCreateTime;
        }
        public String getDataCreateTime() {
            return dataCreateTime;
        }

        public void setTimeRunning(int timeRunning) {
            this.timeRunning = timeRunning;
        }
        public int getTimeRunning() {
            return timeRunning;
        }

        public void setUpdateTimestamp(long updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
        }
        public long getUpdateTimestamp() {
            return updateTimestamp;
        }

        public void setTimeUpdate(String timeUpdate) {
            this.timeUpdate = timeUpdate;
        }
        public String getTimeUpdate() {
            return timeUpdate;
        }

        public void setNeutral(int neutral) {
            this.neutral = neutral;
        }
        public int getNeutral() {
            return neutral;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
        public String getDistance() {
            return distance;
        }

        public void setSide(String side) {
            this.side = side;
        }
        public String getSide() {
            return side;
        }

        public void setGameScore(String gameScore) {
            this.gameScore = gameScore;
        }
        public String getGameScore() {
            return gameScore;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }
        public int getIsVisible() {
            return isVisible;
        }

        public void setPreviousMatchId(String previousMatchId) {
            this.previousMatchId = previousMatchId;
        }
        public String getPreviousMatchId() {
            return previousMatchId;
        }

        public void setMergeId(int mergeId) {
            this.mergeId = mergeId;
        }
        public int getMergeId() {
            return mergeId;
        }

    }
}
