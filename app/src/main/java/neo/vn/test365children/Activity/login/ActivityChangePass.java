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
import neo.vn.test365children.Activity.ActivityHome;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Models.vip.ChildX;
import neo.vn.test365children.Models.vip.ObjLoginVip;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityChangePass extends BaseActivity implements PresenterChangePass.View {
    PresenterChangePass mPresenter;
    String sUserMe;
    String sUserCon;
    String sPassword;
    String sPassOld = "", sPassNew = "", sPassConfirm = "";
    @BindView(R.id.edtPassOld)
    EditText edtPassOld;
    @BindView(R.id.edtPassNew)
    EditText edtPassNew;
    @BindView(R.id.edtPassConfirm)
    EditText edtPassConfirm;
    @BindView(R.id.btnChangePass)
    Button btnChangePass;
    @BindView(R.id.imgShowPassOld)
    ImageView imgShowPassOld;
    @BindView(R.id.imgShowPass)
    ImageView imgShowPass;
    @BindView(R.id.imgShowPassConfirm)
    ImageView imgShowPassConfirm;
    @BindView(R.id.imgBack)
    ImageView icBackLogin;
    boolean isShowPassOld = false;
    boolean isShowPassNew = false;
    boolean isShowPassConfirm = false;

    @Override
    public int setContentViewId() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterChangePass(this);
        loadView();
        initEvent();
    }

    private void initEvent() {
        icBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgShowPassOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPassOld) {
                    imgShowPassOld.setImageResource(R.drawable.ic_eye_hide);
                    edtPassOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassOld.setSelection(edtPassOld.getText().length());
                    isShowPassOld = false;
                } else {
                    imgShowPassOld.setImageResource(R.drawable.ic_eye_show);
                    edtPassOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassOld.setSelection(edtPassOld.getText().length());
                    isShowPassOld = true;
                }
            }
        });

        imgShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPassNew) {
                    imgShowPass.setImageResource(R.drawable.ic_eye_hide);
                    edtPassNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassNew.setSelection(edtPassNew.getText().length());
                    isShowPassNew = false;
                } else {
                    imgShowPass.setImageResource(R.drawable.ic_eye_show);
                    edtPassNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassNew.setSelection(edtPassNew.getText().length());
                    isShowPassNew = true;
                }
            }
        });

        imgShowPassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPassConfirm) {
                    imgShowPassConfirm.setImageResource(R.drawable.ic_eye_hide);
                    edtPassConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassConfirm.setSelection(edtPassConfirm.getText().length());
                    isShowPassConfirm = false;
                } else {
                    imgShowPassConfirm.setImageResource(R.drawable.ic_eye_show);
                    edtPassConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassConfirm.setSelection(edtPassConfirm.getText().length());
                    isShowPassConfirm = true;
                }
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
    }

    private void loadView() {

    }

    private void changePass() {
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
        sPassOld = edtPassOld.getText().toString().trim();
        sPassNew = edtPassNew.getText().toString().trim();
        sPassConfirm = edtPassConfirm.getText().toString().trim();
        if (sPassOld.isEmpty()){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass_old));
            return;
        }
        if (sPassNew.isEmpty()){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass_new));
            return;
        }
        if (sPassConfirm.isEmpty()){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass_confirm));
            return;
        }
        if (!sPassNew.equals(sPassConfirm)){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_passconfirm_not_same));
            return;
        }
        if (!sPassOld.equals(sPassOld)){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass_not_same));
            return;
        }
        showDialogLoading();
        mPresenter.apiChangePass(sPassNew);


    }



    @Override
    public void show_api_change_pass(ResponInitChil mLis) {
        if (mLis.getsERROR().equals("0000")){
            Intent intent = new Intent(ActivityChangePass.this, ActivityLoginNew.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }else {
            showAlertDialog(getString(R.string.notify), mLis.getsRESULT());
            return;
        }

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
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

}
