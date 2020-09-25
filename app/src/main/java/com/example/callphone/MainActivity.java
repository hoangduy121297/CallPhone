package com.example.callphone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_READ_CONTACTS = 79;
    public static final int REQUEST_CALL_PHONE = 69;
    public static final int REQUEST_WRITE_CONTACTS = 59;
    public static final int REQUEST_READ_LOG= 49;
    public  FloatingActionButton floatingActionButton;
    TabLayout tabLayout;
    ViewPager viewPager;
    FrameLayout videoCall;
    LinearLayout frameCall;
    FrameLayout navigationBack;
    TextView textView;
    FloatingActionButton callPhone;
    List<Person> list=new ArrayList<>();
    public static FrameLayout frame;
    ListView listView;
    FrameLayout backSpace;
    FrameLayout hideFragmentCall;
    PersonFragmentCallAdapter personFragmentCallAdapter;
    public static boolean isShow=false ;
    public static boolean isCallShow=false;
    GetListConstact getListConstact;
    FrameLayout[] number=new FrameLayout[12];

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_WRITE_CONTACTS);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CALL_LOG},
                    REQUEST_READ_LOG);
        }
        getListConstact=new GetListConstact(MainActivity.this);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tab);
        frame= findViewById(R.id.frame);
        videoCall=findViewById(R.id.videocall);
        frameCall=findViewById(R.id.framecall);
        textView=findViewById(R.id.edit_input_number);
        floatingActionButton=findViewById(R.id.dialpad);
        number[0]= findViewById(R.id.number0);
        number[1]= findViewById(R.id.number1);
        number[2]= findViewById(R.id.number2);
        number[3]= findViewById(R.id.number3);
        number[4]= findViewById(R.id.number4);
        number[5]= findViewById(R.id.number5);
        number[6]= findViewById(R.id.number6);
        number[7]= findViewById(R.id.number7);
        number[8]= findViewById(R.id.number8);
        number[9]= findViewById(R.id.number9);
        number[10]= findViewById(R.id.numbersao);
        number[11]=findViewById(R.id.numberthang);
        navigationBack=findViewById(R.id.navigation_back);

        hideFragmentCall=findViewById(R.id.hideFragmentCall);
        backSpace=findViewById(R.id.backspace);
        callPhone=findViewById(R.id.callPhoneButton);
        listView=findViewById(R.id.listViewFragmentContact);
        personFragmentCallAdapter=new PersonFragmentCallAdapter(MainActivity.this,R.layout.listcallitem,new ArrayList());
        listView.setAdapter(personFragmentCallAdapter);
        list=getListConstact.getListAllContact();
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for(int i=0;i<12;i++){
            number[i].setOnClickListener(this);
        }
        hideFragmentCall.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        backSpace.setOnClickListener(this);
        navigationBack.setOnClickListener(this);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        listView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//                Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.exit_to_bottom);
//                frameCall.startAnimation(animation);
//                frameCall.setVisibility(View.GONE);
//                isCallShow=false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(isCallShow==true){
                    Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.exit_to_bottom);
                    frameCall.startAnimation(animation);
                    frameCall.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.GONE);
                    navigationBack.setVisibility(View.VISIBLE);
                    isCallShow=false;
                }

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isCallShow==false){
                    Animation animation=AnimationUtils.loadAnimation(MainActivity.this, R.anim.enter_from_bottom);
                    frameCall.startAnimation(animation);
                    frameCall.setVisibility(View.VISIBLE);
                    navigationBack.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                    isCallShow=true;
                }
                textView.setText(personFragmentCallAdapter.getListPerson().get(i).getPhoneNumber());
            }
        });
        tabItemClick();
        LogContacts log=new LogContacts();
        if(String.valueOf(log.id).compareTo("duy")==0){
            Log.d("duy","1");
        }

    }
    public void filter(String s){
        List<Person> listFiter=new ArrayList<>();
        if(s.length()>0){
            for(Person p: list){
              if(  p.getPhoneNumber().contains(s)){
                  listFiter.add(p);
              }
            }
        }
        personFragmentCallAdapter.updateList(listFiter);
    }
    public void tabItemClick(){
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(isShow==true){
                    Animation animationshow=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show);
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.startAnimation(animationshow);
                    Animation animationout= AnimationUtils.loadAnimation(MainActivity.this,R.anim.exit_to_bottom);
                    frame.startAnimation(animationout);
                    frame.setVisibility(View.GONE);
                    isShow=false;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(isShow==true){
                    Animation animationshow=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show);
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.startAnimation(animationshow);
                    Animation animationout= AnimationUtils.loadAnimation(MainActivity.this,R.anim.exit_to_bottom);
                    frame.startAnimation(animationout);
                    frame.setVisibility(View.GONE);
                    isShow=false;
                }
            }
        });
    }

    public Uri getPhotoContact(String idContact){
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Data.CONTENT_URI,null,ContactsContract.Data.CONTACT_ID + "=" + idContact + " AND "
                + ContactsContract.Data.MIMETYPE + "='"
                + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'",null,null);
        if(cursor!= null){
            if(!cursor.moveToNext()){
                return null;
            }
        }
        else{
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong((idContact)));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.number0:
                String input=textView.getText().toString()+"0";
                textView.setText(input);
                break;
            case  R.id.number1:
                input=textView.getText().toString()+"1";
                textView.setText(input);
                break;
            case  R.id.number2:
                input=textView.getText().toString()+"2";
                textView.setText(input);
                break;
            case  R.id.number3:
                input=textView.getText().toString()+"3";
                textView.setText(input);
                break;
            case  R.id.number4:
                input=textView.getText().toString()+"4";
                textView.setText(input);
                break;
            case  R.id.number5:
                input=textView.getText().toString()+"5";
                textView.setText(input);
                break;
            case  R.id.number6:
                input=textView.getText().toString()+"6";
                textView.setText(input);
                break;
            case  R.id.number7:
                input=textView.getText().toString()+"7";
                textView.setText(input);
                break;
            case  R.id.number8:
                input=textView.getText().toString()+"8";
                textView.setText(input);
                break;
            case  R.id.number9:
                input=textView.getText().toString()+"9";
                textView.setText(input);
                break;
            case  R.id.numbersao:
                input=textView.getText().toString()+"*";
                textView.setText(input);
                break;
            case  R.id.numberthang:
                input=textView.getText().toString()+"#";
                textView.setText(input);
                break;
            case R.id.backspace:
                input=textView.getText().toString();
                if(input.length()>0){
                    textView.setText(input.substring(0,input.length()-1));
                }
                break;
            case R.id.callPhoneButton:
                if(textView.length()>0) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView.getText().toString()));
                    startActivity(intent);
                }
                break;
            case R.id.hideFragmentCall:
                Animation animationshow=AnimationUtils.loadAnimation(MainActivity.this,R.anim.show);
                floatingActionButton.setVisibility(View.VISIBLE);
                floatingActionButton.startAnimation(animationshow);
                Animation animationout= AnimationUtils.loadAnimation(MainActivity.this,R.anim.exit_to_bottom);
                frame.startAnimation(animationout);
                frame.setVisibility(View.GONE);
                isShow=false;
                isCallShow=false;
                break;
            case R.id.dialpad:
                isShow=true;
                isCallShow=true;
                floatingActionButton.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                Animation animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.enter_from_bottom);
                frameCall.setVisibility(View.VISIBLE);
                frameCall.startAnimation(animation);
                break;
            case R.id.navigation_back:
                navigationBack.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);
                frame.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
                textView.setText("");
                isCallShow=false;
                isShow=false;
                break;
        }
    }
}
