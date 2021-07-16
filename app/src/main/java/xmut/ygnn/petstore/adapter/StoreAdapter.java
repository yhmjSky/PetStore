package xmut.ygnn.petstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.R;
import xmut.ygnn.petstore.StoreActivity;
import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.Store;

import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;


public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder>{


    public static int pos;
    private Context mContext;
    static List<Store> stores;
    View view;

    public StoreAdapter(Context mContext) {
        this.mContext = mContext;
        stores = getAllStore();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.store_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(stores.get(position).getName());

        Glide.with(mContext)
                .load(stores.get(position).getImg())
                .into(holder.thumbnail);

        holder.inf.setText(stores.get(position).getDes());

        holder.star.setText(String.valueOf(stores.get(position).getStar()));


        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, StoreActivity.class);
                pos = position + 1;
//                intent.putExtra("position",position);
//                intent.putExtra("sender", "FilesIsSending");
                mContext.startActivity(intent);
            }
        });


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"1111111",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return stores.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView name , inf , star;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            name = itemView.findViewById(R.id.store_name);
            inf = itemView.findViewById(R.id.store_inf);
            star = itemView.findViewById(R.id.store_star);
        }


    }


    public static List<Store> getAllStore(){


        List<Store> tempStore = new ArrayList<>();


        Result result = DatabaseUtil.selectList(
                HttpAddress.get(HttpAddress.store(), "list"));


        tempStore = DatabaseUtil.getObjectList(result,Store.class);

        return tempStore;

    }


}
