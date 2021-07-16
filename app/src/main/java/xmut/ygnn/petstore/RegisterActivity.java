package xmut.ygnn.petstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.User;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

import static java.lang.Thread.sleep;

public class RegisterActivity extends AppCompatActivity {

    //view
    EditText username_v;
    EditText password_v;
    EditText psw_confirm_v;
    RadioGroup sex_v;
    EditText age_v;
    EditText phone_v;
    EditText email_v;

    Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = username_v.getText().toString();
                String psw = password_v.getText().toString();
                String psw_2 = psw_confirm_v.getText().toString();

                String agestr = age_v.getText().toString().trim();
                if(agestr.equals("")) agestr = "0";//否则转换成整型报错
                Integer age = Integer.parseInt(agestr);


                String sexstr=((RadioButton)RegisterActivity.this.findViewById(sex_v.getCheckedRadioButtonId())).getText().toString();
                Integer sex = chSexId(sexstr);

                String phone = phone_v.getText().toString().trim();
                String email = email_v.getText().toString().trim();

                //openid, username,  nickname, password, age,  sex, phone, email, pression = 1
                User u0 = new User(null,name,name,psw,age,sex,phone,email, 1);


                if(name.equals(""))
                    Toast.makeText(RegisterActivity.this, "请输入用户账号", Toast.LENGTH_LONG).show();

                else if(psw.equals(""))
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_LONG).show();

                else if(!psw.equals(psw_2))
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_LONG).show();

                else if(phone.equals("") && email.equals(""))
                    Toast.makeText(RegisterActivity.this, "手机和邮箱不能全部为空", Toast.LENGTH_LONG).show();

                else {
                    // 输入信息 符合注册条件
                    Result result = DatabaseUtil.insert(u0,
                            HttpAddress.get(HttpAddress.user(), "insert"));
                    System.out.println(result.toString());

                    if (result.getCode() == 200) {

                        Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();
//                        User user = DatabaseUtil.getEntity(result,User.class);
                        System.out.println(u0);

                        try {
                            sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Result result2 = DatabaseUtil.login(new User(name, psw),
                                HttpAddress.get(HttpAddress.user(), "login"));

                        finish();

                    } else {

                        System.out.println("注册失败");
                        Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();
                    }

                    System.out.println("----------------------------------");
                }

            }
        });
    }
    private void findViews() {
        username_v = findViewById(R.id.regName);
        password_v = findViewById(R.id.regPassword);
        psw_confirm_v = findViewById(R.id.confirmPassword);

        sex_v = findViewById(R.id.regSex);
        age_v = findViewById(R.id.regAge);
        phone_v = findViewById(R.id.regPhone);
        email_v = findViewById(R.id.regEmail);

        register = findViewById(R.id.register_confirm);


    }


    public int chSexId(String sexstr){
        int sex = 0;
        switch(sexstr){
            case "保密":
                sex = 0;
                break;

            case "男" :
                sex = 1;
                break;

            case "女" :
                sex = 2;
                break;

        };

        return sex;
    }

}
