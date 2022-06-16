package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlFeedback;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterFeedback;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityComplete extends BaseActivity implements ImpBaitap.View, ImlFeedback.View {
    private static final String TAG = "ActivityComplete";
    @BindView(R.id.txt_pointlambai)
    TextView txt_pointlambai;
    @BindView(R.id.textView3)
    TextView txt_chucmung;
    @BindView(R.id.txt_timelambai)
    TextView txt_timelambai;
    @BindView(R.id.imageView9)
    ImageView img_background;
    @BindView(R.id.img_bg_complete1)
    ImageView img_bg_complete1;
    @BindView(R.id.img_bg_complete2)
    ImageView img_bg_complete2;
    @BindView(R.id.img_bg_complete3)
    ImageView img_bg_complete3;
    ExerciseAnswer objExer;
    Realm mRealm;
    @BindView(R.id.btn_guidiem)
    Button btn_guidiem;
    PresenterBaitap mPresenter;
    long durationInMillis;
    @BindView(R.id.ll_show_ketqua)
    LinearLayout ll_ketqua;
    @BindView(R.id.btn_dong)
    Button btn_dong;
    @BindView(R.id.btn_gui)
    Button btn_gui;
    @BindView(R.id.rb_rate_1_1)
    RadioButton rb_rate_1_1;
    @BindView(R.id.rb_rate_1_2)
    RadioButton rb_rate_1_2;
    @BindView(R.id.rb_rate_1_3)
    RadioButton rb_rate_1_3;

    @BindView(R.id.rb_rate_2_3)
    RadioButton rb_rate_2_3;

    @BindView(R.id.rb_rate_2_1)
    RadioButton rb_rate_2_1;
    @BindView(R.id.imageView27)
    ImageView imageView27;
    @BindView(R.id.rb_rate_2_2)
    RadioButton rb_rate_2_2;
    @BindView(R.id.view_danhgia)
    ConstraintLayout view_danhgia;
    PresenterFeedback mPresenterFeedback;
    String rate_1 = "";
    String rate_2 = "";

    @BindView(R.id.rating_exer)
    RatingBar rating_exer;
//    @BindView(R.id.adView_week_homework)
//    AdView adView_week_homework;
//    private InterstitialAd interstitialAd;

    @Override
    public int setContentViewId() {
        return R.layout.activity_comple_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterBaitap(this);
        mPresenterFeedback = new PresenterFeedback(this);
        btn_guidiem.setEnabled(false);
        initData();
        initEvent();
        initRating();
//        initAd();
    }

//    private void initAd(){
//        if (SharedPrefs.getInstance().get(Constants.IS_VIP, Boolean.class)) return;
//        MobileAds.initialize(this, initializationStatus -> {
//        });
//        AdRequest adRequest = new AdRequest.Builder()
//                .build();
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
//        interstitialAd.loadAd(adRequest);
//        interstitialAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//                if (interstitialAd.isLoaded()){
//                    interstitialAd.show();
//                }
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//        });
//    }

    private void initRating() {
        rating_exer.setRating(5);
    }

    private void show_feedback() {
        view_danhgia.setVisibility(View.VISIBLE);
        ll_ketqua.setVisibility(View.GONE);
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityComplete.this,
                R.anim.animation_show_question);
        view_danhgia.startAnimation(animationRotale);


    }

    private void initEvent() {

        btn_guidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_feedback();
                btn_guidiem.setEnabled(false);
            }
        });
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityComplete.this);
              //  setResult(RESULT_OK, new Intent());
                Intent intent = new Intent(ActivityComplete.this, ActivityHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityComplete.this);
                showDialogLoading();
                int rating = (int) rating_exer.getRating();
                if (objExer.isLuyenthi()) {
                    mPresenterFeedback.api_send_feetback(objExer.getsId_userMe(), objExer.getsId_userCon(),
                            "" + rating, "1", objExer.getEXCERCISE_ID_LUYENTAP());
                } else {
                    mPresenterFeedback.api_send_feetback(objExer.getsId_userMe(), objExer.getsId_userCon(),
                            "" + rating, "1", objExer.getsId_exercise());
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private float fPoint = 0;
    private RealmList<Cauhoi> mRealmList = new RealmList<>();
    private boolean isPlay_Again;

    private void initData() {
        ObjLogin chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        /*if (chil != null && chil.getsFULLNAME() != null) {
            txt_chucmung.setText("CHÚC MỪNG BẠN " + chil.getsFULLNAME().toUpperCase() +
                    "\n ĐÃ HOÀN THÀNH BÀI TẬP HÔM NAY");
        }*/
        Glide.with(this).load(R.drawable.bg_doc_hieu).into(img_background);
        Glide.with(this).load(R.drawable.icon_bang).into(imageView27);
        Glide.with(this).load(R.drawable.bg_complete_1).into(img_bg_complete1);
        Glide.with(this).load(R.drawable.bg_complete_2).into(img_bg_complete2);
        Glide.with(this).load(R.drawable.bg_complete_3).into(img_bg_complete3);
        isPlay_Again = getIntent().getBooleanExtra(Constants.KEY_SEND_EXER_AGAIN, false);
      /*  if (isPlay_Again) {

        } else
            objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);*/
        objExer = App.mExercise;
        if (objExer != null)
            nopbai();
        else {
            Toast.makeText(this, "Bài tập đã được hoàn thành", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, new Intent());
            App.mLisCauhoi.clear();
        }
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_PLAYING_EXER, false);

    }

    public void nopbai() {
        Log.e(TAG, "nopbai: start");
        fPoint = 0;
        List<CauhoiAnswer> mListCauhoiAnswer = new ArrayList<>();
        //  String sDanhsachcau = App.self().getGSon().toJson(App.mLisCauhoi);
        for (int j = 0; j < App.mLisCauhoi.size(); j++) {
            Cauhoi obj = App.mLisCauhoi.get(j);
            if (obj.getLisInfo() != null) {
                RealmList<CauhoiDetailAnswer> mLisCauhoiDetailAnswer = new RealmList<>();
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = App.mLisCauhoi.get(j).getLisInfo().get(i);
                    mLisCauhoiDetailAnswer.add(new CauhoiDetailAnswer(objCauhoiDetail.getsID(), objCauhoiDetail.getsPART_ID(),
                            objExer.getsTimeketthuclambai(), objCauhoiDetail.getsANSWER_CHILD(),
                            objCauhoiDetail.getsRESULT_CHILD(), objCauhoiDetail.getsPOINT_CHILD()));
                    if (objCauhoiDetail.isAnserTrue()) {
                        fPoint = fPoint + Float.parseFloat(objCauhoiDetail.getsPOINT());
                    } else {
                        if (obj.getsKIEU().equals("11") || obj.getsKIEU().equals("5")) {
                            if (objCauhoiDetail.isDalam()) {
                                float fTotalPoint = Float.parseFloat(objCauhoiDetail.getsPOINT()) / 4;
                                if (objCauhoiDetail.getsHTML_A().equals(objCauhoiDetail.getsEGG_1_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_B().equals(objCauhoiDetail.getsEGG_2_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_C().equals(objCauhoiDetail.getsEGG_3_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_D().equals(objCauhoiDetail.getsEGG_4_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                            }
                        } else if (obj.getsKIEU().equals("6")) {
                            fPoint = fPoint + objCauhoiDetail.getfTempPoint();
                        }
                    }
                }
                mListCauhoiAnswer.add(new CauhoiAnswer(mLisCauhoiDetailAnswer, obj.getsID(), obj.getsEXCERCISE_ID()
                        , obj.getsKIEU(), obj.getsUPDATETIME()));
            }
        }
        // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã làm bài xong 3: đã nộp bài
        objExer.setIsTrangthailambai("2");
        objExer.setsStatus_Play("0");
        objExer.setsPoint("" + StringUtil.format_point(fPoint));
        mRealmList.addAll(App.mLisCauhoi);
        objExer.setmLisCauhoi(mRealmList);
        txt_pointlambai.setText("Điểm con đạt được: " + StringUtil.format_point(fPoint) + " điểm");
        if (objExer.getsKieunopbai().equals("1")) {
            long timeTotal = Long.parseLong(objExer.getsTimequydinh());
            txt_timelambai.setText("Thời gian làm bài: "
                    + TimeUtils.formatTime_Complete((int) (timeTotal)));
        } else {
            if (objExer.getsThoiluonglambai() != null && objExer.getsThoiluonglambai().length() > 0) {
                if (objExer.getsTimequydinh() != null && objExer.getsTimequydinh().length() > 0) {
                    durationInMillis = Long.parseLong(objExer.getsThoiluonglambai());
                    long timeTotal = Long.parseLong(objExer.getsTimequydinh());

                    if (timeTotal > 0) {
                        long time = (timeTotal / 1000) - (durationInMillis);
                        txt_timelambai.setText("Thời gian làm bài: "
                                + TimeUtils.formatTime_Complete((int) (time * 1000)));
                    } else {
                        long timeTotal_test = 30 * 60;
                        long time = timeTotal_test - durationInMillis;
                        txt_timelambai.setText("Thời gian làm bài: "
                                + TimeUtils.formatTime_Complete((int) (time * 1000)));
                    }

                } else {
                    durationInMillis = Long.parseLong(objExer.getsThoiluonglambai());
                    long timeTotal = 30 * 60;
                    long time = timeTotal - durationInMillis;
                    txt_timelambai.setText("Thời gian làm bài: "
                            + TimeUtils.formatTime_Complete((int) (time * 1000)));
                }

            } else {
                txt_timelambai.setText("Thời gian làm bài không xác định");
            }
        }

        int iTime = (int) (durationInMillis / 1000);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String sDanhsachcau = gson.toJson(mListCauhoiAnswer);
        objExer.setsDetailExercise(sDanhsachcau);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objExer);
        mRealm.commitTransaction();

        int iTime_duration = Integer.parseInt(objExer.getsTimequydinh()) / 1000;

        Log.e(TAG, "nopbai: loading submit");
        if (!objExer.isLuyenthi()) {
            showDialogLoading();
            mPresenter.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise()
                    , objExer.getsThoiluonglambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                    "" + iTime_duration, objExer.getsKieunopbai(), objExer.getsPoint(), sDanhsachcau);
        } else {
            objExer.setIsTrangthailambai("4");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
            showDialogLoading();
            mPresenter.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(),
                    objExer.getEXCERCISE_ID_LUYENTAP()
                    , objExer.getsThoiluonglambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                    "" + iTime_duration, objExer.getsKieunopbai(), objExer.getsPoint(), sDanhsachcau);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK, new Intent());
        App.mLisCauhoi.clear();
    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    /*@Override
    public void show_list_get_part(List<Cauhoi> mLis) {

    }*/

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        Log.e(TAG, "show_error_api: ");
        btn_guidiem.setEnabled(true);
    }

    @Override
    public void show_get_excercise_needed(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_start_taken(ErrorApi mLis) {

    }

    @Override
    public void show_submit_execercise(ErrorApi mLis) {
        hideDialogLoading();
        if (mLis.getsERROR().equals("0000")) {
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: success");
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_feedback();
                }
            }, 4000);*/

            // showDialogNotify("Thông báo", "Nộp bài thành công");
        } else {
            Log.i(TAG, "show_submit_execercise: error" + mLis.getsRESULT());
            showDialogNotify("Thông báo", "Bài làm chưa được gửi cho mẹ," +
                    " con có vào phần Bài tập đã làm và gửi lại sau");
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: " + mLis.getsRESULT());
        }
    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void showHomeworkDone(HomeworkDone homeworkDone) {

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_send_feedback(ErrorApi objError) {
        hideDialogLoading();
        if (objError.getsERROR().equals("0000")) {
            showDialogComfirm("Thông báo",
                    "Đánh giá đã được gửi đi. Cảm ơn con đã đóng góp xây dựng ứng dụng.",
                    false, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            Intent intent = new Intent(ActivityComplete.this, ActivityHome.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });

        } else {
            showDialogNotify(objError.getMESSGE(), objError.getsRESULT());
        }
    }

 /*   @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: success");
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
        } else {
            showDialogNotify("Lỗi", mLis.get(0).getsRESULT());
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: " + mLis.get(0).getsRESULT());
        }
    }*/
}
