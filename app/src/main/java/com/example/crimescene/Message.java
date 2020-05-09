package com.example.crimescene;

import android.net.Uri;

public class Message {
    private String messageContent;
    private String messageDate;
    private String messageTime;
    String replyingTo;

  //  private Uri displayPicture;
    private String from;
    private String timestamp;
   private boolean sentAnonymously;
    public Message(String messageContent, String messageDate, String messageTime, String from,String timestamp,String replyingTo) {
        this.messageContent = messageContent;
        this.messageDate = messageDate;
        this.messageTime = messageTime;
    //    this.displayPicture = displayPicture;
        this.replyingTo = replyingTo;
        this.from = from;

        this.timestamp = timestamp;

    }
    public Message(){}

    public String getReplyingTo() {
        return replyingTo;
    }

    public void setReplyingTo(String replyingTo) {
        this.replyingTo = replyingTo;
    }

    public boolean isSentAnonymously() {
        return sentAnonymously;
    }

    public void setSentAnonymously(boolean sentAnonymously) {
        this.sentAnonymously = sentAnonymously;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

//    public Uri getDisplayPicture() {
//        return displayPicture;
//    }
//
//    public void setDisplayPicture(Uri displayPicture) {
//        this.displayPicture = displayPicture;
//    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
