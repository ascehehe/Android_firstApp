package com.example.meng.android_firstapp.manhua;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meng.android_firstapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.manhua.ManhuaAdapter;
import Modle.manhua.Manhua;
import Modle.manhua.WeekResponse;
import Network.manhua.APIClient;

/**
 * Created by meng on 2017/7/3.
 */

public class HomeFragment extends Fragment {

    private  RecyclerView listV;
    private  ManhuaAdapter adapter;
    private  Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homelayout,container,false);
        listV=(RecyclerView)view.findViewById(R.id.list);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        listV.setLayoutManager(linearLayoutManager);
        adapter=new ManhuaAdapter();
        adapter.manhuaList=new ArrayList<>();
        listV.setAdapter(adapter);

        mHandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        //完成主界面更新,拿到数据
                        List<Manhua>list = (List < Manhua>) msg.obj;
                        adapter.setManhuaList(list);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };

        getData();

        return view;
    }

    private void  getData()
    {

        new Thread(){

            @Override
            public void run() {
                String str="week_list&page=1&pageSize=50&dates=4";
                APIClient client=new APIClient();
                String result= null;
                try {
                    result = client.post(str);
                    Gson gson = new Gson();
                    WeekResponse response= gson.fromJson(result,WeekResponse.class);
                    List<Manhua>list=response.getManhuaList();

                    Message msg = new Message();
                    msg.obj =list;
                    mHandler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
