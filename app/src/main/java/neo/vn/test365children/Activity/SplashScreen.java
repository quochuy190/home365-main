package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.regex.Pattern;

import neo.vn.test365children.Activity.login.ActivityLoginNew;
import neo.vn.test365children.Activity.login.ActivityWelcomApp;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;


public class SplashScreen extends BaseActivity {
    private static final String TAG = "SplashScreen";

    ImageView img_splash;
    // public static Storage storage; // this Preference comes for free from the library
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent = new Intent();
    Intent mainIntent_welcom = new Intent();
    String id;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        boolean is_welcom_app = SharedPrefs.getInstance().get(Constants.KEY_ISLOGIN, Boolean.class);
        img_splash = (ImageView) findViewById(R.id.img_splash);
        Glide.with(this).load(R.drawable.bg_splash).into(img_splash);
        Intent deepLinkIntent = getIntent();
        String data = deepLinkIntent.getDataString();
        if (data != null){
            try{
                Log.d("data_deep_link", data);
                String dataUse = data.split(Pattern.quote("?"))[1];
                String[] arrayData = dataUse.split("&");
                Log.d("data_deep_link", Arrays.toString(arrayData));
                SharedPrefs.getInstance().put(Constants.PHONE_VIP, arrayData[0].split("=")[1]);
                SharedPrefs.getInstance().put(Constants.PASSWORD_VIP, arrayData[1].split("=")[1]);
                SharedPrefs.getInstance().put(Constants.IS_VIP, true);
                SharedPrefs.getInstance().put(Constants.USERCHILD_VIP, arrayData[2].split("=")[1]);
            }catch (Exception e){}
        }
        if (is_welcom_app) {
            mainIntent.setClass(SplashScreen.this, ActivityHome.class);
        } else {
            mainIntent.setClass(SplashScreen.this, ActivityLoginNew.class);
        }
        start_activity();
    }

    private String sTokenKey;


    private void start_activity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_splash_screen;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
}