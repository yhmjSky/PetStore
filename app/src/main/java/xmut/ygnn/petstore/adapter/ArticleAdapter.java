package xmut.ygnn.petstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.R;
import xmut.ygnn.petstore.entity.Article;
import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.Store;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder>{


    public static int pos;
    private Context mContext;
    static List<Article> articles;
    View view;


    public ArticleAdapter(Context mContext) {
        this.mContext = mContext;
        articles = getAll();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.article_item,parent,false);
        return new ArticleAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Glide.with(mContext)
                .load(articles.get(position).getImg())
                .into(holder.img);

        holder.title.setText(articles.get(position).getTitle());

        holder.author.setText("author: " + articles.get(position).getAuthor());

//        holder.des.setText(articles.get(position).getDes());



    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title , author , des;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.articleImg);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
//            des = itemView.findViewById(R.id.des);

        }


    }


    public static List<Article> getAll(){


        List<Article> tempArticle = new ArrayList<>();


        Result result = DatabaseUtil.selectList(
                HttpAddress.get(HttpAddress.article(), "list"));


        tempArticle = DatabaseUtil.getObjectList(result,Article.class);

        return tempArticle;

    }


}
