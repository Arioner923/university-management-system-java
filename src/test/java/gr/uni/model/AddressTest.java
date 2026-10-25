package gr.uni.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateValidAddress() {
        Address address = new Address(
                "Example Street",
                "12A",
                "71410"
        );

        assertEquals("Example Street", address.getStreet());
        assertEquals("12A", address.getNumber());
        assertEquals("71410", address.getPostalCode());
    }

    @Test
    void shouldRejectInvalidPostalCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Address("Example Street", "12", "123")
        );
    }

    @Test
    void shouldRejectInvalidStreet() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Address("Street 123", "12", "71410")
        );
    }

    @Test
    void equalAddressesShouldBeEqual() {
        Address first = new Address(
                "Example Street",
                "12A",
                "71410"
        );

        Address second = new Address(
                "Example Street",
                "12A",
                "71410"
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}