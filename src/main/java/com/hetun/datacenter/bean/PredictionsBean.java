package com.hetun.datacenter.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hetun.datacenter.tools.DateToLongSerializer;
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
    private String leagueName;
    private String changci;
    private String mainTeamName;
    private String mainTeamImg;
    private String visitTeamName;
    private String visitTeamImg;

    @JsonSerialize(using = DateToLongSerializer.class)
    private Date startTime;
    private String boDan;
    private String duYing;
    private String bigBall;
    private String smallBall;
    private Integer goal;
    private Boolean goalsFromBothSides;
}
