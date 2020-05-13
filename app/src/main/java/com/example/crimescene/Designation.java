package com.example.crimescene;

public class Designation {
    String KEY_EMAIL;
    String KEY_DESIGNATION;

    public Designation(String KEY_EMAIL, String KEY_DESIGNATION) {
        this.KEY_EMAIL = KEY_EMAIL;
        this.KEY_DESIGNATION = KEY_DESIGNATION;
    }

    public String getKEY_EMAIL() {
        return KEY_EMAIL;
    }

    public void setKEY_EMAIL(String KEY_EMAIL) {
        this.KEY_EMAIL = KEY_EMAIL;
    }

    public String getKEY_DESIGNATION() {
        return KEY_DESIGNATION;
    }

    public void setKEY_DESIGNATION(String KEY_DESIGNATION) {
        this.KEY_DESIGNATION = KEY_DESIGNATION;
    }
}
