package com.example.callphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragmentCallAdapter extends ArrayAdapter {
    List<Person> listPerson;
    Context context;
    public PersonFragmentCallAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        listPerson=objects;
        this.context=context;
    }
    class ViewHolder{
        CircleImageView photo;
        TextView name;
        TextView phoneNumber;
    }

    @Override
    public int getCount() {
        return listPerson.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(this.context).inflate(R.layout.listcallitem,null);
            viewHolder.name=view.findViewById(R.id.nameFragmentCall);
            viewHolder.phoneNumber=view.findViewById(R.id.phoneNumberFragmentCall);
            viewHolder.photo=view.findViewById(R.id.photoFragmentCall);
            view.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(listPerson.get(position).getName());
        if(listPerson.get(position).getPhoneNumber().length()>0){
            viewHolder.phoneNumber.setText(listPerson.get(position).getPhoneNumber());
        }
        else{
            viewHolder.phoneNumber.setText("");
        }
        if(listPerson.get(position).getPhoto()!=null){
            viewHolder.photo.setImageURI(listPerson.get(position).getPhoto());
        }
        else {
            viewHolder.photo.setImageResource(R.drawable.photodefault);
        }
        return view;
    }
    public void updateList(List<Person> list){
        this.listPerson=list;
        notifyDataSetChanged();
    }
    public List<Person> getListPerson(){
        return listPerson;
    }
}
