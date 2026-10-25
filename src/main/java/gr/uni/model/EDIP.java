package gr.uni.model;

import java.util.List;

public class EDIP extends TeachingStaff {
    private String specialty;

    public EDIP(
            String at,
            String firstName,
            String lastName,
            Address address,
            double salary,
            List<String> phones,
            String specialty
    ) {
        super(at, firstName, lastName, address, salary, phones);
        this.specialty = requireNonBlank(specialty, "specialty");
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = requireNonBlank(specialty, "specialty");
    }

    @Override
    public String role() {
        return "EDIP";
    }

    @Override
    protected String extraInfo() {
        return "specialty=" + specialty;
    }
}