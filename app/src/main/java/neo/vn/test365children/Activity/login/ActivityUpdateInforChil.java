package neo.vn.test365children.Activity.login;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.MenuSearch.ActivityDanhsachKhoihoc;
import neo.vn.test365children.MenuSearch.ActivityListCity;
import neo.vn.test365children.MenuSearch.ActivityListDistrict;
import neo.vn.test365children.MenuSearch.ActivityListSchools;
import neo.vn.test365children.Models.City;
import neo.vn.test365children.Models.District;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.InfoKids;
import neo.vn.test365children.Models.InfoStudent;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.Schools;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Presenter.Iml_init;
import neo.vn.test365children.Presenter.Presenter_Init_Login;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.ReadPathUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityUpdateInforChil extends BaseActivity implements ImpUploadImage.View, Iml_init.View {
    @BindView(R.id.txt_input_city)
    TextInputLayout txt_input_city;
    @BindView(R.id.txt_input_district)
    TextInputLayout txt_input_district;
    @BindView(R.id.txt_input_school)
    TextInputLayout txt_input_school;
    @BindView(R.id.txt_input_level_id)
    TextInputLayout txt_input_level_id;
    @BindView(R.id.img_level_id)
    ImageView img_level_id;
    @BindView(R.id.edt_class_name)
    EditText edt_class_name;
    @BindView(R.id.edt_full_name)
    EditText edt_full_name;
    @BindView(R.id.edt_city_addsub)
    EditText edt_city_addsub;
    @BindView(R.id.edt_district)
    EditText edt_district;
    @BindView(R.id.edt_school)
    EditText edt_school;
    @BindView(R.id.edt_username)
    TextView edt_username;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.img_city)
    ImageView img_city;
    @BindView(R.id.img_school)
    ImageView img_shool;
    @BindView(R.id.img_district)
    ImageView img_district;
    @BindView(R.id.edt_level_id)
    EditText edt_level_id;
    @BindView(R.id.img_get_image)
    ImageView img_get_image;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_avata_update)
    CircleImageView img_avata_update;
    @BindView(R.id.btn_ok_add_sub)
    Button btn_ok_add_sub;
    @BindView(R.id.btnChangePass)
    Button btnChangePass;
    String sIdTruong;
    ObjLogin chil;
    PresenterUploadImage mPresenterUpload;
    Presenter_Init_Login mPresetner;
    String sLevel_id, sIs_status_update;
    Realm mRealm;

    @Override
    public int setContentViewId() {
        return R.layout.activity_update_infor_child;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterUpload = new PresenterUploadImage(this);
        mPresetner = new Presenter_Init_Login(this);
        Glide.with(this).load(R.drawable.bg_select_class).into(img_background);
        KeyboardUtil.hideSoftKeyboard(ActivityUpdateInforChil.this);
        //checkPermistion();
        initData();
        initEvent();
    }

    private void initData() {
        mRealm = RealmController.with(this).getRealm();

        try {
            chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
            if (chil == null)
                return;
            if (chil.getsObjInfoKid() == null)
                return;
            InfoKids obj = chil.getsObjInfoKid();
            ArrayList<InfoStudent> listStudent = new ArrayList<>(mRealm.where(InfoStudent.class).findAll());
            if (!listStudent.isEmpty()){
                InfoStudent std = listStudent.get(0);
            }

            InfoStudent objStudent = mRealm.where(InfoStudent.class)
                    .equalTo("id", SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class))
                    .and()
                    .equalTo("idChild", SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class))
                    .findFirst();
            if (obj != null) {
                if (obj.getsUSERNAME() != null) {
                    edt_username.setText("MÃ HS: " + obj.getsUSERNAME());
                }
                if (chil.getsObjInfoKid().getsLEVEL_ID() != null && !obj.getsLEVEL_ID().equals("0")) {
                    sIs_status_update = "2";
                    img_level_id.setEnabled(false);
                    sLevel_id = chil.getsObjInfoKid().getsLEVEL_ID();
                    edt_level_id.setText(chil.getsObjInfoKid().getsLEVEL_ID());
                } else {
                    boolean is_update_level_id = SharedPrefs.getInstance()
                            .get(Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, Boolean.class);
                    if (is_update_level_id) {
                        sIs_status_update = "2";
                        img_level_id.setEnabled(false);
                        edt_level_id.setText("");
                    } else {
                        sIs_status_update = "1";
                        img_level_id.setEnabled(true);
                        edt_level_id.setText("");
                    }
                }
                if (objStudent != null  && objStudent.getCity() != null) {
                    City objCity = new City(objStudent.getCityID(), objStudent.getCity());
                    App.mCity = objCity;
                    edt_city_addsub.setText(objStudent.getCity());
                } //else App.mCity = null;
                if (objStudent != null  && objStudent.getDistrict() != null) {
                    District objDistrict = new District(objStudent.getDistrictID(), objStudent.getDistrict());
                    App.mDistrict = objDistrict;
                    edt_district.setText(objStudent.getDistrict());
                } else {
                    //App.mDistrict = null;
                }
                if (objStudent != null  && objStudent.getSchool() != null) {
                    Schools objSchool = new Schools(objStudent.getSchoolID(), objStudent.getSchool());
                    App.mSchools = objSchool;
                    edt_school.setText(objStudent.getSchool());
                } //else App.mSchools = null;
                if (objStudent != null  && objStudent.getKidName() != null) {
                    edt_full_name.setText(objStudent.getKidName());
                }
                if (objStudent != null  && objStudent.getClassName() != null) {
                    edt_class_name.setText(objStudent.getClassName());
                }
                if (obj.getsPHONENUMBER() != null) {
                    edt_phone.setText(obj.getsPHONENUMBER());
                }
                if (obj.getEMAIL() != null) {
                    edt_email.setText(obj.getEMAIL());
                }
                if (obj.getsAVATAR() != null) {
                    sAvata = chil.getsObjInfoKid().getsAVATAR();
                    Glide.with(this)
                            .load(Config.URL_IMAGE + chil.getsObjInfoKid().getsAVATAR())
                            .asBitmap()
                            .placeholder(R.drawable.ic_avata_home)
                            .into(new BitmapImageViewTarget(img_avata_update) {
                                @Override
                                public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                                    super.onResourceReady(drawable, anim);
                                    //   progressBar.setVisibility(View.GONE);
                                }
                            });
                } else {

                }
            } else {
                boolean is_update_level_id = SharedPrefs.getInstance()
                        .get(Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, Boolean.class);
                if (is_update_level_id) {
                    sIs_status_update = "2";
                    img_level_id.setEnabled(false);
                    edt_level_id.setText("");
                } else {
                    sIs_status_update = "1";
                    img_level_id.setEnabled(true);
                    edt_level_id.setText("");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getImageFromAlbum() {
        try {
            image_uri = null;
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.setType("image/*");
            Intent chooserIntent = Intent.createChooser(i,"Choose Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
            image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
            
            startActivityForResult(chooserIntent, Constants.RequestCode.GET_IMAGE);
//            startActivityForResult(getPickImageIntent(this), Constants.RequestCode.GET_IMAGE);
            /* *//*     Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.RequestCode.GET_IMAGE);*//*

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");
            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
            startActivityForResult(chooserIntent, Constants.RequestCode.GET_IMAGE);*/
        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
       /* MultiImageSelector.create()
                .single() // single mode
                .start(ActivityAddSubUser.this, Constants.RequestCode.GET_IMAGE);*/
    }

    public static Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    "Pick Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    private void checkPermistion() {
//        if (ContextCompat.checkSelfPermission(ActivityUpdateInforChil.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityUpdateInforChil.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//            } else {
//                ActivityCompat.requestPermissions(ActivityUpdateInforChil.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        1);
//            }
//        }
        ActivityCompat.requestPermissions(ActivityUpdateInforChil.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        1);
    }

    private void initEvent() {
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUpdateInforChil.this, ActivityChangePass.class);
                startActivity(intent);
            }
        });
        img_level_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityUpdateInforChil.this, "Level id click", Toast.LENGTH_SHORT).show();
            }
        });
        btn_ok_add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoading();
                if (IMAGE_PATH != null && IMAGE_PATH.length() > 0) {
                    mPresenterUpload.api_upload_image(IMAGE_PATH);
                } else {
                    api_login();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT < 23) {
                    getImageFromAlbum();
                } else {
                    if (ActivityCompat.checkSelfPermission(ActivityUpdateInforChil.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ActivityUpdateInforChil.this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        getImageFromAlbum();
                    } else {
                        checkPermistion();
                    }
                }

            }
        });
        img_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.mCity = null;
                App.mDistrict = null;
                Intent intent_city = new Intent(ActivityUpdateInforChil.this, ActivityListCity.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_CITY);
            }
        });
        img_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mCity != null && App.mCity.getsPROVINCE_NAME() != null) {
                    App.mDistrict = null;
                    App.mSchools = null;
                    Intent intent = new Intent(ActivityUpdateInforChil.this, ActivityListDistrict.class);
                    intent.putExtra(Constants.KEY_SEND_CITY_ID, App.mCity.getsID());
                    startActivityForResult(intent, Constants.RequestCode.GET_DISTRICT);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_city_null));
                }
            }
        });
        img_shool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mDistrict != null && App.mDistrict.getsDISTRICT_NAME() != null) {
                    App.mSchools = null;
                    Intent intent_schools = new Intent(ActivityUpdateInforChil.this,
                            ActivityListSchools.class);
                    intent_schools.putExtra(Constants.KEY_SEND_DISTRICT_ID, App.mDistrict.getsID());
                    startActivityForResult(intent_schools, Constants.RequestCode.GET_SCHOOLS);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_city_null));
                }
            }
        });
        img_level_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mSchools != null && App.mSchools.getsSCHOOL_NAME() != null) {
                    App.mKhoihoc = null;
                    Intent intent_khoihoc = new Intent(ActivityUpdateInforChil.this,
                            ActivityDanhsachKhoihoc.class);
                    startActivityForResult(intent_khoihoc, Constants.RequestCode.GET_KHOIHOC);
                } else {
                    showDialogNotify(getString(R.string.notify), getString(R.string.notify_school_null));
                }
            }
        });
    }

    String IMAGE_PATH = "";
    Uri image_uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CITY:
                if (App.mCity != null) {
                    edt_city_addsub.setText(App.mCity.getsPROVINCE_NAME());
                    edt_district.setText("");
                    edt_school.setText("");
                } else {
//                    edt_city_addsub.setText("");
//                    edt_district.setText("");
//                    edt_school.setText("");
                }
                break;
            case Constants.RequestCode.GET_DISTRICT:
                if (App.mDistrict != null) {
                    edt_district.setText(App.mDistrict.getsDISTRICT_NAME());
                    //sIdQuan = App.mDistrict.getsID();
                    edt_school.setText("");
                } else {
                    // sIdQuan = "";
//                    edt_district.setText("");
//                    edt_school.setText("");
                }

                break;
            case Constants.RequestCode.GET_SCHOOLS:
                if (App.mSchools != null) {
                    sIdTruong = App.mSchools.getsID();
                    edt_school.setText(App.mSchools.getsSCHOOL_NAME());
                } else {
                    sIdTruong = "";
//                    edt_school.setText("");
                    //  edt_school.setHint("Tên trường");
                }
                break;
            case Constants.RequestCode.GET_KHOIHOC:
                if (App.mKhoihoc != null) {
                    sLevel_id = App.mKhoihoc;
                    edt_level_id.setText(App.mKhoihoc);
                } else {
                    sLevel_id = "";
                    edt_level_id.setText("");
                    //  edt_school.setHint("Tên trường");
                }
                break;
            case Constants.RequestCode.GET_IMAGE:
                if (resultCode == RESULT_OK) {
                    try {
                        if (image_uri!=null && data==null){
                            Glide.with(this).load(image_uri).into(img_avata_update);
                            IMAGE_PATH = ReadPathUtil.getPath(ActivityUpdateInforChil.this, image_uri);
                            return;
                        }
                        
                        IMAGE_PATH = ReadPathUtil.getPath(ActivityUpdateInforChil.this, data.getData());
                        if (IMAGE_PATH!=null) {
                            File file = new File(IMAGE_PATH);
                            final Uri imageUri = data.getData();
                            File file1 = new File(imageUri.getPath());
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            Glide.with(this).load(data.getData()).into(img_avata_update);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    String sLop, sFullname, sAvata = "", sMobile = "", sEmail = "";

    public void api_login() {
        if (isNetwork()) {
            if (App.mCity == null) {
                hideDialogLoading();
                showDialogNotify("Thông báo", "Bạn chưa chọn tỉnh/thành phố");
                return;
            }
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
            sLop = edt_class_name.getText().toString().trim();
            if (sLop.length() == 0) {
                hideDialogLoading();
                Toast.makeText(ActivityUpdateInforChil.this,
                        "Bạn chưa nhập vào tên lớp của bé", Toast.LENGTH_SHORT).show();
                edt_class_name.requestFocus();
                KeyboardUtil.requestKeyboard(edt_class_name);
                return;
            } else sLop = edt_class_name.getText().toString().trim();
            sFullname = edt_full_name.getText().toString().trim();
            if (sFullname.length() == 0) {
                hideDialogLoading();
                //  showDialogNotify("Thông báo", "Bạn chưa nhập vào tên của bé");
                Toast.makeText(ActivityUpdateInforChil.this,
                        "Bạn chưa nhập vào tên cho bé", Toast.LENGTH_SHORT).show();
                edt_full_name.requestFocus();
                KeyboardUtil.requestKeyboard(edt_full_name);
                return;
            } else sFullname = edt_full_name.getText().toString().trim();
//            if (edt_phone.getText().toString().length() == 0) {
//                hideDialogLoading();
//                //  showDialogNotify("Thông báo", "Bạn chưa nhập vào tên của bé");
//                Toast.makeText(ActivityUpdateInforChil.this,
//                        "Bạn cần nhập vào số điện thoại để giúp khôi phục tài khoản.", Toast.LENGTH_SHORT).show();
//                edt_phone.requestFocus();
//                KeyboardUtil.requestKeyboard(edt_phone);
//                return;
//            } else sMobile = edt_phone.getText().toString().trim();
/*            if (edt_phone.getText().toString().length() > 0) {
                sMobile = edt_phone.getText().toString();
            } else {
                sMobile = "";
            }*/
            if (edt_email.getText().toString().length() > 0) {
                sEmail = edt_email.getText().toString();
            } else {
                sEmail = "";
            }
            String sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            String sUserChild = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            String sPass = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
            if (sLevel_id != null && sLevel_id.length() > 0) {
                sMobile = edt_phone.getText().toString();
                mPresetner.api_update_info_chil_2(sUserMother, sUserChild, App.mSchools.getsID(), sLevel_id,
                        sLop, "1", sFullname, sPass, sAvata, sMobile, sEmail, sIs_status_update);
            } else {
                hideDialogLoading();
            }
            InfoStudent infoStudent = new InfoStudent(
                    SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class),
                    SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class),
                    edt_city_addsub.getText().toString().trim(),
                    edt_district.getText().toString().trim(),
                    edt_school.getText().toString().trim(),
                    edt_class_name.getText().toString().trim(),
                    edt_full_name.getText().toString().trim(),App.mCity.getsID(),App.mDistrict.getsID(),App.mSchools.getsID());
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(infoStudent);
            mRealm.commitTransaction();
        } else {
            hideDialogLoading();
        }
    }


    @Override
    public void show_error_api_uploadimage(List<ErrorApi> mLis) {
        sAvata = "";
        api_login();
    }

    @Override
    public void show_upload_image(String sUrlImage) {
        if (sUrlImage != null && sUrlImage.length() > 0) {
            sAvata = sUrlImage;
            api_login();
        }
    }

    @Override
    public void show_init(ResponInitChil mLis) {

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_update_infor_child(ErrorApi mLis) {
        hideDialogLoading();
        if (mLis.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, true);
            setResult(RESULT_OK, new Intent());
            finish();
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            //showDialogNotify("Thông báo", "Cập nhật thông tin thành công");
//            startActivity(new Intent(this, ActivityHome.class));
        } else {
            showDialogNotify("Thông báo", mLis.getsRESULT());
        }
    }

    @Override
    public void show_update_infor_child_2(ErrorApi mLis) {
        hideDialogLoading();
        if (mLis.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, true);
            setResult(RESULT_OK, new Intent());
            finish();
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            //showDialogNotify("Thông báo", "Cập nhật thông tin thành công");
        } else {
            showDialogNotify("Thông báo", mLis.getsRESULT());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 1:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            getImageFromAlbum();
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
