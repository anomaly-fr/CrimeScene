package com.example.crimescene;

import android.net.Uri;

public class UserInfo {
    private static final UserInfo instance = new UserInfo();

    private String fullName;
    private String nickName;
    private String emailID;
    private String isCop;
    private Uri displayPicture;

    public static UserInfo getInstance() {

        if (instance == null) {
            UserInfo instance = new UserInfo();
        }
            return instance;
        }


    private UserInfo() {}

    public String getCop() {
        return isCop;
    }

    public void setCop(String cop) {
        isCop = cop;
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
