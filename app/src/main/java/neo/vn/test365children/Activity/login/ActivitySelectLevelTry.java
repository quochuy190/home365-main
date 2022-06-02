package neo.vn.test365children.Activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.list_child.DataChild;
import neo.vn.test365children.Models.list_child.INFOItem;
import neo.vn.test365children.Models.list_child.ResponseCreateChild;
import neo.vn.test365children.Models.vip.ObjLoginVip;
import neo.vn.test365children.Presenter.ImgGetUserTry;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.PresenterCreateUserTry;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivitySelectLevelTry extends BaseActivity
        implements View.OnClickListener, ImgGetUserTry.View , ImlLogin.View {
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_level_1)
    Button btn_level_1;
    @BindView(R.id.btn_level_2)
    Button btn_level_2;
    @BindView(R.id.btn_level_3)
    Button btn_level_3;
    @BindView(R.id.btn_level_4)
    Button btn_level_4;
    @BindView(R.id.btn_level_5)
    Button btn_level_5;
    private PresenterCreateUserTry mPresenter;
    private String sUUID;
    private String sUserMe="", sUserCon="", sPassword="";
    private List<INFOItem> listUserChild;
    private int sLevel = 1;
    PresenterLogin mPresenterLogin;

    @Override
    public int setContentViewId() {
        return R.layout.activity_select_level_usertry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listUserChild = new ArrayList<>();
        mPresenter = new PresenterCreateUserTry(this);
        mPresenterLogin = new PresenterLogin(this);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        showDialogLoading();
        mPresenter.apiGetListChildrem(sUserMe, sUserCon);
        sUUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        initImage();
        initEvent();
       /* Glide.with(this).load(urlImage)
                .apply(bitmapTransform(new BlurTransformation(10)))
                .into(img_buysong_detail_nen);*/
    }


    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_level_1.setOnClickListener(this);
        btn_level_2.setOnClickListener(this);
        btn_level_3.setOnClickListener(this);
        btn_level_4.setOnClickListener(this);
        btn_level_5.setOnClickListener(this);
    }

    private void initImage() {
        Glide.with(this).load(R.drawable.bg_home).into(img_background);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_level_1:
                get_user_try(1);
                break;
            case R.id.btn_level_2:
                get_user_try(2);
                break;
            case R.id.btn_level_3:
                get_user_try(3);
                break;
            case R.id.btn_level_4:
                get_user_try(4);
                break;
            case R.id.btn_level_5:
                get_user_try(5);
                break;
        }

    }

    private void get_user_try(int sLevel) {
        this.sLevel = sLevel;
        for (INFOItem data :listUserChild){
            if (data.getIDLEVEL() == sLevel){
                sPassword = data.getPASS();
                sUserCon = data.getUSERNAME();
                mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
                return;
            }
        }
        mPresenter.apiCreateChild(sUserMe, sLevel+"");
    }

    private void callback(String sLevel){
        setResult(RESULT_OK, new Intent());
        App.sLevel = sLevel;
        finish();
    }

    @Override
    public void show_create_user_try(ObjLogin obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            setResult(RESULT_OK, new Intent());
            App.sObjLogin = obj;
            finish();
        } else showDialogNotify(obj.getsMESSAGE(), obj.getsRESULT());
    }

    @Override
    public void show_api_login(ObjLogin mLis) {
        hideDialogLoading();
        if (mLis != null) {
            if (mLis.getsERROR().equals("0000")) {
                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis);
                SharedPrefs.getInstance().put(Constants.KEY_USER_ME, sUserMe);
                SharedPrefs.getInstance().put(Constants.KEY_USER_CON, sUserCon);
                SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, sPassword);
                callback(sLevel+"");
            }
        }
    }

    @Override
    public void show_update_infochil(ErrorApi obj) {

    }

    @Override
    public void show_error_api(ErrorApi objError) {
        hideDialogLoading();
    }

    @Override
    public void show_api_login_Vip(ObjLoginVip loginVip) {

    }

    @Override
    public void showDataUserChild(DataChild dataChild) {
        hideDialogLoading();
        if (dataChild.getERROR().equals("0000")){
            listUserChild.clear();
            listUserChild.addAll(dataChild.getINFO());
        }
    }

    @Override
    public void showResposeCreateChild(ResponseCreateChild responseCreateChild) {
        hideDialogLoading();
        sUserCon = responseCreateChild.getUSERCHILD();
        sPassword =responseCreateChild.getPASSWORD();
        mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
    }
}
