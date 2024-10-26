package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTest {
    @Test
    public void testIsValidUndergradID_InvalidLength() {
        assertFalse(ValidationUtils.isValidUserID("123456", "Student"));
        assertFalse(ValidationUtils.isValidUserID("123456789", "Student"));
    }

    @Test
    public void testIsValidUndergradID_InvalidYear() {
        assertFalse(ValidationUtils.isValidUserID("18011001", "Student"));
        assertFalse(ValidationUtils.isValidUserID("25011001", "Student"));
    }

    @Test
    public void testIsValidUndergradID_InvalidCollege() {
        assertFalse(ValidationUtils.isValidUserID("23001001", "Student"));
        assertFalse(ValidationUtils.isValidUserID("23441001", "Student"));
    }

    @Test
    public void testIsValidUndergradID_InvalidClassNumber() {
        assertFalse(ValidationUtils.isValidUserID("23370001", "Student"));
        assertFalse(ValidationUtils.isValidUserID("23377001", "Student"));
    }

    @Test
    public void testIsValidUndergradID_InvalidLastThreeDigits() {
        assertFalse(ValidationUtils.isValidUserID("23376000", "Student"));
    }

    @Test
    public void testIsValidUndergradID() {
        assertTrue(ValidationUtils.isValidUserID("23376001", "Student"));
    }

    @Test
    public void testIsValidMasterID_InvalidPrefix() {
        assertFalse(ValidationUtils.isValidUserID("AB2101101", "Student"));
    }

    @Test
    public void testIsValidMasterID_InvalidYear() {
        assertFalse(ValidationUtils.isValidUserID("SY2011101", "Student"));
        assertFalse(ValidationUtils.isValidUserID("ZY2501101", "Student"));
    }

    @Test
    public void testIsValidMasterID_InvalidCollege() {
        assertFalse(ValidationUtils.isValidUserID("SY2100101", "Student"));
        assertFalse(ValidationUtils.isValidUserID("ZY2144101", "Student"));
    }

    @Test
    public void testIsValidMasterID_InvalidClassNumber() {
        assertFalse(ValidationUtils.isValidUserID("SY2121001", "Student"));
        assertFalse(ValidationUtils.isValidUserID("ZY2121701", "Student"));
    }

    @Test
    public void testIsValidMasterID_InvalidLastTwoDigits() {
        assertFalse(ValidationUtils.isValidUserID("SY212100", "Student"));
    }

    @Test
    public void testIsValidMasterID() {
        assertTrue(ValidationUtils.isValidUserID("SY2121101", "Student"));
    }

    @Test
    public void testIsValidPhDID_InvalidPrefix() {
        assertFalse(ValidationUtils.isValidUserID("XY1921101", "Student"));
    }

    @Test
    public void testIsValidPhDID_InvalidYear() {
        assertFalse(ValidationUtils.isValidUserID("BY1811101", "Student"));
        assertFalse(ValidationUtils.isValidUserID("BY2501101", "Student"));
    }

    @Test
    public void testIsValidPhDID_InvalidCollege() {
        assertFalse(ValidationUtils.isValidUserID("BY1900101", "Student"));
        assertFalse(ValidationUtils.isValidUserID("BY1944101", "Student"));
    }

    @Test
    public void testIsValidPhDID_InvalidClassNumber() {
        assertFalse(ValidationUtils.isValidUserID("BY1921001", "Student"));
        assertFalse(ValidationUtils.isValidUserID("BY1921701", "Student"));
    }

    @Test
    public void testIsValidPhDID_InvalidLastTwoDigits() {
        assertFalse(ValidationUtils.isValidUserID("BY192100", "Student"));
    }

    @Test
    public void testIsValidPhDID() {
        assertTrue(ValidationUtils.isValidUserID("BY1921101", "Student"));
    }

    @Test
    public void testIsValidTeacherID_InvalidLength() {
        assertFalse(ValidationUtils.isValidUserID("1234", "Teacher"));
        assertFalse(ValidationUtils.isValidUserID("123456", "Teacher"));
    }

    @Test
    public void testIsValidTeacherID_InvalidNumber() {
        assertFalse(ValidationUtils.isValidUserID("00000", "Teacher"));
    }

    @Test
    public void testIsValidTeacherID() {
        assertTrue(ValidationUtils.isValidUserID("12345", "Teacher"));
    }

    @Test
    public void testIsValidAdminID_InvalidPrefix() {
        assertFalse(ValidationUtils.isValidUserID("XY001", "Administrator"));
    }

    @Test
    public void testIsValidAdminID_InvalidNumber() {
        assertFalse(ValidationUtils.isValidUserID("AD00", "Administrator"));
        assertFalse(ValidationUtils.isValidUserID("AD1000", "Administrator"));
    }

    @Test
    public void testIsValidAdminID() {
        assertTrue(ValidationUtils.isValidUserID("AD001", "Administrator"));
    }

    @Test
    public void testIsValidUserID() {
        assertFalse(ValidationUtils.isValidUserID("123456"));
        assertFalse(ValidationUtils.isValidUserID("AB*123456"));
        assertTrue(ValidationUtils.isValidUserID("23376001"));
        assertTrue(ValidationUtils.isValidUserID("SY2121101"));
        assertTrue(ValidationUtils.isValidUserID("BY1921101"));
        assertTrue(ValidationUtils.isValidUserID("12345"));
        assertTrue(ValidationUtils.isValidUserID("AD001"));
    }

    @Test
    public void testIsValidName() {
        assertTrue(ValidationUtils.isValidName("Alice"));
        assertTrue(ValidationUtils.isValidName("Alice_abc"));
        assertFalse(ValidationUtils.isValidName("_Alice"));
        assertFalse(ValidationUtils.isValidName("Al"));
        assertFalse(ValidationUtils.isValidName("Alice12345678901234"));
        assertFalse(ValidationUtils.isValidName("Alice@123"));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(ValidationUtils.isValidPassword("Password1@"));
        assertTrue(ValidationUtils.isValidPassword("Pass_1%"));
        assertTrue(ValidationUtils.isValidPassword("pass1@"));
        assertTrue(ValidationUtils.isValidPassword("PASSWORD1@_%$"));
        assertFalse(ValidationUtils.isValidPassword("Password@"));
        assertFalse(ValidationUtils.isValidPassword("Pass1"));
        assertFalse(ValidationUtils.isValidPassword("Password1@Password1@"));
        assertFalse(ValidationUtils.isValidPassword("Password^&*("));
    }
}