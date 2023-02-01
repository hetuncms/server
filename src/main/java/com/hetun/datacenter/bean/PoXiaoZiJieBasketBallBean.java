package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PoXiaoZiJieBasketBallBean {
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
        @JsonProperty("type")
        private Object type;
        @JsonProperty("arena_en")
        private Object arenaEn;
        @JsonProperty("arena_zh")
        private Object arenaZh;
        @JsonProperty("match_start_time")
        private Integer matchStartTime;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("has_odds")
        private Integer hasOdds;
        @JsonProperty("has_inplay")
        private Integer hasInplay;
        @JsonProperty("has_stream")
        private Integer hasStream;
        @JsonProperty("has_lottery")
        private Integer hasLottery;
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

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getArenaEn() {
            return arenaEn;
        }

        public void setArenaEn(Object arenaEn) {
            this.arenaEn = arenaEn;
        }

        public Object getArenaZh() {
            return arenaZh;
        }

        public void setArenaZh(Object arenaZh) {
            this.arenaZh = arenaZh;
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

        public Integer getHasLottery() {
            return hasLottery;
        }

        public void setHasLottery(Integer hasLottery) {
            this.hasLottery = hasLottery;
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
            @JsonProperty("player")
            private Object player;

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

            public Object getPlayer() {
                return player;
            }

            public void setPlayer(Object player) {
                this.player = player;
            }
        }
    }
}
