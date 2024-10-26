package services;

import course.Course;
import user.Teacher;
import user.User;
import except.CourseBatchException;

import static services.UserService.*;
import static services.CourseService.*;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CourseBatchService implements Serializable {

    public static void outputCourseBatch(String filePath) throws CourseBatchException {
        User currentUser = getCurrentUser();
        if (noCurrentUser()) {
            throw new CourseBatchException("No one is online");
        }
        if (!(Objects.equals(currentUser.getRole(), "Teacher"))) {
            throw new CourseBatchException("Permission denied");
        }

        Teacher teacher = (Teacher) currentUser;
        List<Course> teacherCourses = getCourses().stream()
                .filter(course -> course.getTeacher().equals(teacher))
                .sorted(Comparator.comparingInt(course -> Integer.parseInt(course.getCourseID().substring(2))))
                .toList();

        File directory = new File("./data/");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("./data/" + filePath))) {
            dos.writeInt(teacherCourses.size());
            for (Course course : teacherCourses) {
                dos.writeUTF(course.getCourseName());
                dos.writeUTF(course.getCourseTime());
                dos.writeDouble(course.getCredit());
                dos.writeInt(course.getPeriod());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Output course batch success");
    }

    public static void inputCourseBatch(String filePath) throws CourseBatchException {
        User currentUser = getCurrentUser();
        if (noCurrentUser()) {
            throw new CourseBatchException("No one is online");
        }
        if (!(Objects.equals(currentUser.getRole(), "Teacher"))) {
            throw new CourseBatchException("Permission denied");
        }

        File file = new File("./data/" + filePath);
        if (!file.exists()) {
            throw new CourseBatchException("File does not exist");
        }
        if (file.isDirectory()) {
            throw new CourseBatchException("File is a directory");
        }

        Teacher teacher = (Teacher) currentUser;
        long courseCount = getCourses().stream().filter(course -> course.getTeacher().equals(teacher)).count();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String courseName = dis.readUTF();
                String courseTime = dis.readUTF();
                double credit = dis.readDouble();
                int period = dis.readInt();
                String courseId = "C-" + getCourseCounter();
                if (courseCount >= 10) {
                    System.out.println("Course count reaches limit");
                    break;
                }
                if (getCourses().stream()
                        .anyMatch(c -> c.getCourseName().equals(courseName) && c.getTeacher().equals(teacher))) {
                    System.out.println("Course name already exists");
                    continue;
                }
                if (getCourses().stream()
                        .anyMatch(c -> c.isTimeConflict(courseTime) && c.getTeacher().equals(teacher))) {
                    System.out.println("Course time conflicts");
                    continue;
                }
                courseCount++;
                CourseService.createCourse(courseId, courseName, courseTime, credit, period, teacher);
                System.out.println("Create course success (courseId: " + courseId + ")");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Input course batch success");
    }
}