package tracker;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Student {

    private static int id = 9999;
    private String firstName;
    private String lastName;
    private String email;

    private int javaCoursePoints;
    private int dsaCoursePoints;
    private int dbCoursePoints;
    private int springCoursePoints;

    public Student(String firstName, String lastName, String email) {
        this.id++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJavaCoursePoints() {
        return javaCoursePoints;
    }

    public void setJavaCoursePoints(int javaCoursePoints) {
        this.javaCoursePoints = javaCoursePoints;
    }

    public int getDsaCoursePoints() {
        return dsaCoursePoints;
    }

    public void setDsaCoursePoints(int dsaCoursePoints) {
        this.dsaCoursePoints = dsaCoursePoints;
    }

    public int getDbCoursePoints() {
        return dbCoursePoints;
    }

    public void setDbCoursePoints(int dbCoursePoints) {
        this.dbCoursePoints = dbCoursePoints;
    }

    public int getSpringCoursePoints() {
        return springCoursePoints;
    }

    public void setSpringCoursePoints(int springCoursePoints) {
        this.springCoursePoints = springCoursePoints;
    }

    public static boolean isValidFirstName(String word) {
        String namePattern = "^[a-zA-Z][a-zA-Z-']{1,}";         //matches
        if (patternMatches(word, namePattern)) {
            if (word.substring(word.length() - 1).matches("[-']")
                    || twoSpecialCharsInRow(word)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidLastName(String word) {
        String lastNamePattern = "^[a-zA-Z][a-zA-Z-']{1,}";
        if (patternMatches(word, lastNamePattern)) {
            if (word.substring(word.length() - 1).matches("[-']")
                    || twoSpecialCharsInRow(word)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{1,}$";
        if (patternMatches(email, emailPattern)) {
            return true;
        }
        return false;
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean twoSpecialCharsInRow(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == '-' && (word.charAt(i + 1) == '-' || word.charAt(i + 1) == '\'')
                    || word.charAt(i) == '\'' && (word.charAt(i + 1) == '-' || word.charAt(i + 1) == '\'')) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> makeStudentInfoMap() {
        Map<String, String> studentInfoMap = new HashMap<>();
        studentInfoMap.put("id", String.valueOf(Student.getId()));
        studentInfoMap.put("firstName", getFirstName());
        studentInfoMap.put("lastName", getLastName());
        studentInfoMap.put("email", getEmail());
        studentInfoMap.put("javaCoursePoints", String.valueOf(getJavaCoursePoints()));
        studentInfoMap.put("dsaCoursePoints", String.valueOf(getDsaCoursePoints()));
        studentInfoMap.put("dbCoursePoints", String.valueOf(getDbCoursePoints()));
        studentInfoMap.put("springCoursePoints", String.valueOf(getSpringCoursePoints()));
        return studentInfoMap;
    }
}
