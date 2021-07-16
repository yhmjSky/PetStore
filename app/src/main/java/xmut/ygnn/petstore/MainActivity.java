package xmut.ygnn.petstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import xmut.ygnn.petstore.entity.Result;
import xmut.ygnn.petstore.entity.Store;
import xmut.ygnn.petstore.entity.User;
import xmut.ygnn.petstore.util.DatabaseUtil;
import xmut.ygnn.petstore.util.http.HttpAddress;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 123;

    public static User user = null;

    static ArrayList<Store> stores = new ArrayList<>();


    BottomNavigationView bottomNav;//下方栏菜单
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }


        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavView);
        permission();
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        FragmentTransaction fragment3
                                = getSupportFragmentManager().beginTransaction();

                        fragment3.replace(R.id.mainFragment,new HomeFragment());
                        fragment3.commit();

                        item.setChecked(true);

                        break;

                    case R.id.video:

//                        Toast.makeText(MainActivity.this,
//                                "视频",Toast.LENGTH_SHORT).show();
                        System.out.println("视频");
                        FragmentTransaction fragment2
                                = getSupportFragmentManager().beginTransaction();

                        fragment2.replace(R.id.mainFragment,new VideoFragment());
                        fragment2.commit();

                        item.setChecked(true);

                        break;

                    case R.id.message:
                        item.setChecked(true);

                        FragmentTransaction fragment20
                                = getSupportFragmentManager().beginTransaction();

                        fragment20.replace(R.id.mainFragment,new MessageFragment());
                        fragment20.commit();

                        break;

                    case R.id.person:
//                        Toast.makeText(MainActivity.this,
//                                "个人中心",Toast.LENGTH_SHORT).show();
                        System.out.println("个人中心");
                        FragmentTransaction fragmentTransaction
                                = getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.replace(R.id.mainFragment,new PersonFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });



    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
        else
        {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, new HomeFragment());
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.mainFragment, new PersonFragment());
                fragmentTransaction.commit();
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSION);
            }
        }
    }



}