package gr.uni.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JOptionPane;

public class Department {
    private static final String LETTERS_AND_SPACES = "^\\p{L}+(?:[ -]\\p{L}+)*$";
    private static final String TK_5_DIGITS    = "^\\d{5}$";
    private static final String NUM_AND_LET    = "^\\d+(\\p{L})?$";
    private static final String PHONE_10_DIGITS = "^\\d{10}$";

    private final String name;

    // Teaching staff indexed by AT
    private final Map<String, TeachingStaff> staffByAt = new HashMap<>();
    // Students indexed by AM
    private final Map<String, Student> studentsByAm = new HashMap<>();

    public Department(String name) {
        this.name = requireNonBlank(name, "name");
    }

    public String getName() { return name; }

    public Collection<TeachingStaff> getAllStaff() {
        return Collections.unmodifiableCollection(staffByAt.values());
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(studentsByAm.values());
    }

    public void addStaff(TeachingStaff staff) {
        Objects.requireNonNull(staff, "staff");
        if (staffByAt.containsKey(staff.getAt())) {
            throw new IllegalArgumentException("AT already exists: " + staff.getAt());
        }
        staffByAt.put(staff.getAt(), staff);
    }

    public void addStudent(Student st) {
        Objects.requireNonNull(st, "student");
        if (studentsByAm.containsKey(st.getAm())) {
            throw new IllegalArgumentException("AM already exists: " + st.getAm());
        }
        studentsByAm.put(st.getAm(), st);
    }

    // ===================== GUI: Teaching Staff =====================
    public void addTeachingStaffViaGui() {
        String[] options = {"Professor", "EDIP"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select teaching staff type to add:",
                "Add Teaching Staff",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == JOptionPane.CLOSED_OPTION) return;

        String at = askNonBlankLoop("AT (key):");
        if (at == null) return;

        if (staffByAt.containsKey(at)) {
            JOptionPane.showMessageDialog(null, "AT already exists: " + at, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String firstName = askRegexLoop("First name:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
        if (firstName == null) return;

        String lastName = askRegexLoop("Last name:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
        if (lastName == null) return;

        Address address = askAddressLoop();
        if (address == null) return;

        Double salary = askSalaryLoop("Salary (accepts 1200,50 or 1200.50):");
        if (salary == null) return;

        List<String> phones = askPhonesLoop();
        if (phones == null) return;

        try {
            TeachingStaff staff;

            if (choice == 0) { // Professor
                // Read the professor's subject area.
                String subjectArea = askRegexLoop("Subject area:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
                if (subjectArea == null) return;

                // Read a non-negative publication count.
                Integer publications = askNonNegativeIntLoop("Publications (non-negative integer):");
                if (publications == null) return;

                staff = new Professor(at, firstName, lastName, address, salary, phones, subjectArea, publications);
            } else { // EDIP
                String specialty = askNonBlankLoop("Specialty:");
                if (specialty == null) return;

                staff = new EDIP(at, firstName, lastName, address, salary, phones, specialty);
            }

            addStaff(staff);
            JOptionPane.showMessageDialog(null, "Added:\n" + staff, "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== GUI: Students =====================
    public void addStudentViaGui() {
        String am = askNonBlankLoop("AM (key):");
        if (am == null) return;

        if (studentsByAm.containsKey(am)) {
            JOptionPane.showMessageDialog(null, "AM already exists: " + am, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String firstName = askRegexLoop("First name:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
        if (firstName == null) return;

        String lastName = askRegexLoop("Last name:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
        if (lastName == null) return;

        String phone = askRegexLoop("Phone (exactly 10 digits):", PHONE_10_DIGITS, "Phone must be exactly 10 digits.");
        if (phone == null) return;

        List<String> courses = askCoursesLoop();
        if (courses == null) return;

        try {
            Student st = new Student(am, firstName, lastName, phone, courses);
            addStudent(st);
            JOptionPane.showMessageDialog(null, "Added:\n" + st, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== Helpers (reprompt loops) =====================
    private static Address askAddressLoop() {
        String street = askRegexLoop("Address - street:", LETTERS_AND_SPACES, "Use letters, spaces, or hyphens only.");
        if (street == null) return null;

        String number = askRegexLoop(
            "Address - number (e.g. 2 or 18A):",
            NUM_AND_LET,
            "Format: digits, optionally followed by 1 letter (e.g. 2 or 18A)."
        );
        if (number == null) return null;

        String tk = askRegexLoop("Address - TK (exactly 5 digits):", TK_5_DIGITS, "TK must be exactly 5 digits.");
        if (tk == null) return null;

        return new Address(street, number, tk);
    }

    private static List<String> askPhonesLoop() {
        List<String> phones = new ArrayList<>();
        while (true) {
            String p = JOptionPane.showInputDialog(null, "Phone (10 digits) - leave empty to finish:");
            if (p == null) return null; // cancel -> abort
            p = p.trim();
            if (p.isEmpty()) break;

            if (!p.matches(PHONE_10_DIGITS)) {
                JOptionPane.showMessageDialog(null, "Invalid phone. Must be exactly 10 digits.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            phones.add(p);
        }
        return phones;
    }

    private static List<String> askCoursesLoop() {
        List<String> courses = new ArrayList<>();
        while (true) {
            String c = JOptionPane.showInputDialog(null, "Course - leave empty to finish:");
            if (c == null) return null; // cancel -> abort
            c = c.trim();
            if (c.isEmpty()) break;
            courses.add(c);
        }
        return courses;
    }

    private static String askNonBlankLoop(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(null, prompt);
            if (s == null) return null; // cancel -> abort
            s = s.trim();
            if (s.isEmpty()) {
                JOptionPane.showMessageDialog(null, "This field cannot be empty.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            return s;
        }
    }

    private static String askRegexLoop(String prompt, String regex, String errorMsg) {
        while (true) {
            String s = JOptionPane.showInputDialog(null, prompt);
            if (s == null) return null; // cancel -> abort
            s = s.trim();
            if (!s.matches(regex)) {
                JOptionPane.showMessageDialog(null, errorMsg, "Invalid input", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            return s;
        }
    }

    // Reads a non-negative integer value.
    private static Integer askNonNegativeIntLoop(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(null, prompt);
            if (s == null) return null;
            s = s.trim();
            try {
                int v = Integer.parseInt(s);
                if (v < 0) {
                    JOptionPane.showMessageDialog(null, "Value must be >= 0.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer number (e.g. 10).", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Accepts salary values using either a comma or a decimal point.
    private static Double askSalaryLoop(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(null, prompt);
            if (s == null) return null;
            s = s.trim();
            if (s.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Salary cannot be empty.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Normalize decimal separator.
            s = s.replace(',', '.');

            try {
                double v = Double.parseDouble(s);
                if (v < 0) {
                    JOptionPane.showMessageDialog(null, "Salary cannot be negative.", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Enter a valid number like 1200,50 or 1200.50", "Invalid input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String requireNonBlank(String s, String field) {
        if (s == null || s.trim().isEmpty()) throw new IllegalArgumentException(field + " cannot be blank");
        return s.trim();
    }
}
