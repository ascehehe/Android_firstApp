package Adapter.manhua;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Utils.MyApplication;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaDetailAdpter extends BaseAdapter {

    private  Context currentContest;
    public List<String>photoList=new ArrayList<>();

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }


    public ManhuaDetailAdpter(Context currentContest) {
        this.currentContest = currentContest;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String photoStr=photoList.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null)
        {
            view= LayoutInflater.from(currentContest).inflate(R.layout.manhua_detail_item_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.photo=(ImageView)view.findViewById(R.id.detail_item_photo);

            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

//        Log.i("ManhuaDetailAdpter", "getView: "+photoStr);

        Picasso.with(MyApplication.getContext()).
                load(photoStr)
                .placeholder(R.drawable.photo_default)
                .error(R.drawable.photo_default)
                .into(viewHolder.photo);

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    class ViewHolder
    {
        ImageView photo;


    }
}
