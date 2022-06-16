package neo.vn.test365children.Presenter;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;

public class PresenterBaitap implements ImpBaitap.Presenter {
    private static final String TAG = "PresenterBaitap";
    ApiServiceIml mApiService;
    ImpBaitap.View mView;

    public PresenterBaitap(ImpBaitap.View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }

    @Override
    public void get_api_list_buy(String sUserMe, String sUserCon, String sIdMon, String sIdKhoi) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_children");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "1");
        mMap.put("P1", sUserMe);

        mApiService.getApiService(new CallbackData<String>() {
            JSONObject jobj;
            JSONArray jArray;

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    // List<Childrens> mLis = Childrens.getList(objT);
                    mView.show_error_api(new ArrayList<ErrorApi>());
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_get_part(String sUserMe, String sUserCon, String sIdDebai) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_exe_detail";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);
        mMap.put("ID_EXCERCISE", sIdDebai);
        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    ResponDetailExer obj = new Gson().fromJson(objT, ResponDetailExer.class);
                    Log.i(TAG, "onGetDataSuccess: " + obj);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_list_get_part(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    // mView.show_error_api(null);
                }
            }
        }, sService, mMap);
    }

    @Override
    public void get_api_get_excercise_needed(String sUserMe, String sUserCon, String sDay) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_excercise_needed";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                List<ErrorApi> mLis = new ArrayList<>();
                ErrorApi obj = new ErrorApi();
                obj.setsERROR("0001");
                obj.setsRESULT("needed");
                mLis.add(obj);
                mView.show_error_api(mLis);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    ResponseObjWeek obj = new Gson().fromJson(objT, ResponseObjWeek.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_needed(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    // mView.show_error_api(null);
                }
            }
        }, sService, mMap);
    }

    @Override
    public void get_api_get_excercise_expired(String sUserMe, String sUserCon) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_excercise_expired";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    ResponseObjWeek obj = new Gson().fromJson(objT, ResponseObjWeek.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_expired(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                }
            }
        }, sService, mMap);
    }

    @Override
    public void get_api_start_taken(String sUserMe, String sUserCon, String sId_baitap,
                                    String time_lambai, String thoiluonglambai) {
        Map<String, String> mMap = new LinkedHashMap<>();
        /*mMap.put("Service", "start_taken");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "5");*/
        String sService = "start_taken";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);
        mMap.put("ID_EXCERCISE", sId_baitap);
        mMap.put("START_TIME", time_lambai);
        mMap.put("DURATION", thoiluonglambai);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_start_taken(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, sService, mMap);

    }

    @Override
    public void get_api_submit_execercise(String sUserMe, String sUserCon, String sId_baitap, String time_giaobai,
                                          String time_bdlambai, String time_ktlambai, String tong_time_lambai, String sKieunop,
                                          String sDiem, String sDanhsachcau) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "submit_execercise";
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);
        mMap.put("ID_EXCERCISE", sId_baitap);
        mMap.put("DELIVERY_TIME", time_bdlambai);
        mMap.put("START_TAKE_TIME", time_bdlambai);
        mMap.put("END_TAKE_TIME", time_ktlambai);
        mMap.put("DURATION", tong_time_lambai);
        mMap.put("SUBMIT_TYPE", sKieunop);
        mMap.put("POINT", sDiem);
        mMap.put("DETAIL", sDanhsachcau);
        Log.e("ActivityComplete", "get_api_submit_execercise: call submit");
        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: submit fault" + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i("ActivityComplete", "onGetDataSuccess: submid suscess " + objT);
                try {
                    //jArray = new JSONArray(c);
                    ErrorApi obj = new Gson().fromJson(objT, ErrorApi.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_submit_execercise(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                }
            }
        }, sService, mMap);
    }

    @Override
    public void get_exe_detail_taken(String USER_MOTHER, String USER_CHILD, String ID_EXCERCISE) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "get_exe_detail_taken";
        mMap.put("USER_MOTHER", USER_MOTHER);
        mMap.put("USER_CHILD", USER_CHILD);
        mMap.put("ID_EXCERCISE", ID_EXCERCISE);

        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    ResponDetailTakenExercise obj = new Gson().fromJson(objT, ResponDetailTakenExercise.class);
                    //   List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_detail_taken(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                }
            }
        }, sService, mMap);
    }

    @Override
    public void get_excercise_taken(String USER_MOTHER, String USER_CHILD) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("USER_MOTHER", USER_MOTHER);
        mMap.put("USER_CHILD", USER_CHILD);
        String sService = "get_excercise_taken";
        mApiService.getApiPostResfull_ALL(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                HomeworkDone homeworkDone = new Gson().fromJson(objT, HomeworkDone.class);
                mView.showHomeworkDone(homeworkDone);
            }

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
            }
        }, sService, mMap);
    }
}
