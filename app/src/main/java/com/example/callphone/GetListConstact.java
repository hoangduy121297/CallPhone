package com.example.callphone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetListConstact {
    Context context;
    GetListConstact(Context context){
        this.context=context;
    }
    public ArrayList<Person> getListAllContact(){
        ArrayList<Person> arrayList = new ArrayList<>();
        ContentResolver cr =context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String lookupKey=cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uriLookupKey=Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,lookupKey);
                String phoneNo="";
                if (cur.getInt(cur.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                }
                Uri photo=getPhotoContact(id);
                arrayList.add(new Person(id,name,phoneNo,photo,uriLookupKey));
            }
        }
        if (cur != null) {
            cur.close();
        }
        return arrayList;
    }
    public Uri getPhotoContact(String idContact) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + "=" + idContact + " AND "
                + ContactsContract.Data.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null, null);
        if (cursor != null) {
            if (!cursor.moveToNext()) {
                return null;
            }
        } else {
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong((idContact)));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
    public ArrayList<LogContacts> listLog(){
        ArrayList<LogContacts> list=new ArrayList<>();
        ArrayList<Person> listPerson=getListAllContact();
        ContentResolver contentResolver=context.getContentResolver();
        Cursor cursor=contentResolver.query(CallLog.Calls.CONTENT_URI,null,null,null,android.provider.CallLog.Calls.DATE + " DESC");
        if((cursor!= null ? cursor.getCount():0)>0){
            while(cursor!=null && cursor.moveToNext()){
                String id=cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int type=cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String name=null;
                String date=cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                Uri phto=null;
                for(Person p:listPerson){
                    if(number.compareTo(p.getPhoneNumber())==0){
                        name=p.getName();
                        phto=p.getPhoto();
                        break;
                    }
                }
               list.add(new LogContacts(phto, number, name, id, new Date(Long.valueOf(date)), type));




            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public ArrayList<LogContacts> getListLogInAdapter(){
        ArrayList<LogContacts> list=listLog();
        ArrayList<LogContacts> newList=new ArrayList<>();
        if(list.size()>0){
            LogContacts init=new LogContacts();
            init.setDate(list.get(0).getDate());
            newList.add(init);
           for(int i=0;i<list.size();++i){
               int ngay=list.get(i).getDate().getDay();
               int thang=list.get(i).getDate().getMonth();
               int nam=list.get(i).getDate().getYear();
               int ngay1=newList.get(newList.size()-1).getDate().getDay();
               int thang1=newList.get(newList.size()-1).getDate().getMonth();
               int nam1=newList.get(newList.size()-1).getDate().getYear();
               if(nam==nam1 && thang==thang1 && ngay==ngay1){
                   newList.add(list.get(i));
               }
               else {
                   LogContacts logContacts=new LogContacts();
                   logContacts.setDate(list.get(i).getDate());
                   newList.add(logContacts);
                   newList.add(list.get(i));
               }
           }
        }
        for(int i=0;i<newList.size();++i){
            Log.d("time",""+newList.get(i).getViewType()+ newList.get(i).getPhoneNumberLog());
        }

        return newList ;

    }
}
