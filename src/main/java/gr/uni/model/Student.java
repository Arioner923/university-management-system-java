package gr.uni.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private static final String LETTERS_AND_SPACES = "^\\p{L}+(?:[ -]\\p{L}+)*$";
    private static final String PHONE_10_DIGITS = "^\\d{10}$";

    private final String am;
    private String firstName;
    private String lastName;
    private String phone;
    private final List<String> courses = new ArrayList<>();

    public Student(
            String am,
            String firstName,
            String lastName,
            String phone,
            List<String> courses
    ) {
        this.am = requireNonBlank(am, "AM");
        this.firstName = requireMatch(firstName, LETTERS_AND_SPACES, "firstName");
        this.lastName = requireMatch(lastName, LETTERS_AND_SPACES, "lastName");
        this.phone = requireMatch(phone, PHONE_10_DIGITS, "phone (10 digits)");

        if (courses != null) {
            for (String course : courses) {
                addCourse(course);
            }
        }
    }

    public String getAm() {
        return am;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setFirstName(String firstName) {
        this.firstName = requireMatch(firstName, LETTERS_AND_SPACES, "firstName");
    }

    public void setLastName(String lastName) {
        this.lastName = requireMatch(lastName, LETTERS_AND_SPACES, "lastName");
    }

    public void setPhone(String phone) {
        this.phone = requireMatch(phone, PHONE_10_DIGITS, "phone (10 digits)");
    }

    public void addCourse(String course) {
        courses.add(requireNonBlank(course, "course"));
    }

    @Override
    public String toString() {
        return "Student [AM=" + am +
                ", " + firstName + " " + lastName +
                ", phone=" + phone +
                ", courses=" + courses + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student other)) return false;
        return am.equals(other.am);
    }

    @Override
    public int hashCode() {
        return am.hashCode();
    }

    private static String requireNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }

        return s.trim();
    }

    private static String requireMatch(String s, String regex, String field) {
        if (s == null) {
            throw new IllegalArgumentException(field + " cannot be null");
        }

        s = s.trim();

        if (!s.matches(regex)) {
            throw new IllegalArgumentException("Invalid " + field + ": " + s);
        }

        return s;
    }
}