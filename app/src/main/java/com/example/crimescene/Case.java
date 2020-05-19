package com.example.crimescene;

public class Case {
    String caseName,caseNotes;
    int caseType;
    int priority;
    String timeCreated;
    public Case(){}

    public Case(String caseName, int caseType,String timeCreated,int priority) {
        this.caseName = caseName;
        this.caseType = caseType;
        this.timeCreated = timeCreated;
        this.priority = priority;
    }
    public Case(String caseName, int caseType, String timeCreated,String caseNotes) {
        this.caseName = caseName;
        this.caseType = caseType;
        this.caseNotes = caseNotes;
        this.timeCreated = timeCreated;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }

    public String getCaseNotes() {
        return caseNotes;
    }

    public void setCaseNotes(String caseNotes) {
        this.caseNotes = caseNotes;
    }
}
