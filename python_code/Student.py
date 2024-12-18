import json

from Course import Course
from Transcript import Transcript

class Student(object):
    def __init__(self, student_id: str):
       # super().init(name, address)
        self.__student_id = student_id
        self.__transcript = Transcript(student_id)
        self.__schedule_status=False

        with open(f"students/{student_id}.json", "r") as f:
            data = json.load(f)
            self.__semester = data["SSemester"]

    def set_status(self, status: bool):
        self.__schedule_status = status

    def get_status(self):
        return bool(self.__schedule_status)

    def get_student_id(self) -> str:
        return self.__student_id

    def set_student_id(self, student_id: str):
        self.__student_id = student_id

    def get_transcript(self) -> Transcript:
        return self.__transcript

    def set_transcript(self, transcript: Transcript):
        self.__transcript = transcript

    def getSemester(self) -> str:
        return self.__semester

    def set_semester(self, semester: str):
        self.__semester = semester

    def read_name(self):
        with open(f"students/{self.__student_id}.json", "r") as f:
            data = json.load(f)
            first_name = data["FirstName"]
            last_name = data["LastName"]

        print(f"Full Name is: {first_name} {last_name}")

    def update_status(self):
        with open(f"students/{self.__student_id}.json", "r") as f:
            data = json.load(f)

        data.pop("ScheduleStatus")
        data["ScheduleStatus"] = "Approved"

        with open(f"students/{self.__student_id}.json", "w") as f:
            json.dump(data, f)

    def update_schedule(self, course: Course):
        self.__transcript.add_course_to_schedule(course)

        with open(f"students/{self.__student_id}.json", "r") as f:
            data = json.load(f)
        
        schedule = data["Schedule"]
        schedule.append(course.get_course_id())
        data["Schedule"]=schedule

        with open(f"students/{self.__student_id}.json", "w") as f:
            json.dump(data, f)