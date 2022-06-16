package neo.vn.test365children.Activity.login;

import android.util.Log;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Untils.SharedPrefs;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 7/31/2018
 * @updated 7/31/2018
 * @modified by
 * @updated on 7/31/2018
 * @since 1.0
 */
public class PresenterChangePass {
    private static final String TAG = "PresenterLogin";
    ApiServiceIml mApiService;
    View mView;

    public PresenterChangePass(View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }


    public void apiChangePass(String newPass) {
        final Map<String, String> mMap = new LinkedHashMap<>();
        String sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        String sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        String sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
        String sService = "changepass";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);
        mMap.put("OLDPASS ", sPassword);
        mMap.put("NEWPASS", newPass);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                ResponInitChil objLogin = new Gson().fromJson(objT, ResponInitChil.class);
                mView.show_api_change_pass(objLogin);
            }
        }, sService, mMap);
    }

    interface View {
        void show_api_change_pass(ResponInitChil mLis);

        void show_error_api(ErrorApi mLis);
    }
}

