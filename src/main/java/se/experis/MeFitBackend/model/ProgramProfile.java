package se.experis.MeFitBackend.model;


import javax.persistence.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */

@Entity
@Table
public class ProgramProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProgramProfileId;

    @OneToOne
    @JoinColumn(name="program_fk")
    private Program programFk;

    @ManyToOne
    @JoinColumn(name="profile_fk")
    private Profile profileFk;

    public ProgramProfile(Program programFk, Profile profileFk) {
        this.programFk = programFk;
        this.profileFk = profileFk;
    }

    public ProgramProfile() {
    }

    public int getProgramProfileId() {
        return ProgramProfileId;
    }

    public Program getProgramFk() {
        return programFk;
    }

    public Profile getProfileFk() {
        return profileFk;
    }
}
