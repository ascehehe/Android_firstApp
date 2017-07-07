package Adapter.manhua;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meng.android_firstapp.R;
import com.example.meng.android_firstapp.manhua.ManhuaSummaryActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import Modle.manhua.Manhua;
import Utils.MyApplication;

/**
 * Created by meng on 2017/7/4.
 */

public class ManhuaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Manhua>manhuaList;

    public List<Manhua> getManhuaList() {
        return manhuaList;
    }

    public void setManhuaList(List<Manhua> manhuaList) {
        this.manhuaList = manhuaList;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView typeText;
        public TextView titleText;
        public ImageView photoImg;
        public TextView chapterText;
        public TextView numText;

        public ViewHolder(View itemView) {
            super(itemView);
            typeText=(TextView)itemView.findViewById(R.id.type_manhua_item);
            titleText=(TextView)itemView.findViewById(R.id.title_manhua_item);
            photoImg=(ImageView)itemView.findViewById(R.id.imageView_manhua_item);
            chapterText=(TextView)itemView.findViewById(R.id.chapter_manhua_item);
            numText=(TextView)itemView.findViewById(R.id.num_manhua_item);

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manhua_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Manhua manhua=manhuaList.get(position);

                Intent intent=new Intent(view.getContext(), ManhuaSummaryActivity.class);
                intent.putExtra("manhua",manhua);
                view.getContext().startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        Manhua manhua=manhuaList.get(position);
        ViewHolder tempHolder=(ViewHolder)holder;
        tempHolder.typeText.setText(manhua.getCatname());
        tempHolder.titleText.setText(manhua.getParent_catname());
        tempHolder.chapterText.setText(manhua.getTitle());
        tempHolder.numText.setText(manhua.getUpdatetime());
        Picasso.with(MyApplication.getContext()).
                load(manhua.getImage())
                .placeholder(R.drawable.photo_default)
                .error(R.drawable.photo_default)
                .into(tempHolder.photoImg);

    }

    @Override
    public int getItemCount() {

        return manhuaList.size();
    }
}
