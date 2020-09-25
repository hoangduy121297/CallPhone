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
import de.hdodenhof.circleimageview.CircleImageView;
public class PersonAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Person> listPerson;
    public PersonAdapter(@NonNull Context context, int resource, ArrayList<Person> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.listPerson=list;
    }
    class ViewHolder{
        CircleImageView photoPerson;
        TextView namePerson;
        TextView fistNamePerson;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return listPerson.get(position).getStyle();
    }

    @Override
    public int getCount() {
       return listPerson.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        View v=convertView;
        if(v==null){

            viewHolder=new ViewHolder();
            if(listPerson.get(position).getStyle()==0){
                v=LayoutInflater.from(this.context).inflate(R.layout.listcontactnametype,null);
                viewHolder.fistNamePerson=v.findViewById(R.id.firstname);
            }
            else{
                v=LayoutInflater.from(this.context).inflate(R.layout.listcontactitem,null);

                viewHolder.namePerson=v.findViewById(R.id.name);
                viewHolder.photoPerson=v.findViewById(R.id.photo);
            }
            v.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) v.getTag();
        }
        if(listPerson.get(position).getStyle()==0){
            viewHolder.fistNamePerson.setText(listPerson.get(position).getCurrentText().toUpperCase());
        }
        else{
            if(listPerson.get(position).getPhoto()!=null){
                viewHolder.photoPerson.setImageURI(listPerson.get(position).getPhoto());
            }
            else {
                viewHolder.photoPerson.setImageResource(R.drawable.photodefault);
            }
            viewHolder.namePerson.setText(listPerson.get(position).getName());
        }
        return v;
    }
    public void updateListView(ArrayList<Person> list){
        this.listPerson=list;
        notifyDataSetChanged();
    }
    public ArrayList<Person> getListPerson(){
        return listPerson;
    }
}
