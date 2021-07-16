package xmut.ygnn.petstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.File;

import xmut.ygnn.petstore.activity.VipActivity;

public class PersonFragment extends Fragment {


    GridLayout unloginGL,loginedGL;
    View view;
    View exitLogin;

    private EditText nickname;

    private ImageView toInf;
    private ImageView icon, vip;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_person,container,false);

        if(MainActivity.user != null)
        {

            unloginGL = view.findViewById(R.id.unloginperson);
            unloginGL.setVisibility(View.GONE);

            loginedGL = view.findViewById(R.id.loginedperson);
            loginedGL.setVisibility(View.VISIBLE);

            TextView userName = view.findViewById(R.id.personName);
            userName.setText(MainActivity.user.getUsername());

            exitLogin = view.findViewById(R.id.exitLoginLL);
            exitLogin.setVisibility(View.VISIBLE);


        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.unLogin_icon);

//        Glide.with(getActivity())
//                .load("https://img2.baidu.com/it/u=3717120934,3932520698&fm=26&fmt=auto&gp=0.jpg")
//                .placeholder(R.drawable.hwid_auth_button_background)
//                .into(imageView);

//        imageView.setImageURI(Uri.parse("https://img1.baidu.com/it/u=1485012388,2380514454&fm=26&fmt=auto&gp=0.jpg"));
        TextView textView = (TextView)getActivity().findViewById(R.id.unLogin_text);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                System.out.println("------头像被点击-----");
                Intent intent = new Intent();
                LoginActivity loginActivity = new LoginActivity();
                intent.setClass(getActivity(),loginActivity.getClass());
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                System.out.println("-----文本被点击-----");
                Intent intent = new Intent();
                LoginActivity loginActivity = new LoginActivity();
                intent.setClass(getActivity(),loginActivity.getClass());
                startActivity(intent);
            }
        });



    }


    /**
     * 每次切换后都会‘刷新’ 数据
     */

    @Override
    public void onResume() {


        if(MainActivity.user != null)
        {

            unloginGL = view.findViewById(R.id.unloginperson);
            unloginGL.setVisibility(View.GONE);

            loginedGL = view.findViewById(R.id.loginedperson);
            loginedGL.setVisibility(View.VISIBLE);

            TextView userName = view.findViewById(R.id.personName);
            userName.setText(MainActivity.user.getUsername());

            exitLogin = view.findViewById(R.id.exitLoginLL);
            exitLogin.setVisibility(View.VISIBLE);

            findViews();


        }
        super.onResume();
    }

    private void findViews() {

        icon = view.findViewById(R.id.logined_icon);

        Glide.with(getActivity())
        .load("https://img2.baidu.com/it/u=3717120934,3932520698&fm=26&fmt=auto&gp=0.jpg")
        .placeholder(R.drawable.hwid_auth_button_background)
        .into(icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        toInf = view.findViewById(R.id.toPersonInf);

        toInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                EditPersonInfActivity editPersonInfActivity = new EditPersonInfActivity();
                intent.setClass(getActivity(),editPersonInfActivity.getClass());
                startActivity(intent);
            }
        });


        TextView exit = view.findViewById(R.id.exit);
        exitLogin = view.findViewById(R.id.exitLoginLL);
        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user = null;
                Intent intent = new Intent();
                MainActivity activity = new MainActivity();
                intent.setClass(getActivity(),activity.getClass());
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.user = null;
                Intent intent = new Intent();
                MainActivity activity = new MainActivity();
                intent.setClass(getActivity(),activity.getClass());
                startActivity(intent);
            }
        });



        vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                VipActivity activity = new VipActivity();
                intent.setClass(getActivity(),activity.getClass());
                startActivity(intent);

            }
        });


    }
}
