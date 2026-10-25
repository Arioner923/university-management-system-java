package gr.uni.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class TeachingStaff {
    private static final String LETTERS_AND_SPACES = "^\\p{L}+(?:[ -]\\p{L}+)*$";
    private static final String PHONE_10_DIGITS = "^\\d{10}$";

    private final String at;
    private String firstName;
    private String lastName;
    private Address address;
    private double salary;
    private final List<String> phones = new ArrayList<>();

    protected TeachingStaff(String at, String firstName, String lastName, Address address, double salary, List<String> phones) {
        this.at = requireNonBlank(at, "AT");
        this.firstName = requireMatch(firstName, LETTERS_AND_SPACES, "firstName");
        this.lastName = requireMatch(lastName, LETTERS_AND_SPACES, "lastName");
        this.address = Objects.requireNonNull(address, "address");
        setSalary(salary);

        if (phones != null) {
            for (String p : phones) addPhone(p);
        }
    }

    public String getAt() { return at; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Address getAddress() { return address; }
    public double getSalary() { return salary; }

    public void setFirstName(String firstName) { this.firstName = requireMatch(firstName, LETTERS_AND_SPACES, "firstName"); }
    public void setLastName(String lastName) { this.lastName = requireMatch(lastName, LETTERS_AND_SPACES, "lastName"); }
    public void setAddress(Address address) { this.address = Objects.requireNonNull(address, "address"); }

    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("salary cannot be negative");
        this.salary = salary;
    }

    public List<String> getPhones() {
        return Collections.unmodifiableList(phones);
    }

    public void addPhone(String phone) {
        String p = requireMatch(phone, PHONE_10_DIGITS, "phone (10 digits)");
        phones.add(p);
    }

    public abstract String role();
    protected abstract String extraInfo();

    @Override
    public String toString() {
        return role() + " [AT=" + at +
                ", " + firstName + " " + lastName +
                ", address=" + address +
                ", salary=" + salary +
                ", phones=" + phones +
                ", " + extraInfo() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeachingStaff other)) return false;
        return at.equals(other.at);
    }

    @Override
    public int hashCode() {
        return at.hashCode();
    }

    protected static String requireNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
        return s.trim();
    }

    protected static String requireMatch(String s, String regex, String field) {
        if (s == null) throw new IllegalArgumentException(field + " cannot be null");
        s = s.trim();
        if (!s.matches(regex)) throw new IllegalArgumentException("Invalid " + field + ": " + s);
        return s;
    }
}
