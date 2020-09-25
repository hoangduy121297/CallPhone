package com.example.callphone;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LogContactAdpater extends ArrayAdapter {
    Context context;
    ArrayList<LogContacts> list;
    public LogContactAdpater(@NonNull Context context, int resource, @NonNull ArrayList<LogContacts> objects) {
        super(context, resource, objects);
        this.context=context;
        list=objects;
    }
    class ViewHolder{
        CircleImageView photoLog;
        TextView nameLog;
        ImageView typeLog;
        TextView time;
        TextView date;
        TextView isSave;
        CheckBox checkBox;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            if(list.get(position).getViewType()==0){
                convertView= LayoutInflater.from(this.context).inflate(R.layout.listitemcalllog_type0,null);
                viewHolder.date=convertView.findViewById(R.id.date);

            }
            else{
                convertView= LayoutInflater.from(this.context).inflate(R.layout.listitemcalllog_type1,null);
                viewHolder.photoLog=convertView.findViewById(R.id.photoLog);
                viewHolder.nameLog=convertView.findViewById(R.id.nameLog);
                viewHolder.typeLog=convertView.findViewById(R.id.typeLog);
                viewHolder.time=convertView.findViewById(R.id.timeLog);
                viewHolder.isSave=convertView.findViewById(R.id.isSave);
                viewHolder.checkBox=convertView.findViewById(R.id.checkbox);
            }
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        if(list.get(position).getViewType()==0){
            Date data=list.get(position).getDate();
            DateFormat dateFormatNgay = new SimpleDateFormat("dd");
            DateFormat dateFormatThang = new SimpleDateFormat("MM");
            DateFormat dateFormatNam = new SimpleDateFormat("YYYY");
            String ngay,thang, nam;
            ngay=dateFormatNgay.format(data);
            thang=dateFormatThang.format(data);
            nam=dateFormatNam.format(data);
            viewHolder.date.setText("Ngày "+ngay+" tháng "+thang+" năm "+nam);
        }
        else {
            LogContacts data = list.get(position);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(data.getDate());
            if (data.getPhotoLog() != null) {
                viewHolder.photoLog.setImageURI(data.getPhotoLog());
            }
            else {
                viewHolder.photoLog.setImageResource(R.drawable.photodefault);
            }
            if (data.getNameLog() != null) {
                viewHolder.nameLog.setText(data.getNameLog());
                viewHolder.isSave.setText("Di Động");
            } else {
                viewHolder.nameLog.setText(data.getPhoneNumberLog());
                viewHolder.isSave.setText("Chưa lưu");
            }
            viewHolder.time.setText(time);
            switch (data.getType()) {
                case CallLog.Calls.OUTGOING_TYPE:
                    viewHolder.typeLog.setImageResource(R.drawable.goingout);
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    viewHolder.typeLog.setImageResource(R.drawable.callback);
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    viewHolder.typeLog.setImageResource(R.drawable.missed);
            }


        }

        return convertView;
    }
    public void updateCheck(){
        for(int i=0;i<list.size();++i){
            list.get(i).setCheck(true);
        }
        notifyDataSetChanged();
    }
}
