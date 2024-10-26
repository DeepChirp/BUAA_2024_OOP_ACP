package services;

import except.CourseListStudentException;
import course.Course;
import user.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static services.UserService.*;
import static services.CourseService.*;
import static utils.ValidationUtils.*;

public class CourseListStudent {
    protected static void listStudent(String courseId) throws CourseListStudentException {
        User currentUser = getCurrentUser();
        if (noCurrentUser()) {
            throw new CourseListStudentException("No one is online");
        }
        if (!(Objects.equals(currentUser.getRole(), "Teacher") || Objects.equals(currentUser.getRole(), "Administrator"))) {
            throw new CourseListStudentException("Permission denied");
        }
        if (!isValidCourseID(courseId)) {
            throw new CourseListStudentException("Illegal course id");
        }

        Course course = getCourse(courseId);
        if (course == null || (currentUser instanceof Teacher && !course.getTeacher().equals(currentUser))) {
            throw new CourseListStudentException("Course does not exist");
        }

        List<Student> students = course.getEnrolledStudents().stream()
                .sorted(new StudentComparator())
                .toList();

        if (students.isEmpty()) {
            throw new CourseListStudentException("Student does not select course");
        }

        for (User student : students) {
            System.out.println(student.getID() + ": " + student.getName());
        }
        System.out.println("List student success");
    }

    static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            String id1 = s1.getID();
            String id2 = s2.getID();

            int rank1 = getRank(id1);
            int rank2 = getRank(id2);

            if (rank1 != rank2) {
                return Integer.compare(rank1, rank2);
            }

            return id1.compareTo(id2);
        }

        private int getRank(String id) {
            if (id.startsWith("BY")) {
                return 5; // 博士生
            } else if (id.startsWith("SY")) {
                return 4; // 学术硕士
            } else if (id.startsWith("ZY")) {
                return 3; // 专业硕士
            } else if (id.matches("\\d{9}")) {
                return 2; // 本科生
            } else {
                return 1;
            }
        }
    }
}
