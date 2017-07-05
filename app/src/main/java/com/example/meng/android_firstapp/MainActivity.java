package com.example.meng.android_firstapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.meng.android_firstapp.manhua.HomeFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private RelativeLayout home_Layout;
    private RelativeLayout dymaic_Layout;
    private RelativeLayout discover_Layout;
    private RelativeLayout message_Layout;
    private RelativeLayout mine_Layout;

    private TextView tab_home_Text;
    private TextView tab_dynamic_Text;
    private TextView tab_discover_Text;
    private TextView tab_message_Text;
    private TextView tab_mine_Text;

    private ImageView tab_home_Img;
    private ImageView tab_dynamic_Img;
    private ImageView tab_discover_Img;
    private ImageView tab_message_Img;
    private ImageView tab_mine_Img;

    private HomeFragment homeFragment;
    private DynamicFragment dynamicFragment;
    private DiscoverFragment discoverFragment;
    private MessageFragment messageFragment;
    private  MineFragment mineFragment;

    private List<String> titleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleList= Arrays.asList("首页","动态","发现","消息","我的");
        setContentView(R.layout.activity_main);
        initView();
        refreshItem(0);



    }

    private void initView()
    {
        home_Layout=(RelativeLayout)findViewById(R.id.homelayout);
        dymaic_Layout=(RelativeLayout)findViewById(R.id.dynamiclayout);
        discover_Layout=(RelativeLayout)findViewById(R.id.discoverlayout);
        message_Layout=(RelativeLayout)findViewById(R.id.messagelayout);
        mine_Layout=(RelativeLayout)findViewById(R.id.minelayout);

        home_Layout.setOnClickListener(MainActivity.this);
        dymaic_Layout.setOnClickListener(MainActivity.this);
        discover_Layout.setOnClickListener(MainActivity.this);
        message_Layout.setOnClickListener(MainActivity.this);
        mine_Layout.setOnClickListener(MainActivity.this);

        tab_home_Img=(ImageView)findViewById(R.id.homeimg);
        tab_dynamic_Img=(ImageView)findViewById(R.id.dynamicimg);
        tab_discover_Img=(ImageView)findViewById(R.id.discoverimg);
        tab_message_Img=(ImageView)findViewById(R.id.messageimg);
        tab_mine_Img=(ImageView)findViewById(R.id.mineimg);

        tab_home_Text=(TextView)findViewById(R.id.hometext);
        tab_dynamic_Text=(TextView)findViewById(R.id.dynamictext);
        tab_discover_Text=(TextView)findViewById(R.id.discovertext);
        tab_message_Text=(TextView)findViewById(R.id.messagetext);
        tab_mine_Text=(TextView)findViewById(R.id.minetext);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.homelayout:
            {
                refreshItem(0);
            }
            break;

            case R.id.dynamiclayout:
            {
                refreshItem(1);
            }
            break;

            case R.id.discoverlayout:
            {
                refreshItem(2);
            }
            break;

            case R.id.messagelayout:
            {
                refreshItem(3);
            }
            break;

            case R.id.minelayout:
            {
              refreshItem(4);
            }
            break;

          default:break;
        }

    }

    private  void  refreshItem(int index)
    {
        TextView titleV=(TextView)findViewById(R.id.titleText);
        titleV.setText(titleList.get(index));

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);

        if (index==0)
        {
            if (homeFragment==null)
            {
                homeFragment=new HomeFragment();
                transaction.add(R.id.content,homeFragment);
            }else {

                transaction.show(homeFragment);
            }

            tab_home_Img.setImageResource(R.drawable.tabbar_icon0_pressed);
            tab_home_Text.setTextColor(ContextCompat.getColor(this,R.color.C1));

            tab_dynamic_Img.setImageResource(R.drawable.tabbar_icon1_normal);
            tab_dynamic_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_discover_Img.setImageResource(R.drawable.tabbar_icon2_normal);
            tab_discover_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_message_Img.setImageResource(R.drawable.tabbar_icon3_normal);
            tab_message_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_mine_Img.setImageResource(R.drawable.tabbar_icon4_normal);
            tab_mine_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

        }else if (index==1)
        {

            if (dynamicFragment==null)
            {
                dynamicFragment=new DynamicFragment();
                transaction.add(R.id.content,dynamicFragment);
            }else {

                transaction.show(dynamicFragment);
            }

            tab_home_Img.setImageResource(R.drawable.tabbar_icon0_normal);
            tab_home_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_dynamic_Img.setImageResource(R.drawable.tabbar_icon1_pressed);
            tab_dynamic_Text.setTextColor(ContextCompat.getColor(this,R.color.C1));

            tab_discover_Img.setImageResource(R.drawable.tabbar_icon2_normal);
            tab_discover_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_message_Img.setImageResource(R.drawable.tabbar_icon3_normal);
            tab_message_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_mine_Img.setImageResource(R.drawable.tabbar_icon4_normal);
            tab_mine_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

        }else if (index==2)
        {
            if (discoverFragment==null)
            {
                discoverFragment=new DiscoverFragment();
                transaction.add(R.id.content,discoverFragment);
            }else {

                transaction.show(discoverFragment);
            }
            tab_home_Img.setImageResource(R.drawable.tabbar_icon0_normal);
            tab_home_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_dynamic_Img.setImageResource(R.drawable.tabbar_icon1_normal);
            tab_dynamic_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_discover_Img.setImageResource(R.drawable.tabbar_icon2_pressed);
            tab_discover_Text.setTextColor(ContextCompat.getColor(this,R.color.C1));

            tab_message_Img.setImageResource(R.drawable.tabbar_icon3_normal);
            tab_message_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_mine_Img.setImageResource(R.drawable.tabbar_icon4_normal);
            tab_mine_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

        }else if (index==3)
        {
            if (messageFragment==null)
            {
                messageFragment=new MessageFragment();
                transaction.add(R.id.content,messageFragment);
            }else {

                transaction.show(messageFragment);
            }
            tab_home_Img.setImageResource(R.drawable.tabbar_icon0_normal);
            tab_home_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_dynamic_Img.setImageResource(R.drawable.tabbar_icon1_normal);
            tab_dynamic_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_discover_Img.setImageResource(R.drawable.tabbar_icon2_normal);
            tab_discover_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_message_Img.setImageResource(R.drawable.tabbar_icon3_pressed);
            tab_message_Text.setTextColor(ContextCompat.getColor(this,R.color.C1));

            tab_mine_Img.setImageResource(R.drawable.tabbar_icon4_normal);
            tab_mine_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

        }else if (index==4)
        {
            if (mineFragment==null)
            {
                mineFragment=new MineFragment();
                transaction.add(R.id.content,mineFragment);
            }else {

                transaction.show(mineFragment);
            }
            tab_home_Img.setImageResource(R.drawable.tabbar_icon0_normal);
            tab_home_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_dynamic_Img.setImageResource(R.drawable.tabbar_icon1_normal);
            tab_dynamic_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_discover_Img.setImageResource(R.drawable.tabbar_icon2_normal);
            tab_discover_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_message_Img.setImageResource(R.drawable.tabbar_icon3_normal);
            tab_message_Text.setTextColor(ContextCompat.getColor(this,R.color.C3));

            tab_mine_Img.setImageResource(R.drawable.tabbar_icon4_pressed);
            tab_mine_Text.setTextColor(ContextCompat.getColor(this,R.color.C1));
        }
        transaction.commit();

    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (dynamicFragment != null) {
            fragmentTransaction.hide(dynamicFragment);
        }
        if (discoverFragment != null) {
            fragmentTransaction.hide(discoverFragment);
        }
        if (messageFragment != null) {
            fragmentTransaction.hide(messageFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }

}
