package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "predictions")
@Setter
@Getter
public class PredictionsBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String leagueName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(nullable = false,columnDefinition = "timestamp(0)")
    private Date startTime;
    @Column(nullable = false)
    private String boDan;
    @Column(nullable = false)
    private String duYing;
    @Column(nullable = true)
    private String bigBall;
    @Column(nullable = true)
    private String smallBall;
    @Column(nullable = true)
    private Integer goal;
    @Column(nullable = false)
    private String  bet;
    @Column(nullable = false)
    private String  betUrl;
    @Column(nullable = false)
    private String mainTeamName;
    @Column(nullable = false)
    private String mainTeamImg;
    @Column(nullable = false)
    private String visitTeamName;
    @Column(nullable = false)
    private String visitTeamImg;
}
