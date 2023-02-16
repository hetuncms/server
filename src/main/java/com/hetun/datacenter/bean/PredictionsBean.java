package com.hetun.datacenter.bean;

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
    private Date start_time;
    private String boDan;
    private String duYing;
    private String bigBall;
    private String smallBall;
    private String goalsFromBothSides;
}
