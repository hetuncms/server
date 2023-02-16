package com.hetun.datacenter.bean;

import com.hetun.datacenter.tripartite.bean.LeagueBean;

public class FootballPlayInfoBean extends PlayInfoBean{
    private LeagueBean.Result footballLeague;

    public LeagueBean.Result getFootballLeague() {
        return footballLeague;
    }

    public void setFootballLeague(LeagueBean.Result footballLeague) {
        this.footballLeague = footballLeague;
    }
}
