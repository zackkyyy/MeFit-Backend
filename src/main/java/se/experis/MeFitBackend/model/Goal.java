package se.experis.MeFitBackend.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goalId;

    @Column
    private Boolean achieved;

    @Column
    private Date endDate;

    public Goal(Boolean achieved, Date endDate) {
        this.achieved = achieved;
        this.endDate = endDate;
    }

    public Goal() {
    }

    public int getId() {
        return goalId;
    }

    public Boolean getAchieved() {
        return achieved;
    }

    public Date getEndDate() {
        return endDate;
    }
}
