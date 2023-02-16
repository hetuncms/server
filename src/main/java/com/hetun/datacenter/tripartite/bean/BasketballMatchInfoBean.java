package com.hetun.datacenter.tripartite.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BasketballMatchInfoBean {

    @JsonProperty("match_id")
    private Integer matchId;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("countdown")
    private Integer countdown;
    @JsonProperty("team")
    private List<Team> team;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCountdown() {
        return countdown;
    }

    public void setCountdown(Integer countdown) {
        this.countdown = countdown;
    }

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    public static class Team {
        @JsonProperty("team_id")
        private Integer teamId;
        @JsonProperty("is_home")
        private Integer isHome;
        @JsonProperty("score")
        private List<Integer> score;
        @JsonProperty("three_point_goal")
        private Integer threePointGoal;
        @JsonProperty("three_point_shot_attempt")
        private Object threePointShotAttempt;
        @JsonProperty("three_point_shot_success_rate")
        private Object threePointShotSuccessRate;
        @JsonProperty("two_point_goal")
        private Integer twoPointGoal;
        @JsonProperty("two_point_shot_attempt")
        private Object twoPointShotAttempt;
        @JsonProperty("two_point_shot_success_rate")
        private Object twoPointShotSuccessRate;
        @JsonProperty("field_shot_attempt")
        private Object fieldShotAttempt;
        @JsonProperty("field_goal")
        private Object fieldGoal;
        @JsonProperty("field_shot_success_rate")
        private Object fieldShotSuccessRate;
        @JsonProperty("free_throw_attempt")
        private Object freeThrowAttempt;
        @JsonProperty("free_throw_goal")
        private Integer freeThrowGoal;
        @JsonProperty("free_throw_success_rate")
        private String freeThrowSuccessRate;
        @JsonProperty("foul")
        private List<Integer> foul;
        @JsonProperty("break")
        private Integer breakX;
        @JsonProperty("break_left")
        private Integer breakLeft;
        @JsonProperty("offensive_rebound")
        private Object offensiveRebound;
        @JsonProperty("defensive_rebound")
        private Object defensiveRebound;
        @JsonProperty("rebound")
        private Object rebound;
        @JsonProperty("assist")
        private Object assist;
        @JsonProperty("steal")
        private Object steal;
        @JsonProperty("block")
        private Object block;
        @JsonProperty("turnover")
        private Object turnover;
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

        public List<Integer> getScore() {
            return score;
        }

        public void setScore(List<Integer> score) {
            this.score = score;
        }

        public Integer getThreePointGoal() {
            return threePointGoal;
        }

        public void setThreePointGoal(Integer threePointGoal) {
            this.threePointGoal = threePointGoal;
        }

        public Object getThreePointShotAttempt() {
            return threePointShotAttempt;
        }

        public void setThreePointShotAttempt(Object threePointShotAttempt) {
            this.threePointShotAttempt = threePointShotAttempt;
        }

        public Object getThreePointShotSuccessRate() {
            return threePointShotSuccessRate;
        }

        public void setThreePointShotSuccessRate(Object threePointShotSuccessRate) {
            this.threePointShotSuccessRate = threePointShotSuccessRate;
        }

        public Integer getTwoPointGoal() {
            return twoPointGoal;
        }

        public void setTwoPointGoal(Integer twoPointGoal) {
            this.twoPointGoal = twoPointGoal;
        }

        public Object getTwoPointShotAttempt() {
            return twoPointShotAttempt;
        }

        public void setTwoPointShotAttempt(Object twoPointShotAttempt) {
            this.twoPointShotAttempt = twoPointShotAttempt;
        }

        public Object getTwoPointShotSuccessRate() {
            return twoPointShotSuccessRate;
        }

        public void setTwoPointShotSuccessRate(Object twoPointShotSuccessRate) {
            this.twoPointShotSuccessRate = twoPointShotSuccessRate;
        }

        public Object getFieldShotAttempt() {
            return fieldShotAttempt;
        }

        public void setFieldShotAttempt(Object fieldShotAttempt) {
            this.fieldShotAttempt = fieldShotAttempt;
        }

        public Object getFieldGoal() {
            return fieldGoal;
        }

        public void setFieldGoal(Object fieldGoal) {
            this.fieldGoal = fieldGoal;
        }

        public Object getFieldShotSuccessRate() {
            return fieldShotSuccessRate;
        }

        public void setFieldShotSuccessRate(Object fieldShotSuccessRate) {
            this.fieldShotSuccessRate = fieldShotSuccessRate;
        }

        public Object getFreeThrowAttempt() {
            return freeThrowAttempt;
        }

        public void setFreeThrowAttempt(Object freeThrowAttempt) {
            this.freeThrowAttempt = freeThrowAttempt;
        }

        public Integer getFreeThrowGoal() {
            return freeThrowGoal;
        }

        public void setFreeThrowGoal(Integer freeThrowGoal) {
            this.freeThrowGoal = freeThrowGoal;
        }

        public String getFreeThrowSuccessRate() {
            return freeThrowSuccessRate;
        }

        public void setFreeThrowSuccessRate(String freeThrowSuccessRate) {
            this.freeThrowSuccessRate = freeThrowSuccessRate;
        }

        public List<Integer> getFoul() {
            return foul;
        }

        public void setFoul(List<Integer> foul) {
            this.foul = foul;
        }

        public Integer getBreakX() {
            return breakX;
        }

        public void setBreakX(Integer breakX) {
            this.breakX = breakX;
        }

        public Integer getBreakLeft() {
            return breakLeft;
        }

        public void setBreakLeft(Integer breakLeft) {
            this.breakLeft = breakLeft;
        }

        public Object getOffensiveRebound() {
            return offensiveRebound;
        }

        public void setOffensiveRebound(Object offensiveRebound) {
            this.offensiveRebound = offensiveRebound;
        }

        public Object getDefensiveRebound() {
            return defensiveRebound;
        }

        public void setDefensiveRebound(Object defensiveRebound) {
            this.defensiveRebound = defensiveRebound;
        }

        public Object getRebound() {
            return rebound;
        }

        public void setRebound(Object rebound) {
            this.rebound = rebound;
        }

        public Object getAssist() {
            return assist;
        }

        public void setAssist(Object assist) {
            this.assist = assist;
        }

        public Object getSteal() {
            return steal;
        }

        public void setSteal(Object steal) {
            this.steal = steal;
        }

        public Object getBlock() {
            return block;
        }

        public void setBlock(Object block) {
            this.block = block;
        }

        public Object getTurnover() {
            return turnover;
        }

        public void setTurnover(Object turnover) {
            this.turnover = turnover;
        }

        public List<?> getPlayer() {
            return player;
        }

        public void setPlayer(List<?> player) {
            this.player = player;
        }
    }
}
