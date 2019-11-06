package se.experis.MeFitBackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programId;

    @Column
    private String name;

    @Column
    private String category;

    public Program(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Program() {
    }

    public int getProgramId() {
        return programId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

}


