package xmut.ygnn.petstore.video;

import android.app.Application;
import android.util.Log;

import com.huawei.hms.videokit.player.InitFactoryCallback;
import com.huawei.hms.videokit.player.WisePlayerFactory;
import com.huawei.hms.videokit.player.WisePlayerFactoryOptionsExt;

import java.util.UUID;

public class VideoKitPlayApplication extends Application {
    private static final String TAG = VideoKitPlayApplication.class.getSimpleName();
    private static WisePlayerFactory factory = null;

    @Override
    public void onCreate() {
        super.onCreate();
        String uniqueID= UUID.randomUUID().toString();
        // setDeviceId方法需要传入手机的DeviceId
            // setServeCountry方法需要传入国家/地区码
        System.out.println("deviceid"+uniqueID);
        WisePlayerFactoryOptionsExt factoryOptions =
                new WisePlayerFactoryOptionsExt.Builder().setDeviceId(uniqueID).build();

        // Application onCreate在多进程的场景下会被调用多次
        // App需要在App进程（App包名）和播放器进程（App包名:player）的Application onCreate中调用WisePlayerFactory.initFactory()接口
        WisePlayerFactory.initFactory(this, factoryOptions, new InitFactoryCallback() {
            @Override
            public void onSuccess(WisePlayerFactory wisePlayerFactory) {
                Log.d(TAG, "onSuccess wisePlayerFactory:" + wisePlayerFactory);
                factory = wisePlayerFactory;
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Log.e(TAG, "onFailure errorcode:" + errorCode + " reason:" + msg);
            }
        });
    }

    public static WisePlayerFactory getWisePlayerFactory() {
        if (null != factory) {
            System.out.println( " factory"+" success");
            return factory;
        }
        System.out.println( " factory"+" null");
        return null;
    }


}