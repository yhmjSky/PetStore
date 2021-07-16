package xmut.ygnn.petstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.User;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

public class EditPersonInfActivity extends AppCompatActivity {
    private static final String TAG = "EditPersonInfActivity";

    private EditText nickname;
    private EditText age;
    private RadioGroup sex;
    Integer sexId = 0;
    private EditText address;

    private Button editButton;
    private Button cancelButton;
    private Button confirmButton;

    LinearLayout l1;
    LinearLayout l2 ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_personinf);
        findViews();
    }


    private void findViews() {

        User u1 = MainActivity.user;
        nickname = findViewById(R.id.new_nickname);
        nickname.setText(u1.getNickname());

        age = findViewById(R.id.new_age);
        age.setText(u1.getAge().toString());

        sex = findViewById(R.id.new_sex);
        switch (u1.getSex()){
            case 0:
                sexId = R.id.it;
                break;
            case 1:
                sexId = R.id.man;
                break;
            case 2:
                sexId = R.id.woman;
                break;
        }
        sex.check(sexId);



        address = findViewById(R.id.new_address);
        address.setText(u1.getAddress());

        cancelButton = findViewById(R.id.new_cancel);
        confirmButton = findViewById(R.id.new_update_inf);

        l1 = findViewById(R.id.to_edit_ll);//进入
        l2 = findViewById(R.id.in_edit_ll);//完成/退出

        editButton = findViewById(R.id.new_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nickname.setEnabled(true);
                age.setEnabled(true);
                address.setEnabled(true);

                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //恢复原本数据
                nickname.setText(u1.getNickname());
                sex.check(sexId);
                age.setText(u1.getAge().toString());
                address.setText(u1.getAddress());

                //禁止编辑
                age.setEnabled(false);
                nickname.setEnabled(false);
                address.setEnabled(false);

                //切换按钮所在布局可见性
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
            }
        });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u1.setNickname(nickname.getText().toString().trim());
                u1.setAge(Integer.parseInt(age.getText().toString().trim()));
                //u1.setSex();
                u1.setAddress(address.getText().toString().trim());

                //待实现 更新信息
                Result result = DatabaseUtil.updateById(u1,
                        HttpAddress.get(HttpAddress.user(), "update"));


                nickname.setEnabled(false);
                age.setEnabled(false);
                address.setEnabled(false);

                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);

            }
        });

    }

}
