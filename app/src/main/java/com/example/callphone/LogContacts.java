package com.example.callphone;

import android.net.Uri;
import android.provider.CallLog;

import java.util.Date;

public class LogContacts {
    Uri photoLog;
    String phoneNumberLog;
    String nameLog;
    String id;
    Date date;
    int type;
    boolean isSave=false;
    int viewType;
    boolean check=false;
    boolean isChecked=false;

    LogContacts(){
        viewType=0;
    }

    public LogContacts(Uri photoLog, String phoneNumberLog, String nameLog, String id, Date date,int type) {
        this.photoLog = photoLog;
        this.phoneNumberLog = phoneNumberLog;
        this.nameLog = nameLog;
        this.id = id;
        this.date = date;
        this.type=type;
        if(this.nameLog!=null){
            isSave=true;
        }
        viewType=1;

    }

    public Uri getPhotoLog() {
        return photoLog;
    }

    public void setPhotoLog(Uri photoLog) {
        this.photoLog = photoLog;
    }

    public String getPhoneNumberLog() {
        return phoneNumberLog;
    }

    public void setPhoneNumberLog(String phoneNumberLog) {
        this.phoneNumberLog = phoneNumberLog;
    }

    public String getNameLog() {
        return nameLog;
    }

    public void setNameLog(String nameLog) {
        this.nameLog = nameLog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getType(){
        return type;
    }
    public boolean getIsSave(){
        return isSave;
    }
    public int getViewType(){
        return viewType;
    }
    public void setCheck(boolean check){
        this.check=check;
    }
    public boolean getCheck(){
        return check;
    }
    public void setIsCheck(boolean check){
        this.isChecked=check;
    }
    public boolean getIsCheck(){
        return isChecked;
    }
}
