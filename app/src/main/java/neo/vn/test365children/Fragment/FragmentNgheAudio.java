package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;
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
public class FragmentNgheAudio extends BaseFragment implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener
        , View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "FragmentNgheAudio";
    private CauhoiDetail mCauhoi;
    private MediaPlayer mPlayer;
    @BindView(R.id.btnPlay)
    ImageView btnPlay;
    @BindView(R.id.player_progressbar)
    SeekBar seekBar;
    @BindView(R.id.songCurrentDurationLabel)
    TextView txtDuration;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    LinearLayout ll_cauhoi;
    @BindView(R.id.recycler_dapan)
    RecyclerView recycle_dapan;
    List<DapAn> mLis;
    AdapterDapan adapter;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    private boolean isClickXemdiem = false;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    @BindView(R.id.webview_anwser_A)
    WebView webview_anwser_A;
    @BindView(R.id.webview_anwser_B)
    WebView webview_anwser_B;
    @BindView(R.id.webview_anwser_C)
    WebView webview_anwser_C;
    @BindView(R.id.webview_anwser_D)
    WebView webview_anwser_D;
    @BindView(R.id.ll_webview_A)
    LinearLayout ll_webview_A;
    @BindView(R.id.ll_webview_B)
    LinearLayout ll_webview_B;
    @BindView(R.id.ll_webview_C)
    LinearLayout ll_webview_C;
    @BindView(R.id.ll_webview_D)
    LinearLayout ll_webview_D;

    @BindView(R.id.img_checkbox_A)
    ImageView img_checkbox_A;
    @BindView(R.id.img_checkbox_B)
    ImageView img_checkbox_B;
    @BindView(R.id.img_checkbox_C)
    ImageView img_checkbox_C;
    @BindView(R.id.img_checkbox_D)
    ImageView img_checkbox_D;
    @BindView(R.id.img_reload)
    ImageView img_reload;

    public static FragmentNgheAudio newInstance(CauhoiDetail restaurant, int current) {
        FragmentNgheAudio restaurantDetailFragment = new FragmentNgheAudio();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        args.putInt("current", current);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    public CauhoiDetail getmCauhoi() {
        return mCauhoi;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview_anwser_A.clearFormData();
        webview_anwser_A.clearCache(true);
        webview_anwser_A.clearHistory();
        webview_anwser_B.clearFormData();
        webview_anwser_B.clearCache(true);
        webview_anwser_B.clearHistory();
        webview_anwser_C.clearFormData();
        webview_anwser_C.clearCache(true);
        webview_anwser_C.clearHistory();
        webview_anwser_D.clearFormData();
        webview_anwser_D.clearCache(true);
        webview_anwser_D.clearHistory();
        webview_debai.clearFormData();
        webview_debai.clearCache(true);
        webview_debai.clearHistory();
    }

    private int current;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Audio")) {
            Log.i(TAG, "onMessageEvent: Audio");
            if (mPlayer.isPlaying()) {
                Log.i(TAG, "onMessageEvent: Audio play");
                btnPlay.setImageResource(R.drawable.btn_play);
                mPlayer.pause();
            }
            int iPoint = (int) event.getPoint();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
        current = getArguments().getInt("current");
        mPlayer = new MediaPlayer();
    }

    private void reload() {
        webview_debai.reload();
        webview_anwser_A.reload();
        webview_anwser_C.reload();
        webview_anwser_D.reload();
        webview_anwser_B.reload();

        webview_debai.setWebViewClient(new WebViewClient());
        webview_anwser_A.setWebViewClient(new WebViewClient());
        webview_anwser_B.setWebViewClient(new WebViewClient());
        webview_anwser_C.setWebViewClient(new WebViewClient());
        webview_anwser_D.setWebViewClient(new WebViewClient());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nghevatraloi, container, false);
        mLis = new ArrayList<>();
        ButterKnife.bind(this, view);
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initLoadImage();
        initData();
        initEvent();
        return view;
    }

    private void initLoadImage() {
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
    }

    private void initEvent() {
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        ll_webview_A.setOnClickListener(this);
        ll_webview_B.setOnClickListener(this);
        ll_webview_C.setOnClickListener(this);
        ll_webview_D.setOnClickListener(this);
        img_checkbox_A.setOnClickListener(this);
        img_checkbox_B.setOnClickListener(this);
        img_checkbox_C.setOnClickListener(this);
        img_checkbox_D.setOnClickListener(this);
        webview_anwser_A.setOnTouchListener(this);
        webview_anwser_B.setOnTouchListener(this);
        webview_anwser_C.setOnTouchListener(this);
        webview_anwser_D.setOnTouchListener(this);

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
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    isClickXemdiem = true;
                    img_anwser_chil.setVisibility(View.VISIBLE);
                    if (anwser()) {
                        Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                        EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    } else {
                        Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                        EventBus.getDefault().post(new MessageEvent("Point_false_sau", 0, 0));
                        setImageFalse(sAnwser);
                    }
                    EventBus.getDefault().post(new MessageEvent(Constants.KEY_SAVE_LIST_EXER_PLAYING, 0, 0));
                }
            }
        });

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
    }


    private Handler mHandler = new Handler();
    //Make sure you update Seekbar on UI thread
    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
//            if (isDetached()) return;
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

    private void initData() {
        try {
            if (mCauhoi != null) {
                if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                    txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                            + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                            + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
                StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
            }
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                    != null && mCauhoi.getsSubNumberCau().equals("1")) {
                showDialogLoading();
            }
            Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
            try {
                mPlayer.reset();
                String url = Config.URL_VIDEO + mCauhoi.getsAudioPath();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(url);
                mPlayer.setOnPreparedListener(this);
                mPlayer.setOnErrorListener(this);
                mPlayer.prepareAsync();
                mHandler.postDelayed(mProgressCallback, 0);
            } catch (IOException e) {
                Log.e(TAG, "play: ", e);
            }
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview_center(webview_debai, mCauhoi.getsHTML_CONTENT());
                    initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
                    initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
                    initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
                    initWebview(webview_anwser_D, mCauhoi.getsHTML_D());
                }
            });

            if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
                ll_webview_A.setVisibility(View.VISIBLE);
            } else {
                ll_webview_A.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
                ll_webview_B.setVisibility(View.VISIBLE);
            } else {
                ll_webview_B.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
                ll_webview_C.setVisibility(View.VISIBLE);
            } else {
                ll_webview_C.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
                ll_webview_D.setVisibility(View.VISIBLE);
            } else {
                ll_webview_D.setVisibility(View.GONE);
            }
            if (mCauhoi.isDalam()) {
                isClickXemdiem = true;
                img_anwser_chil.setVisibility(View.VISIBLE);
                if (mCauhoi.isAnserTrue()) {
                    Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                } else {
                    Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);

                }
                check_anwser_chil();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initWebview_center(final WebView webview, String link_web) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings();
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        webview.setBackgroundColor(Color.TRANSPARENT);
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webview.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
      //  String pish = "<html><body  align='center'>";
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "</style></head>"
                + "<body>"
                + "<div>"
                + StringUtil.convert_html(link_web)
                + "</div>"
                + "</body></html>";
        webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        switch (view.getId()) {
                            case R.id.webview_debai:
                                new CountDownTimer(1000, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        hideDialogLoading();
                                    }
                                }.start();
                                break;
                        }
                    }
                }.start();
            }
        });
    }

    public void initWebview(final WebView webview, String link_web) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings();
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        webview.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;}</style>"
                + "</style></head>"
                + "<body>"
                + StringUtil.convert_html(link_web)
                + "</body></html>";
        webview.loadDataWithBaseURL("", text,
                "text/html", "UTF-8", "");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        int i = 0;
                        switch (view.getId()) {
                            case R.id.webview_debai:
                                hideDialogLoading();
                                break;
                            case R.id.webview_anwser_A:
                                break;
                            case R.id.webview_anwser_B:
                                break;
                            case R.id.webview_anwser_C:
                                break;
                            case R.id.webview_anwser_D:
                                break;
                        }
                    }
                }.start();
            }
        });
    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapan(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                2, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (!isClickXemdiem) {
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                    if (!mLis.get(position).isClick()) {
                        for (DapAn obj : mLis) {
                            //obj.setClick(true);
                            if (obj.getsName().equals(mLis.get(position).getsName())) {
                                if (obj.getsDapan_Dung().equals(obj.getsName())) {
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
                                } else {
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
                                }

                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(obj.getsName());
                                obj.setsDapan_Traloi(obj.getsName());
                            } else {
                                obj.setsDapan_Traloi("");
                            }
                        }
                        isTraloi = true;
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

    private boolean isPaused;

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i(TAG, "onPrepared: succec");
    }

    private void setImageFalse(String sClickAnwser) {
        switch (sClickAnwser) {
            case "A":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_A);
                break;
            case "B":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_B);
                break;
            case "C":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_C);
                break;
            case "D":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_D);
                break;
        }
        switch (mCauhoi.getsANSWER()) {
            case "A":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_A);
                break;
            case "B":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_B);
                break;
            case "C":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_C);
                break;
            case "D":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_D);
                break;
        }
    }

    @BindView(R.id.webview_debai)
    WebView webview_debai;

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i(TAG, "onError: mp3");
        return false;
    }

    private String sAnwser = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_anwser_A:
                if (!isClickXemdiem) {
                    sAnwser = "A";
                    click_anwser(sAnwser);
                    /*anwser();*/
                }

                break;
            case R.id.ll_anwser_B:
                if (!isClickXemdiem) {

                    sAnwser = "B";
                    click_anwser(sAnwser);
                    //anwser();
                }

                break;
            case R.id.ll_anwser_C:
                if (!isClickXemdiem) {

                    sAnwser = "C";
                    click_anwser(sAnwser);
                    // anwser();
                }

                break;
            case R.id.ll_anwser_D:
                if (!isClickXemdiem) {

                    sAnwser = "D";
                    click_anwser(sAnwser);
                    //  anwser();
                }

                break;
            case R.id.img_checkbox_A:
                if (!isClickXemdiem) {
                    sAnwser = "A";
                    click_anwser(sAnwser);
                    /*anwser();*/
                }

                break;
            case R.id.img_checkbox_B:
                if (!isClickXemdiem) {

                    sAnwser = "B";
                    click_anwser(sAnwser);
                    //anwser();
                }

                break;
            case R.id.img_checkbox_C:
                if (!isClickXemdiem) {

                    sAnwser = "C";
                    click_anwser(sAnwser);
                    // anwser();
                }

                break;
            case R.id.img_checkbox_D:
                if (!isClickXemdiem) {

                    sAnwser = "D";
                    click_anwser(sAnwser);
                    //  anwser();
                }

                break;
        }
    }

    private void click_anwser(String sClick) {
        if (!isClickXemdiem) {
            btn_xemdiem.setEnabled(true);
            btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_1));
            switch (sClick) {
                case "A":
                    Glide.with(this).load(R.drawable.ic_checked_blue).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "B":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checked_blue).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "C":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checked_blue).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "D":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checked_blue).into(img_checkbox_D);
                    break;
            }
            anwser();
        }
    }

    private void check_anwser_chil() {
        switch (mCauhoi.getsANSWER_CHILD()) {
            case "A":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_A);
                break;
            case "B":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_B);
                break;
            case "C":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_C);
                break;
            case "D":
                Glide.with(getActivity()).load(R.drawable.ic_checked).into(img_checkbox_D);
                break;
        }
        switch (mCauhoi.getsANSWER()) {
            case "A":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_A);
                break;
            case "B":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_B);
                break;
            case "C":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_C);
                break;
            case "D":
                Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_D);
                break;
        }
    }

    private boolean anwser() {
        if (sAnwser.length() > 0) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnwser);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
            if (sAnwser.equals(mCauhoi.getsANSWER())) {
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
                return true;

            } else {
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
                return false;
            }
        } else showDialogNotify("Thông báo", "Bạn chưa chọn đáp án nào");
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
