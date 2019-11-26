package se.experis.MeFitBackend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class Exercise {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String exerciseId;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public String targetMuscle;

    @Column
    public String imageLink;

    @Column
    public String videoLink;

    public Exercise() {
    }

    public Exercise(String name, String description, String targetMuscle, String imageLink, String videoLink) {
        this.name = name;
        this.description = description;
        this.targetMuscle = targetMuscle;
        this.imageLink = imageLink;
        this.videoLink = videoLink;
    }

    public Exercise(String exerciseId, String name, String description, String targetMuscle, String imageLink, String videoLink) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.description = description;
        this.targetMuscle = targetMuscle;
        this.imageLink = imageLink;
        this.videoLink = videoLink;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetMuscle() {
        return targetMuscle;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTargetMuscle(String targetMuscle) {
        this.targetMuscle = targetMuscle;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
