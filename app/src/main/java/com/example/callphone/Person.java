package com.example.callphone;

import android.graphics.Bitmap;
import android.net.Uri;

public class Person implements Comparable<Person>{
    private String id;
    private String name;
    private Uri lookupKey;
    private String phoneNumber;
    private Uri photo;
    private int style=1;
    String currentText;

    public Person(String id, String name, String phoneNumber,Uri photo,Uri lookupKey)  {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo=photo;
        this.lookupKey=lookupKey;
    }
    public Person(){
        style=0;
    }
    public Uri getPhoto(){
        return photo;
    }
    public String getId() {
        return id;
    }
    public Uri getLookupKey(){
        return lookupKey;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setStyle(int style){
        this.style=style;
    }
    public int getStyle(){
        return style;
    }
    public void setCurrenText(String s){
        currentText=s;
    }
    public String getCurrentText(){
        return currentText;
    }
    @Override
    public int compareTo(Person person) {
        return this.getName().toLowerCase().compareTo(person.getName().toLowerCase());
    }
}
