package services;

import course.Course;
import user.Student;
import except.SelectCourseException;

import java.util.Optional;

import static utils.ValidationUtils.isValidCourseID;
import static services.CourseService.*;
import static services.UserService.*;

public class CourseSelect {
    protected static void selectCourse(String courseId) throws SelectCourseException {
        if (noCurrentUser()) {
            throw new SelectCourseException("No one is online");
        }

        if (!getCurrentUser().getRole().equals("Student")) {
            throw new SelectCourseException("Permission denied");
        }

        if (!isValidCourseID(courseId)) {
            throw new SelectCourseException("Illegal course id");
        }

        Student student = (Student) getCurrentUser();

        Optional<Course> courseOpt = getCourses().stream()
                .filter(course -> course.getCourseID().equals(courseId))
                .findFirst();

        if (courseOpt.isEmpty()) {
            throw new SelectCourseException("Course does not exist");
        }

        Course course = courseOpt.get();

        for (Course c : getCourses()) {
            if (c.getEnrolledStudents().contains(student) && c.getCourseTime().equals(course.getCourseTime())) {
                throw new SelectCourseException("Course time conflicts");
            }
        }

        if (!course.addStudent(student)) {
            throw new SelectCourseException("Course capacity is full");
        }

        System.out.println("Select course success (courseId: " + courseId + ")");
    }
}