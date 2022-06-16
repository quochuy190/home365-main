package neo.vn.test365children.Activity.game.game_kow;

import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.Dictionary;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.ResponDetailKow;
import neo.vn.test365children.Models.ResponGetTopicKow;
import neo.vn.test365children.Models.TopicKoW;
import neo.vn.test365children.Presenter.ImlGameKoW;
import neo.vn.test365children.Presenter.PresenterGameKoW;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.ServiceDownTimeGame;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.TimeUtils;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class ActivityKowPlay_Level_Two extends BaseActivity implements ImlGameKoW.View {
    private static final String TAG = "FragmentSapxep";
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.btn_okie)
    Button btn_xemdiem;
    @BindView(R.id.img_background)
    ImageView img_background;
    String sUserMother, sUserKiD;
    private PresenterGameKoW mPresenter;
    List<Dictionary> lisDictionary;
    private String mKeyTopic = "";
    MediaPlayer mPlayer, mPlayer_Anwser;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    @BindView(R.id.btn_exit)
    Button btn_exit;
    @BindView(R.id.rl_win_lose)
    ConstraintLayout rl_win_lose;
    @BindView(R.id.img_game_win_lose)
    ImageView img_game_win_lose;
    @BindView(R.id.txt_char_1)
    TextView txt_char_1;
    @BindView(R.id.txt_char_2)
    TextView txt_char_2;
    @BindView(R.id.txt_char_3)
    TextView txt_char_3;
    @BindView(R.id.txt_char_4)
    TextView txt_char_4;
    @BindView(R.id.txt_char_5)
    TextView txt_char_5;
    @BindView(R.id.txt_char_6)
    TextView txt_char_6;
    @BindView(R.id.txt_char_7)
    TextView txt_char_7;
    @BindView(R.id.txt_char_8)
    TextView txt_char_8;
    @BindView(R.id.txt_char_9)
    TextView txt_char_9;
    @BindView(R.id.txt_char_10)
    TextView txt_char_10;
    @BindView(R.id.txt_destination_1)
    TextView txt_destination_1;
    @BindView(R.id.txt_destination_2)
    TextView txt_destination_2;
    @BindView(R.id.txt_destination_3)
    TextView txt_destination_3;
    @BindView(R.id.txt_destination_4)
    TextView txt_destination_4;
    @BindView(R.id.txt_destination_5)
    TextView txt_destination_5;
    @BindView(R.id.txt_destination_6)
    TextView txt_destination_6;
    @BindView(R.id.txt_destination_7)
    TextView txt_destination_7;
    @BindView(R.id.txt_destination_8)
    TextView txt_destination_8;
    @BindView(R.id.txt_destination_9)
    TextView txt_destination_9;
    @BindView(R.id.txt_destination_10)
    TextView txt_destination_10;
    @BindView(R.id.view_play_game)
    ConstraintLayout view_play_game;
    List<String> mLisChar;
    List<String> mLisCharTeml;
    private int iCountAnwserTrue = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mPlayer_Anwser.release();
        if (intent_service != null) {
            stopService(intent_service);
        }
    }

    Intent intent_service;

    public void resetTime() {
        if (intent_service != null)
            stopService(intent_service);
        intent_service = new Intent(ActivityKowPlay_Level_Two.this, ServiceDownTimeGame.class);
        intent_service.putExtra(Constants.KEY_SEND_TIME_SERVICE, 120000);
        startService(intent_service);
    }

    int time = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Service_Game")) {
            if (event.point == 0) {
                time = (int) event.time;
                txt_time.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                time = (int) event.time;
                stopService(intent_service);
                check_anwser();
            }
        }
    }

    boolean isMute = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (mPlayer != null && !mPlayer.isPlaying() && !isMute) {
            mPlayer.start();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_kow_playgame_level_two;
    }

    public void play_music_bg() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(this, R.raw.home365);
        mPlayer.setLooping(true);
        mPlayer.setVolume(20, 20);
        mPlayer.start();

    }

    public void play_true() {
        //mp3 = new MediaPlayer();
        mPlayer_Anwser.release();
        mPlayer_Anwser = MediaPlayer.create(this, R.raw.yeah_mp3);
        mPlayer_Anwser.setLooping(false);
        mPlayer_Anwser.setVolume(20, 20);
        mPlayer_Anwser.start();

    }

    public void play_false() {
        //mp3 = new MediaPlayer();
        mPlayer_Anwser.release();
        mPlayer_Anwser = MediaPlayer.create(this, R.raw.false_te);
        mPlayer_Anwser.setLooping(false);
        mPlayer_Anwser.setVolume(20, 20);
        mPlayer_Anwser.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGameKoW(this);
        mLisChar = new ArrayList<>();
        mLisCharTeml = new ArrayList<>();
        array_number_hide = new ArrayList<>();
        mPlayer_Anwser = new MediaPlayer();
        getApiNewWord();
        init_textview();
        initEvent();
    }

    private TopicKoW objTopic;
    @BindView(R.id.txt_name_topic)
    TextView txt_name_topic;

    private void getApiNewWord() {
        mPlayer = new MediaPlayer();
        Glide.with(this).load(R.drawable.bg_kow_playgame).into(img_background);
        play_music_bg();
        objTopic = (TopicKoW) getIntent().getSerializableExtra(Constants.KEY_SEND_LEVEL_KOW);
        if (objTopic != null) {
            mKeyTopic = objTopic.getID();
            txt_name_topic.setText(objTopic.getNAME());
        }
        initDic();
    }

    @BindView(R.id.rl_show_anwser)
    ConstraintLayout rl_show_anwser;
    Dictionary mDicPlay;
    List<Integer> array_number_hide;

    private void reloadData() {
        array_number_hide.clear();
        set_map_anwser();

        visible_txt();
        init_textview();
        resetTime();
        mLisChar.clear();
        mLisCharTeml.clear();
        gone_des();
        btn_xemdiem.setVisibility(View.VISIBLE);
        isClickXemdiem = false;
        rl_show_anwser.setVisibility(View.GONE);
        mDicPlay = lisDictionary.get(new Random().nextInt(lisDictionary.size()));
        if (mDicPlay.getsNewWord() != null) {
            if (mDicPlay.getsNewWord().length() < 11) {
                String sNewword = mDicPlay.getsNewWord().toLowerCase();
                sNewword.toUpperCase();
                String[] dapan = sNewword.split("");
                mLisChar = new ArrayList<String>(Arrays.asList(dapan));
                mLisChar.remove(0);
                mLisCharTeml.addAll(mLisChar);
                int iTotal = (50 * mLisChar.size()) / 100;
                array_number_hide = get_number_hide(iTotal, mLisChar.size());
                if (mLisChar.size() > 0) {
                    for (int i = 0; i < mLisChar.size(); i++) {
                        boolean isCheck_hide = false;
                        for (int j = 0; j < array_number_hide.size(); j++) {
                            if (i == array_number_hide.get(j)) {
                                isCheck_hide = true;
                            }
                        }
                        if (isCheck_hide) {
                            mLisCharTeml.set(i, " ");
                            set_text_des(i + 1, mLisChar.get(i), false);
                        } else {
                            set_text_des(i + 1, mLisChar.get(i), true);
                        }

                    }
                    for (int k = 0; k < 10; k++) {
                        if (k < mLisChar.size()) {
                            set_text_char(k, mLisChar.get(k));
                        } else {
                            set_text_char(k, generateCode());
                        }
                    }
                }
            } else {
                reloadData();
            }

        } else {
            reloadData();
        }
    }

    private void set_map_anwser() {
        mMap_Anwser.put(1, -1);
        mMap_Anwser.put(2, -1);
        mMap_Anwser.put(3, -1);
        mMap_Anwser.put(4, -1);
        mMap_Anwser.put(5, -1);
        mMap_Anwser.put(6, -1);
        mMap_Anwser.put(7, -1);
        mMap_Anwser.put(8, -1);
        mMap_Anwser.put(9, -1);
        mMap_Anwser.put(0, -1);
    }

    private void visible_txt() {
        txt_char_1.setVisibility(View.VISIBLE);
        txt_char_2.setVisibility(View.VISIBLE);
        txt_char_3.setVisibility(View.VISIBLE);
        txt_char_4.setVisibility(View.VISIBLE);
        txt_char_5.setVisibility(View.VISIBLE);
        txt_char_6.setVisibility(View.VISIBLE);
        txt_char_7.setVisibility(View.VISIBLE);
        txt_char_8.setVisibility(View.VISIBLE);
        txt_char_9.setVisibility(View.VISIBLE);
        txt_char_10.setVisibility(View.VISIBLE);
        set_color_reload();
    }

    private void set_color_reload() {
        txt_destination_1.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_2.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_3.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_4.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_5.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_6.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_7.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_8.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_9.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
        txt_destination_10.setTextColor(getResources().getColor(R.color.blue_theme_game_kow));
    }

    private void set_text_char(int k, String sContent) {
        switch (k) {
            case 0:
                txt_char_1.setText(sContent.toUpperCase());
                break;
            case 1:
                txt_char_2.setText(sContent.toUpperCase());
                break;
            case 2:
                txt_char_3.setText(sContent.toUpperCase());
                break;
            case 3:
                txt_char_4.setText(sContent.toUpperCase());
                break;
            case 4:
                txt_char_5.setText(sContent.toUpperCase());
                break;
            case 5:
                txt_char_6.setText(sContent.toUpperCase());
                break;
            case 6:
                txt_char_7.setText(sContent.toUpperCase());
                break;
            case 7:
                txt_char_8.setText(sContent.toUpperCase());
                break;
            case 8:
                txt_char_9.setText(sContent.toUpperCase());
                break;
            case 9:
                txt_char_10.setText(sContent.toUpperCase());
                break;
        }
    }

    ConstraintLayout constraintLayout;
    ConstraintSet constraintSet;
    int iHightScreen, iWidthScreen;
    int widthPixels, heightPixels;

    private void init_textview() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float widthDpi = metrics.xdpi;
        float heightDpi = metrics.ydpi;
        iHightScreen = (int) heightDpi;
        iWidthScreen = (int) widthDpi;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        set_position_char_option_1();

    }

    private void set_position_char_option_1() {
        set_size__rotrung(widthPixels / 2, heightPixels / 12, txt_char_1);
        set_size__rotrung((widthPixels * 2 / 3), heightPixels / 15, txt_char_2);
        set_size__rotrung(widthPixels / 3, heightPixels / 9, txt_char_3);
        set_size__rotrung(widthPixels / 4, heightPixels / 18, txt_char_4);
        set_size__rotrung(widthPixels * 3 / 4, heightPixels * 2 / 7, txt_char_5);
        set_size__rotrung(widthPixels * 2 / 5, heightPixels / 5, txt_char_6);
        set_size__rotrung(widthPixels / 7, heightPixels / 11, txt_char_7);
        set_size__rotrung(widthPixels / 8, heightPixels / 4, txt_char_8);
        set_size__rotrung(widthPixels / 10, heightPixels / 23, txt_char_9);
        set_size__rotrung(widthPixels * 4 / 5, heightPixels / 16, txt_char_10);
    }

    private void set_size__rotrung(int margin_left, int margin_top, final TextView view) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(margin_left, margin_top, 0, 0);
        view.setLayoutParams(params);
    }

    public static String generateCode() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String fullalphabet = alphabet + alphabet.toLowerCase();
        Random random = new Random();
        char code = alphabet.charAt(random.nextInt(9));
        return Character.toString(code);
    }

    private List<Integer> get_number_hide(int iTotal, int leng_content) {
        List<Integer> array_number = new ArrayList<>();
        while (array_number.size() < iTotal) {
            boolean isCheck = false;
            if (array_number.size() > 0) {
                int sCount = new Random().nextInt(leng_content);
                for (int i = 0; i < array_number.size(); i++) {
                    if (sCount == array_number.get(i)) {
                        isCheck = true;
                    }
                }
                if (!isCheck) {
                    array_number.add(sCount);
                }
            } else {
                int sCount = new Random().nextInt(leng_content);
                array_number.add(sCount);
            }
        }
        return array_number;
    }


    private void set_text_des(int position, String sText, boolean Visible) {
        switch (position) {
            case 1:
                txt_destination_1.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_1.setText(sText.toUpperCase());
                } else {
                    txt_destination_1.setText("");
                }
                break;
            case 2:
                txt_destination_2.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_2.setText(sText.toUpperCase());
                } else {
                    txt_destination_2.setText("");
                }
                break;
            case 3:
                txt_destination_3.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_3.setText(sText.toUpperCase());
                } else {
                    txt_destination_3.setText("");
                }
                break;
            case 4:
                txt_destination_4.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_4.setText(sText.toUpperCase());
                } else {
                    txt_destination_4.setText("");
                }
                break;
            case 5:
                txt_destination_5.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_5.setText(sText.toUpperCase());
                } else {
                    txt_destination_5.setText("");
                }
                break;
            case 6:
                txt_destination_6.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_6.setText(sText.toUpperCase());
                } else {
                    txt_destination_6.setText("");
                }
                break;
            case 7:
                txt_destination_7.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_7.setText(sText.toUpperCase());
                } else {
                    txt_destination_7.setText("");
                }
                break;
            case 8:
                txt_destination_8.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_8.setText(sText.toUpperCase());
                } else {
                    txt_destination_8.setText("");
                }
                break;
            case 9:
                txt_destination_9.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_9.setText(sText.toUpperCase());
                } else {
                    txt_destination_9.setText("");
                }
                break;
            case 10:
                txt_destination_10.setVisibility(View.VISIBLE);
                if (Visible) {
                    txt_destination_10.setText(sText.toUpperCase());
                } else {
                    txt_destination_10.setText("");
                }
                break;
        }
    }

    private void gone_des() {
        txt_destination_1.setVisibility(View.GONE);
        txt_destination_2.setVisibility(View.GONE);
        txt_destination_3.setVisibility(View.GONE);
        txt_destination_4.setVisibility(View.GONE);
        txt_destination_5.setVisibility(View.GONE);
        txt_destination_6.setVisibility(View.GONE);
        txt_destination_7.setVisibility(View.GONE);
        txt_destination_8.setVisibility(View.GONE);
        txt_destination_9.setVisibility(View.GONE);
        txt_destination_10.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        initBack();
    }

    private void initBack() {
        showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn thoát khỏi trò chơi",
                true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        finish();
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        txt_count_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadData();
            }
        });
        event_touch();
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_anwser();

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBack();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        reloadData();
                    }
                }, 1000);
                // reloadData();
            }
        });
        img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        isMute = true;
                        Glide.with(getApplication()).load(R.drawable.icon_tat_loa).into(img_mute);
                        mPlayer.pause();
                    } else {
                        isMute = false;
                        Glide.with(getApplication()).load(R.drawable.img_mute).into(img_mute);
                        mPlayer.start();
                    }
                }
            }
        });
    }

    private void check_anwser() {
        String sNewWord = "";
        String sWordDoing = "";
        for (int i = 0; i < mLisChar.size(); i++) {
            sNewWord = sNewWord + mLisChar.get(i);
        }
        for (int i = 0; i < mLisCharTeml.size(); i++) {
            sWordDoing = sWordDoing + mLisCharTeml.get(i);
        }
        sWordDoing.toLowerCase();
        if (sNewWord.equals(sWordDoing)) {
            play_true();
            check_gameover(true);
        } else {
            play_false();
            check_gameover(false);
        }

    }

    private void event_touch() {
        txt_char_1.setOnTouchListener(onTouchListener());
        txt_char_2.setOnTouchListener(onTouchListener());
        txt_char_3.setOnTouchListener(onTouchListener());
        txt_char_4.setOnTouchListener(onTouchListener());
        txt_char_5.setOnTouchListener(onTouchListener());
        txt_char_6.setOnTouchListener(onTouchListener());
        txt_char_7.setOnTouchListener(onTouchListener());
        txt_char_8.setOnTouchListener(onTouchListener());
        txt_char_9.setOnTouchListener(onTouchListener());
        txt_char_10.setOnTouchListener(onTouchListener());
    }


    int[] arrBgItem = new int[]{R.drawable.img_menu_toan, R.drawable.img_menu_blue, R.drawable.img_menu_tim,
            R.drawable.img_menu_green, R.drawable.img_menu_red, R.drawable.img_menu_toan, R.drawable.img_menu_blue,
            R.drawable.img_menu_tim, R.drawable.img_menu_green, R.drawable.img_menu_red,};
    List<String> mLiDapan;

    private void initDic() {
        lisDictionary = new ArrayList<>();
        sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserKiD = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        if (sUserKiD != null && sUserMother != null && mKeyTopic != null) {
            showDialogLoading();
            mPresenter.api_get_detail_kow(sUserMother, sUserKiD, mKeyTopic);
        }
    }

    @Override
    public void show_error_api(ErrorApi objError) {
        hideDialogLoading();
        showAlertErrorNetwork();

    }

    @Override
    public void show_list_topic(ResponGetTopicKow obj) {

    }

    @Override
    public void show_list_detail_kow(ResponDetailKow obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            if (obj.getLisInfo() != null) {
                for (Dictionary objDic : obj.getLisInfo()) {
                    if (objDic.getsNewWord() != null) {
                        objDic.setsNewWord(objDic.getsNewWord().replace(" ", "-").trim());
                        lisDictionary.add(objDic);
                    }
                }
                //   lisDictionary.addAll(obj.getLisInfo());
                reloadData();
            }
        } else {
            showAlertDialog("Thông báo", obj.getsRESULT());
        }
    }

    @BindView(R.id.img_title_gameover)
    TextView img_title_gameover;
    @BindView(R.id.txt_count_point)
    TextView txt_count_point;
    @BindView(R.id.txt_show_turn)
    TextView txt_show_turn;
    @BindView(R.id.txt_anwser_newword)
    TextView txt_anwser_newword;
    @BindView(R.id.txt_anwser_translate)
    TextView txt_anwser_translate;
    int iCoutTrue = 0;

    private void check_gameover(boolean isAnwser) {
        btn_xemdiem.setVisibility(View.GONE);
        rl_show_anwser.setVisibility(View.VISIBLE);
        //rl_win_lose.setVisibility(View.VISIBLE);
        txt_anwser_newword.setText(mDicPlay.getsNewWord());
        txt_anwser_translate.setText(mDicPlay.getsTranslate());
     /*   Animation animationRotale = AnimationUtils.loadAnimation(this,
                R.anim.animation_game_over);
        img_game_win_lose.startAnimation(animationRotale);*/
        Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKowPlay_Level_Two.this,
                R.anim.animation_show_question);
        rl_show_anwser.startAnimation(animationRotaletxt);

       /* Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_gameover_kow);
        txt_anwser_newword.startAnimation(animationRotaletxt);
        txt_anwser_translate.startAnimation(animationRotaletxt);*/

        if (isAnwser) {
            img_title_gameover.setText("YOU WIN");
            iCoutTrue++;
            txt_count_point.setText("" + iCoutTrue);
            //   Glide.with(this).load(R.drawable.img_winner).into(img_title_gameover);
        } else {
            img_title_gameover.setText("YOU LOSE");
            //  Glide.with(this).load(R.drawable.title_game_over).into(img_title_gameover);
        }
    }

    private int xDelta, x_start1, y_start1;
    private int yDelta;
    private Map<Integer, Integer> mMap_Anwser = new LinkedHashMap<>();

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                try {
                    final int x = (int) event.getRawX();
                    final int y = (int) event.getRawY();
                    String sContent = view.toString();
                    String id = String.valueOf(view.getId());
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            //txt_char_1.setVisibility(View.VISIBLE);
                            ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                            xDelta = x - lParams.leftMargin;
                            yDelta = y - lParams.topMargin;
                            x_start1 = lParams.leftMargin;
                            y_start1 = lParams.topMargin;
                            break;
                        case MotionEvent.ACTION_UP:
                            if (inViewInBounds(view_play_game, (int) event.getRawX(), (int) event.getRawY())) {
                                if (inViewInBounds(txt_destination_2, (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(1) == -1) {
                                        if (check_des_null(2)) {
                                            settext_action_up(txt_destination_2, view.getId(), 1);
                                            mLisCharTeml.set(1, txt_destination_2.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams)
                                                    view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(1), x_start1, y_start1);
                                        settext_action_up(txt_destination_2, view.getId(), 1);
                                        mLisCharTeml.set(1, txt_destination_2.getText().toString().toLowerCase());
                                    }

                                } else if (inViewInBounds(txt_destination_1,
                                        (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(0) == -1) {
                                        if (check_des_null(1)) {
                                            settext_action_up(txt_destination_1, view.getId(), 0);
                                            mLisCharTeml.set(0, txt_destination_1.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(0), x_start1, y_start1);
                                        settext_action_up(txt_destination_1, view.getId(), 0);
                                        mLisCharTeml.set(0, txt_destination_1.getText().toString().toLowerCase());
                                    }

                                } else if (inViewInBounds(txt_destination_3, (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(2) == -1) {
                                        if (check_des_null(3)) {
                                            settext_action_up(txt_destination_3, view.getId(), 2);
                                            mLisCharTeml.set(2, txt_destination_3.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(2), x_start1, y_start1);
                                        settext_action_up(txt_destination_3, view.getId(), 2);
                                        mLisCharTeml.set(2, txt_destination_3.getText().toString().toLowerCase());
                                    }


                                } else if (inViewInBounds(txt_destination_4, (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(3) == -1) {
                                        if (check_des_null(4)) {
                                            settext_action_up(txt_destination_4, view.getId(), 3);
                                            mLisCharTeml.set(3, txt_destination_4.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(3), x_start1, y_start1);
                                        settext_action_up(txt_destination_4, view.getId(), 3);
                                        mLisCharTeml.set(3, txt_destination_4.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_5, (int) event.getRawX(), (int) event.getRawY())) {

                                    if (mMap_Anwser.get(4) == -1) {
                                        if (check_des_null(5)) {
                                            settext_action_up(txt_destination_5, view.getId(), 4);
                                            mLisCharTeml.set(4, txt_destination_5.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(4), x_start1, y_start1);
                                        settext_action_up(txt_destination_5, view.getId(), 4);
                                        mLisCharTeml.set(4, txt_destination_5.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_6, (int) event.getRawX(), (int) event.getRawY())) {

                                    if (mMap_Anwser.get(5) == -1) {
                                        if (check_des_null(6)) {
                                            settext_action_up(txt_destination_6, view.getId(), 5);
                                            mLisCharTeml.set(5, txt_destination_6.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(5), x_start1, y_start1);
                                        settext_action_up(txt_destination_6, view.getId(), 5);
                                        mLisCharTeml.set(5, txt_destination_6.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_7, (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(6) == -1) {
                                        if (check_des_null(7)) {
                                            settext_action_up(txt_destination_7, view.getId(), 6);
                                            mLisCharTeml.set(6, txt_destination_7.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(6), x_start1, y_start1);
                                        settext_action_up(txt_destination_7, view.getId(), 6);
                                        mLisCharTeml.set(6, txt_destination_7.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_8, (int) event.getRawX(), (int) event.getRawY())) {

                                    if (mMap_Anwser.get(7) == -1) {
                                        if (check_des_null(8)) {
                                            settext_action_up(txt_destination_8, view.getId(), 7);
                                            mLisCharTeml.set(7, txt_destination_8.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(7), x_start1, y_start1);
                                        settext_action_up(txt_destination_8, view.getId(), 7);
                                        mLisCharTeml.set(7, txt_destination_8.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_9, (int) event.getRawX(), (int) event.getRawY())) {


                                    if (mMap_Anwser.get(8) == -1) {
                                        if (check_des_null(9)) {
                                            settext_action_up(txt_destination_9, view.getId(), 8);
                                            mLisCharTeml.set(8, txt_destination_9.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(8), x_start1, y_start1);
                                        settext_action_up(txt_destination_9, view.getId(), 8);
                                        mLisCharTeml.set(8, txt_destination_9.getText().toString().toLowerCase());
                                    }
                                } else if (inViewInBounds(txt_destination_10, (int) event.getRawX(), (int) event.getRawY())) {
                                    if (mMap_Anwser.get(9) == -1) {
                                        if (check_des_null(10)) {
                                            settext_action_up(txt_destination_10, view.getId(), 9);
                                            mLisCharTeml.set(9, txt_destination_10.getText().toString().toLowerCase());
                                        } else {
                                            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                            layoutParams1.leftMargin = x_start1;
                                            layoutParams1.topMargin = y_start1;
                                            layoutParams1.rightMargin = 0;
                                            layoutParams1.bottomMargin = 0;
                                            view.setLayoutParams(layoutParams1);
                                        }
                                    } else {
                                        set_text_swap(mMap_Anwser.get(9), x_start1, y_start1);
                                        settext_action_up(txt_destination_10, view.getId(), 9);
                                        mLisCharTeml.set(9, txt_destination_10.getText().toString().toLowerCase());
                                    }
                                } else {
                                    ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                    layoutParams1.leftMargin = x_start1;
                                    layoutParams1.topMargin = y_start1;
                                    layoutParams1.rightMargin = 0;
                                    layoutParams1.bottomMargin = 0;
                                    view.setLayoutParams(layoutParams1);
                                }
                            } else {
                                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                layoutParams1.leftMargin = x_start1;
                                layoutParams1.topMargin = y_start1;
                                layoutParams1.rightMargin = 0;
                                layoutParams1.bottomMargin = 0;
                                view.setLayoutParams(layoutParams1);
                            }

                            break;
                        case MotionEvent.ACTION_MOVE:
                            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                            layoutParams.leftMargin = x - xDelta;
                            layoutParams.topMargin = y - yDelta;
                            view.setLayoutParams(layoutParams);
                            break;
                    }
                    view_play_game.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        };
    }

    private void set_text_swap(int position, int x_start1, int y_start1) {
        switch (position) {
            case 1:
                txt_char_1.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_1, x_start1, y_start1);
                break;
            case 2:
                txt_char_2.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_2, x_start1, y_start1);
                break;
            case 3:
                txt_char_3.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_3, x_start1, y_start1);
                break;
            case 4:
                txt_char_4.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_4, x_start1, y_start1);
                break;
            case 5:
                txt_char_5.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_5, x_start1, y_start1);
                break;
            case 6:
                txt_char_6.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_6, x_start1, y_start1);
                break;
            case 7:
                txt_char_7.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_7, x_start1, y_start1);
                break;
            case 8:
                txt_char_8.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_8, x_start1, y_start1);
                break;
            case 9:
                txt_char_9.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_9, x_start1, y_start1);
                break;
            case 10:
                txt_char_10.setVisibility(View.VISIBLE);
                set_position_textview(txt_char_10, x_start1, y_start1);
                break;
        }
    }

    private void set_position_textview(TextView txt_char_1, int x_start1, int y_start1) {
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams)
                txt_char_1.getLayoutParams();
        layoutParams1.leftMargin = x_start1;
        layoutParams1.topMargin = y_start1;
        layoutParams1.rightMargin = 0;
        layoutParams1.bottomMargin = 0;
        txt_char_1.setLayoutParams(layoutParams1);
    }

    private boolean check_des_null(int iCount) {
        boolean isCheck = false;
        if (array_number_hide.size() > 0) {
            for (int i = 0; i < array_number_hide.size(); i++) {
                if (iCount == (array_number_hide.get(i) + 1)) {
                    isCheck = true;
                    return isCheck;
                }
            }
        }
        return isCheck;
    }

    private void settext_action_up(TextView txt_des, int id, int iPositionDes) {
        remove_array(iPositionDes);
        txt_des.setTextColor(getResources().getColor(R.color.green));
        switch (id) {
            case R.id.txt_char_1:
                mMap_Anwser.put(iPositionDes, 1);
                txt_des.setText(txt_char_1.getText());
                txt_char_1.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_2:
                mMap_Anwser.put(iPositionDes, 2);
                txt_des.setText(txt_char_2.getText());
                txt_char_2.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_3:
                mMap_Anwser.put(iPositionDes, 3);
                txt_des.setText(txt_char_3.getText());
                txt_char_3.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_4:
                mMap_Anwser.put(iPositionDes, 4);
                txt_des.setText(txt_char_4.getText());
                txt_char_4.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_5:
                mMap_Anwser.put(iPositionDes, 5);
                txt_des.setText(txt_char_5.getText());
                txt_char_5.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_6:
                mMap_Anwser.put(iPositionDes, 6);
                txt_des.setText(txt_char_6.getText());
                txt_char_6.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_7:
                mMap_Anwser.put(iPositionDes, 7);
                txt_des.setText(txt_char_7.getText());
                txt_char_7.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_8:
                mMap_Anwser.put(iPositionDes, 8);
                txt_des.setText(txt_char_8.getText());
                txt_char_8.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_9:
                mMap_Anwser.put(iPositionDes, 9);
                txt_des.setText(txt_char_9.getText());
                txt_char_9.setVisibility(View.INVISIBLE);
                break;
            case R.id.txt_char_10:
                mMap_Anwser.put(iPositionDes, 10);
                txt_des.setText(txt_char_10.getText());
                txt_char_10.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void remove_array(int i) {
        if (array_number_hide.size() > 0) {
            for (int j = 0; j < array_number_hide.size(); j++) {
                if (array_number_hide.get(j) == i) {
                    array_number_hide.remove(j);
                }
            }
            Log.i(TAG, "remove_array: " + array_number_hide);
        }
    }

    Rect outRect = new Rect();
    int[] location = new int[2];

    private boolean inViewInBounds(View view, int x, int y) {
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

}
