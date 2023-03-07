package com.hetun.datacenter.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hetun.datacenter.tripartite.bean.LeagueBean;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "live_table")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIgnoreProperties(value = {"liveSource", "old", "hot", "upDataTime", "upDataCount", "leagueId"})
public class LiveItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String liveId;
    private Integer liveType;
    private Integer liveStatus;
//    private Integer leagueId;
    private String liveSource;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date matchStartTime;
    @Column(nullable = false)
    private boolean top;
    private String leftName;
    private String rightName;
    private String leftImg;
    private String rightImg;
    private String gameName;
    private Long upDataTime;
    private Long upDataCount;
    @Column(nullable = false)
    private boolean hot;
    @Column(nullable = false)
    private boolean old;
    private Integer hasOdds;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> visitingScore;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> mainScore;
    @Column(nullable = false)
    private boolean liveing;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> leftTeamScore;
    @Column(columnDefinition = "text")
    @Type(JsonType.class)
    private List<Integer> rightTeamScore;
    @OneToOne(targetEntity = LeagueBean.LeagueResult.class)
    @JoinColumn(name = "league_id",referencedColumnName = "id",
                foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    private LeagueBean.LeagueResult leagueResult;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LiveItem liveItem = (LiveItem) o;
        return id != null && Objects.equals(id, liveItem.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
