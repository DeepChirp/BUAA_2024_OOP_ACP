package services;

import except.ListCourseException;

import course.Course;
import user.Teacher;
import user.User;
import java.util.Comparator;
import java.util.List;

import static services.CourseService.*;
import static utils.ValidationUtils.isValidUserID;
import static services.UserService.*;

public class CourseList {
    protected static void listCourse(String... args) throws ListCourseException {
        if (noCurrentUser()) {
            throw new ListCourseException("No one is online");
        }

        if (args.length == 1) {
            if (!getCurrentUser().getRole().equals("Administrator")) {
                throw new ListCourseException("Permission denied");
            }

            String teacherID = args[0];
            if (!isValidUserID(teacherID)) {
                throw new ListCourseException("Illegal user id");
            }

            User user = getUser(teacherID);
            if (user == null) {
                throw new ListCourseException("User does not exist");
            }

            if (!user.getRole().equals("Teacher")) {
                throw new ListCourseException("User id does not belong to a Teacher");
            }

            Teacher teacher = (Teacher) user;
            List<Course> courseList = CourseService.getCourses(teacher).stream()
                    .sorted(Comparator.comparingInt(c -> Integer.parseInt(c.getCourseID().substring(2))))
                    .toList();

            if (courseList.isEmpty()) {
                throw new ListCourseException("Course does not exist");
            }

            for (Course course : courseList) {
                System.out.printf("%s %s %s %s %.1f %d\n",
                        course.getTeacher().getName(), course.getCourseID(), course.getCourseName(),
                        course.getCourseTime(),
                        course.getCredit(), course.getPeriod());
            }
            System.out.println("List course success");
            return;
        }

        List<Course> courseList = getCourses().stream()
                .sorted(Comparator.comparing((Course c) -> c.getTeacher().getName())
                        .thenComparingInt(c -> Integer.parseInt(c.getCourseID().substring(2))))
                .toList();

        if (courseList.isEmpty()) {
            throw new ListCourseException("Course does not exist");
        }

        if (getCurrentUser().getRole().equals("Teacher")) {
            Teacher teacher = (Teacher) getCurrentUser();
            List<Course> teacherCourses = CourseService.getCourses(teacher).stream()
                    .sorted(Comparator.comparingInt(course -> Integer.parseInt(course.getCourseID().substring(2))))
                    .toList();

            if (teacherCourses.isEmpty()) {
                throw new ListCourseException("Course does not exist");
            }

            for (Course course : teacherCourses) {
                System.out.printf("%s %s %s %.1f %d\n",
                        course.getCourseID(), course.getCourseName(), course.getCourseTime(),
                        course.getCredit(), course.getPeriod());
            }
        } else {
            for (Course course : courseList) {
                System.out.printf("%s %s %s %s %.1f %d\n",
                        course.getTeacher().getName(), course.getCourseID(), course.getCourseName(),
                        course.getCourseTime(), course.getCredit(), course.getPeriod());
            }
        }

        System.out.println("List course success");
    }
}