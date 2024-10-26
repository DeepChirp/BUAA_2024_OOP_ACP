package services;

import course.Course;
import except.UserListCourseScheduleException;
import user.*;

import java.util.Comparator;
import java.util.List;

import static services.UserService.*;
import static services.CourseService.*;
import static utils.ValidationUtils.*;

public class UserListCourseSchedule {
    protected static void listCourseSchedule(String... studentID) throws UserListCourseScheduleException {
        User currentUser = getCurrentUser();
        if (noCurrentUser()) {
            throw new UserListCourseScheduleException("No one is online");
        }

        Student student;
        if (studentID.length == 0) {
            if (!(currentUser instanceof Student)) {
                throw new UserListCourseScheduleException("Permission denied");
            }
            student = (Student) currentUser;
        } else {
            if (!(currentUser instanceof Administrator)) {
                throw new UserListCourseScheduleException("Permission denied");
            }
            if (!isValidUserID(studentID[0])) {
                throw new UserListCourseScheduleException("Illegal user id");
            }
            User user = getUser(studentID[0]);
            if (user == null) {
                throw new UserListCourseScheduleException("User does not exist");
            }
            if (!(user instanceof Student)) {
                throw new UserListCourseScheduleException("User id does not belong to a Student");
            }
            student = (Student) user;
        }

        List<Course> courses = getCourses(student);
        if (courses.isEmpty()) {
            throw new UserListCourseScheduleException("Student does not select course");
        }

        courses = courses.stream()
                .sorted(new CourseTimeComparator())
                .toList();

        for (Course course : courses) {
            System.out.println(course.getCourseTime() + " " + course.getCourseName() + " " + course.getCredit() + " "
                    + course.getPeriod() + " " + course.getTeacher().getName());
        }
        System.out.println("List course schedule success");
    }

    static class CourseTimeComparator implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            String[] time1 = c1.getCourseTime().split("_|-");
            String[] time2 = c2.getCourseTime().split("_|-");

            int day1 = Integer.parseInt(time1[0]);
            int day2 = Integer.parseInt(time2[0]);

            if (day1 != day2) {
                return Integer.compare(day1, day2);
            }

            int startTime1 = Integer.parseInt(time1[1]);
            int startTime2 = Integer.parseInt(time2[1]);

            return Integer.compare(startTime1, startTime2);
        }
    }
}
