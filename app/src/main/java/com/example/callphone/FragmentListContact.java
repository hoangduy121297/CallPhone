package com.example.callphone;
import android.content.ContentResolver;
import android.content.ContentUris;

import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageButton;

import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentListContact extends Fragment {
    GetListConstact getListConstact;

    ListView listView;
    ArrayList<Person> arrayList;
    ArrayList<Person> listPersonInAdapter;
    int indexListPersonInAdapter;
    FrameLayout menu;
    EditText search;
    PersonAdapter personAdapter;
    View dialogView;
    BottomSheetDialog sheetDialog;
    TextView nameSheet;
    TextView phoneSheet;
    FrameLayout callSheet;
    FrameLayout messSheet;
    CircleImageView photoSheet;
    FrameLayout addPerson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentlistcontact,container,false);
        listView=view.findViewById(R.id.list_fmlist_contact);
        search=view.findViewById(R.id.searchlistcontact);
        dialogView=getLayoutInflater().inflate(R.layout.bottomsheetdialog,null);
        sheetDialog=new BottomSheetDialog(getContext());
        sheetDialog.setContentView(dialogView);
        menu=dialogView.findViewById(R.id.menu);
        nameSheet=dialogView.findViewById(R.id.namesheet);
        callSheet=dialogView.findViewById(R.id.callsheet);
        messSheet=dialogView.findViewById(R.id.messsheet);
        phoneSheet=dialogView.findViewById(R.id.phonesheet);
        photoSheet=dialogView.findViewById(R.id.photosheet);
        addPerson=view.findViewById(R.id.addPerson);
        getListConstact=new GetListConstact(getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("FragmentListContact","onActivityCreated");
        arrayList=getListConstact.getListAllContact();
        personAdapter=new PersonAdapter(getContext(),R.layout.listcontactitem,getAllListPersonIncludeStyle(arrayList));
        listView.setAdapter(personAdapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<Person> listFilter=filter(editable.toString());
                personAdapter.updateListView(getAllListPersonIncludeStyle(listFilter));

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listPersonInAdapter=personAdapter.getListPerson();
                indexListPersonInAdapter=i;
                if(listPersonInAdapter.get(i).getStyle()==1){
                    nameSheet.setText(listPersonInAdapter.get(i).getName());
                    phoneSheet.setText(listPersonInAdapter.get(i).getPhoneNumber());
                    if(listPersonInAdapter.get(i).getPhoto()!=null){
                        photoSheet.setImageURI(listPersonInAdapter.get(i).getPhoto());
                    }
                    else photoSheet.setImageResource(R.drawable.photodefault);
                    sheetDialog.show();

                }
            }
        });
        callSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneSheet.getText().toString()));
                startActivity(intent);
            }
        });
        messSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",phoneSheet.getText().toString(),null));
                startActivity(intent);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),addPersonActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 ){
            if(resultCode==RESULT_OK){
                Toast.makeText(getContext(),"Tạo thành công",Toast.LENGTH_LONG).show();
                arrayList=getListConstact.getListAllContact();
                personAdapter.updateListView(getAllListPersonIncludeStyle(arrayList));

            }
            else{
                Toast.makeText(getContext(),"Chưa tạo",Toast.LENGTH_LONG).show();
            }
        }
    }

    private ArrayList<Person> filter(String s) {
        ArrayList<Person> filterList=new ArrayList<>();

        for(Person p:arrayList){
            if(p.getName().contains(s)){
                filterList.add(p);
            }
        }
        return filterList;
    }



    public void showPopup(View v) {
        final PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_in_bottom_sheet, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                listPersonInAdapter=personAdapter.getListPerson();
                switch (item.getItemId()){
                    case R.id.delete:
                        deleteContact(listPersonInAdapter.get(indexListPersonInAdapter).getLookupKey());
                        Toast.makeText(getContext(),"Deleted "+listPersonInAdapter.get(indexListPersonInAdapter).getName(),Toast.LENGTH_LONG).show();
                        sheetDialog.hide();
                        return true;

                    default: return false;
                }

            }
        });
        popup.show();

    }
    public ArrayList<Person> getAllListPersonIncludeStyle(ArrayList<Person> list){
        ArrayList<Person> newList=new ArrayList<>();
        Collections.sort(list);
        if(list.size()>0){
            String currentText= list.get(0).getName().substring(0,1);
            Person person=new Person();
            person.setCurrenText(currentText);
            newList.add(person);
            for(Person p: list){
                if(p.getName().substring(0,1).equalsIgnoreCase(currentText)){
                    newList.add(p);
                }
                else {
                    Person person2=new Person();
                    person2.setCurrenText(p.getName().substring(0,1));
                    newList.add(person2);
                    newList.add(p);
                    currentText=p.getName().substring(0,1);
                }
            }
        }
        return newList;
    }
    public void deleteContact(Uri uriLookupKey){
        ContentResolver contentResolver=getActivity().getContentResolver();
        contentResolver.delete(uriLookupKey,null,null);
        arrayList=getListConstact.getListAllContact();
        personAdapter.updateListView(getAllListPersonIncludeStyle(arrayList));
    }
}