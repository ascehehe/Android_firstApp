package com.example.meng.android_firstapp.weather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Modle.weather.City;
import Modle.weather.County;
import Modle.weather.Province;
import Network.weather.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by meng on 2017/7/3.
 */

public class DynamicFragment extends Fragment {
    public static  final  int LEVEL_PROVINCE=0;
    public static  final  int LEVEL_CITY=1;
    public static  final  int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;

    private TextView titleView;
    private ListView listView;
    private List<String>dataList=new ArrayList<String>();
    private List<Province>provinceList;
    private List<City>cityList;
    private List<County>countyList;
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;
    private Button rightBtn;
    private ArrayAdapter<String>adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dynamiclayout,container,false);
        titleView=(TextView)getActivity().findViewById(R.id.titleText);
        rightBtn=(Button)getActivity().findViewById(R.id.rightBtn);
        rightBtn.setBackgroundResource(R.drawable.tabbar_icon4_normal);
        listView=(ListView)view.findViewById(R.id.weather_list);
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel==LEVEL_PROVINCE)
                {
                    selectedProvince=provinceList.get(position);
                    queryCitys();


                }else if(currentLevel==LEVEL_CITY)
                {
                    selectedCity=cityList.get(position);
                    queryCounties();
                }else if(currentLevel==LEVEL_COUNTY)
                {
                  County county=  countyList.get(position);
                    Intent intent=new Intent(getActivity(),WeatherActivity.class);
                    intent.putExtra("weatherId",county.getWeatherId());
                    startActivity(intent);


                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel==LEVEL_COUNTY)
                {
                    queryCitys();

                }else if(currentLevel==LEVEL_CITY)
                {
                    queryProvinces();
                }
            }
        });

        queryProvinces();
    }


    private void queryProvinces()
    {
        titleView.setText("中国");
        rightBtn.setVisibility(View.INVISIBLE);
        provinceList= DataSupport.findAll(Province.class);
        if (provinceList.size()>0)
        {
            dataList.clear();
            for (Province temp :provinceList)
            {
                dataList.add(temp.getProvinceName());

            }
            adapter.notifyDataSetChanged();
           currentLevel=LEVEL_PROVINCE;
        }else
        {
            String address="http://guolin.tech/api/china";
            queryFromServer(address,LEVEL_PROVINCE);
        }

    }

    private void queryCitys()
    {
        titleView.setText(selectedProvince.getProvinceName());
        rightBtn.setVisibility(View.VISIBLE);
        cityList= DataSupport.where("provinceId=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0)
        {
            dataList.clear();
            for (City temp :cityList)
            {
                dataList.add(temp.getCityName());

            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_CITY;
        }else
        {
            String code=selectedProvince.getProvinceCode();
            String address="http://guolin.tech/api/china/"+code;

            queryFromServer(address,LEVEL_CITY);
        }

    }

    private void queryCounties()
    {
        titleView.setText(selectedCity.getCityName());
        rightBtn.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityId=?",String.valueOf(selectedCity.getId())).find(County.class);
                DataSupport.findAll(County.class);
        if (countyList.size()>0)
        {
            dataList.clear();
            for (County temp :countyList)
            {
                dataList.add(temp.getCountyName());

            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_COUNTY;
        }else
        {
            String code=selectedProvince.getProvinceCode();
            String cityCode=selectedCity.getCityCode();
            String address="http://guolin.tech/api/china/"+code+"/"+cityCode;
            queryFromServer(address,LEVEL_COUNTY);
        }


    }

    private void  queryFromServer(String address,final int typeI)
    {
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                boolean success=true;

                if (typeI==LEVEL_PROVINCE)
                {
                    JSONArray all= null;
                    try {
                        all = new JSONArray(result);
                        for (int i=0;i<all.length();i++)
                        {
                                JSONObject obj=all.getJSONObject(i);
                                Province tmp=new Province();
                                tmp.setProvinceName(obj.getString("name"));
                                tmp.setProvinceCode(obj.getString("id"));
                                tmp.save();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else if (typeI==LEVEL_CITY) {

                    JSONArray all= null;
                    try {
                        all = new JSONArray(result);
                        for (int i=0;i<all.length();i++)
                        {
                            JSONObject obj=all.getJSONObject(i);
                            City tmp=new City();
                            tmp.setCityName(obj.getString("name"));
                            tmp.setCityCode(obj.getString("id"));
                            tmp.setProvinceId(selectedProvince.getId());
                            tmp.save();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else if (typeI==LEVEL_COUNTY) {
                    JSONArray all= null;
                    try {
                        all = new JSONArray(result);
                        for (int i=0;i<all.length();i++)
                        {
                            JSONObject obj=all.getJSONObject(i);
                            County tmp=new County();
                            tmp.setCountyName(obj.getString("name"));
                            tmp.setWeatherId(obj.getString("weather_id"));
                            tmp.setCityId(selectedCity.getId());
                            tmp.save();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (success)
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (typeI==LEVEL_PROVINCE)
                            {
                                queryProvinces();

                            }else if (typeI==LEVEL_CITY) {

                                queryCitys();

                            }else if (typeI==LEVEL_COUNTY) {

                                queryCounties();

                            }
                        }
                    });


                }

            }
        });

    }

    private void showProgressDialog()
    {
        if (progressDialog==null)
        {
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog()
    {
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
        }

    }
}
