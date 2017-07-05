package com.example.meng.android_firstapp.manhua;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.ManhuaDetailAdpter;
import Modle.Manhua;
import Modle.ManhuaDetailResponse;
import Modle.ManhuaSummaryResponse;
import Modle.WeekResponse;
import Network.APIClient;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaDetailActivity extends Activity {
    private ManhuaSummaryResponse.ManhuaSummary currentManhua;
    private ListView listV;
    private ManhuaDetailAdpter adapter;
    private Handler mHandler;
    private List <String>dataDource=new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhua_detail_layout);
        currentManhua=(ManhuaSummaryResponse.ManhuaSummary) getIntent().getSerializableExtra("manhua");
        initNavigation();
        listV=(ListView)findViewById(R.id.manhua_detail_list);
        adapter=new ManhuaDetailAdpter(ManhuaDetailActivity.this);
        listV.setAdapter(adapter);
        mHandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        List<String>list = (List < String>) msg.obj;
                        adapter.photoList.addAll(list);
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
        titleV.setText(currentManhua.title);

        Button backBtn=(Button)findViewById(R.id.backBtn);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }

    private void  getData()
    {
        new Thread(){

            @Override
            public void run() {
                String str="content_detail&catid="+currentManhua.catid+"&id="+currentManhua.id;
                APIClient client=new APIClient();
                String result= null;
                try {
                    result = client.post(str);
                    Gson gson = new Gson();
                    ManhuaDetailResponse response= gson.fromJson(result,ManhuaDetailResponse.class);
                    List<String>list=response.data.content;

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

