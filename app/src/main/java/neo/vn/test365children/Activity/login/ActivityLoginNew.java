package neo.vn.test365children.Activity.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import neo.vn.test365children.Activity.ActivityHome;
import neo.vn.test365children.Activity.login.auth.PhoneAuthActivity;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.InfoKids;
import neo.vn.test365children.Models.InfoStudent;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Models.vip.ChildX;
import neo.vn.test365children.Models.vip.ObjLoginVip;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityLoginNew extends BaseActivity implements ImlLoginNew.View, ImlLogin.View {
    PresenterLoginNew mPresenter;
    PresenterLogin mPresenterLogin;
    String sUserMe;
    String sUserCon;
    String sPassword;
    String phoneV = "", passV = "";
    Boolean isShowPw = false;
    @BindView(R.id.edt_user_con)
    EditText edtPhoneLogin;
    @BindView(R.id.edt_user_pass)
    EditText edtPassLoginVip;
    @BindView(R.id.img_dangnhap)
    Button buttonLoginVip;
    @BindView(R.id.txtForgotPass)
    TextView tvForgotPassLoginVip;
    @BindView(R.id.imgBack)
    ImageView icBackLogin;
    @BindView(R.id.txtRegister)
    TextView tvRegister;
    @BindView(R.id.img_user_pass)
    ImageView imgPass;
    Realm mRealm;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLoginNew(this);
        mPresenterLogin = new PresenterLogin(this);
        mRealm = RealmController.with(this).getRealm();
        loadView();
        initEvent();
    }

    private void initEvent() {
        imgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPw) {
                    imgPass.setImageResource(R.drawable.ic_eye_hide);
                    edtPassLoginVip.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassLoginVip.setSelection(edtPassLoginVip.getText().length());
                    isShowPw = false;
                } else {
                    imgPass.setImageResource(R.drawable.ic_eye_show);
                    edtPassLoginVip.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassLoginVip.setSelection(edtPassLoginVip.getText().length());
                    isShowPw = true;
                }
            }
        });
    }

    private void loadView() {

    }

    private void login() {
        if (edtPhoneLogin.getText().toString().trim().length() > 0 && edtPassLoginVip.getText().toString().length() > 0) {
            showDialogLoading();
            phoneV = edtPhoneLogin.getText().toString().trim();
            passV = edtPassLoginVip.getText().toString();
            mPresenterLogin.apiLoginVip(phoneV, passV, "", "");
        }
    }

    @Override
    public void show_api_login(ResponInitChil mLis) {
    }

    @Override
    public void show_api_login(ObjLogin mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis);
            SharedPrefs.getInstance().put(Constants.KEY_USER_ME, sUserMe);
            SharedPrefs.getInstance().put(Constants.KEY_USER_CON, sUserCon);
            SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, sPassword);
            InfoKids objKid = mLis.getsObjInfoKid();
            InfoStudent infoStudent = new InfoStudent(
                    SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class),
                    SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class),
                    objKid.getsPROVINCE(),
                    objKid.getsDISTRICT(),
                    objKid.getsSCHOOL(),
                    objKid.getsCLASS(),
                    objKid.getsFULLNAME(),
                    objKid.getPROVINCE_ID(),
                    objKid.getDISTRICT_ID(),
                    objKid.getsSCHOOL_ID());
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(infoStudent);
            mRealm.commitTransaction();
            showDialogComfirm("Thông báo", mLis.getsRESULT(), false, new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    startActivity(new Intent(ActivityLoginNew.this, ActivityHome.class));
                    finish();
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        } else {
            showAlertDialog("Thông báo", mLis.getsRESULT());
        }

    }

    @Override
    public void show_update_infochil(ErrorApi obj) {

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_api_login_Vip(ObjLoginVip loginVip) {
        hideDialogLoading();
        if (loginVip != null) {
            if (loginVip.getERROR().equals("0000")) {
//                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
                SharedPrefs.getInstance().put(Constants.KEY_USER_ME, loginVip.getINFO().getUSERNAME());
                if (!loginVip.getINFO().getCHILDS().isEmpty()) {
                    ChildX childX = loginVip.getINFO().getCHILDS().get(0);
                    SharedPrefs.getInstance().put(Constants.KEY_USER_CON, childX.getUSERNAME());
                    SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, childX.getPASSWORD());
                    SharedPrefs.getInstance().put(Constants.IS_VIP, true);
                    SharedPrefs.getInstance().put(Constants.PHONE_VIP, phoneV);
                    SharedPrefs.getInstance().put(Constants.PASSWORD_VIP, passV);
                    sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
                    sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
                    sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
                    mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
                } else {
                    Intent intent = new Intent(ActivityLoginNew.this, ActivitySelectLevelTry.class);
                    startActivityForResult(intent, Constants.RequestCode.START_USER_TRY);
                }

            } else showDialogNotify("Thông báo", loginVip.getMESSAGE());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.START_USER_TRY:
                if (resultCode == RESULT_OK) {
//                    showDialogLoading();
                    sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
                    sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
                    sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
                    mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
//                    mPresenter_init.api_update_info_chil(sUserMe, sUserCon, "", App.sLevel, "",
//                            "", "", sPassword, "", "", "");
//                    check_update_token_push();
                }
                break;
            case Constants.RequestCode.START_UPDATE_INFOR_CHILD:
                if (resultCode == RESULT_OK) {
//                    Log.d(TAG, "onActivityResult: " + App.sLevel);
//                    initLogin();
                }
                break;
        }
    }

    private void registerVip2() {
//        String registerText = "Hãy soạn tin nhắn DK EH gửi 999 để đăng ký Gói cước EduVIP Home365 với ưu đãi cực lớn của Mobifone" +
//                " (1.5G data tốc độ cao và miễn phí tất cả các cuộc gọi < 20 phút trong ngày, giá gói 6.000đ/ngày)";
//        showDialogComfirm("Thông báo", registerText, true, new ClickDialog() {
//            @Override
//            public void onClickYesDialog() {
//            }
//
//            @Override
//            public void onClickNoDialog() {
//
//            }
//        });
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "999");
        smsIntent.putExtra("sms_body", "DK EVH");
        try {
            startActivity(smsIntent);
        } catch (ActivityNotFoundException e) {
            // Display some sort of error message here.
        }
    }

    private void registerVip1(String content, String phoneSend) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phoneSend);
        smsIntent.putExtra("sms_body", content);
        try {
            startActivity(smsIntent);
        } catch (ActivityNotFoundException e) {
            // Display some sort of error message here.
        }
    }

    private void forgotPass() {
        String sHotline = "Nhắn tin theo cú pháp <b><u><font color='#c21b37'>MK H1</font></u></b>" +
                " gửi <b><u><font color='#c21b37'>9285 </font></u></b> để lấy lại mật khẩu (cước SMS: Miễn phí) ";
        showDialogComfirm("Thông báo", sHotline, true, new ClickDialog() {
            @Override
            public void onClickYesDialog() {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "9285");
                smsIntent.putExtra("sms_body", "MK H1");
                try {
                    startActivity(smsIntent);
                } catch (ActivityNotFoundException e) {
                    // Display some sort of error message here.
                }
            }

            @Override
            public void onClickNoDialog() {

            }
        });
    }

    @OnClick({R.id.img_dangnhap, R.id.txtForgotPass, R.id.imgBack, R.id.txtRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_dangnhap:
                login();
                break;
            case R.id.txtForgotPass:
                //forgotPass();
                break;
            case R.id.imgBack:
                startActivity(new Intent(ActivityLoginNew.this, ActivityHome.class));
                finish();
                break;
            case R.id.txtRegister:
                startActivity(new Intent(ActivityLoginNew.this, ActivityRegister.class));
                finish();
                break;
        }
    }
}
