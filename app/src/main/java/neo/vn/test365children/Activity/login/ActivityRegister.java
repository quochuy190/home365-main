    package neo.vn.test365children.Activity.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import neo.vn.test365children.Activity.ActivityHome;
import neo.vn.test365children.Activity.ActivityLogin;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.BuildConfig;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.MenuSearch.ActivityDanhsachKhoihoc;
import neo.vn.test365children.MenuSearch.ActivityListDistrict;
import neo.vn.test365children.MenuSearch.ActivityListSchools;
import neo.vn.test365children.Models.City;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Models.vip.ChildX;
import neo.vn.test365children.Models.vip.ObjLoginVip;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.ReadPathUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityRegister extends BaseActivity implements PresenterRegister.View {
    PresenterRegister mPresenter;

    String sFullName;
    String sPhone;
    String sPassword;
    String sPasswordConfirm;

//    @BindView(R.id.edtFullName)
//    EditText edtFullName;
//    @BindView(R.id.edtPhone)
//    EditText edtPhone;
    @BindView(R.id.edtPass)
    EditText edtPass;
    @BindView(R.id.edtPassConfirm)
    EditText edtPassConfirm;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.txtLogin)
    TextView txtLogin;
    @BindView(R.id.imgPass)
    ImageView imgPass;
    @BindView(R.id.imgPassConfirm)
    ImageView imgPassConfirm;
    @BindView(R.id.imgDistrict)
    ImageView imgDistrict;
    @BindView(R.id.imgSchools)
    ImageView imgSchools;
    @BindView(R.id.imgLevel)
    ImageView imgLevel;
    @BindView(R.id.edtDistrict)
    EditText edtDistrict;
    @BindView(R.id.edtSchools)
    EditText edtSchools;
    @BindView(R.id.edtLevel)
    EditText edtLevel;
    @BindView(R.id.edtCodePupul)
    EditText edtCodePupul;
    Boolean isShowPw = false;
    Boolean getIsShowPwConfirm = false;
    String sClassId, sCodePupil;
    String sIdTruong;
    @Override
    public int setContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterRegister(this);
        String sPhone = getIntent().getStringExtra("phone");
     //   edtPhone.setText(sPhone);
        imgPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowPw) {
                    imgPass.setImageResource(R.drawable.ic_eye_hide);
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPass.setSelection(edtPass.getText().length());
                    isShowPw = false;
                } else {
                    imgPass.setImageResource(R.drawable.ic_eye_show);
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPass.setSelection(edtPass.getText().length());
                    isShowPw = true;
                }
            }
        });

        imgPassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIsShowPwConfirm) {
                    imgPassConfirm.setImageResource(R.drawable.ic_eye_hide);
                    edtPassConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassConfirm.setSelection(edtPassConfirm.getText().length());
                    getIsShowPwConfirm = false;
                } else {
                    imgPassConfirm.setImageResource(R.drawable.ic_eye_show);
                    edtPassConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassConfirm.setSelection(edtPassConfirm.getText().length());
                    getIsShowPwConfirm = true;
                }
            }
        });
        initEvent();
    }

    private void initEvent() {
        imgDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City objCity = new City();
                objCity.setsPROVINCE_NAME("Tỉnh Bắc Giang");
                objCity.setsID("24");
                App.mCity = objCity;
                if (App.mCity != null && App.mCity.getsPROVINCE_NAME() != null) {
                    App.mDistrict = null;
                    App.mSchools = null;
                    Intent intent = new Intent(ActivityRegister.this, ActivityListDistrict.class);
                    intent.putExtra(Constants.KEY_SEND_CITY_ID, App.mCity.getsID());
                    startActivityForResult(intent, Constants.RequestCode.GET_DISTRICT);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_city_null));
                }
            }
        });
        imgSchools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mDistrict != null && App.mDistrict.getsDISTRICT_NAME() != null) {
                    App.mSchools = null;
                    Intent intent_schools = new Intent(ActivityRegister.this,
                            ActivityListSchools.class);
                    intent_schools.putExtra(Constants.KEY_SEND_DISTRICT_ID, App.mDistrict.getsID());
                    startActivityForResult(intent_schools, Constants.RequestCode.GET_SCHOOLS);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_city_null));
                }
            }
        });
        imgLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mSchools != null && App.mSchools.getsSCHOOL_NAME() != null) {
                    App.mKhoihoc = null;
                    Intent intent_khoihoc = new Intent(ActivityRegister.this,
                            ActivityDanhsachKhoihoc.class);
                    startActivityForResult(intent_khoihoc, Constants.RequestCode.GET_KHOIHOC);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_school_null));
                }
            }
        });
    }


    private void register() {
       // sPhone = edtPhone.getText().toString().trim();
        sPassword = edtPass.getText().toString();
      //  sFullName = edtFullName.getText().toString().trim();
        sPasswordConfirm = edtPassConfirm.getText().toString().trim();
//        if (sFullName.isEmpty()){
//            showAlertDialog(getString(R.string.notify),
//                    getString(R.string.error_fullname));
//            return;
//        }
//        if (sPhone.isEmpty()){
//            showAlertDialog(getString(R.string.notify),
//                    getString(R.string.error_phone));
//            return;
//        }
        if (App.mDistrict == null) {
            hideDialogLoading();
            showDialogNotify("Thông báo", "Bạn chưa chọn quận/huyện");
            return;
        }
        if (App.mSchools == null) {
            hideDialogLoading();
            showDialogNotify("Thông báo", "Bạn chưa chọn trường của bé");
            return;
        }
        if (sClassId.isEmpty()) {
            showDialogNotify("Thông báo", "Bạn chưa chọn lớp của bé");
            return;
        }
        sCodePupil = edtCodePupul.getText().toString().trim();
        if (sCodePupil.isEmpty()) {
            showDialogNotify("Thông báo", "Bạn chưa nhập vào mã học sinh");
            return;
        }
        if (sPassword.isEmpty()){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass));
            return;
        }
        if (sPasswordConfirm.isEmpty()){
            showAlertDialog(getString(R.string.notify),
                    getString(R.string.error_pass_confirm));
            return;
        }
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String sTokenkey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
        if (sTokenkey != null && sTokenkey.length() > 0) {
            mPresenter.apiRegister(sClassId,sCodePupil, sIdTruong, sPassword, id);
        }else {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                mPresenter.apiRegister(sClassId,sCodePupil, sIdTruong, sPassword, id);
                                return;
                            }
                            // Get new FCM registration token
                            String token = task.getResult();
                            SharedPrefs.getInstance().put(Constants.KEY_TOKEN, token);
                            mPresenter.apiRegister(sClassId,sCodePupil, sIdTruong, sPassword, id);
                        }
                    });
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case Constants.RequestCode.START_USER_TRY:
//                if (resultCode == RESULT_OK) {
//
//                }
//                break;
//            case Constants.RequestCode.START_UPDATE_INFOR_CHILD:
//                if (resultCode == RESULT_OK) {
////                    Log.d(TAG, "onActivityResult: " + App.sLevel);
////                    initLogin();
//                }
//                break;
//        }
//    }
    

    @OnClick({R.id.btnRegister, R.id.txtLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                register();
                break;
            case R.id.txtLogin:
                startActivity(new Intent(ActivityRegister.this, ActivityLoginNew.class));
                finish();
                break;
            case R.id.imgBack:
                startActivity(new Intent(ActivityRegister.this, ActivityLogin.class));
                finish();
                break;
        }
    }

    @Override
    public void onHideProgressDialog() {
        hideDialogLoading();
    }

    @Override
    public void onShowProgressDialog() {
        showDialogLoading();
    }

    @Override
    public void showErrorRegister(String error) {
        showAlertDialog(getString(R.string.error), error);
    }

    @Override
    public void showSuccessRegister() {
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityRegister.this, ActivityLoginNew.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_DISTRICT:
                if (App.mDistrict != null) {
                    edtDistrict.setText(App.mDistrict.getsDISTRICT_NAME());
                    //sIdQuan = App.mDistrict.getsID();
                    edtSchools.setText("");
                } else {
                    // sIdQuan = "";
                    edtDistrict.setText("");
                    edtSchools.setText("");
                }

                break;
            case Constants.RequestCode.GET_SCHOOLS:
                if (App.mSchools != null) {
                    sIdTruong = App.mSchools.getsID();
                    edtSchools.setText(App.mSchools.getsSCHOOL_NAME());
                } else {
                    sIdTruong = "";
//                    edt_school.setText("");
                    //  edt_school.setHint("Tên trường");
                }
                break;
            case Constants.RequestCode.GET_KHOIHOC:
                if (App.mKhoihoc != null) {
                    sClassId = App.mKhoihoc;
                    edtLevel.setText(App.mKhoihoc);
                } else {
                    sClassId = "";
                    edtLevel.setText("");
                    //  edt_school.setHint("Tên trường");
                }
                break;
        }
    }
}
