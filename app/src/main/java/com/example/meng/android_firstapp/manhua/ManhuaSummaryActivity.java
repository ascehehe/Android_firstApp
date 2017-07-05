package com.example.meng.android_firstapp.manhua;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import Adapter.ManhuaAdapter;
import Adapter.ManhuaSummaruAdapter;
import Modle.Manhua;
import Modle.ManhuaDetailResponse;
import Modle.ManhuaSummaryResponse;
import Network.APIClient;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaSummaryActivity extends Activity {
    private Manhua currentManhua;
    private Handler mHandler;
    private ManhuaSummaruAdapter adapter;
    private RecyclerView listV;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhua_summary_layout);
        currentManhua=(Manhua) getIntent().getSerializableExtra("manhua");
        initNavigation();
        listV=(RecyclerView) findViewById(R.id.summarylist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        listV.setLayoutManager(linearLayoutManager);
        adapter=new ManhuaSummaruAdapter();
        listV.setAdapter(adapter);
        setHeader(listV);
        mHandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        List<ManhuaSummaryResponse.ManhuaSummary>list = (List<ManhuaSummaryResponse.ManhuaSummary>) msg.obj;
                        adapter.manhuaList.addAll(list);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };
        getData();
    }


    private void  initNavigation()
    {
        TextView titleV=(TextView)findViewById(R.id.titleText);
        titleV.setText("漫画列表");

        Button backBtn=(Button)findViewById(R.id.backBtn);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.summary_header, view, false);
        adapter.setmHeaderView(header);
    }

    private void  getData()
    {
        new Thread(){

            @Override
            public void run() {
                String str="zhuanti_list&catid="+currentManhua.getCatid();
                APIClient client=new APIClient();
                String result= null;
                try {
                    result = client.post(str);
                    Gson gson = new Gson();
                    ManhuaSummaryResponse response= gson.fromJson(result,ManhuaSummaryResponse.class);
                    if (response.success.equals("1"))
                    {
                        List<ManhuaSummaryResponse.ManhuaSummary> list=response.data;
                        Message msg = new Message();
                        msg.obj =list;
                        mHandler.sendMessage(msg);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
