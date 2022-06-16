package neo.vn.test365children;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.City;
import neo.vn.test365children.Models.DetailExercise;
import neo.vn.test365children.Models.District;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.GameTNNL;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.Schools;
import neo.vn.test365children.Models.Sticker;

public class App extends Application {
    private static App sInstance;
    private Gson mGSon;

    public static App self() {
        return sInstance;
    }
    public static District mDistrict;
    public static City mCity;
    public static Schools mSchools;
    public static String mKhoihoc;
    public static String mNamhoc;
    public static List<District> mLisDistrict;
    public static List<City> mLisCity;
    public static List<Schools> mLisSchools;

    public static List<Cauhoi> mLisCauhoi;
    public static List<Baitap_Tuan> mBaiTapTuan;
    public static List<GameTrieuPhuTriThuc> mLisGameTPTT;
    public static ExerciseAnswer mExercise;
    public static ExerciseAnswer mExerciseReview;
    public static List<GameTNNL> mLisGameTNNL;
    public static String sTime = "";
    public static List<Sticker> mListSticker;
    public static ObjLogin sObjLogin;
    public static String sLevel;
    public static final String CHANNEL_ID = "home365kid";
    public static DetailExercise mExerLuyentap;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        sInstance = this;
        mGSon = new Gson();
        mBaiTapTuan = new ArrayList<>();
        mLisDistrict = new ArrayList<>();
        mLisCity = new ArrayList<>();
        mLisSchools = new ArrayList<>();
        sObjLogin = new ObjLogin();
        mListSticker = new ArrayList<>();
        mLisGameTNNL = new ArrayList<>();
        mLisCauhoi = new ArrayList<>();
        mLisGameTPTT = new ArrayList<>();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("test365.realm")
                .schemaVersion(42)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Home365",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    public Gson getGSon() {
        return mGSon;
    }
}
