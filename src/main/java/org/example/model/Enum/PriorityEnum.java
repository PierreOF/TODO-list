package org.example.model.Enum;

public enum PriorityEnum {
    LOW("Low"),
    NORMAL("Normal"),
    IMPORTANT("Important"),
    VERY_IMPORTANT("Very Important"),
    CRITICAL("Critical");

    private final String description;

    PriorityEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PriorityEnum getPriorityEnum(String description) {
        for (PriorityEnum priorityEnum : PriorityEnum.values()) {
            if (priorityEnum.getDescription().equalsIgnoreCase(description)) {
                return priorityEnum;
            }
        }
        System.out.println("PriorityEnum not found");
        return null;
    }
}
