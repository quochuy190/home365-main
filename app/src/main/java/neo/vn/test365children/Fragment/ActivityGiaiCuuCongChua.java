package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;


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
public class ActivityGiaiCuuCongChua extends BaseActivity
        implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "ActivityGiaiCuuCongChua";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.imageView3)
    ImageView img_background;
    @BindView(R.id.webview_debai)
    WebView webview_debai;

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
    private String sAnwser = "";
    @BindView(R.id.img_reload)
    ImageView img_reload;
    int[] arr_image = {R.drawable.bg_congchua1, R.drawable.bg_congchua2, R.drawable.bg_congchua3,
            R.drawable.bg_congchua4, R.drawable.bg_congchua6};
    int iRandom;

    @Override
    public int setContentViewId() {
        return R.layout.activity_cauhoi_congchua;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = SharedPrefs.getInstance().get(Constants.KEY_SEND_CAUHOI_CONGCHUA, CauhoiDetail.class);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
        Random ran = new Random();
        iRandom = ran.nextInt(arr_image.length);
        Glide.with(this).load(arr_image[iRandom]).into(img_background);
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private boolean isClickXemdiem = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("close_princess")) {
            if (sAnwser != null && sAnwser.equals(mCauhoi.getsANSWER())) {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, true);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            } else {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            }
        }
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
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    anwser();
                }
            }
        });
    }

    private void initData() {
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                showDialogLoading();
                initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
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
    }

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

    boolean isAnimation = false;

    private void click_anwser(String sClick) {
        if (!isClickXemdiem) {
            btn_xemdiem.setEnabled(true);
            btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_1));
            switch (sClick) {
                case "A":
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "B":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "C":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "D":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_D);
                    break;
            }

        }
    }

    private void anwser() {
        if (sAnwser.length() > 0) {
            if (sAnwser.equals(mCauhoi.getsANSWER())) {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, true);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            } else {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            }
        } else showDialogNotify("Thông báo", "Bạn chưa chọn đáp án nào");
        isClickXemdiem = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview_anwser_A.clearHistory();
        webview_anwser_A.clearFormData();
        webview_anwser_A.clearCache(true);
        webview_anwser_B.clearHistory();
        webview_anwser_B.clearFormData();
        webview_anwser_B.clearCache(true);
        webview_anwser_C.clearHistory();
        webview_anwser_C.clearFormData();
        webview_anwser_C.clearCache(true);
        webview_anwser_D.clearHistory();
        webview_anwser_D.clearFormData();
        webview_anwser_D.clearCache(true);
        webview_debai.clearHistory();
        webview_debai.clearFormData();
        webview_debai.clearCache(true);
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
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
               // + "<style type=\"text/css\">body{color: #fff;}"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;color: #fff;}</style>"
                + "</style></head>"
                + "<body>"
                + StringUtil.convert_html(link_web)
                + "</body></html>";
        webview.loadDataWithBaseURL("", text,
                "text/html", "UTF-8", "");
       /* webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");*/
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
    public void initWebview_center(final WebView webview, String link_web) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webview.setBackgroundColor(Color.TRANSPARENT);
        webview.clearSslPreferences();
        webview.clearFormData();
        webview.clearCache(true);
        webview.clearHistory();
        webview.clearMatches();
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        //    webSettings .setMediaPlaybackRequiresUserGesture(false);
        webview.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
        // webSettings.setUseWideViewPort(true);
        //  webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.1));
        //  webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        //  webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
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
        webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (!isAnimation) {
                    switch (v.getId()) {
                        case R.id.webview_anwser_A:
                            if (!isClickXemdiem) {

                                sAnwser = "A";
                                click_anwser(sAnwser);
                                //  anwser();
                            }

                            break;
                        case R.id.webview_anwser_B:
                            if (!isClickXemdiem) {
                                sAnwser = "B";
                                click_anwser(sAnwser);
                                //    anwser();
                            }
                            break;
                        case R.id.webview_anwser_C:
                            if (!isClickXemdiem) {

                                sAnwser = "C";
                                click_anwser(sAnwser);
                                // anwser();
                            }
                            break;
                        case R.id.webview_anwser_D:
                            if (!isClickXemdiem) {
                                sAnwser = "D";
                                click_anwser(sAnwser);
                                //anwser();
                            }
                            break;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}
