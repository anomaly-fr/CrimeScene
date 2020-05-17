package com.example.crimescene;

public class Case {
    String caseName,caseNotes;
    int caseType;
    String timeCreated;
    public Case(){}

    public Case(String caseName, int caseType,String timeCreated) {
        this.caseName = caseName;
        this.caseType = caseType;
        this.timeCreated = timeCreated;
    }
    public Case(String caseName, int caseType, String timeCreated,String caseNotes) {
        this.caseName = caseName;
        this.caseType = caseType;
        this.caseNotes = caseNotes;
        this.timeCreated = timeCreated;
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
