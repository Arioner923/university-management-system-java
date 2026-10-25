package gr.uni.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void shouldAddTeachingStaff() {
        Department department = new Department("Computer Engineering");

        Professor professor = new Professor(
                "AT3001",
                "Test",
                "Professor",
                new Address("Example Street", "10", "71410"),
                1800.0,
                List.of("6912345678"),
                "Computer Science",
                10
        );

        department.addStaff(professor);

        assertEquals(1, department.getAllStaff().size());
        assertTrue(department.getAllStaff().contains(professor));
    }

    @Test
    void shouldRejectDuplicateTeachingStaffAt() {
        Department department = new Department("Computer Engineering");

        Professor first = new Professor(
                "AT3002",
                "First",
                "Professor",
                new Address("Example Street", "10", "71410"),
                1800.0,
                List.of("6912345678"),
                "Computer Science",
                10
        );

        EDIP second = new EDIP(
                "AT3002",
                "Second",
                "Staff",
                new Address("Second Street", "20", "71410"),
                1500.0,
                List.of("6998765432"),
                "Laboratory Support"
        );

        department.addStaff(first);

        assertThrows(
                IllegalArgumentException.class,
                () -> department.addStaff(second)
        );
    }

    @Test
    void shouldAddStudent() {
        Department department = new Department("Computer Engineering");

        Student student = new Student(
                "AM3001",
                "Test",
                "Student",
                "6912345678",
                List.of("Programming")
        );

        department.addStudent(student);

        assertEquals(1, department.getAllStudents().size());
        assertTrue(department.getAllStudents().contains(student));
    }

    @Test
    void shouldRejectDuplicateStudentAm() {
        Department department = new Department("Computer Engineering");

        Student first = new Student(
                "AM3002",
                "First",
                "Student",
                "6912345678",
                List.of("Programming")
        );

        Student second = new Student(
                "AM3002",
                "Second",
                "Student",
                "6998765432",
                List.of("Databases")
        );

        department.addStudent(first);

        assertThrows(
                IllegalArgumentException.class,
                () -> department.addStudent(second)
        );
    }

    @Test
    void shouldRejectBlankDepartmentName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Department("   ")
        );
    }
}