package com.example.callphone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentLog  extends Fragment {
    ListView listView;
    LogContactAdpater logContactAdpater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentlog,container,false);
        listView=view.findViewById(R.id.listLog);

        logContactAdpater=new LogContactAdpater(getContext(),R.layout.listitemcalllog_type1,new GetListConstact(getContext()).getListLogInAdapter());
        listView.setAdapter(logContactAdpater);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });
    }
}
