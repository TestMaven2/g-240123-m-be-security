package de.telran.g240123mbesecurity.filters;

public class Attribute {

    private int id;
    private String description;
    private boolean isValid;

    public Attribute(int id, String description, boolean isValid) {
        this.id = id;
        this.description = description;
        this.isValid = isValid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return String.format("Received attribute: ID - %d, description - %s, isValid - %b",
                id, description, isValid);
    }
}