package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
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
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
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
public class FragmentChondapanDung extends BaseFragment
        implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    List<DapAn> mLis;
    AdapterDapan adapter;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.webview_debai)
    WebView webview_debai;
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
    private int current;

    public static FragmentChondapanDung newInstance(CauhoiDetail restaurant, int current) {
        FragmentChondapanDung restaurantDetailFragment = new FragmentChondapanDung();
        Bundle args = new Bundle();
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        args.putInt("current", current);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
        current = getArguments().getInt("current");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chondapandung, container, false);
        ButterKnife.bind(this, view);
        initLoadImage();
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

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
    }

    private void initLoadImage() {
        Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_A);
        Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_B);
        Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_C);
        Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_D);
    }

    private void initData() {
        try {
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                    != null && mCauhoi.getsSubNumberCau().equals("1")) {
                showDialogLoading();
            }
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            Glide.with(getActivity()).load(R.drawable.bg_nghe_nhin).into(img_background);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview_center(webview_debai, mCauhoi.getsHTML_CONTENT());
                    // initWebview_center(webview_debai, sHtml);
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
        //String pish = "<html><body  align='center'>";
     //   String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
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
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium}</style>"
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

    int iHeightmax = 0;

    private boolean anwser() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
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

                break;
        }
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
                    Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_A);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "B":
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_B);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "C":
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_C);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "D":
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(getActivity()).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(getActivity()).load(R.drawable.ic_checked_blue).into(img_checkbox_D);
                    break;
            }
            anwser();
        }
    }
}
