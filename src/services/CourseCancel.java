package services;

import course.Course;
import user.Student;
import user.Teacher;

import java.util.Optional;

import static utils.ValidationUtils.isValidCourseID;
import static services.CourseService.*;
import static services.UserService.*;
import except.CancelCourseException;

public class CourseCancel {
    protected static void cancelCourse(String courseId) throws CancelCourseException {

        if (noCurrentUser()) {
            throw new CancelCourseException("No one is online");
        }

        if (!isValidCourseID(courseId)) {
            throw new CancelCourseException("Illegal course id");
        }

        Optional<Course> courseOpt = getCourses().stream()
                .filter(course -> course.getCourseID().equals(courseId))
                .findFirst();

        if (courseOpt.isEmpty()) {
            throw new CancelCourseException("Course does not exist");
        }

        Course course = courseOpt.get();

        if (getCurrentUser().getRole().equals("Student")) {
            Student student = (Student) getCurrentUser();
            if (!course.getEnrolledStudents().contains(student)) {
                throw new CancelCourseException("Course does not exist");
            }
            course.getEnrolledStudents().remove(student);
            System.out.println("Cancel course success (courseId: " + courseId + ")");
        }

        if (getCurrentUser().getRole().equals("Teacher")) {
            Teacher teacher = (Teacher) getCurrentUser();
            if (!course.getTeacher().equals(teacher)) {
                throw new CancelCourseException("Course does not exist");
            }
            removeCourse(course);
            System.out.println("Cancel course success (courseId: " + courseId + ")");
        }

        if (getCurrentUser().getRole().equals("Administrator")) {
            removeCourse(course);
            System.out.println("Cancel course success (courseId: " + courseId + ")");
        }
    }
}