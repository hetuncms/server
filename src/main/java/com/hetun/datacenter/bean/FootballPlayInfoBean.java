package com.hetun.datacenter.bean;

import com.hetun.datacenter.tripartite.bean.LeagueBean;

public class FootballPlayInfoBean extends PlayInfoBean{
    private LeagueBean.LeagueResult footballLeague;

    public LeagueBean.LeagueResult getFootballLeague() {
        return footballLeague;
    }

    public void setFootballLeague(LeagueBean.LeagueResult footballLeague) {
        this.footballLeague = footballLeague;
    }
}
