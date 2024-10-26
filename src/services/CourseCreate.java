package services;

import user.Teacher;
import utils.ValidationUtils;
import except.CreateCourseException;

import static services.CourseService.*;
import static services.UserService.*;

public class CourseCreate {
    protected static void createCourse(String courseName, String courseTime, double credit, int period)
            throws CreateCourseException {

        if (noCurrentUser()) {
            throw new CreateCourseException("No one is online");
        }

        if (!getCurrentUser().getRole().equals("Teacher")) {
            throw new CreateCourseException("Permission denied");
        }

        Teacher teacher = (Teacher) getCurrentUser();

        long courseCount = getCourses().stream().filter(course -> course.getTeacher().equals(teacher))
                .count();
        if (courseCount >= 10) {
            throw new CreateCourseException("Course count reaches limit");
        }

        if (!ValidationUtils.isValidCourseName(courseName)) {
            throw new CreateCourseException("Illegal course name");
        }

        boolean courseNameExists = getCourses().stream()
                .anyMatch(
                        course -> course.getCourseName().equals(courseName) && course.getTeacher().equals(teacher));
        if (courseNameExists) {
            throw new CreateCourseException("Course name exists");
        }

        if (!ValidationUtils.isValidCourseTime(courseTime)) {
            throw new CreateCourseException("Illegal course time");
        }

        String[] timeParts = courseTime.split("_");
        // int dayOfWeek = Integer.parseInt(timeParts[0]);
        String[] periodParts = timeParts[1].split("-");
        int startPeriod;
        int endPeriod;
        try {
            startPeriod = Integer.parseInt(periodParts[0]);
            endPeriod = Integer.parseInt(periodParts[1]);
        } catch (NumberFormatException e) {
            throw new CreateCourseException("Illegal course time");
        }

        if (startPeriod > endPeriod) {
            throw new CreateCourseException("Illegal course time");
        }

        boolean timeConflict = getCourses().stream()
                .anyMatch(course -> course.isTimeConflict(courseTime) && course.getTeacher().equals(teacher));

        if (timeConflict) {
            throw new CreateCourseException("Course time conflicts");
        }

        if (!ValidationUtils.isValidCredit(credit)) {
            throw new CreateCourseException("Illegal course credit");
        }

        if (!ValidationUtils.isValidPeriod(period)) {
            throw new CreateCourseException("Illegal course period");
        }

        String courseId = "C-" + getCourseCounter();
        CourseService.createCourse(courseId, courseName, courseTime, credit, period, teacher);

        System.out.println("Create course success (courseId: " + courseId + ")");
    }
}