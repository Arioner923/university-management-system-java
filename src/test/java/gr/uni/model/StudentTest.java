package gr.uni.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void shouldCreateValidStudent() {
        Student student = new Student(
                "AM1001",
                "Test",
                "Student",
                "6912345678",
                List.of("Programming", "Databases")
        );

        assertEquals("AM1001", student.getAm());
        assertEquals("Test", student.getFirstName());
        assertEquals("Student", student.getLastName());
        assertEquals("6912345678", student.getPhone());
        assertEquals(2, student.getCourses().size());
    }

    @Test
    void shouldAddCourse() {
        Student student = new Student(
                "AM1002",
                "Test",
                "Student",
                "6912345678",
                null
        );

        student.addCourse("Algorithms");

        assertTrue(student.getCourses().contains("Algorithms"));
    }

    @Test
    void shouldRejectInvalidPhone() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Student(
                        "AM1003",
                        "Test",
                        "Student",
                        "12345",
                        List.of("Programming")
                )
        );
    }

    @Test
    void shouldRejectBlankCourse() {
        Student student = new Student(
                "AM1004",
                "Test",
                "Student",
                "6912345678",
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> student.addCourse("   ")
        );
    }

    @Test
    void studentsWithSameAmShouldBeEqual() {
        Student first = new Student(
                "AM2000",
                "First",
                "Student",
                "6912345678",
                null
        );

        Student second = new Student(
                "AM2000",
                "Second",
                "Student",
                "6998765432",
                null
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}