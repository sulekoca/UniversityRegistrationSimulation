import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

public class CourseRegistrationSystem {
    
    private List<Course> courses;
    private List<CourseSection> courseSections;
    private List<Student> students;
    private List<Staff> staff;
    private List<Registration> registrations;
    private List<Grade> grades;
    
    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.courseSections = new ArrayList<>();
        this.students = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.registrations = new ArrayList<>();
        this.grades = new ArrayList<>();
    }
    
   

  public void register(Registration registration, String semester) throws FileNotFoundException, IOException, ParseException, java.text.ParseException{
     
        if(registration.isProvidePrereqs()== true){
            
            if(registration.isEnoughSeatLimit()==true){
              
                
                if(registration.getStudent().getTranscript().GPAchecked()==true){
                    
                     if(registration.getStudent().getTranscript().isCourseNotPassedBefore(registration.getCourses())==true){

                        if(registration.isSemesterCorrect()){

                        
                            System.out.println("Atama gerçekleşti");
                            registration.getStudent().updateSchedule(registration.getCourses());
               
                        }


                        else{
                        
                            Log log = new Log();
                            log.logging_error("SemestorError",semester);
                            System.out.println("Semester Error");
                        }
                }
                else{
                  System.out.println("Course is already passed");
                  Log log = new Log();
                  log.logging_error("AlreadyPassedError",semester);
                }

                    
                }
                else{
                    System.out.println("Can not register new course.");
                    Log log = new Log();
                    log.logging_error("GPAError",semester);
                    System.out.println("GPA Error");
                }

            }
            
            else{
                    Log log = new Log();
                    log.logging_error("SeatLimitError",semester);
                    System.out.println("Not enough limits");
    
            }
        }
   
        else{
            Log log = new Log();
            log.logging_error("PrerequisiteError",semester);
            System.out.println("No Prerequisite");
        }
            
    }



    public void afterReg(Student std) throws FileNotFoundException, IOException, ParseException{
        
        List<Course> student_schedule= std.getTranscript().getSchedule();
        String[] schedule = new String[student_schedule.size()];
 
        
    Advisor advisor = new Advisor();
    advisor.ApproveRegistration(std);
    if(std.getStatus()){

       for(int i=0;i<student_schedule.size();i++){
            System.out.println(String.valueOf(student_schedule.get(i).getCourseId()));
            schedule[i]=String.valueOf(student_schedule.get(i).getCourseId());
            System.out.println(schedule[i]);
            std.updateJSON(schedule[i]);
        }

        std.updateStatus();

    }
    else{
        Log log = new Log();
        log.logging_error("NotApproved", std.getSemester());
        System.out.println("Advisor did not approved schedule!");
    }

    }

}

