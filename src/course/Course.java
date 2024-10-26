package course;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import user.*;

public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected String courseID;
    protected String courseName;
    protected String courseTime;
    protected double credit;
    protected int period;
    protected Teacher teacher;
    private final List<Student> enrolledStudents = new ArrayList<>();

    public Course(String courseID, String courseName, String courseTime, double credit, int period, Teacher teacher) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.credit = credit;
        this.period = period;
        this.teacher = teacher;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public double getCredit() {
        return credit;
    }

    public int getPeriod() {
        return period;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean addStudent(Student student) {
        if (enrolledStudents.size() >= 30) {
            return false;
        }
        return enrolledStudents.add(student);
    }

    public boolean removeStudent(Student student) {
        return enrolledStudents.remove(student);
    }

    public boolean isTimeConflict(String courseTime) {
        String[] timeParts = courseTime.split("_");
        int dayOfWeek = Integer.parseInt(timeParts[0]);
        String[] periodParts = timeParts[1].split("-");
        int startPeriod = Integer.parseInt(periodParts[0]);
        int endPeriod = Integer.parseInt(periodParts[1]);

        String[] existingTimeParts = this.courseTime.split("_");
        int existingDayOfWeek = Integer.parseInt(existingTimeParts[0]);
        String[] existingPeriodParts = existingTimeParts[1].split("-");
        int existingStartPeriod = Integer.parseInt(existingPeriodParts[0]);
        int existingEndPeriod = Integer.parseInt(existingPeriodParts[1]);

        return dayOfWeek == existingDayOfWeek &&
                (startPeriod <= existingEndPeriod && endPeriod >= existingStartPeriod);
    }
}