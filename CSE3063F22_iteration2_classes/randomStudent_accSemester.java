import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class randomStudent_accSemester {
    private int semester;
    private int number;
    private static final DecimalFormat df = new DecimalFormat("0.00");
 


public randomStudent_accSemester(){
  
}

public int getSemester(){
    return semester;
}

public int getNumber(){
    return number;
}

public void generate_randomStudent(String semester, int number) throws IOException, ParseException, java.text.ParseException{
    String[] FName={"Zeynep", "Sena","Hazal","Bihter","Nilufer","Sule", "Beyza","Safa","Esref","Emre","Nur","Yagmur","Betul","Merve","Irem"};
    String[] LName={"Destan","Yilmaz","Boylan","Akdem","Koca","Cabuk","San","Yildirim","Bakirci","Uslu","Ersan","Kalem","Keskin","Tunc","Okumus"};

    int student_number=number;
    List<String> ids=new ArrayList<String>();
  
    for(int i=0;i<student_number;i++){
        JSONObject jsonObject = new JSONObject();

        //Random studentID
        String random_id = "150120";
        for(int x=0; x<3; x++){
            random_id=random_id+String.valueOf(ThreadLocalRandom.current().nextInt(1,9));
        }

        if(ids.contains(random_id)){
            System.out.println("This ID is used!");
            continue;
        }
        ids.add(random_id);
        
        jsonObject.put("StudentID", random_id);
        
        //Random first name
        jsonObject.put("FirstName", FName[(new Random()).nextInt(FName.length)]);

        //Random last name
        jsonObject.put("LastName", LName[(new Random()).nextInt(LName.length)]);

        //Random semester
     //   String random_sem = String.valueOf(ThreadLocalRandom.current().nextInt(1,9));
        jsonObject.put("SSemester", semester);

        //Random passed courses
        JSONArray passed_courses = new JSONArray();
        jsonObject.put("PassedCourses",passed_courses);

        //Random schedule
        JSONArray schedule = new JSONArray();
        jsonObject.put("Schedule",schedule);

        //Random GPA
        double random_GPA = ThreadLocalRandom.current().nextDouble(1,4);
        String String = df.format(random_GPA);
        String str =String.replace(",",".");//Replacing the comma in the GPA value with a period
        String GPA = String.valueOf(str);
        jsonObject.put("GPA", GPA);

        // Schedule status
        jsonObject.put("ScheduleStatus", "NotApproved");

        FileWriter file = new FileWriter("students/"+random_id+".json");
        file.write(jsonObject.toJSONString());
        file.close();

        //Random passed courses
        File dir = new File("courses/");
        File[] courses = dir.listFiles();
        int times = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(semester)*6);

        
        Student std = new Student(random_id);
        
        for(int n=0;n<times;n++){

        File course = courses[ThreadLocalRandom.current().nextInt(courses.length)];
        String course_id = String.valueOf(course);

        course_id = course.getAbsolutePath().substring(course.getAbsolutePath().lastIndexOf("\\")+1);
        course_id = course_id.split(".json")[0];
        

        Course c = new Course(course_id);
        System.out.println("Course id:"+course_id);
        Registration register_toPassedCourses = new Registration(std, c);
        CourseRegistrationSystem forAdding_PassedCourse = new CourseRegistrationSystem();
        forAdding_PassedCourse.register(register_toPassedCourses,semester);

       Object obj = new JSONParser().parse(new FileReader("students/"+std.getStudentId()+".json"));
       JSONObject jo = (JSONObject) obj;
        jo.put("PassedCourses",jo.get("Schedule"));
        
        FileWriter f = new FileWriter("students/"+std.getStudentId()+".json",false);
        f.write(jo.toJSONString());
        f.close();
    }
    
    Object obj = new JSONParser().parse(new FileReader("students/"+std.getStudentId()+".json"));
    JSONObject jo = (JSONObject) obj;
    jo.remove("Schedule");
    JSONArray sch = new JSONArray();
    jo.put("Schedule",sch);
    FileWriter f = new FileWriter("students/"+std.getStudentId()+".json",false);
    f.write(jo.toJSONString());
    f.close();

    }

}

}

