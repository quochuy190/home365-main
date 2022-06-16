package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Activity.doctruyen.Activity_webview_doctruyen;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.DesExercises;
import neo.vn.test365children.Models.DetailExercise;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.StatisticDetailExer;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlExerDetail;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityExercisesDetail extends BaseActivity implements ImlExerDetail.View, ImpBaitap.View {
    private static final String TAG = "ActivityExercisesDetail";
    private ExerciseAnswer objExercises;
    String sIdDe;
    @BindView(R.id.txt_exer_bancunglam)
    TextView txt_bancunglam;
    @BindView(R.id.txt_start_time)
    TextView txt_start_time;
    @BindView(R.id.txt_duration_time)
    TextView txt_duration_time;
    @BindView(R.id.txt_exer_bancungtruong)
    TextView txt_bancungtruong;
    @BindView(R.id.txt_exer_bancunglop)
    TextView txt_bancunglop;
    @BindView(R.id.txt_exer_debai)
    TextView txt_debai;
    @BindView(R.id.btn_xemlaibai)
    Button btn_xemlaibai;
    @BindView(R.id.txt_exer_pointmax)
    TextView txt_caonhat;
    @BindView(R.id.txt_exer_pointtb)
    TextView txt_trungbinh;
    @BindView(R.id.txt_exer_point_min)
    TextView txt_thapnhat;
    /*    @BindView(R.id.txt_exer_comment_mother)
        TextView txt_exer_comment_mother;*/
    @BindView(R.id.img_background)
    ImageView imageView10;
    @BindView(R.id.img_title_exe_detail)
    ImageView img_title_exe_detail;
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_lable_exer)
    TextView txt_lable_exer;
    @BindView(R.id.txt_point)
    TextView txt_point;
    /*    @BindView(R.id.txt_lable_nhanxet)
        TextView txt_lable_nhanxet;*/
    @BindView(R.id.img_sticker)
    ImageView img_sticker;
    @BindView(R.id.img_share_exer)
    ImageView img_share_exer;
    @BindView(R.id.img_review_video)
    ImageView img_review_video;
    private String sStatus;
    /*    @BindView(R.id.rl_guilaibai)
        RelativeLayout rl_guilaibai;
        @BindView(R.id.rl_xembaitap)
        RelativeLayout rl_xembaitap;*/
    @BindView(R.id.btn_guilai)
    Button btn_guilai;
    ExerciseAnswer objExer;
    private PresenterBaitap mPresenterBaitap;
    Realm mRealm;
    List<Cauhoi> mListCauhoiRemote;

    @Override
    public int setContentViewId() {
        return R.layout.activity_review_exer_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterBaitap = new PresenterBaitap(this);
        // mRealm = RealmController.with(this).getRealm();
        initData();
        initEvent();
    }

    private void initEvent() {
        img_share_exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityExercisesDetail.this);
              /*  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));*/
                if (obj != null && obj.getFILE_PDF() != null && obj.getFILE_PDF().length() > 0) {
                    Intent intent = new Intent(ActivityExercisesDetail.this, Activity_webview_doctruyen.class);
                    intent.putExtra(Constants.KEY_SEND_LANGUAGE, "share_exer");
                    intent.putExtra(Constants.KEY_SEND_URL_WEBVIEW, obj.getFILE_PDF());
                    startActivity(intent);
                } else {
                    showDialogNotify("Thông báo", "Bạn không có quyền tài đề bài này");
                }

            }
        });
        img_review_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityExercisesDetail.this);
                if (obj != null && obj.getLINK() != null && obj.getLINK().length() > 0) {
                    Intent intent = new Intent(ActivityExercisesDetail.this, Activity_webview_doctruyen.class);
                    intent.putExtra(Constants.KEY_SEND_LANGUAGE, "review_video");
                    intent.putExtra(Constants.KEY_SEND_URL_WEBVIEW, obj.getLINK());
                    startActivity(intent);
                } else {
                    showDialogNotify("Thông báo", "Bài giảng tuần này chưa sẵn sàng");
                }

            }
        });
        btn_guilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityExercisesDetail.this);
                if (objExer != null) {
                    if (objExer.getsId_userMe() == null)
                        return;
                    if (objExer.getsId_userCon() == null)
                        return;
                    if (objExer.getsId_exercise() == null)
                        return;
                    if (objExer.getsTimebatdaulambai() == null)
                        return;
                    if (objExer.getsTimebatdaulambai() == null)
                        return;
                    if (objExer.getsTimeketthuclambai() == null)
                        return;
                    if (objExer.getsThoiluonglambai() == null)
                        return;
                    if (objExer.getsKieunopbai() == null)
                        return;
                    if (objExer.getsPoint() == null)
                        return;
                    if (objExer.getsDetailExercise() == null)
                        return;
                    showDialogLoading();
                    mPresenterBaitap.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise(),
                            objExer.getsTimebatdaulambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                            objExer.getsThoiluonglambai(), objExer.getsKieunopbai(), objExer.getsPoint(),
                            objExer.getsDetailExercise());
                }

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityExercisesDetail.this);
                finish();
            }
        });
        btn_xemlaibai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(ActivityExercisesDetail.this);
                if (objExer != null)
                    App.mLisCauhoi = objExer.getmLisCauhoi();
                else
                    App.mLisCauhoi = mListCauhoiRemote;
                Intent intent = new Intent(ActivityExercisesDetail.this,
                        ActivityReviewExercises.class);
                startActivity(intent);
                btn_xemlaibai.setEnabled(false);
                btn_xemlaibai.getBackground().setAlpha(50);
                //  KeyboardUtil.button_disable(btn_xemlaibai);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_xemlaibai.getBackground().setAlpha(255);
        btn_xemlaibai.setEnabled(true);
        //KeyboardUtil.button_enable(btn_xemlaibai);
    }

    String sUserMe, sUserCon, sMon;

    private void initData() {
        mRealm = RealmController.with(this).getRealm();
        mListCauhoiRemote = new ArrayList<>();
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(imageView10);
        Glide.with(this).load(R.drawable.bg_exer_nhatxet).into(img_title_exe_detail);
        Glide.with(this).load(R.drawable.exer_bg_ketqua2).into(imageView11);
//        if (App.mExerciseReview != null) {
//            objExer = App.mExerciseReview;
//        }
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sIdDe = getIntent().getStringExtra(Constants.KEY_SEND_EXERCISES_DETAIL);
        sStatus = getIntent().getStringExtra(Constants.KEY_SEND_EXERCISES_DETAIL_STATUS);
        if (sIdDe != null) {
            showDialogLoading();
          /*  mPresenter.api_get_des_exercises(sUserMe, sUserCon, sIdDe);
            mPresenter.api_get_report_exercises(sUserMe, sUserCon, sIdDe);*/
            mPresenterBaitap.get_exe_detail_taken(sUserMe, sUserCon, sIdDe);
            objExer = mRealm.where(ExerciseAnswer.class).equalTo("sId_exercise", sIdDe).findFirst();
            if (objExer != null)
                Log.e(TAG, "initData: " + objExer.getsId_exercise());
            //   Log.e(TAG, "initData listExer: " + objExer.getmLisCauhoi().size());
        }
        if (sStatus != null) {
            if (sStatus.equals("2")) {
                btn_guilai.setVisibility(View.VISIBLE);
            } else if (sStatus.equals("3"))
                btn_guilai.setVisibility(View.GONE);
        }

    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        showAlertErrorNetwork();
        hideDialogLoading();
        btn_xemlaibai.setEnabled(false);
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

//    Realm mRealm;

    @Override
    public void show_submit_execercise(ErrorApi mLis) {
        hideDialogLoading();
        if (mLis.getsERROR().equals("0000")) {
            Log.i(TAG, "show_submit_execercise: success");
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
            btn_guilai.setVisibility(View.GONE);
            showDialogNotify("Thông báo", mLis.getsRESULT());
        } else {
            showDialogNotify("Lỗi", mLis.getsRESULT());
        }
    }

    String sBaseUrl = "https://content1.home365.online/upload///image/sticker/sticker1//";
    DetailExercise obj;

    @Override
    public void show_detail_taken(ResponDetailTakenExercise objRes) {
        hideDialogLoading();
        if (objRes != null && objRes.getDETAILS() != null) {
            btn_xemlaibai.setEnabled(true);
            obj = objRes.getDETAILS();
            mListCauhoiRemote.clear();
            mListCauhoiRemote.addAll(obj.getLisPARTS());
            txt_debai.setText(Html.fromHtml(obj.getsNAME()));
            if (obj.getSTICKER() != null) {
                String sUrlImager = sBaseUrl + obj.getSTICKER();
                Glide.with(this).load(sUrlImager)
                        // .placeholder(R.drawable.sticker)
                        .into(img_sticker);
            }
            if (obj.getsSTART_TAKE_TIME() != null) {
                txt_start_time.setText(obj.getsSTART_TAKE_TIME());
            }
            if (obj.getsEND_TAKE_TIME() != null) {
                txt_duration_time.setText(obj.getsEND_TAKE_TIME());
            }
            if (obj.getsPOINT() != null) {
                txt_point.setText(obj.getsPOINT());
            }
//            if (objExer.getsTimebatdaulambai() != null) {
//                txt_start_time.setText(objExer.getsTimebatdaulambai());
//            }
//            if (obj.getsPOINT() != null && obj.getsPOINT().length() > 0) {
//                String s = StringUtil.format_point(Float.parseFloat(obj.getsPOINT()));
//                txt_point.setText(s + " ĐIỂM");
//            }
//            if (objExer.getsTimeketthuclambai() != null && objExer.getsTimeketthuclambai().length() > 0) {
//                txt_duration_time.setText(objExer.getsTimeketthuclambai());

              /*  txt_duration_time.setText("Thời gian làm bài: "
                        + TimeUtils.formatTime_Complete(iDuration));*/
        }
          /*  if (obj.getRECOMMENT_MOTHER() != null) {
                txt_exer_comment_mother.setText(obj.getRECOMMENT_MOTHER());
            }*/
           /* if (obj.getsDURATION() != null && obj.getsDURATION().length() > 0) {
                int iDuration = Integer.parseInt(obj.getsDURATION()) * 1000;
                txt_duration_time.setText("Thời gian làm bài: "
                        + TimeUtils.formatTime_Complete(iDuration));
            }
            if (obj.getsRECOMMENT_MOTHER() != null) {
                txt_exer_comment_mother.setText(obj.getsRECOMMENT_MOTHER());
            }
            if (obj.getsSTICKER_ID() != null && obj.getsSTICKER_ID().length() > 0) {
                Sticker objSticker = new Sticker();
                for (Sticker objs : App.mListSticker) {
                    if (objs.getsID().equals(obj.getsSTICKER_ID()))
                        objSticker = objs;
                }
                Glide.with(this).load(Config.URL_IMAGE + objSticker.getsPATH())
                        .placeholder(R.drawable.sticker)
                        .into(img_sticker);
            }*/
        switch (obj.getsSUBJECT_ID()) {
            case "1":
                // txt_monhoc.setText("Môn học: Toán");
                txt_lable_exer.setText("Môn Toán - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                break;
            case "2":
                // txt_monhoc.setText("Môn học: Tiếng Việt");
                txt_lable_exer.setText("Môn Tiếng Việt - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                break;
            case "3":
                // txt_monhoc.setText("Môn học: Tiếng Anh");
                txt_lable_exer.setText("Môn Tiếng Anh - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                break;
        }
        StatisticDetailExer objStatis = obj.getObjStatistic();
        if (objStatis.getsCUNGLAM() != null) {
            int cuglam = Integer.parseInt(objStatis.getsCUNGLAM());
            if (cuglam > 0)
                txt_bancunglam.setText(objStatis.getsCUNGLAM());
            else
                txt_bancunglam.setText("0");
        } else
            txt_bancunglam.setText("0");
        if (objStatis.getsCUNGTRUONG() != null && objStatis.getsCUNGTRUONG().length() > 0) {
            int cugtruong = Integer.parseInt(objStatis.getsCUNGTRUONG());
            if (cugtruong > 0)
                txt_bancungtruong.setText(objStatis.getsCUNGTRUONG());
            else
                txt_bancungtruong.setText("0");
        } else
            txt_bancungtruong.setText("0");
        if (objStatis.getsCUNGLOP() != null && objStatis.getsCUNGLOP().length() > 0) {
            int cugtruong = Integer.parseInt(objStatis.getsCUNGLOP());
            if (cugtruong > 0)
                txt_bancunglop.setText(objStatis.getsCUNGLOP());
            else
                txt_bancunglop.setText("0");
        } else
            txt_bancunglop.setText("0");
        if (objStatis.getsCAONHAT() != null) {
            txt_caonhat.setText(StringUtil.format_point(Float.parseFloat(objStatis.getsCAONHAT())) + " ĐIỂM");
        } else {
            txt_caonhat.setText("0 ĐIỂM");
        }
        if (objStatis.getsTRUNGBINH() != null)
            txt_trungbinh.setText(StringUtil.format_point(Float.parseFloat(objStatis.getsTRUNGBINH())) + " ĐIỂM");
        else
            txt_trungbinh.setText("0 ĐIỂM");
        if (objStatis.getsTHAPNHAT() != null) {
            txt_thapnhat.setText(StringUtil.format_point(Float.parseFloat(objStatis.getsTHAPNHAT())) + " ĐIỂM");
        } else {
            txt_thapnhat.setText("0 ĐIỂM");
        }
    }
//        else showAlertDialog("Lỗi", objRes.getsRESULT());

    @Override
    public void showHomeworkDone(HomeworkDone homeworkDone) {

    }

    @Override
    public void show_des_exercises(List<DesExercises> listDepExe) {
        hideDialogLoading();
        if (listDepExe != null) {
            DesExercises obj = listDepExe.get(0);
            txt_debai.setText(Html.fromHtml(obj.getsNAME()));
            if (obj.getsSTART_TAKE_TIME() != null) {
                txt_start_time.setText("Thời gian bắt đầu: " + TimeUtils.convent_date(obj.getsSTART_TAKE_TIME(),
                        "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm"));
            }
            if (obj.getsPOINT() != null && obj.getsPOINT().length() > 0) {
                String s = StringUtil.format_point(Float.parseFloat(obj.getsPOINT()));
                txt_point.setText(s + " ĐIỂM");
            }
            if (obj.getsDURATION() != null && obj.getsDURATION().length() > 0) {
                int iDuration = Integer.parseInt(obj.getsDURATION()) * 1000;
                txt_duration_time.setText("Thời gian làm bài: "
                        + TimeUtils.formatTime_Complete(iDuration));
            }
           /* if (obj.getsRECOMMENT_MOTHER() != null) {
                txt_exer_comment_mother.setText(obj.getsRECOMMENT_MOTHER());
            }*/
            if (obj.getsSTICKER_ID() != null && obj.getsSTICKER_ID().length() > 0) {
                Sticker objSticker = new Sticker();
                for (Sticker objs : App.mListSticker) {
                    if (objs.getsID().equals(obj.getsSTICKER_ID()))
                        objSticker = objs;
                }
                Glide.with(this).load(Config.URL_IMAGE + objSticker.getsPATH())
                        .placeholder(R.drawable.sticker)
                        .into(img_sticker);
            }
            switch (obj.getsSUBJECT_ID()) {
                case "1":
                    // txt_monhoc.setText("Môn học: Toán");
                    txt_lable_exer.setText("Môn Toán - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
                case "2":
                    // txt_monhoc.setText("Môn học: Tiếng Việt");
                    txt_lable_exer.setText("Môn Tiếng Việt - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
                case "3":
                    // txt_monhoc.setText("Môn học: Tiếng Anh");
                    txt_lable_exer.setText("Môn Tiếng Anh - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
            }

         /*   if (obj.getsADMIN_COMMENT() != null)
                txt_nhanxet.setText(Html.fromHtml(StringUtil.convert_html(obj.getsADMIN_COMMENT())));*/

        }
    }

    @Override
    public void show_report_exercises(List<DesExercises> listDepExe) {
        hideDialogLoading();
        if (listDepExe != null && listDepExe.get(0).getsERROR().equals("0000")) {
            DesExercises obj = listDepExe.get(0);
            if (obj.getScunglam() != null && obj.getScunglam().length() > 0) {
                int cuglam = Integer.parseInt(obj.getScunglam());
                if (cuglam > 0)
                    txt_bancunglam.setText("Có " + obj.getScunglam() + " bạn cùng làm bài thi này");
                else
                    txt_bancunglam.setText("Có 0 bạn cùng làm bài thi này");
            }
            if (obj.getScungtruong() != null && obj.getScungtruong().length() > 0) {
                int cugtruong = Integer.parseInt(obj.getScungtruong());
                if (cugtruong > 0)
                    txt_bancungtruong.setText("Số lượng bạn cùng trường tham gia: " + obj.getScungtruong());
                else
                    txt_bancungtruong.setText("Số lượng bạn cùng trường tham gia: 0");
            }
            if (obj.getScunglop() != null && obj.getScunglop().length() > 0) {
                int cugtruong = Integer.parseInt(obj.getScunglop());
                if (cugtruong > 0)
                    txt_bancunglop.setText("Số lượng bạn cùng lớp tham gia: " + obj.getScunglop());
                else
                    txt_bancunglop.setText("Số lượng bạn cùng lớp tham gia: 0");
            }
            txt_caonhat.setText(StringUtil.format_point(Float.parseFloat(obj.getScaonhat())) + " ĐIỂM");
            txt_trungbinh.setText(StringUtil.format_point(Float.parseFloat(obj.getStrungbinh())) + " ĐIỂM");
            txt_thapnhat.setText(StringUtil.format_point(Float.parseFloat(obj.getSthapnhat())) + " ĐIỂM");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (App.mLisCauhoi != null)
//            App.mLisCauhoi.clear();
    }
}
