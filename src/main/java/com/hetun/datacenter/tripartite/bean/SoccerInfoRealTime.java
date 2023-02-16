package com.hetun.datacenter.tripartite.bean;

import java.util.List;

public class SoccerInfoRealTime {
    private int match_id;

    private int time;

    private int status;

    private List<Team> team;

    public int getMatch_id() {
        return this.match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Team> getTeam() {
        return this.team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    public static class Team {
        private int team_id;

        private int is_home;

        private List<Integer> score;

        private int red;

        private int yellow;

        private int corner;

        private String yellow2red;

        private int penalty_goal;

        private String penalty_missed;

        private String substitute;

        private String own_goal;

        private int shot;

        private int target_shot;

        private int off_target_shot;

        private int attack;

        private int dangerous_attack;

        private String possession;

        public int getTeam_id() {
            return this.team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getIs_home() {
            return this.is_home;
        }

        public void setIs_home(int is_home) {
            this.is_home = is_home;
        }

        public List<Integer> getScore() {
            return this.score;
        }

        public void setScore(List<Integer> score) {
            this.score = score;
        }

        public int getRed() {
            return this.red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getYellow() {
            return this.yellow;
        }

        public void setYellow(int yellow) {
            this.yellow = yellow;
        }

        public int getCorner() {
            return this.corner;
        }

        public void setCorner(int corner) {
            this.corner = corner;
        }

        public String getYellow2red() {
            return this.yellow2red;
        }

        public void setYellow2red(String yellow2red) {
            this.yellow2red = yellow2red;
        }

        public int getPenalty_goal() {
            return this.penalty_goal;
        }

        public void setPenalty_goal(int penalty_goal) {
            this.penalty_goal = penalty_goal;
        }

        public String getPenalty_missed() {
            return this.penalty_missed;
        }

        public void setPenalty_missed(String penalty_missed) {
            this.penalty_missed = penalty_missed;
        }

        public String getSubstitute() {
            return this.substitute;
        }

        public void setSubstitute(String substitute) {
            this.substitute = substitute;
        }

        public String getOwn_goal() {
            return this.own_goal;
        }

        public void setOwn_goal(String own_goal) {
            this.own_goal = own_goal;
        }

        public int getShot() {
            return this.shot;
        }

        public void setShot(int shot) {
            this.shot = shot;
        }

        public int getTarget_shot() {
            return this.target_shot;
        }

        public void setTarget_shot(int target_shot) {
            this.target_shot = target_shot;
        }

        public int getOff_target_shot() {
            return this.off_target_shot;
        }

        public void setOff_target_shot(int off_target_shot) {
            this.off_target_shot = off_target_shot;
        }

        public int getAttack() {
            return this.attack;
        }

        public void setAttack(int attack) {
            this.attack = attack;
        }

        public int getDangerous_attack() {
            return this.dangerous_attack;
        }

        public void setDangerous_attack(int dangerous_attack) {
            this.dangerous_attack = dangerous_attack;
        }

        public String getPossession() {
            return this.possession;
        }

        public void setPossession(String possession) {
            this.possession = possession;
        }
    }
}
