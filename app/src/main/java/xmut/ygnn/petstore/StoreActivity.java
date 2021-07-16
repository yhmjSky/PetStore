package xmut.ygnn.petstore;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import xmut.ygnn.petstore.adapter.MyStoreAdapter;
import xmut.ygnn.petstore.adapter.StoreAdapter;

public class StoreActivity extends AppCompatActivity {


    public static int pos = -1;
    MyStoreAdapter myStoreAdapter;
    GestureDetector myGestureDetector;

    LinearLayout left_LL;
    View chLeftView;
    TextView all,pet,food,toy,cloth,medio,miji;

    RecyclerView storeRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        pos = StoreAdapter.pos;

        setContentView(R.layout.activity_store);

        initView();
        initListener();


        myStoreAdapter = new MyStoreAdapter(this);
        storeRecyclerView.setAdapter(myStoreAdapter);
        storeRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));

    }



    private void initView() {
        chLeftView = findViewById(R.id.mainViewRL);
        left_LL = findViewById(R.id.storeLL_left);
        storeRecyclerView = findViewById(R.id.goodslist);

        all = findViewById(R.id.all_goods);
        pet = findViewById(R.id.pet_goods);
        food = findViewById(R.id.food_goods);
        toy = findViewById(R.id.toy_goods);
        cloth = findViewById(R.id.cloth_goods);
        medio = findViewById(R.id.medio_goods);
        miji = findViewById(R.id.miji_goods);
    }

    private void initListener() {

        myGestureDetector = new GestureDetector(new myGestureListener());

        chLeftView.setOnTouchListener(new View.OnTouchListener() {
            //motionEvent可以捕捉我们触摸屏幕的event事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                myGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoodsThisStore();;
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(0);
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        toy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(1);
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        cloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(2);
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(3);
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(4);
                myStoreAdapter.notifyDataSetChanged();
            }
        });

        miji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStoreAdapter.goodsList = MyStoreAdapter.getGoods(5);
                myStoreAdapter.notifyDataSetChanged();
            }
        });




    }


    class myGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX()-e2.getX() >= 50)
            {
                left_LL.setVisibility(View.GONE);

//                Toast.makeText(StoreActivity.this,"从右往左滑动",Toast.LENGTH_LONG).show();

            }else if(e2.getX()-e1.getX() >= 50){
                left_LL.setVisibility(View.VISIBLE);
                left_LL.bringToFront();
//                Toast.makeText(StoreActivity.this,"从左往右滑动",Toast.LENGTH_LONG).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


}
