package services;

import java.util.List;
import java.util.Objects;

import course.Course;
import except.CourseRemoveStudentException;
import user.*;

import static services.CourseService.*;
import static services.UserService.*;
import static utils.ValidationUtils.*;

public class CourseRemoveStudent {
        protected static void removeStudent(String userId, String... courseId) throws CourseRemoveStudentException {
        User currentUser = getCurrentUser();
        if (noCurrentUser()) {
            throw new CourseRemoveStudentException("No one is online");
        }
        if (!(Objects.equals(currentUser.getRole(), "Teacher") || Objects.equals(currentUser.getRole(), "Administrator"))) {
            throw new CourseRemoveStudentException("Permission denied");
        }
        if (!isValidUserID(userId)) {
            throw new CourseRemoveStudentException("Illegal user id");
        }

        User user = getUser(userId);
        if (user == null) {
            throw new CourseRemoveStudentException("User does not exist");
        }
        if (!(user instanceof Student)) {
            throw new CourseRemoveStudentException("User id does not belong to a Student");
        }

        if (courseId.length == 0) {
            removeStudentFromAllCourses((Student) user, currentUser);
        } else {
            removeStudentFromCourse((Student) user, courseId[0], currentUser);
        }
    }

    private static void removeStudentFromAllCourses(Student student, User currentUser) throws CourseRemoveStudentException {
        List<Course> courses = getCourses();
        boolean removed = false;

        for (Course course : courses) {
            if (currentUser instanceof Teacher && !course.getTeacher().equals(currentUser)) {
                continue;
            }
            if (course.removeStudent(student)) {
                removed = true;
            }
        }

        if (!removed) {
            throw new CourseRemoveStudentException("Student does not select course");
        }

        System.out.println("Remove student success");
    }

    private static void removeStudentFromCourse(Student student, String courseId, User currentUser) throws CourseRemoveStudentException {
        if (!isValidCourseID(courseId)) {
            throw new CourseRemoveStudentException("Illegal course id");
        }

        Course course = getCourse(courseId);
        if (course == null || (currentUser instanceof Teacher && !course.getTeacher().equals(currentUser))) {
            throw new CourseRemoveStudentException("Course does not exist");
        }

        if (!course.removeStudent(student)) {
            throw new CourseRemoveStudentException("Student does not select course");
        }

        System.out.println("Remove student success");
    }
}
