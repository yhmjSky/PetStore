package xmut.ygnn.petstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xmut.ygnn.petstore.MainActivity;
import xmut.ygnn.petstore.R;
import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.User;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

public class VipActivity extends AppCompatActivity {


    private TextView userName, vipLevel;
    private TextView applyVip, toSuperVip;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_vip);

        init();

    }

    private void init() {

        userName = findViewById(R.id.vipUser);
        vipLevel = findViewById(R.id.vipLevel);

        applyVip = findViewById(R.id.applyVip);
        toSuperVip = findViewById(R.id.toSuperVip);


        userName.setText( MainActivity.user.getUsername() );

        setLevel();


        applyVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.user.getPermission() == 0){

                    Toast.makeText(VipActivity.this, "账号封禁中", Toast.LENGTH_SHORT).show();

                } else if (MainActivity.user.getPermission() == 1) {

                    MainActivity.user.setPermission(2);
                    User u1 = MainActivity.user;

                    Result result = DatabaseUtil.updateById(u1,
                            HttpAddress.get(HttpAddress.user(), "update"));

                    Toast.makeText(VipActivity.this, "升级会员成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(VipActivity.this, "您已经是会员！！！", Toast.LENGTH_SHORT).show();
                }

                setLevel();

            }
        });


        toSuperVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VipActivity.this,"充值功能暂未开放", Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void setLevel() {

        String str = "";

        int color = R.color.vip0;//默认

        switch (MainActivity.user.getPermission()){

            case 0 :
                str = "封禁中";
                color = R.color.vip0;
                break;

            case 1 :
                str = "普通用户";
                color = R.color.vip1;
                break;

            case 2 :
                str = "会员";
                color = R.color.vip2;
                break;

            case 3 :
                str = "超级会员";
                color = R.color.vip3;
                break;

            default:
                str = "???";
                color = R.color.vip4;
                break;


        }

        vipLevel.setTextColor(getResources().getColor(color));
        vipLevel.setText(str);


    }
}