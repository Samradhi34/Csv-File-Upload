package com.project.constant;

public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    PREFER_NOT_TO_ANSWER("Prefer not to answer");

    private final String dbValue;

    MaritalStatus(String dbValue) {
        this.dbValue = dbValue;
    }
     
    public String getDbValue() {
        return dbValue;
    }

    @Override
    public String toString() {
        return dbValue;
    }
}
