package org.example.model.Enum;

public enum StatusEnum {

    TODO("TODO"),
    DOING("DOING"),
    DONE("DONE");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StatusEnum getByDescription(String description) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        System.out.println("Status not found");
        return null;
    }
}
