package neo.vn.test365children.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.realm.RealmList;
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentBatSauNew;
import neo.vn.test365children.Fragment.FragmentChemchuoi;
import neo.vn.test365children.Fragment.FragmentChondapanDung;
import neo.vn.test365children.Fragment.FragmentCompleteBaitap;
import neo.vn.test365children.Fragment.FragmentCuuCongchua;
import neo.vn.test365children.Fragment.FragmentDienvaochotrong;
import neo.vn.test365children.Fragment.FragmentDocvaTraloi;
import neo.vn.test365children.Fragment.FragmentNgheAudio;
import neo.vn.test365children.Fragment.FragmentNoicau;
import neo.vn.test365children.Fragment.FragmentSapxep;
import neo.vn.test365children.Fragment.FragmentXemanhtraloi;
import neo.vn.test365children.Fragment.FragmentXepTrung;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.BoundServiceCountTime;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

import static neo.vn.test365children.App.sTime;

public class ActivityLambaitap extends BaseActivity implements ImpBaitap.View, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private static final String TAG = "ActivityLambaitap";

    @Override
    public int setContentViewId() {
        return R.layout.activity_lambaitap;
    }

    @BindView(R.id.viewpager_lambai)
    CustomViewPager viewpager_lambai;
    AdapterViewpager adapterViewpager;
    PresenterBaitap mPresenter;
    String sUserMe, sUserCon, sMon;
    private Cauhoi mCauhoi;
    private List<Cauhoi> mLisCauhoi;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_time)
    ImageView img_time;
    @BindView(R.id.txt_point)
    TextView txt_point;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.ll_player)
    LinearLayout ll_player;
    Intent intent_service;
    private long iCurrenTime = 0;
    int iTotalTime;
    private SoundPool mSoundPool;
    private MediaPlayer mPlayer;
    @BindView(R.id.btnPlay)
    ImageView btnPlay;
    @BindView(R.id.player_progressbar)
    SeekBar seekBar;
    @BindView(R.id.songCurrentDurationLabel)
    TextView txtDuration;
    private Intent intent;
    private boolean isBound = false;
    private BoundServiceCountTime myService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundServiceCountTime.MyBinder binder = (BoundServiceCountTime.MyBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll_player.setVisibility(View.GONE);
        mPlayer = new MediaPlayer();
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_PLAYING_EXER, true);
        initSound();
        mPresenter = new PresenterBaitap(this);
        if (sTime.length() > 0) {
            iTotalTime = Integer.parseInt(sTime) * 1000;
        } else {
            iTotalTime = 30 * 60 * 1000;
        }
        //iTotalTime = 2 * 60 * 1000;
        initData();
        initEvent();
        Animation animationRotale = AnimationUtils.loadAnimation(this, R.anim.animation_time);
        img_time.startAnimation(animationRotale);
    }

    private void start_service_downtime() {
        EventBus.getDefault().register(this);
        // Tạo đối tượng Intent cho WeatherService.
        intent = new Intent(this, BoundServiceCountTime.class);
        if (iTotalTime > 0)
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, iTotalTime);
        else
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, 0);
        // Gọi method bindService(..) để giàng buộc dịch vụ với giao diện.
        this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void stop_service() {
        EventBus.getDefault().unregister(this);
        if (isBound) {
            // Tắt Service
            unbindService(connection);
            isBound = false;
        }
    }

    private void initPlayMp3(String sUrl) {
        try {
            mPlayer.reset();
            String url = Config.URL_VIDEO + sUrl;
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(url);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.prepareAsync();
            mHandler.postDelayed(mProgressCallback, 0);
            seekBar.setProgress(0);
        } catch (IOException e) {
            Log.e(TAG, "play: ", e);
        }
    }

    private Handler mHandler = new Handler();
    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
            if (mPlayer.isPlaying()) {
                int progress = (int) (seekBar.getMax() * ((float) mPlayer.getCurrentPosition() / mPlayer.getDuration()));
                updateProgressTextWithDuration(mPlayer.getCurrentPosition());
                updateProgres(progress);
            }
            mHandler.postDelayed(this, 100);
        }
    };

    public void updateProgres(int progress) {
        if (progress >= 0 && progress <= seekBar.getMax()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                seekBar.setProgress(progress, true);
            } else {
                seekBar.setProgress(progress);
            }
        } else mHandler.removeCallbacks(mProgressCallback);
    }

    private int getDuration(int progress) {
        int duration = (int) (mPlayer.getDuration() * ((float) progress / seekBar.getMax()));
        return duration;
    }

    private void updateProgressTextWithDuration(int duration) {
        txtDuration.setText(TimeUtils.formatDuration(duration));
    }

    private int cPlayTrue;
    private int cPlayClick;
    private int cPlayPr_Win_1;
    private int cPlayPr_Win_2;
    private int cPlayPr_Win_lost;
    private int cPlayFalse;
    private int cPlayFalse_Sau;
    private float LEFT_VOL = 1.0f;
    private float RIGHT_VOL = 1.0f;
    private int PRIORITY = 1;
    private int LOOP = 0;
    private float RATE = 1.0f;

    private void initSound() {
        cPlayTrue = mSoundPool.load(getApplicationContext(), R.raw.true_mp3, 1);
        cPlayClick = mSoundPool.load(getApplicationContext(), R.raw.click, 1);
        cPlayFalse = mSoundPool.load(getApplicationContext(), R.raw.false_te, 1);
        cPlayFalse_Sau = mSoundPool.load(getApplicationContext(), R.raw.false_te, 1);
        cPlayPr_Win_1 = mSoundPool.load(getApplicationContext(), R.raw.true_mp3, 1);
        cPlayPr_Win_2 = mSoundPool.load(getApplicationContext(), R.raw.true_mp3, 1);
        cPlayPr_Win_lost = mSoundPool.load(getApplicationContext(), R.raw.false_te, 1);
    }

    public void play_mp3_true() {
        mSoundPool.play(cPlayTrue, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_click() {
        mSoundPool.play(cPlayClick, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_pr_win1() {
        mSoundPool.play(cPlayPr_Win_1, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_pr_win2() {
        mSoundPool.play(cPlayPr_Win_2, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_pr_lost() {
        mSoundPool.play(cPlayPr_Win_lost, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_false() {
        mSoundPool.play(cPlayFalse, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_false_sau() {
        mSoundPool.play(cPlayFalse_Sau, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }
  /*  @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }*/

    private float fPoint = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Service")) {
            iCurrenTime = event.time / (1000);
            if (event.point == 0) {
                if (event.time < (10 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.orange));
                } else txt_time.setTextColor(getResources().getColor(R.color.black));
                if (event.time < (5 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.red_test365));
                }
                txt_time.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                EventBus.getDefault().post(new MessageEvent("close_princess", 0, 0));
                final Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
                objExer.setsKieunopbai("1");
                objExer.setsTimequydinh("" + iTotalTime);
                objExer.setsThoiluonglambai("" + iTotalTime);
                objExer.setsTimeketthuclambai(get_current_time());
                objExer.setsStatus_Play("0");
                showDialogLoading();
                if (isPlay_Again) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideDialogLoading();
                            intent.putExtra(Constants.KEY_SEND_EXER_AGAIN, true);
                            App.mExercise = objExer;
                            startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                            //finish();
                        }
                    }, 2000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideDialogLoading();
                            App.mExercise = objExer;
                            startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                            // put_api_nopbai("1");
                            //finish();
                        }
                    }, 2000);
                }


            }
        } else if (event.message.equals("Point_true")) {
            play_mp3_true();
            fPoint = fPoint + event.point;
            txt_point.setText("" + StringUtil.format_point(fPoint));
        } else if (event.message.equals("Point_false")) {
            if (event.point > 0) {
                fPoint = fPoint + event.point;
                txt_point.setText("" + StringUtil.format_point(fPoint));
            }
            play_mp3_false();
        } else if (event.message.equals("Point_false_sau")) {
            play_mp3_false_sau();
        } else if (event.message.equals("nop_bai")) {
            final Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
            objExer.setsKieunopbai("0");
            objExer.setsThoiluonglambai("" + (iCurrenTime));
            objExer.setsTimequydinh("" + iTotalTime);
            objExer.setsTimeketthuclambai(get_current_time());
            objExer.setsStatus_Play("1");
            save_playing_exer();
            showDialogLoading();
            if (isPlay_Again) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        intent.putExtra(Constants.KEY_SEND_EXER_AGAIN, true);
                        App.mExercise = objExer;
                        startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                        //finish();
                    }
                }, 2000);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        App.mExercise = objExer;
                        startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                        // put_api_nopbai("0");
                    }
                }, 2000);
            }
        } else if (event.message.equals("mp3")) {
            play_mp3_click();
        } else if (event.message.equals("pr_win1")) {
            fPoint = fPoint + event.point;
            txt_point.setText("" + StringUtil.format_point(fPoint));
            play_mp3_pr_win1();
        } else if (event.message.equals("pr_win2")) {
            fPoint = fPoint + event.point;
            txt_point.setText("" + StringUtil.format_point(fPoint));
            play_mp3_pr_win2();
        } else if (event.message.equals("pr_lost")) {
            play_mp3_pr_lost();
        } else if (event.message.equals(Constants.KEY_SAVE_LIST_EXER_PLAYING)) {
            save_playing_exer();
        }
    }

    private void save_playing_exer() {
        RealmList<Cauhoi> mRealmList = new RealmList<>();
        mRealmList.addAll(App.mLisCauhoi);
        objExer.setmLisCauhoi(mRealmList);
        objExer.setsThoiluonglambai("" + iCurrenTime);
        objExer.setsPoint(StringUtil.format_point(fPoint));
        objExer.setsTimeketthuclambai(get_current_time());
        //  objExer.setmLisCauhoi((RealmList<Cauhoi>) App.mLisCauhoi);
        Gson gson = new Gson();
        String json = gson.toJson(objExer);
        SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_EXER_PLAYING, json);
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
        }
    }

    @Override
    public void onBackPressed() {
        if (sUserMe != null &&
                sUserMe.equals("quochuy190")) {
            super.onBackPressed();
            if (intent_service != null) {
                stopService(intent_service);
            }
        } else {
            // super.onBackPressed();
        }

    }

    String sTest = null;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
    }


    private void initEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo(getDuration(seekBar.getProgress()));
                if (mPlayer.isPlaying()) {
                    mHandler.removeCallbacks(mProgressCallback);
                    mHandler.post(mProgressCallback);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mProgressCallback);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateProgressTextWithDuration(progress);
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        btnPlay.setImageResource(R.drawable.btn_play);
                        mPlayer.pause();
                    } else {
                        btnPlay.setImageResource(R.drawable.btn_pause);
                        mPlayer.start();
                    }
                }
            }
        });
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (maxPage > 0)
                    if (current < (maxPage)) {
                        EventBus.getDefault().post(new MessageEvent("Audio", current, 0));
                        viewpager_lambai.setCurrentItem((current + 1));
                    }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (current > 0) {
                    viewpager_lambai.setCurrentItem((current - 1));
                    EventBus.getDefault().post(new MessageEvent("Audio", current, 0));
                }
            }
        });
        viewpager_lambai.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                // Fragment fragment =(Fragment)adapterViewpager.getRegisteredFragment(i);
                Fragment fragment = getSupportFragmentManager().getFragments().get(viewpager_lambai.getCurrentItem());
                if (fragment instanceof FragmentNgheAudio) {
                    CauhoiDetail mCauhoi = ((FragmentNgheAudio) fragment).getmCauhoi();
                    if (!(iAudioCurrent == Integer.parseInt(mCauhoi.getsNumberDe()))) {
                        iAudioCurrent = Integer.parseInt(mCauhoi.getsNumberDe());
                        initPlayMp3(mCauhoi.getsAudioPath());
                    }
                    Log.e(TAG, "onPageSelected: Đề bài: " + mCauhoi.getsNumberDe() + " - câu:" + mCauhoi.getsSubNumberCau());
                    ll_player.setVisibility(View.VISIBLE);
                } else {
                    if (mPlayer != null && mPlayer.isPlaying()) {
                        btnPlay.setImageResource(R.drawable.btn_play);
                        mPlayer.pause();
                    }
                    ll_player.setVisibility(View.GONE);
                }
                // EventBus.getDefault().post(new MessageEvent("Audio", i, 0));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private ExerciseAnswer objExer;
    int current = 0;
    boolean isPlay_Again;
    int iCountAudio = 1;
    int iAudioCurrent;

    private void initData() {
        current = 0;
        isPlay_Again = getIntent().getBooleanExtra(Constants.KEY_SEND_EXER_AGAIN, false);
        if (isPlay_Again) {
            objExer = App.mExercise;
            if (objExer != null) {
                if (objExer.getsPoint() != null) {
                    fPoint = Float.parseFloat(objExer.getsPoint());
                    txt_point.setText("" + StringUtil.format_point(fPoint));
                }
            } else {
                Toast.makeText(this, "Bài tập đã được hoàn thành.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else
            objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);
        mLisCauhoi = new ArrayList<>();
        if (App.mLisCauhoi != null && App.mLisCauhoi.size() > 0) {
            mLisCauhoi.addAll(App.mLisCauhoi);
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            objExer.setsId_userCon(sUserCon);
            objExer.setsId_userMe(sUserMe);
            objExer.setsStatus_Play("1");
            objExer.setsTimequydinh("" + iTotalTime);
            save_playing_exer();
        }
        viewpager_lambai.setPagingEnabled(false);
        if (mLisCauhoi != null && mLisCauhoi.size() > 0) {
            //  viewpager_lambai = new CustomViewPager(this);
            adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
            maxPage = 0;
            for (int j = 0; j < mLisCauhoi.size(); j++) {
                Cauhoi obj = mLisCauhoi.get(j);
                if (obj.getLisInfo() != null) {
                    if (obj.getsKIEU().equals("10")) {
                        obj.setmNumber("" + (j + 1));
                        adapterViewpager.addFragment(FragmentCuuCongchua.
                                newInstance(obj), obj.getsERROR());
                        current++;
                    }
                    if (obj.getsKIEU().equals("9")) {
                        if (j == 0) {
                            ll_player.setVisibility(View.VISIBLE);
                        }
                        if (!(iCountAudio > 1)) {
                            iAudioCurrent = j + 1;
                            initPlayMp3(obj.getsPATH_AUDIO());
                            iCountAudio++;
                        } else {
                            iCountAudio++;
                        }
                    }
                    for (int i = 0; i < obj.getLisInfo().size(); i++) {
                        maxPage++;
                        obj.getLisInfo().get(i).setsImagePath(obj.getsPATH_IMAGE());
                        obj.getLisInfo().get(i).setsAudioPath(obj.getsPATH_AUDIO());
                        obj.getLisInfo().get(i).setsNumberDe("" + (j + 1));
                        obj.getLisInfo().get(i).setsSubNumberCau("" + (i + 1));
                        obj.getLisInfo().get(i).setsCauhoi_huongdan(obj.getsHUONGDAN());
                        obj.getLisInfo().get(i).setsTextDebai(obj.getsTEXT());
                        if (!isPlay_Again) {
                            App.mLisCauhoi.get(j).getLisInfo().get(i).setsRESULT_CHILD("0");
                            App.mLisCauhoi.get(j).getLisInfo().get(i).setsPOINT_CHILD("0");
                            App.mLisCauhoi.get(j).getLisInfo().get(i).setsANSWER_CHILD("");
                        }
                        if (obj.getsKIEU().equals("1")) {
                            adapterViewpager.addFragment(FragmentChondapanDung.
                                    newInstance(obj.getLisInfo().get(i), current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("2")) {
                            adapterViewpager.addFragment(FragmentBatSauNew.newInstance(obj.
                                    getLisInfo().get(i), current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("3")) {
                            adapterViewpager.addFragment(FragmentChemchuoi.newInstance(obj.getLisInfo().get(i), current)
                                    , obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("4")) {
                            adapterViewpager.addFragment(FragmentSapxep.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("5")) {
                            adapterViewpager.addFragment(FragmentXepTrung.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("6")) {
                            adapterViewpager.addFragment(FragmentDienvaochotrong.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("7")) {
                            adapterViewpager.addFragment(FragmentDocvaTraloi.newInstance(obj.getLisInfo().get(i),
                                    current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("8")) {
                            adapterViewpager.addFragment(FragmentXemanhtraloi.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("9")) {
                            adapterViewpager.addFragment(FragmentNgheAudio.newInstance(obj.getLisInfo().get(i), current),
                                    obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("11")) {
                            adapterViewpager.addFragment(FragmentNoicau.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        }
                    }
                }
            }
            adapterViewpager.addFragment(FragmentCompleteBaitap.newInstance(new CauhoiDetail()), "");
            viewpager_lambai.setOffscreenPageLimit(maxPage);
            viewpager_lambai.setAdapter(adapterViewpager);
            start_service_downtime();
            //    SharedPrefs.getInstance().put("abc", App.mLisCauhoi);
        } else showDialogNotify("Thông báo", "Tải bài tập lỗi, kiểm tra lại mạng và thử lại.");
    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    int maxPage = 0;

    /*@Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();
    }*/
    @Override
    public void show_error_api(List<ErrorApi> mLis) {

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

    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void showHomeworkDone(HomeworkDone homeworkDone) {

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        SharedPrefs.getInstance().put("abc", "test crash");
        stop_service();
        if (mSoundPool != null) {
            mSoundPool.release();
        }
        if (intent_service != null)
            stopService(intent_service);
        App.mLisCauhoi.clear();
     /*   EventBus.getDefault().unregister(this);
        stopService(intent_service);
        App.mLisCauhoi.clear();*/
    }

    // result chil 2 trường hợp: 0 là sai,1 là đúng
    public void put_api_nopbai(final String sKieunop) {
        EventBus.getDefault().post(new MessageEvent("nopbai", 5, 0));
        stopService(intent_service);
    }

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

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btnPlay.setImageResource(R.drawable.btn_play);
        //   mPlayer.pause();
        seekBar.setProgress(0);
    }
}
