package gr.uni.model;

import java.util.Objects;

public final class Address {
    private static final String LETTERS_AND_SPACES = "^\\p{L}+(?:[ -]\\p{L}+)*$";
    private static final String TK_5_DIGITS = "^\\d{5}$";
    private static final String NUM_AND_LET = "^\\d+(\\p{L})?$";

    private final String street;
    private final String number;
    private final String postalCode;

    public Address(String street, String number, String postalCode) {
        this.street = requireMatch(street, LETTERS_AND_SPACES, "street");
        this.number = requireMatch(number, NUM_AND_LET, "number (e.g. 2 or 2A)");
        this.postalCode = requireMatch(postalCode, TK_5_DIGITS, "postalCode/TK (exactly 5 digits)");
    }

    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return street + " " + number + ", TK " + postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address other)) return false;
        return street.equals(other.street) && number.equals(other.number) && postalCode.equals(other.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, postalCode);
    }

    private static String requireMatch(String s, String regex, String field) {
        if (s == null) throw new IllegalArgumentException(field + " cannot be null");
        s = s.trim();
        if (!s.matches(regex)) throw new IllegalArgumentException("Invalid " + field + ": " + s);
        return s;
    }
}
