package Adapter.manhua;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.example.meng.android_firstapp.manhua.ManhuaDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Modle.manhua.ManhuaSummaryResponse;
import Utils.MyApplication;

/**
 * Created by meng on 2017/7/5.
 */

public class ManhuaSummaruAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public List<ManhuaSummaryResponse.ManhuaSummary>manhuaList=new ArrayList<>();

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemChanged(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView timeText;
        public TextView titleText;
        public ImageView photoImg;
        public TextView numText;
        public  ImageView header;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView)
            {
                header=(ImageView)mHeaderView.findViewById(R.id.summary_header);
                return;
            }else {
                titleText=(TextView)itemView.findViewById(R.id.summary_title);
                photoImg=(ImageView)itemView.findViewById(R.id.summary_photo);
                timeText=(TextView)itemView.findViewById(R.id.summary_time);
                numText=(TextView)itemView.findViewById(R.id.summary_num);

            }


        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ManhuaSummaruAdapter.ViewHolder tempHolder=( ManhuaSummaruAdapter.ViewHolder)holder;
        if(getItemViewType(position) == TYPE_HEADER)
        {
            if(manhuaList.size()>0)
            {
                ManhuaSummaryResponse.ManhuaSummary manhuaHeader=  manhuaList.get(0);
                Picasso.with(MyApplication.getContext()).
                        load(manhuaHeader.thumb)
                        .placeholder(R.drawable.photo_default)
                        .error(R.drawable.photo_default)
                        .into(tempHolder.header);

            }

            return;
        };
        final int pos = getRealPosition(holder);
        ManhuaSummaryResponse.ManhuaSummary manhua=manhuaList.get(pos);



        tempHolder.titleText.setText(manhua.title);
        tempHolder.timeText.setText(manhua.inputtime);
        tempHolder.numText.setText(manhua.views);
        Picasso.with(MyApplication.getContext()).
                load(manhua.thumb)
                .placeholder(R.drawable.photo_default)
                .error(R.drawable.photo_default)
                .into(tempHolder.photoImg);


    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER)
        {

            return  new ManhuaSummaruAdapter.ViewHolder(mHeaderView);
        }



        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manhua_summary_item,parent,false);
        final ManhuaSummaruAdapter.ViewHolder holder=new ManhuaSummaruAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getRealPosition(holder);
                ManhuaSummaryResponse.ManhuaSummary manhua=manhuaList.get(position);
                Intent intent=new Intent(view.getContext(), ManhuaDetailActivity.class);
                intent.putExtra("manhua",manhua);
                view.getContext().startActivity(intent);

            }
        });
        return holder;

    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? manhuaList.size() : manhuaList.size() + 1;
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
}
