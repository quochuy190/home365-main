package neo.vn.test365children.Activity.game.game_kow;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterChucai;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Chucai;
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

public class ActivityKoWPlayGame extends BaseActivity implements ImlGameKoW.View {
    private static final String TAG = "ActivityKoWPlayGame";
    @BindView(R.id.recycle_list_chucai)
    RecyclerView recycle_list_chucai;
    List<Chucai> lisChucai;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterChucai mAdapter;
    @BindView(R.id.txt_content)
    TextView txt_content;
    List<String> listTudien;
    List<Dictionary> lisDictionary;
    Dictionary mDicPlay;
    @BindView(R.id.rl_show_anwser)
    ConstraintLayout rl_show_anwser;
    @BindView(R.id.btn_exit)
    Button btn_exit;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    private String mLevel = "1";
    private TopicKoW objTopic;
    private String mKeyTopic = "";
    String sUserMother, sUserKiD;
    private PresenterGameKoW mPresenter;
    @BindView(R.id.txt_name_topic)
    TextView txt_name_topic;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.ll_goiy)
    RelativeLayout ll_goiy;
    @BindView(R.id.txt_goiy_number)
    TextView txt_goiy_number;
    MediaPlayer mPlayer, mPlayer_Anwser;


    @Override
    public int setContentViewId() {
        return R.layout.activity_kow_playgame;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mPlayer_Anwser.release();
        if (intent_service != null)
            stopService(intent_service);
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

    Intent intent_service;

    public void resetTime() {
        if (intent_service != null)
            stopService(intent_service);
        intent_service = new Intent(ActivityKoWPlayGame.this, ServiceDownTimeGame.class);
        intent_service.putExtra(Constants.KEY_SEND_TIME_SERVICE, 120000);
        startService(intent_service);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGameKoW(this);
        mPlayer = new MediaPlayer();
        mPlayer_Anwser = new MediaPlayer();
        Glide.with(this).load(R.drawable.bg_kow_playgame).into(img_background);
        play_music_bg();
        objTopic = (TopicKoW) getIntent().getSerializableExtra(Constants.KEY_SEND_LEVEL_KOW);
        if (objTopic != null) {
            mKeyTopic = objTopic.getID();
            txt_name_topic.setText(objTopic.getNAME());
        }
        initDic();
        initData();
        init();
        initEvent();
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
                check_gameover(false);
            }
        }
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
    protected void onPause() {
        super.onPause();

    }

    boolean isGoiy = false;

    private void initEvent() {
        ll_goiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGoiy) {
                    isGoiy = true;
                    txt_goiy_number.setText("0");
                    if (mDicPlay.getsNewWord() != null && txt_content.length() < mDicPlay.getsNewWord().length()) {
                        txt_content.setText(mDicPlay.getsNewWord().substring(0, (txt_content.length() + 1)).toUpperCase());
                        isClick = true;
                        stopService(intent_service);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                check_chucai(txt_content.getText().toString().toUpperCase());
                                //check_level_two(txt_content.getText().toString().toUpperCase());
                            }
                        }, 500);
                    }
                } else
                    showAlertDialog("Thông báo", "Bạn đã hết quyền gợi ý");

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

    private void initDic() {
        lisDictionary = new ArrayList<>();
        sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserKiD = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        if (sUserKiD != null && sUserMother != null && mKeyTopic != null) {
            showDialogLoading();
            mPresenter.api_get_detail_kow(sUserMother, sUserKiD, mKeyTopic);
        }
    }

    private void initData() {
        lisChucai = new ArrayList<>();
        lisChucai.add(new Chucai("A", 0, "", R.drawable.a));
        lisChucai.add(new Chucai("B", 0, "", R.drawable.b));
        lisChucai.add(new Chucai("C", 0, "", R.drawable.c));
        lisChucai.add(new Chucai("D", 0, "", R.drawable.d));
        lisChucai.add(new Chucai("E", 0, "", R.drawable.e));
        lisChucai.add(new Chucai("F", 0, "", R.drawable.f));
        lisChucai.add(new Chucai("G", 0, "", R.drawable.g));
        lisChucai.add(new Chucai("H", 0, "", R.drawable.h));
        lisChucai.add(new Chucai("I", 0, "", R.drawable.i));
        lisChucai.add(new Chucai("J", 0, "", R.drawable.j));
        lisChucai.add(new Chucai("K", 0, "", R.drawable.k));
        lisChucai.add(new Chucai("L", 0, "", R.drawable.l));
        lisChucai.add(new Chucai("M", 0, "", R.drawable.m));
        lisChucai.add(new Chucai("N", 0, "", R.drawable.n));
        lisChucai.add(new Chucai("O", 0, "", R.drawable.o));
        lisChucai.add(new Chucai("P", 0, "", R.drawable.p));
        lisChucai.add(new Chucai("Q", 0, "", R.drawable.q));
        lisChucai.add(new Chucai("R", 0, "", R.drawable.r));
        lisChucai.add(new Chucai("S", 0, "", R.drawable.s));
        lisChucai.add(new Chucai("T", 0, "", R.drawable.t));
        lisChucai.add(new Chucai("U", 0, "", R.drawable.u));
        lisChucai.add(new Chucai("V", 0, "", R.drawable.v));
        lisChucai.add(new Chucai("W", 0, "", R.drawable.w));
        lisChucai.add(new Chucai("X", 0, "", R.drawable.x));
        lisChucai.add(new Chucai("Y", 0, "", R.drawable.y));
        lisChucai.add(new Chucai("Z", 0, "", R.drawable.z));
    }

    private void reloadData() {
        isClick = false;
        isGoiy = false;
        txt_goiy_number.setText("1");
        resetTime();
        rl_show_anwser.setVisibility(View.GONE);
        mDicPlay = lisDictionary.get(new Random().nextInt(lisDictionary.size()));
        if (mDicPlay.getsNewWord() != null) {
            if (mLevel.equals("1")) {
                String a_letter = Character.toString(mDicPlay.getsNewWord()
                        .charAt(0));
                txt_content.setText(a_letter.toUpperCase());
            }
        } else {
            reloadData();
        }


    }

    private boolean isClick = false;

    private void init() {
        mAdapter = new AdapterChucai(lisChucai, this);
        mLayoutManager = new GridLayoutManager(this, 13,
                GridLayoutManager.VERTICAL, false);
        //  mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_list_chucai.setLayoutManager(mLayoutManager);
        recycle_list_chucai.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (item != null) {
                    if (!isClick) {
                        isClick = true;
                        Chucai sChucai = (Chucai) item;
                        if (sChucai != null) {
                            String s = txt_content.getText().toString();
                            if (mLevel.length() > 0 && mLevel.equals("1")) {
                                if (intent_service != null)
                                    stopService(intent_service);
                                txt_content.setText(s + sChucai.getsChucai());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        check_chucai(txt_content.getText().toString().toUpperCase());
                                        //check_level_two(txt_content.getText().toString().toUpperCase());
                                    }
                                }, 500);
                            } else if (mLevel.length() > 0 && mLevel.equals("2")) {
                      /*  txt_content.setText(sChucai.getsChucai() + s);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // check_chucai(txt_content.getText().toString().toUpperCase());
                                check_level_two(txt_content.getText().toString().toUpperCase());
                            }
                        }, 500);*/
                            } else if (mLevel.length() > 0 && mLevel.equals("3")) {
                            }
                        }
                    }
                }

            }
        });
    }

    List<Dictionary> lisTeml = new ArrayList<>();

    private void check_chucai(final String sContent) {
        lisTeml.clear();
        for (Dictionary s : lisDictionary) {
            if (s.getsNewWord() != null) {
                int index = s.getsNewWord().toUpperCase().indexOf(sContent);
                if (s.getsNewWord().toUpperCase().indexOf(sContent) == 0)
                    lisTeml.add(s);
            }
        }
        if (lisTeml.size() > 0) {
            get_chart_random(sContent);
        } else {
            check_gameover(false);
        }
    }

    private void get_chart_random(final String sContent) {
        if (lisTeml.size() == 1) {
            mDicPlay = lisTeml.get(0);
            if (lisTeml.get(0).getsNewWord().toUpperCase().equals(sContent.toUpperCase())) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        check_gameover(true);
                    }
                }, 1000);
            } else {
                change_turn("Lượt đi của máy");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        change_turn_off();
                    }
                }, 2000);
                new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        String a_letter = Character.toString(lisTeml.get(0).getsNewWord()
                                .charAt(txt_content.getText().toString().length()));
                        if (txt_content.getText().toString().length() < (lisTeml.get(0).getsNewWord().length() - 1)) {
                            String a_letter_1check = Character.toString(lisTeml.get(0).getsNewWord()
                                    .charAt(txt_content.getText().toString().length() + 1));
                            if (a_letter_1check.equals(" ")) {
                                txt_content.setText(sContent + a_letter.toUpperCase() + " ");
                            } else if (a_letter_1check.equals("-")) {
                                txt_content.setText(sContent + a_letter.toUpperCase() + "-");
                            } else {
                                txt_content.setText(sContent + a_letter.toUpperCase());
                            }
                        } else {
                            txt_content.setText(sContent + a_letter.toUpperCase());
                        }
                        if (check_computer_chose(txt_content.getText().toString())) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_gameover(false);
                                }
                            }, 1000);
                            return;
                        }
                        resetTime();
                        isClick = false;
                    }
                }.start();

            }
        } else {
            int position = new Random().nextInt(lisTeml.size());
            String sPosition = lisTeml.get(position).getsNewWord().toUpperCase();
            mDicPlay = lisTeml.get(position);
            if (sPosition.equals(sContent.toUpperCase())) {
                lisTeml.remove(position);
                get_chart_random(sContent.toUpperCase());
            } else {
                change_turn("Lượt đi của máy");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        change_turn_off();
                    }
                }, 2000);
                new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        String a_letter = Character.toString(mDicPlay.getsNewWord()
                                .charAt(txt_content.getText().toString().length()));
                        if (txt_content.getText().toString().length() < (mDicPlay.getsNewWord().length() - 1)) {
                            String a_letter_1check = Character.toString(mDicPlay.getsNewWord()
                                    .charAt(txt_content.getText().toString().length() + 1));
                            if (a_letter_1check.equals(" ")) {
                                txt_content.setText(sContent + a_letter.toUpperCase() + " ");
                            } else if (a_letter_1check.equals("-")) {
                                txt_content.setText(sContent + a_letter.toUpperCase() + "-");
                            } else {
                                txt_content.setText(sContent + a_letter.toUpperCase());
                            }
                        } else {
                            txt_content.setText(sContent + a_letter.toUpperCase());
                        }
                        /*String a_letter_1check = Character.toString(lisTeml.get(0).getsNewWord()
                                .charAt(txt_content.getText().toString().length() + 1));
                        if (a_letter_1check.equals(" ")) {
                            txt_content.setText(sContent + a_letter.toUpperCase() + " ");
                        } else if (a_letter_1check.equals("-")) {
                            txt_content.setText(sContent + a_letter.toUpperCase() + "-");
                        } else {
                            txt_content.setText(sContent + a_letter.toUpperCase());
                        }*/
                        if (check_computer_chose(txt_content.getText().toString())) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_gameover(false);
                                }
                            }, 1000);
                            return;
                        }
                        resetTime();
                        isClick = false;
                    }
                }.start();

            }
        }
    }

    private boolean check_computer_chose(String sContent) {
        List<Dictionary> lisCheckTemlp = new ArrayList<>();
        for (Dictionary sDic : lisDictionary) {
            if (sDic.getsNewWord() != null && sDic.getsNewWord().length() > 0) {
                int index = sDic.getsNewWord().toUpperCase().indexOf(sContent);
                if (sDic.getsNewWord().toUpperCase().indexOf(sContent) == 0)
                    lisCheckTemlp.add(sDic);
                Log.i(TAG, "onFinish: " + index);
            }
        }
        if (lisCheckTemlp.size() > 1) {
            return false;
        } else if (lisCheckTemlp.size() == 1) {
            if (lisCheckTemlp.get(0).getsNewWord().toUpperCase().equals(sContent.toUpperCase())) {
                return true;
            } else return false;
        }
        return false;
    }

    @BindView(R.id.img_title_gameover)
    TextView img_title_gameover;
    @BindView(R.id.txt_show_turn)
    TextView txt_show_turn;
    @BindView(R.id.txt_anwser_newword)
    TextView txt_anwser_newword;
    @BindView(R.id.txt_anwser_translate)
    TextView txt_anwser_translate;

    private void check_gameover(boolean isAnwser) {
        if (isAnwser) {
            play_true();
        } else {
            play_false();
        }
        rl_show_anwser.setVisibility(View.VISIBLE);
        txt_anwser_newword.setText(mDicPlay.getsNewWord());
        txt_anwser_translate.setText(mDicPlay.getsTranslate());
      /*  Animation animationRotale = AnimationUtils.loadAnimation(this,
                R.anim.animation_game_over);
        img_title_gameover.startAnimation(animationRotale);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                        R.anim.animation_show_question);
                rl_show_anwser.startAnimation(animationRotaletxt);
                ;
            }
        }, 5);

       /* Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_gameover_kow);
        txt_anwser_newword.startAnimation(animationRotaletxt);
        txt_anwser_translate.startAnimation(animationRotaletxt);*/

        if (isAnwser) {
            img_title_gameover.setText("Winner");
            //   Glide.with(this).load(R.drawable.img_winner).into(img_title_gameover);
        } else {
            img_title_gameover.setText("Game Over");
            //  Glide.with(this).load(R.drawable.title_game_over).into(img_title_gameover);
        }
    }

    private void change_turn(String sContent) {
        txt_show_turn.setText(sContent);
        txt_show_turn.setVisibility(View.VISIBLE);
      /*  Animation animationRotale = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_game_kow_change_turn);
        txt_show_turn.startAnimation(animationRotale);*/
    }

    private void change_turn_off() {
        txt_show_turn.setVisibility(View.VISIBLE);
    /*    Animation animationRotale = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_game_kow_change_turn_off);
        txt_show_turn.startAnimation(animationRotale);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt_show_turn.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override
    public void show_error_api(ErrorApi objError) {

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
        }
    }
}
