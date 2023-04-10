package com.project.gamersworld.models;

public enum PlayLevel {
    DEBUTANT("Debutant"),
    EXPERT("Expert"),
    CASUAL("Casual"),
    WANTING_TO_LEARN("Wanting to Learn");

    private String enumValue;

    private PlayLevel(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }

}
