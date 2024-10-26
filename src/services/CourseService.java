package services;

import course.*;
import user.Student;
import user.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private static final List<Course> courses = new ArrayList<>();
    protected static int courseCounter = 1;

    protected static List<Course> getCourses() {
        return courses;
    }

    protected static void removeCourse(Course course) {
        courses.remove(course);
    }

    protected static void createCourse(String courseId, String courseName, String courseTime, double credit, int period, Teacher teacher) {
        Course course = new Course(courseId, courseName, courseTime, credit, period, teacher);
        courses.add(course);
        courseCounter++;
    }

    protected static Course getCourse(String courseId) {
        for (Course course : courses) {
            if (course.getCourseID().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    protected static List<Course> getCourses(Teacher teacher) {
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTeacher().equals(teacher)) {
                teacherCourses.add(course);
            }
        }
        return teacherCourses;
    }

    protected static List<Course> getCourses(Student student) {
        List<Course> studentCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getEnrolledStudents().contains(student)) {
                studentCourses.add(course);
            }
        }
        return studentCourses;
    }

    protected static void removeStudentFromCourse(Student student, String courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
            course.removeStudent(student);
        }
    }

    protected static int getCourseCounter() {
        return courseCounter;
    }
}