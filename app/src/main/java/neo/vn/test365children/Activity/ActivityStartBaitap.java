package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Activity.doctruyen.Activity_webview_doctruyen;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ConfigChildren;
import neo.vn.test365children.Models.DetailExercise;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlConfigChil;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterConfigChil;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityStartBaitap extends BaseActivity implements ImpBaitap.View, ImlConfigChil.View {

    private static final String TAG = "ActivityStartBaitap";
    @BindView(R.id.btn_start_lambai)
    ConstraintLayout btn_start_lambai;
    @BindView(R.id.btn_share)
    ConstraintLayout btn_share;
    @BindView(R.id.btn_review_video)
    ConstraintLayout btn_review_video;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    Baitap_Tuan objBaitapTuan;
    @BindView(R.id.txt_lable_mon)
    TextView txt_lable_mon;
    @BindView(R.id.txt_noidung)
    TextView txt_noidung;
    @BindView(R.id.txt_muctieu)
    TextView txt_muctieu;
    @BindView(R.id.txt_tuan)
    TextView txt_tuan;
    PresenterBaitap mPresenter;
    PresenterConfigChil mPresenterConfig;
    String sUserMe, sUserCon, sMon;
    @BindView(R.id.txt_soluongcauhoi)
    TextView txt_soluongcauhoi;
    @BindView(R.id.txt_thoigianlambai)
    TextView txt_thoigianlambai;
    private List<Cauhoi> mLisCauhoi;
    boolean isClickStart = false;
    Realm mRealm;
    @BindView(R.id.img_background)
    ImageView img_background;
    String sCount_Start_Exer;

    @Override
    public int setContentViewId() {
        return R.layout.activity_start_lambai;
    }

    boolean isLuyenthi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isClickStart = false;
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterBaitap(this);
        mPresenterConfig = new PresenterConfigChil(this);
        DetailExercise objDetailExer = (DetailExercise) getIntent().getSerializableExtra(Constants.KEY_SEND_EXER_LUYENTHI);
        isLuyenthi = getIntent().getBooleanExtra(Constants.KEY_SEND_IS_START_EXER_LUYENTHI, false);
        Glide.with(this).load(R.drawable.bg_home).into(img_background);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        if (isLuyenthi) {
            try {
                btn_start_lambai.setEnabled(true);
                // KeyboardUtil.button_disable(btn_start_lambai);
                // btn_start_lambai.getBackground().setAlpha(50);
                btn_start_lambai.getBackground().setAlpha(250);
                btn_share.setVisibility(View.GONE);
                btn_review_video.setVisibility(View.GONE);
                DetailExercise objDetail = App.mExerLuyentap;
                txt_noidung.setText("NỘI DUNG: " + objDetail.getsREQUIREMENT());
                txt_thoigianlambai.setText("Thời gian làm bài: "
                        + (Integer.parseInt(objDetail.getDURATION()) / 60) + " phút");
                App.sTime = objDetail.getDURATION();
                switch (objDetail.getsSUBJECT_ID()) {
                    case "1":
                        txt_lable_mon.setText("BÀI TẬP TOÁN");
                        txt_tuan.setText("Lớp " + objDetail.getsLEVEL_ID());
                        break;
                    case "2":
                        txt_lable_mon.setText("BÀI TẬP TIẾNG VIỆT");
                        // txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                        txt_tuan.setText("Lớp " + objDetail.getsLEVEL_ID());
                        break;
                    case "3":
                        txt_lable_mon.setText("BÀI TẬP TIẾNG ANH");
                        // txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                        txt_tuan.setText("Lớp " + objDetail.getsLEVEL_ID());
                        break;
                }
                mLisCauhoi = new ArrayList<>();
                mLisCauhoi.addAll(objDetail.getLisPARTS());
                obj_answer = new ExerciseAnswer();
                obj_answer.setEXCERCISE_ID_LUYENTAP(objDetail.getEXCERCISE_ID_LUYENTAP());
                obj_answer.setsIdTuan(objDetail.getsWEEK_ID());
                obj_answer.setsId_exercise(objDetail.getEXCERCISE_ID());
                obj_answer.setsId_userMe(sUserMe);
                obj_answer.setsId_userCon(sUserCon);
                obj_answer.setsMonhoc(objDetail.getsSUBJECT_ID());
                obj_answer.setLuyenthi(true);
                // Trạng thái làm bài 0: chưa làm
                obj_answer.setIsTrangthailambai(objDetail.getsTrangthailambai());
        /*    mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(obj_answer);
            mRealm.commitTransaction();*/
                App.mExercise = obj_answer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            btn_start_lambai.setEnabled(false);
            // KeyboardUtil.button_disable(btn_start_lambai);
            // btn_start_lambai.getBackground().setAlpha(50);
            btn_start_lambai.getBackground().setAlpha(70);
            btn_share.setVisibility(View.GONE);
            btn_review_video.setVisibility(View.GONE);
            initData();
        }
        initEvent();
        //  play_mp3();
    }

    ExerciseAnswer obj_answer;

    private void initData() {
        try {
            sCount_Start_Exer = SharedPrefs.getInstance().get(Constants.KEY_SAVE_COUNT_START_EXER, String.class);
            mLisCauhoi = new ArrayList<>();
            objBaitapTuan = getIntent().getParcelableExtra(Constants.KEY_SEND_BAITAPTUAN);
            show_time_lambai(objBaitapTuan);
            ExerciseAnswer obj = mRealm.where(ExerciseAnswer.class)
                    .equalTo("sId_exercise",
                            objBaitapTuan.getsID()).findFirst();
            if (objBaitapTuan != null && objBaitapTuan.getsREQUIREMENT() != null)
                txt_muctieu.setText("MỤC TIÊU: " + objBaitapTuan.getsREQUIREMENT());
            if (objBaitapTuan != null && objBaitapTuan.getsNAME() != null)
                txt_noidung.setText("NỘI DUNG: " + objBaitapTuan.getsNAME());
            switch (objBaitapTuan.getsSUBJECT_ID()) {
                case "1":
                    txt_lable_mon.setText("BÀI TẬP TOÁN");
                    txt_tuan.setText("Lớp " + objBaitapTuan.getsLEVEL_ID() + " - Tuần " + objBaitapTuan.getsWEEK_ID());
                    break;
                case "2":
                    txt_lable_mon.setText("BÀI TẬP TIẾNG VIỆT");
                    // txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                    txt_tuan.setText("Lớp " + objBaitapTuan.getsLEVEL_ID() + " - Tuần " + objBaitapTuan.getsWEEK_ID());
                    break;
                case "3":
                    txt_lable_mon.setText("BÀI TẬP TIẾNG ANH");
                    // txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                    txt_tuan.setText("Lớp " + objBaitapTuan.getsLEVEL_ID() + " - Tuần " + objBaitapTuan.getsWEEK_ID());
                    break;
            }
            obj_answer = new ExerciseAnswer();
            obj_answer.setsIdTuan(objBaitapTuan.getsWEEK_ID());
            obj_answer.setsId_exercise(objBaitapTuan.getsID());
            obj_answer.setsId_userMe(sUserMe);
            obj_answer.setsId_userCon(sUserCon);
            obj_answer.setsMonhoc(objBaitapTuan.getsSUBJECT_ID());
            // Trạng thái làm bài 0: chưa làm
            obj_answer.setIsTrangthailambai("0");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(obj_answer);
            mRealm.commitTransaction();
            App.mExercise = obj_answer;
            get_api();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void get_api() {
        if (isNetwork()) {
            showDialogLoading();
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            // mPresenterConfig.api_get_config_children(sUserMe, sUserCon);
            mPresenter.get_api_get_part(sUserMe, sUserCon, objBaitapTuan.getsID());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }

    private void initEvent() {
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityStartBaitap.this);
                Intent intent = new Intent(ActivityStartBaitap.this, Activity_webview_doctruyen.class);
                intent.putExtra(Constants.KEY_SEND_LANGUAGE, "share_exer");
                intent.putExtra(Constants.KEY_SEND_URL_WEBVIEW, objDetail.getFILE_PDF());
                startActivity(intent);


            }
        });
        btn_review_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityStartBaitap.this);
                Intent intent = new Intent(ActivityStartBaitap.this, Activity_webview_doctruyen.class);
                intent.putExtra(Constants.KEY_SEND_LANGUAGE, "review_video");
                intent.putExtra(Constants.KEY_SEND_URL_WEBVIEW, objDetail.getLINK());
                startActivity(intent);
            }
        });
        btn_start_lambai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityStartBaitap.this);
                if (!isClickStart) {
                    btn_start_lambai.getBackground().setAlpha(70);
                    if (sCount_Start_Exer != null && sCount_Start_Exer.length() > 0) {
                        int count = Integer.parseInt(sCount_Start_Exer);
                        count++;
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_COUNT_START_EXER, "" + count);
                    } else {
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_COUNT_START_EXER, "" + 1);
                    }
                    //  btn_start_lambai.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
                    if (isLuyenthi) {

                    } else {
                        mPresenter.get_api_start_taken(sUserMe, sUserCon, objBaitapTuan.getsID(),
                                get_current_time(), "30");
                    }
                    if (obj_answer == null)
                        return;
                    obj_answer.setsTimebatdaulambai(get_current_time());
                    // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã làm bài xong 3: đã nộp bài
                    obj_answer.setIsTrangthailambai("1");
                    obj_answer.setsStatus_Play("1");
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(obj_answer);
                    mRealm.commitTransaction();
                    Intent intent = new Intent(ActivityStartBaitap.this, ActivityLambaitap.class);
                    App.mLisCauhoi.clear();
                    App.mLisCauhoi.addAll(mLisCauhoi);
                    intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, obj_answer);
                    startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                    isClickStart = true;
                }
                //   startActivity(intent);
                // startActivity(new Intent(ActivityStartBaitap.this, ActivityLambaitap.class));
            }
        });
        img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityStartBaitap.this);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_START_LAMBAI:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                break;
            case Constants.RequestCode.LOGIN_RESTART_SESSION:
                if (resultCode == RESULT_OK) {
                    get_api();
                }
                break;
        }
    }

    MediaPlayer mp3;

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    DetailExercise objDetail;

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {
        hideDialogLoading();
        if (objDetailExer.getsERROR().equals("0000")) {
            btn_start_lambai.setEnabled(true);
            btn_start_lambai.getBackground().setAlpha(255);
            //   KeyboardUtil.button_enable(btn_start_lambai);
            //  txt_soluongcauhoi.setText("Số lượng câu hỏi: " + mLis.size());
            List<Cauhoi> lisCauhoi = objDetailExer.getDETAILS().getLisPARTS();
            objDetail = objDetailExer.getDETAILS();
            if (objDetail.getLINK() != null && objDetail.getLINK().length() > 0) {
                btn_review_video.setVisibility(View.VISIBLE);
            } else
                btn_review_video.setVisibility(View.GONE);
            if (objDetail.getFILE_PDF() != null && objDetail.getFILE_PDF().length() > 0) {
                btn_share.setVisibility(View.VISIBLE);
            } else
                btn_share.setVisibility(View.GONE);
            txt_soluongcauhoi.setText("Số lượng câu hỏi: " + objDetailExer.getDETAILS().getLisPARTS().size());
            mLisCauhoi.addAll(lisCauhoi);
        } else if (objDetailExer.getsERROR().equals("0002")) {
            Intent intent = new Intent(ActivityStartBaitap.this, ActivityLogin.class);
            startActivityForResult(intent, Constants.RequestCode.LOGIN_RESTART_SESSION);
        } else {
            showDialogNotify("Lỗi", objDetailExer.getsRESULT());
            btn_start_lambai.setEnabled(false);
        }
    }

    /*  @Override
      public void show_list_get_part(List<Cauhoi> mLis) {
          hideDialogLoading();
          if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
              btn_start_lambai.getBackground().setAlpha(255);
              btn_start_lambai.setEnabled(true);
              //   KeyboardUtil.button_enable(btn_start_lambai);
              txt_soluongcauhoi.setText("Số lượng câu hỏi: " + mLis.size());
              mLisCauhoi.addAll(mLis);
          } else {
              showDialogNotify("Lỗi", mLis.get(0).getsRESULT());
              btn_start_lambai.getBackground().setAlpha(50);
              btn_start_lambai.setEnabled(false);
          }

      }
  */
    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        btn_start_lambai.setEnabled(false);
        btn_start_lambai.getBackground().setAlpha(70);
        //  showDialogNotify("Lỗi", "Có thể mẹ chưa mua bài tập này cho con, con kiểm tra và thử lại sau nhé.");
        //KeyboardUtil.button_disable(btn_start_lambai);
    }

    @Override
    public void show_get_excercise_needed(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_config_children(List<ConfigChildren> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            ConfigChildren obj = mLis.get(0);
            switch (objBaitapTuan.getsSUBJECT_ID()) {
                case "1":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsMATH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsMATH_TAKEN_DURATION();
                    break;
                case "2":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsVIETNAMESE_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsVIETNAMESE_TAKEN_DURATION();
                    break;
                case "3":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsENGLISH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsENGLISH_TAKEN_DURATION();
                    break;
            }
        }
    }

    public void show_time_lambai(Baitap_Tuan obj) {
        switch (objBaitapTuan.getsSUBJECT_ID()) {
            case "1":
                if (obj.getsVIETNAMESE_TAKEN_DURATION() != null && obj.getsVIETNAMESE_TAKEN_DURATION().length() > 0) {
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsMATH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsMATH_TAKEN_DURATION();
                } else {
                    txt_thoigianlambai.setText("Thời gian làm bài: " + "30 phút");
                    App.sTime = "" + 1800;
                }
                break;
            case "2":
                if (obj.getsVIETNAMESE_TAKEN_DURATION() != null && obj.getsVIETNAMESE_TAKEN_DURATION().length() > 0) {
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsVIETNAMESE_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsVIETNAMESE_TAKEN_DURATION();
                } else {
                    txt_thoigianlambai.setText("Thời gian làm bài: " + "30 phút");
                    App.sTime = "" + 1800;
                }
                break;
            case "3":
                if (obj.getsENGLISH_TAKEN_DURATION() != null && obj.getsENGLISH_TAKEN_DURATION().length() > 0) {
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsENGLISH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsENGLISH_TAKEN_DURATION();
                } else {
                    txt_thoigianlambai.setText("Thời gian làm bài: " + "30 phút");
                    App.sTime = "" + 1800;
                }
                break;
        }
    }

    @Override
    public void show_start_taken(ErrorApi mLis) {
        if (mLis != null && mLis.getsERROR().equals("0000"))
            Log.i(TAG, "show_start_taken: success");
    }

    @Override
    public void show_submit_execercise(ErrorApi mLis) {

    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void showHomeworkDone(HomeworkDone homeworkDone) {

    }

  /*  @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {

    }*/

    Calendar cal;
    Date date;
    SimpleDateFormat dft = null;

    private String get_current_time() {
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }

}
