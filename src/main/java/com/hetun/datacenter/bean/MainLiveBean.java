package com.hetun.datacenter.bean;

import com.alibaba.fastjson2.annotation.JSONField;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name = "tripartiteLiveBean")
public class MainLiveBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JSONField(name = "Liga")
    private String liga;
    @JSONField(name = "Opp1")
    private String opp1;

    @JSONField(name = "Opp2")
    private String opp2;

    @JSONField(name = "Sport")
    private String sport;

    @JSONField(name = "O1Logo")
    private String o1Logo;
    @JSONField(name = "O2Logo")
    private String o2Logo;
    @JSONField(name = "Opp1ID")
    private String opp1ID;
    @JSONField(name = "Opp2ID")
    private String opp2ID;
    @JSONField(name = "LeagueID")
    private String leagueID;
    @JSONField(name = "vid")
    private String vid;
    @JSONField(name = "Scores")
    private String scores;
    @JSONField(name = "SportId")
    private int sportId;
    @JSONField(name = "Time")
    private String time;

    private Long longTime;
    @JSONField(name = "StreamName")
    private String streamName;
    @JSONField(name = "Matchtime")
    private long matchtime;
    @JSONField(name = "StreamType")
    private String streamType;
    @JSONField(name = "StreamFrom")
    private String streamFrom;
    @JSONField(name = "StreamInfo")

    private StreamInfo streamInfo;
    @JSONField(name = "Odds")
    private Odds odds;
    @JSONField(name = "MatchInfo")

    private MatchInfo matchInfo;
    @JSONField(name = "StreamSource")
    private int streamSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    @Embeddable
    public static class MatchInfo {
        @JSONField(name = "Section")
        private int section;
        @JSONField(name = "PlayingTime")
        private int playingTime;
        @JSONField(name = "AddTime")
        private String addTime;
        @JSONField(name = "YellowCard1")
        private String yellowCard1;
        @JSONField(name = "YellowCard2")
        private String yellowCard2;
        @JSONField(name = "RedCard1")
        private String redCard1;
        @JSONField(name = "RedCard2")
        private String redCard2;
        @JSONField(name = "Status")
        private int status;

        public void setSection(int Section) {
            this.section = Section;
        }

        public int getSection() {
            return section;
        }

        public void setPlayingTime(int PlayingTime) {
            this.playingTime = PlayingTime;
        }

        public int getPlayingTime() {
            return playingTime;
        }

        public void setAddTime(String AddTime) {
            this.addTime = AddTime;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setYellowCard1(String YellowCard1) {
            this.yellowCard1 = YellowCard1;
        }

        public String getYellowCard1() {
            return yellowCard1;
        }

        public void setYellowCard2(String YellowCard2) {
            this.yellowCard2 = YellowCard2;
        }

        public String getYellowCard2() {
            return yellowCard2;
        }

        public void setRedCard1(String RedCard1) {
            this.redCard1 = RedCard1;
        }

        public String getRedCard1() {
            return redCard1;
        }

        public void setRedCard2(String RedCard2) {
            this.redCard2 = RedCard2;
        }

        public String getRedCard2() {
            return redCard2;
        }

        public void setStatus(int Status) {
            this.status = Status;
        }

        public int getStatus() {
            return status;
        }

    }

    @Embeddable
    public static class StreamInfo {

        private boolean state;
        private int kbps;

        private Video video;

        private Audio audio;

        @Embeddable
        public static class Audio {
            @Column(name = "audio_codec")
            private String codec;
            @Column(name = "audio_sample_rate")
            private int sample_rate;
            @Column(name = "audio_channel")
            private int channel;
            @Column(name = "audio_profile")
            private String profile;

            public void setCodec(String codec) {
                this.codec = codec;
            }

            public String getCodec() {
                return codec;
            }

            public void setSample_rate(int sample_rate) {
                this.sample_rate = sample_rate;
            }

            public int getSample_rate() {
                return sample_rate;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public int getChannel() {
                return channel;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public String getProfile() {
                return profile;
            }

        }

        @Embeddable
        public static class Video {

            @Column(name = "video_codec")
            private String codec;
            @Column(name = "video_profile")
            private String profile;
            @Column(name = "video_level")
            private String level;

            public void setCodec(String codec) {
                this.codec = codec;
            }

            public String getCodec() {
                return codec;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public String getProfile() {
                return profile;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getLevel() {
                return level;
            }

        }

        public void setState(boolean state) {
            this.state = state;
        }

        public boolean getState() {
            return state;
        }

        public void setKbps(int kbps) {
            this.kbps = kbps;
        }

        public int getKbps() {
            return kbps;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Video getVideo() {
            return video;
        }

        public void setAudio(Audio audio) {
            this.audio = audio;
        }

        public Audio getAudio() {
            return audio;
        }

    }

    @Embeddable
    public static class Odds {
        @JSONField(name = "Euro")

        private Euro euro;
        @JSONField(name = "Letgoal")

        private Letgoal letgoal;

        @JSONField(name = "Score")
        private Score score;

        @Embeddable
        public static class Score {
            @JSONField(name = "Home")
            @Column(name = "score_home")
            private double home;
            @JSONField(name = "Draw")
            @Column(name = "score_draw")
            private double draw;
            @JSONField(name = "Away")
            @Column(name = "score_away")
            private double scoreAway;

            public void setHome(double Home) {
                this.home = Home;
            }

            public double getHome() {
                return home;
            }

            public void setDraw(double Draw) {
                this.draw = Draw;
            }

            public double getDraw() {
                return draw;
            }

            public double getScoreAway() {
                return scoreAway;
            }

            public void setScoreAway(double scoreAway) {
                this.scoreAway = scoreAway;
            }
        }

        @Embeddable
        public static class Letgoal {

            @Column(name = "letgoal_home")
            @JSONField(name = "Home")
            private double home;
            @Column(name = "letgoal_draw")
            @JSONField(name = "Draw")
            private String draw;
            @JSONField(name = "Away")
            @Column(name = "letgoal_away")
            private double away;

            public void setHome(double Home) {
                this.home = Home;
            }

            public double getHome() {
                return home;
            }

            public void setDraw(String Draw) {
                this.draw = Draw;
            }

            public String getDraw() {
                return draw;
            }


        }

        @Embeddable
        public static class Euro {

            @JSONField(name = "Home")
            private double home;
            @JSONField(name = "Draw")
            private double draw;
            @JSONField(name = "Away")
            private double away;

            public void setHome(double Home) {
                this.home = Home;
            }

            public double getHome() {
                return home;
            }

            public void setDraw(double Draw) {
                this.draw = Draw;
            }

            public double getDraw() {
                return draw;
            }

            public void setAway(double Away) {
                this.away = Away;
            }

            public double getAway() {
                return away;
            }

        }

        public void setEuro(Euro Euro) {
            this.euro = Euro;
        }

        public Euro getEuro() {
            return euro;
        }

        public void setLetgoal(Letgoal Letgoal) {
            this.letgoal = Letgoal;
        }

        public Letgoal getLetgoal() {
            return letgoal;
        }

        public void setScore(Score Score) {
            this.score = Score;
        }

        public Score getScore() {
            return score;
        }

    }

    public void setLiga(String Liga) {
        this.liga = Liga;
    }

    public String getLiga() {
        return liga;
    }

    public void setOpp1(String Opp1) {
        this.opp1 = Opp1;
    }

    public String getOpp1() {
        return opp1;
    }

    public void setOpp2(String Opp2) {
        this.opp2 = Opp2;
    }

    public String getOpp2() {
        return opp2;
    }

    public void setSport(String Sport) {
        this.sport = Sport;
    }

    public String getSport() {
        return sport;
    }

    public void setO1Logo(String O1Logo) {
        this.o1Logo = O1Logo;
    }

    public String getO1Logo() {
        return o1Logo;
    }

    public void setO2Logo(String O2Logo) {
        this.o2Logo = O2Logo;
    }

    public String getO2Logo() {
        return o2Logo;
    }

    public void setOpp1ID(String Opp1ID) {
        this.opp1ID = Opp1ID;
    }

    public String getOpp1ID() {
        return opp1ID;
    }

    public void setOpp2ID(String Opp2ID) {
        this.opp2ID = Opp2ID;
    }

    public String getOpp2ID() {
        return opp2ID;
    }

    public void setLeagueID(String LeagueID) {
        this.leagueID = LeagueID;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVid() {
        return vid;
    }

    public void setScores(String Scores) {
        this.scores = Scores;
    }

    public String getScores() {
        return scores;
    }

    public void setSportId(int SportId) {
        this.sportId = SportId;
    }

    public int getSportId() {
        return sportId;
    }

    public void setTime(String time) {
        try {
            setLongTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .parse(Calendar.getInstance().get(1) + "-" + time).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setStreamName(String StreamName) {
        this.streamName = StreamName;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setMatchtime(long Matchtime) {
        this.matchtime = Matchtime;
    }

    public long getMatchtime() {
        return matchtime;
    }

    public void setStreamType(String StreamType) {
        this.streamType = StreamType;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamFrom(String StreamFrom) {
        this.streamFrom = StreamFrom;
    }

    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamInfo(StreamInfo StreamInfo) {
        this.streamInfo = StreamInfo;
    }

    public StreamInfo getStreamInfo() {
        return streamInfo;
    }

    public void setOdds(Odds Odds) {
        this.odds = Odds;
    }

    public Odds getOdds() {
        return odds;
    }

    public void setMatchInfo(MatchInfo MatchInfo) {
        this.matchInfo = MatchInfo;
    }

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setStreamSource(int StreamSource) {
        this.streamSource = StreamSource;
    }

    public int getStreamSource() {
        return streamSource;
    }


}
