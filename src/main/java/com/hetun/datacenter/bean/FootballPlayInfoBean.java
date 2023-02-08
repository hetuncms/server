package com.hetun.datacenter.bean;

import com.hetun.datacenter.tripartite.bean.FootballLeagueBean;

public class FootballPlayInfoBean extends PlayInfoBean{
    private FootballLeagueBean.Result footballLeague;

    public FootballLeagueBean.Result getFootballLeague() {
        return footballLeague;
    }

    public void setFootballLeague(FootballLeagueBean.Result footballLeague) {
        this.footballLeague = footballLeague;
    }
}
