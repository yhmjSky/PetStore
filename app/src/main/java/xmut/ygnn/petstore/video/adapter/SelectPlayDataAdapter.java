package xmut.ygnn.petstore.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.R;
import xmut.ygnn.petstore.video.contract.OnItemClickListener;
import xmut.ygnn.petstore.video.entity.PlayEntity;
import xmut.ygnn.petstore.video.utils.LogUtil;
import xmut.ygnn.petstore.video.utils.StringUtil;

public class SelectPlayDataAdapter extends Adapter<SelectPlayDataAdapter.PlayViewHolder> {
    private static final String TAG = "SelectPlayDataAdapter";
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<PlayEntity> playList = new ArrayList();

    static class PlayViewHolder extends ViewHolder {


        private ImageView imageView;
        private TextView playName;
        private TextView playType;
        private TextView playUrl;

        public PlayViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {

                this.imageView = itemView.findViewById(R.id.play_icon);
                this.playName = (TextView) itemView.findViewById(R.id.play_name);
                this.playType = (TextView) itemView.findViewById(R.id.play_type);
                this.playUrl = (TextView) itemView.findViewById(R.id.play_url);
            }
        }
    }

    public SelectPlayDataAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelectPlayList(List<PlayEntity> playList) {
        if (this.playList.size() > 0) {
            this.playList.clear();
        }
        this.playList.addAll(playList);
        notifyDataSetChanged();
    }

    public PlayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayViewHolder(LayoutInflater.from(this.context).inflate(R.layout.select_play_item, parent, false));
    }

    public void onBindViewHolder(PlayViewHolder holder, final int position) {
        if (this.playList.size() > position && holder != null) {
            PlayEntity playEntity = (PlayEntity) this.playList.get(position);
            if (playEntity == null) {
                LogUtil.i(TAG, "current item data is empty.");
                return;
            }

//            try {
//                Glide.with(context)
//                        .load(playList.get(position).getUrl())
//                        .into(holder.imageView);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            StringUtil.setTextValue(holder.playName, playEntity.getName());
            StringUtil.setTextValue(holder.playUrl, playEntity.getUrl());
            StringUtil.setTextValue(holder.playType, String.valueOf(playEntity.getUrlType()));
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SelectPlayDataAdapter.this.onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    public int getItemCount() {
        return this.playList.size();
    }
}
