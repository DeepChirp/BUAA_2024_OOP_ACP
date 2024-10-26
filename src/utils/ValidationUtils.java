package utils;

public class ValidationUtils {

    public static boolean isValidUserID(String userID, String identity) {
        return switch (identity) {
            case "Student" -> {
                if (userID.matches("\\d{8}")) {
                    int year = Integer.parseInt(userID.substring(0, 2));
                    int college = Integer.parseInt(userID.substring(2, 4));
                    int classNumber = Integer.parseInt(userID.substring(4, 5));
                    int lastThreeDigits = Integer.parseInt(userID.substring(5, 8));
                    yield year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1
                            && classNumber <= 6 && lastThreeDigits >= 1 && lastThreeDigits <= 999;
                } else if (userID.matches("[A-Z]{2}\\d{7}")) {
                    String prefix = userID.substring(0, 2);
                    int year = Integer.parseInt(userID.substring(2, 4));
                    int college = Integer.parseInt(userID.substring(4, 6));
                    int classNumber = Integer.parseInt(userID.substring(6, 7));
                    int lastTwoDigits = Integer.parseInt(userID.substring(7, 9));
                    if (prefix.equals("SY") || prefix.equals("ZY")) {
                        yield year >= 21 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1
                                && classNumber <= 6 && lastTwoDigits >= 1 && lastTwoDigits <= 99;
                    } else if (prefix.equals("BY")) {
                        yield year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1
                                && classNumber <= 6 && lastTwoDigits >= 1 && lastTwoDigits <= 99;
                    }
                }
                yield false;
            }
            case "Teacher" -> userID.matches("\\d{5}") && !userID.equals("00000");
            case "Administrator" -> userID.matches("AD\\d{3}") && !userID.equals("AD000");
            default -> false;
        };
    }

    public static boolean isValidUserID(String userID) {
        return isValidUserID(userID, "Student") || isValidUserID(userID, "Teacher")
                || isValidUserID(userID, "Administrator");
    }

    public static boolean isValidName(String name) {
        return !name.startsWith("_") && name.matches("[a-zA-Z_]+") && name.length() >= 4 && name.length() <= 16;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6
                && password.length() <= 16
                && password.matches("[a-zA-Z0-9@_%$]+")
                && password.matches(".*[a-zA-Z].*")
                && password.matches(".*[0-9].*")
                && (password.contains("@") || password.contains("_") || password.contains("%")
                        || password.contains("$"));
    }

    public static boolean isValidCourseID(String courseId) {
        return courseId != null && courseId.matches("C-[1-9][0-9]*");
    }

    public static boolean isValidCourseName(String courseName) {
        return courseName.matches("^[a-zA-Z][a-zA-Z0-9-_]{0,19}$");
    }

    public static boolean isValidCourseTime(String courseTime) {
        return courseTime.matches("^[1-7]_(1[0-4]|[1-9])-(1[0-4]|[1-9])$");
    }

    public static boolean isValidCredit(double credit) {
        return credit > 0 && credit <= 12;
    }

    public static boolean isValidPeriod(int period) {
        return period > 0 && period <= 1280;
    }
}