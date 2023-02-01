package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PoXiaoZiJieFootBallBean {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private List<ResultDTO> result;
    @JsonProperty("time_stamp")
    private Integer timeStamp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultDTO> getResult() {
        return result;
    }

    public void setResult(List<ResultDTO> result) {
        this.result = result;
    }

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static class ResultDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("league_id")
        private Integer leagueId;
        @JsonProperty("season_id")
        private Integer seasonId;
        @JsonProperty("stage_id")
        private Integer stageId;
        @JsonProperty("round_num")
        private Object roundNum;
        @JsonProperty("group_num")
        private Object groupNum;
        @JsonProperty("match_start_time")
        private Integer matchStartTime;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("arena_id")
        private Object arenaId;
        @JsonProperty("referee_id")
        private Object refereeId;
        @JsonProperty("has_odds")
        private Integer hasOdds;
        @JsonProperty("has_inplay")
        private Integer hasInplay;
        @JsonProperty("has_stream")
        private Integer hasStream;
        @JsonProperty("weather")
        private String weather;
        @JsonProperty("wind")
        private String wind;
        @JsonProperty("humidity")
        private String humidity;
        @JsonProperty("pressure")
        private String pressure;
        @JsonProperty("temperature")
        private String temperature;
        @JsonProperty("has_lottery")
        private Integer hasLottery;
        @JsonProperty("has_bjlot")
        private Integer hasBjlot;
        @JsonProperty("has_animation")
        private Integer hasAnimation;
        @JsonProperty("team")
        private List<TeamDTO> team;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(Integer leagueId) {
            this.leagueId = leagueId;
        }

        public Integer getSeasonId() {
            return seasonId;
        }

        public void setSeasonId(Integer seasonId) {
            this.seasonId = seasonId;
        }

        public Integer getStageId() {
            return stageId;
        }

        public void setStageId(Integer stageId) {
            this.stageId = stageId;
        }

        public Object getRoundNum() {
            return roundNum;
        }

        public void setRoundNum(Object roundNum) {
            this.roundNum = roundNum;
        }

        public Object getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(Object groupNum) {
            this.groupNum = groupNum;
        }

        public Integer getMatchStartTime() {
            return matchStartTime;
        }

        public void setMatchStartTime(Integer matchStartTime) {
            this.matchStartTime = matchStartTime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Object getArenaId() {
            return arenaId;
        }

        public void setArenaId(Object arenaId) {
            this.arenaId = arenaId;
        }

        public Object getRefereeId() {
            return refereeId;
        }

        public void setRefereeId(Object refereeId) {
            this.refereeId = refereeId;
        }

        public Integer getHasOdds() {
            return hasOdds;
        }

        public void setHasOdds(Integer hasOdds) {
            this.hasOdds = hasOdds;
        }

        public Integer getHasInplay() {
            return hasInplay;
        }

        public void setHasInplay(Integer hasInplay) {
            this.hasInplay = hasInplay;
        }

        public Integer getHasStream() {
            return hasStream;
        }

        public void setHasStream(Integer hasStream) {
            this.hasStream = hasStream;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public Integer getHasLottery() {
            return hasLottery;
        }

        public void setHasLottery(Integer hasLottery) {
            this.hasLottery = hasLottery;
        }

        public Integer getHasBjlot() {
            return hasBjlot;
        }

        public void setHasBjlot(Integer hasBjlot) {
            this.hasBjlot = hasBjlot;
        }

        public Integer getHasAnimation() {
            return hasAnimation;
        }

        public void setHasAnimation(Integer hasAnimation) {
            this.hasAnimation = hasAnimation;
        }

        public List<TeamDTO> getTeam() {
            return team;
        }

        public void setTeam(List<TeamDTO> team) {
            this.team = team;
        }

        public static class TeamDTO {
            @JsonProperty("team_id")
            private Integer teamId;
            @JsonProperty("is_home")
            private Integer isHome;
            @JsonProperty("is_winner")
            private Integer isWinner;
            @JsonProperty("score")
            private List<Integer> score;
            @JsonProperty("corner")
            private Integer corner;
            @JsonProperty("red")
            private Integer red;
            @JsonProperty("yellow")
            private Integer yellow;
            @JsonProperty("formation")
            private Object formation;
            @JsonProperty("player")
            private List<?> player;

            public Integer getTeamId() {
                return teamId;
            }

            public void setTeamId(Integer teamId) {
                this.teamId = teamId;
            }

            public Integer getIsHome() {
                return isHome;
            }

            public void setIsHome(Integer isHome) {
                this.isHome = isHome;
            }

            public Integer getIsWinner() {
                return isWinner;
            }

            public void setIsWinner(Integer isWinner) {
                this.isWinner = isWinner;
            }

            public List<Integer> getScore() {
                return score;
            }

            public void setScore(List<Integer> score) {
                this.score = score;
            }

            public Integer getCorner() {
                return corner;
            }

            public void setCorner(Integer corner) {
                this.corner = corner;
            }

            public Integer getRed() {
                return red;
            }

            public void setRed(Integer red) {
                this.red = red;
            }

            public Integer getYellow() {
                return yellow;
            }

            public void setYellow(Integer yellow) {
                this.yellow = yellow;
            }

            public Object getFormation() {
                return formation;
            }

            public void setFormation(Object formation) {
                this.formation = formation;
            }

            public List<?> getPlayer() {
                return player;
            }

            public void setPlayer(List<?> player) {
                this.player = player;
            }
        }
    }
}
