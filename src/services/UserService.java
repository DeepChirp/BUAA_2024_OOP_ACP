package services;

import except.*;
import user.*;

import java.util.*;

public class UserService {
    private static User currentUser = null;
    private static final LinkedList<User> onlineUsers = new LinkedList<>();
    private static final Map<String, User> users = new HashMap<>();

    protected static void setCurrentUser(User user) {
        currentUser = user;
    }

    protected static User getCurrentUser() {
        return currentUser;
    }

    protected static boolean noCurrentUser() {
        return currentUser == null;
    }

    protected static boolean isOnline(User user) {
        return onlineUsers.contains(user);
    }

    protected static void addOnlineUser(User user) {
        onlineUsers.add(user);
    }

    protected static void removeOnlineUser(User user) {
        onlineUsers.remove(user);
    }

    protected static void addUser(User user) {
        users.put(user.getID(), user);
    }

    protected static User getUser(String userID) {
        return users.get(userID);
    }

    protected static boolean userExists(String userID) {
        return users.containsKey(userID);
    }

    public static void register(String userID, String name, String password, String confirmPassword, String identity)
            throws RegistrationException {
        UserRegistry.register(userID, name, password, confirmPassword, identity);
    }

    public static void login(String userID, String password) throws LoginException {
        UserLogin.login(userID, password);
    }

    public static void logout() throws LogoutException {
        UserLogout.logout();
    }

    public static void logout(String userID) throws LogoutException {
        UserLogout.logout(userID);
    }

    public static void switchUser(String userID) throws SwitchUserException {
        UserSwitch.switchUser(userID);
    }

    public static void logoutAll() {
        while (!onlineUsers.isEmpty()) {
            System.out.println(onlineUsers.getFirst().getID() + " Bye~");
            onlineUsers.removeFirst();
        }
    }

    public static void printInfo() throws PrintInfoException {
        UserPrintInfo.printInfo();
    }

    public static void printInfo(String userID) throws PrintInfoException {
        UserPrintInfo.printInfo(userID);
    }

    public static void createCourse(String courseName, String courseTime, double credit, int period)
            throws CreateCourseException {
        CourseCreate.createCourse(courseName, courseTime, credit, period);
    }

    public static void listCourse(String... args) throws ListCourseException {
        CourseList.listCourse(args);
    }

    public static void selectCourse(String courseId) throws SelectCourseException {
        CourseSelect.selectCourse(courseId);
    }

    public static void cancelCourse(String courseId) throws CancelCourseException {
        CourseCancel.cancelCourse(courseId);
    }

    public static void outputCourseBatch(String filePath) throws CourseBatchException {
        CourseBatchService.outputCourseBatch(filePath);
    }

    public static void inputCourseBatch(String filePath) throws CourseBatchException {
        CourseBatchService.inputCourseBatch(filePath);
    }

    public static void listStudent(String courseId) throws CourseListStudentException {
        CourseListStudent.listStudent(courseId);
    }

    public static void removeStudent(String userId, String... courseId) throws CourseRemoveStudentException {
        CourseRemoveStudent.removeStudent(userId, courseId);
    }

    public static void listCourseSchedule(String... args) throws UserListCourseScheduleException {
        UserListCourseSchedule.listCourseSchedule(args);
    }
}