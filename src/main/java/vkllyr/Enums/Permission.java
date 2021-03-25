package vkllyr.Enums;

public enum Permission {
    PUBLIC("public "),
    DEFAULT(""),
    PROTECT("protect "),
    PRIVATE("private ");

    private String name;

    Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
