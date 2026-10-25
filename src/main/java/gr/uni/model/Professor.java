package gr.uni.model;

import java.util.List;

public class Professor extends TeachingStaff {
    private String subjectArea;
    private int publications;

    public Professor(
            String at,
            String firstName,
            String lastName,
            Address address,
            double salary,
            List<String> phones,
            String subjectArea,
            int publications
    ) {
        super(at, firstName, lastName, address, salary, phones);
        this.subjectArea = requireNonBlank(subjectArea, "subjectArea");
        setPublications(publications);
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public int getPublications() {
        return publications;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = requireNonBlank(subjectArea, "subjectArea");
    }

    public void setPublications(int publications) {
        if (publications < 0) {
            throw new IllegalArgumentException("publications cannot be negative");
        }

        this.publications = publications;
    }

    @Override
    public String role() {
        return "Professor";
    }

    @Override
    protected String extraInfo() {
        return "subjectArea=" + subjectArea + ", publications=" + publications;
    }
}