//package se.experis.MeFitBackend;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import se.experis.MeFitBackend.model.*;
//import se.experis.MeFitBackend.repositories.*;
//
//import javax.annotation.PostConstruct;
//import java.util.Date;
//
///*
//    rjanul created on 2019-11-13
//*/
//@Component
//public class Init {
//
//    @Autowired
//    private final GoalRepository goalRepository;
//    private final GoalWorkoutRepository goalWorkoutRepository;
//    private final ProfileRepository profileRepository;
//    private final WorkoutRepository workoutRepository;
//    private final ProgramGoalRepository programGoalRepository;
//    private final ProgramRepository programRepository;
//    private final ProgramWorkoutRepository programWorkoutRepository;
//    private final AddressRepository addressRepository;
//    private final EndUserRepository endUserRepository;
//    private final ExerciseRepository exerciseRepository;
//    private final SetRepository setRepository;
//
//    public Init(GoalRepository goalRepository,
//                GoalWorkoutRepository goalWorkoutRepository,
//                ProfileRepository profileRepository,
//                WorkoutRepository workoutRepository,
//                ProgramGoalRepository programGoalRepository,
//                ProgramRepository programRepository,
//                ProgramWorkoutRepository programWorkoutRepository,
//                AddressRepository addressRepository,
//                EndUserRepository endUserRepository,
//                ExerciseRepository exerciseRepository,
//                SetRepository setRepository)
//    {
//        this.goalRepository = goalRepository;
//        this.goalWorkoutRepository = goalWorkoutRepository;
//        this.profileRepository = profileRepository;
//        this.workoutRepository = workoutRepository;
//        this.programGoalRepository = programGoalRepository;
//        this.programRepository = programRepository;
//        this.programWorkoutRepository = programWorkoutRepository;
//        this.addressRepository = addressRepository;
//        this.endUserRepository = endUserRepository;
//        this.exerciseRepository = exerciseRepository;
//        this.setRepository = setRepository;
//    }
//
//    @PostConstruct
//    private void init() {
//
//        Address adr = addressRepository.save(new Address("Street", "City", "Country", 9999));
//        Address adr2 = addressRepository.save(new Address("Street", "City", "Country", 9999));
//        Address adr3 = addressRepository.save(new Address("Street", "City", "Country", 9999));
//
//        EndUser usr = endUserRepository.save(new EndUser("Firstname", "Lastname", "password", "email@email"));
//        EndUser usr2 = endUserRepository.save(new EndUser("Firstname2", "Lastname2", "password2", "email2@email"));
//
//        Profile prof = profileRepository.save(new Profile(100, 180, 30, "Begginer", usr, adr));
//        Profile prof2 = profileRepository.save(new Profile(100, 180, 30, "Begginer", usr2, adr2));
//
//        Exercise ex = exerciseRepository.save(new Exercise("name", "description", "targetMuslce", "imageLink", "videoLink"));
//        Exercise ex2 = exerciseRepository.save(new Exercise("name", "description", "targetMuslce", "imageLink", "videoLink"));
//        Exercise ex3 = exerciseRepository.save(new Exercise("name", "description", "targetMuslce", "imageLink", "videoLink"));
//        Exercise ex4 = exerciseRepository.save(new Exercise("name", "description", "targetMuslce", "imageLink", "videoLink"));
//
//        Workout workout = workoutRepository.save(new Workout("name", "type", prof));
//        Workout workout2 = workoutRepository.save(new Workout("name", "type", prof2));
//        Workout workout3 = workoutRepository.save(new Workout("name", "type", null));
//        Workout workout4 = workoutRepository.save(new Workout("name", "type", null));
//
//
//
//        Set set = setRepository.save(new Set(10, 3, ex, workout));
//        Set set2 = setRepository.save(new Set(10, 3, ex2, workout));
//        Set set3 = setRepository.save(new Set(10, 3, ex3, workout));
//        Set set4 = setRepository.save(new Set(10, 3, ex4, workout));
//        Set set5 = setRepository.save(new Set(10, 3, ex, workout2));
//        Set set6 = setRepository.save(new Set(10, 3, ex, workout3));
//        Set set7 = setRepository.save(new Set(10, 3, ex, workout4));
//
//
//        Program program = programRepository.save(new Program("name", "category", prof));
//        Program program2 = programRepository.save(new Program("name", "category", prof));
//
//        ProgramWorkout programWorkout = programWorkoutRepository.save(new ProgramWorkout(program, workout));
//        ProgramWorkout programWorkout2 = programWorkoutRepository.save(new ProgramWorkout(program, workout2));
//        ProgramWorkout programWorkout3 = programWorkoutRepository.save(new ProgramWorkout(program, workout3));
//        ProgramWorkout programWorkout4 = programWorkoutRepository.save(new ProgramWorkout(program2, workout));
//        ProgramWorkout programWorkout5 = programWorkoutRepository.save(new ProgramWorkout(program2, workout4));
//
//
//        Goal goal = goalRepository.save(new Goal(false, new Date(), prof));
//        Goal goal2 = goalRepository.save(new Goal(false, new Date(), prof2));
//
//
//        ProgramGoal programGoal = programGoalRepository.save(new ProgramGoal(false, goal, program));
//
//        GoalWorkout goalWorkout = goalWorkoutRepository.save(new GoalWorkout(false, programGoal, workout));
//        GoalWorkout goalWorkout2 = goalWorkoutRepository.save(new GoalWorkout(false, programGoal, workout2));
//        GoalWorkout goalWorkout3 = goalWorkoutRepository.save(new GoalWorkout(false, programGoal, workout3));
//
//        GoalWorkout goalWorkout4 = goalWorkoutRepository.save(new GoalWorkout(false, goal2, workout));
//        GoalWorkout goalWorkout5 = goalWorkoutRepository.save(new GoalWorkout(false, goal2, workout4));
//        GoalWorkout goalWorkout6 = goalWorkoutRepository.save(new GoalWorkout(false, goal2, workout3));
//
//    }
//}
