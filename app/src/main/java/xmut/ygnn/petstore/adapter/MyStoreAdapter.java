package xmut.ygnn.petstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.R;
import xmut.ygnn.petstore.StoreActivity;
import xmut.ygnn.petstore.entity.Goods;
import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;


public class MyStoreAdapter extends RecyclerView.Adapter<MyStoreAdapter.MyViewHolder>{

    private Context mContext;
    public static List<Goods> goodsList;
    public static int[] storeNum = new int[100];
    public static double[] storePrice = new double[100];
    View view;
    View mainView;
    static Integer id1 = StoreActivity.pos;//宠物店， 类型


    public MyStoreAdapter(Context mContext) {
        this.mContext = mContext;
        goodsList = getGoodsThisStore();

    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.goods_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(goodsList.get(position).getName());
        holder.inf.setText(goodsList.get(position).getDescribe());
        holder.danjia.setText("￥ " + String.valueOf(goodsList.get(position).getPrice()));
        storePrice[position] = Double.parseDouble(String.valueOf(goodsList.get(position).getPrice()));

//        id1 = goodsList.get(position).getStorecode();这种读法等于只读取数据库第几个商品对应的店铺，而数据库的第几个店铺不一定对应
        id1 = StoreActivity.pos;

        holder.danjia.setText(String.valueOf(goodsList.get(position).getPrice()));


        try {
            Glide.with(mContext)
                    .load(goodsList.get(position).getImg())
                    .into(holder.goodImg);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        holder.num.setText("0");
        storeNum[position] = Integer.parseInt(holder.num.getText().toString());
        holder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (storeNum[position]>0) {

                    storeNum[position]--;
                    Toast.makeText(mContext,"-1",Toast.LENGTH_SHORT).show();
                    holder.num.setText(String.valueOf(storeNum[position]));
                    notifyDataSetChanged();
                }
            }
        });



//        holder.num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                storeNum[position] = Integer.parseInt(String.valueOf(v.getText()));
//                return false;
//            }
//        });



        holder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeNum[position]++;
                Toast.makeText(mContext,"+1",Toast.LENGTH_SHORT).show();
                holder.num.setText(String.valueOf(storeNum[position]));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,danjia,num,inf;

        ImageView goodImg,left,right;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            goodImg = itemView.findViewById(R.id.good_img);


            name = itemView.findViewById(R.id.goods_name);
            danjia = itemView.findViewById(R.id.goods_price);
            num = itemView.findViewById(R.id.this_num);

            inf = itemView.findViewById(R.id.goods_inf);

            left = itemView.findViewById(R.id.left_reduce);
            right = itemView.findViewById(R.id.right_add);

        }

    }

    public static List<Goods> getAllGoods() {

        List<Goods> tempStore = new ArrayList<>();


        Result result = DatabaseUtil.selectList(
                HttpAddress.get(HttpAddress.goods(), "list"));

        tempStore = DatabaseUtil.getObjectList(result,Goods.class);

        return tempStore;
    }



    public static List<Goods> getGoodsThisStore() {

        List<Goods> tempStore = new ArrayList<>();
        id1 = StoreActivity.pos;

        Result result = DatabaseUtil.selectList(
                HttpAddress.get(HttpAddress.goods(), "line2",id1));

        tempStore = DatabaseUtil.getObjectList(result,Goods.class);

        return tempStore;
    }




    /**
     * 按照商铺、类型查找
     * @return
     */

    public static List<Goods> getGoods(int type) {

        List<Goods> tempStore = new ArrayList<>();

        Result result = DatabaseUtil.selectList(
                HttpAddress.get(HttpAddress.goods(), "line",id1,type));

        tempStore = DatabaseUtil.getObjectList(result,Goods.class);

        return tempStore;
    }


}
