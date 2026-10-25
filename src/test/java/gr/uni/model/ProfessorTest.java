package gr.uni.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    @Test
    void shouldCreateValidProfessor() {
        Address address = new Address(
                "Example Street",
                "10",
                "71410"
        );

        Professor professor = new Professor(
                "AT1001",
                "Test",
                "Professor",
                address,
                1800.0,
                List.of("6912345678"),
                "Computer Science",
                12
        );

        assertEquals("AT1001", professor.getAt());
        assertEquals("Test", professor.getFirstName());
        assertEquals("Professor", professor.getLastName());
        assertEquals(address, professor.getAddress());
        assertEquals(1800.0, professor.getSalary());
        assertEquals("Computer Science", professor.getSubjectArea());
        assertEquals(12, professor.getPublications());
    }

    @Test
    void shouldRejectNegativeSalary() {
        Address address = new Address(
                "Example Street",
                "10",
                "71410"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Professor(
                        "AT1002",
                        "Test",
                        "Professor",
                        address,
                        -100.0,
                        List.of("6912345678"),
                        "Computer Science",
                        5
                )
        );
    }

    @Test
    void shouldRejectNegativePublications() {
        Address address = new Address(
                "Example Street",
                "10",
                "71410"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Professor(
                        "AT1003",
                        "Test",
                        "Professor",
                        address,
                        1800.0,
                        List.of("6912345678"),
                        "Computer Science",
                        -1
                )
        );
    }

    @Test
    void roleShouldReturnProfessor() {
        Address address = new Address(
                "Example Street",
                "10",
                "71410"
        );

        TeachingStaff staff = new Professor(
                "AT1004",
                "Test",
                "Professor",
                address,
                1800.0,
                List.of("6912345678"),
                "Computer Science",
                3
        );

        assertEquals("Professor", staff.role());
    }
}