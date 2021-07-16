package xmut.ygnn.petstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;

import java.util.List;
import java.util.Random;

import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.User;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

//    public static final String TAG = "LoginActivity";

    Context mContext;
    private AccountAuthParams authParams;
    private AccountAuthService authService;
    private EditText userName;
    private EditText password;

    private TextView register;
    private Button login;
    HuaweiIdAuthButton huaweiIdAuthButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);
        findViews();
    }

    private void findViews() {
        userName=(EditText) findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        login=(Button) findViewById(R.id.login);
        register=(TextView)findViewById(R.id.register);


        huaweiIdAuthButton = (HuaweiIdAuthButton) findViewById(R.id.huaweiLoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                System.out.println(name);
                String pass = password.getText().toString();
                System.out.println(pass);


//                Result result= DatabaseUtil.loginByUserName(
//                        HttpAddress.get(HttpAddress.user(),"login"));
                Result result = DatabaseUtil.login(new User(name, pass),
                        HttpAddress.get(HttpAddress.user(), "login"));

                User user = DatabaseUtil.getEntity(result,User.class);

                if(result.getCode()==200){
                    //登录成功
                    System.out.println(user);
                    MainActivity.user = user;

                    finish();

                }
                else {

                    Toast.makeText(LoginActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();
                }

                System.out.println("----------------------------------");

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                RegisterActivity registerActivity = new RegisterActivity();
                intent.setClass(LoginActivity.this,registerActivity.getClass());
                startActivity(intent);
            }
        });



        huaweiIdAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAuthorizationCode().createParams();
                authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams();
                authService = AccountAuthManager.getService(LoginActivity.this, authParams);
                startActivityForResult(authService.getSignInIntent(), 8888);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //授权登录结果处理，从AuthAccount中获取Authorization Code
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                //登录成功，获取用户的帐号信息和Authorization Code、 ID Token
                AuthAccount authAccount = authAccountTask.getResult();
//                Log.i(TAG, "serverAuthCode:" + authAccount.getAuthorizationCode());


                Log.i(TAG, "open id" + authAccount.getOpenId());
                Log.i(TAG, "idToken:" + authAccount.getIdToken());
                //获取帐号类型，0表示华为帐号、1表示AppTouch帐号
                Log.i(TAG, "accountFlag:" + authAccount.getAccountFlag());

                User uu = new User();
                uu.setOpenid(authAccount.getOpenId());
                Result result = DatabaseUtil.login(uu,
                        HttpAddress.get(HttpAddress.user(), "loginById"));

                User user = DatabaseUtil.getEntity(result,User.class);

                if(result.getCode()==200){
                    //登录成功
                    System.out.println(user);
                    MainActivity.user = user;

                    finish();

                }
                else{
                    //有华为id但登录失败，数据库没数据
                    User newUser = createUser(authAccount.getOpenId());//传入openid，随机创建用户
                    Result result2 = DatabaseUtil.login(newUser,
                            HttpAddress.get(HttpAddress.user(), "loginById"));

                    User user2 = DatabaseUtil.getEntity(result,User.class);
                    if(result2.getCode()==200){
                        //登录成功
                        System.out.println(user2);
                        MainActivity.user = user2;
                        finish();

                    }


                }




            } else {
                //登录失败
                Log.e(TAG, "sign in failed:" + ((ApiException) authAccountTask.getException()).getStatusCode());
            }


        }
    }



    protected User createUser(String openId){

        Random rand = new Random();

        String name = new String("huawei_");

        int charLen = rand.nextInt(2) + 3;
        char[] f_str = new char[10];

        for (int i = 0 ; i < charLen ; i++)
        {
            int temp = rand.nextInt(26);
            name += ('a' + temp);//随机前缀
        }

        int num;
        num = rand.nextInt(899) + 100 ;//随机id
        name += num;


        String pass = "123456";//默认密码

        User cu = new User(name,pass);
        cu.setOpenid(openId);

        return cu;
    }


}
