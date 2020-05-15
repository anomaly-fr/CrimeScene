package com.example.crimescene;

import android.net.Uri;

public class UserInfo {
    private static final UserInfo instance = new UserInfo();

    private String fullName;
    private String nickName;
    private String currentFile,note;
    private String emailID;
    private Boolean isCop;
    private Uri displayPicture;

    public static UserInfo getInstance() {

        if (instance == null) {
            UserInfo instance = new UserInfo();
        }
            return instance;
        }


    private UserInfo() {}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCop() {
        return isCop;
    }

    public void setCop(Boolean cop) {
        isCop = cop;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    public Uri getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(Uri displayPicture) {
        this.displayPicture = displayPicture;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
