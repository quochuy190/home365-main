package neo.vn.test365children.Activity.game.game_kow;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterSapxepKowEasyGame;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.OnStartDragListener;
import neo.vn.test365children.Listener.RecyclerViewItemClickInterface;
import neo.vn.test365children.Listener.RecyclerViewItemTouchHelperCallbackEasy;
import neo.vn.test365children.Models.DapAn;
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
public class ActivityKowPlayEasyGame extends BaseActivity implements
        OnStartDragListener, RecyclerViewItemClickInterface, ImlGameKoW.View {
    private static final String TAG = "FragmentSapxep";
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    List<DapAn> mLisStart;
    @BindView(R.id.btn_okie)
    Button btn_xemdiem;
    private ItemTouchHelper mItemTouchHelper;
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
        intent_service = new Intent(ActivityKowPlayEasyGame.this, ServiceDownTimeGame.class);
        intent_service.putExtra(Constants.KEY_SEND_TIME_SERVICE, 120000);
        startService(intent_service);
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
                //check_gameover(false);
                check_point();
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
        return R.layout.activity_kow_playgame_easy;
    }

    public void play_music_bg() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(this, R.raw.home365);
        mPlayer.setLooping(true);
        mPlayer.setVolume(20, 20);
        mPlayer.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGameKoW(this);
        mPlayer_Anwser = new MediaPlayer();
        mLisStart = new ArrayList<>();
        mLis = new ArrayList<>();
        getApiNewWord();
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

    private void reloadData() {
        resetTime();
        btn_xemdiem.setVisibility(View.VISIBLE);
        isClickXemdiem = false;
        rl_show_anwser.setVisibility(View.GONE);
        mDicPlay = lisDictionary.get(new Random().nextInt(lisDictionary.size()));
        if (mDicPlay.getsNewWord() != null) {
            if (mDicPlay.getsNewWord().length() < 10) {
                initData(mDicPlay.getsNewWord());
                initViews(true);
            } else {
                reloadData();
            }

        } else {
            reloadData();
        }
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
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_point();
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

    private void check_point() {
        String[] string_start = new String[mLisStart.size()];
        if (adapterSapxep!=null&&adapterSapxep.getList()!=null){
            String[] string_sapxep = new String[adapterSapxep.getList().size()];
            for (int i = 0; i < mLisStart.size(); i++) {
                string_start[i] = mLisStart.get(i).getsContent();
            }
            for (int i = 0; i < adapterSapxep.getList().size(); i++) {
                string_sapxep[i] = adapterSapxep.getList().get(i).getsContent();
            }
            boolean isa = Arrays.equals(string_start, string_sapxep);
            if (!isClickXemdiem) {
                if (isa) {
                    // Trả lời đúng
                    play_true();
                    check_gameover(true);
                } else {
                    play_false();
                    //Trả lời sai
                    check_gameover(false);
                }
                isClickXemdiem = true;
                for (DapAn obj : mLis) {
                    obj.setClick(true);
                }
                initViews(false);
                adapterSapxep.notifyDataSetChanged();
            }
        }
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

    private void initData(String sNewWord) {
        //  String sNewWord = "girl-boy";
        Glide.with(this).load(R.drawable.bg_kow_playgame).into(img_background);
        mLis.clear();
        mLisStart.clear();
        String[] dapan = sNewWord.split("");
        mLiDapan = new ArrayList<String>(Arrays.asList(dapan));
        mLiDapan.remove(0);
        if (mLiDapan != null && mLiDapan.size() > 0) {
            for (int i = 0; i < mLiDapan.size(); i++) {
                String s = mLiDapan.get(i);
                if (i == 0) {
                    s.toUpperCase();
                }
                mLisStart.add(new DapAn("1", s, "",
                        "agcbd", false, "" + i));
                mLis.add(new DapAn("1", s, "",
                        "agcbd", false, "" + i));
            }
            Collections.shuffle(mLis);
        }
    }

    AdapterSapxepKowEasyGame adapterSapxep;

    private void initViews(boolean ischange) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
  /*      mLayoutManager = new GridLayoutManager(getContext(),
                6, GridLayoutManager.VERTICAL, false);*/
        recycle_dapan.setLayoutManager(linearLayoutManager);
        adapterSapxep = new AdapterSapxepKowEasyGame(this, mLis, this);
        ItemTouchHelper.Callback callback =
                new RecyclerViewItemTouchHelperCallbackEasy(adapterSapxep, ischange);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recycle_dapan);
        adapterSapxep.delegate = this;
        recycle_dapan.setAdapter(adapterSapxep);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (mItemTouchHelper != null)
            mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onEndDrag() {
    }


    @Override
    public void onItemClicked(String name) {

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
        Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKowPlayEasyGame.this,
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
}
