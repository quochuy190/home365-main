package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class FragmentDocvaTraloi extends BaseFragment
        implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    WebView txt_cauhoi;
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
    @BindView(R.id.img_bang)
    ImageView img_bang;
    @BindView(R.id.icon_zoom)
    ImageView icon_zoom;
    @BindView(R.id.img_broad_question)
    ImageView img_broad_question;
    @BindView(R.id.rl_show_doanvan)
    ConstraintLayout rl_show_doanvan;

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
    @BindView(R.id.scroll_read_text)
    NestedScrollView scroll_read_text;
    private int current;
    @BindView(R.id.img_reload)
    ImageView img_reload;

    public static FragmentDocvaTraloi newInstance(CauhoiDetail restaurant, int current) {
        FragmentDocvaTraloi restaurantDetailFragment = new FragmentDocvaTraloi();
        Bundle args = new Bundle();
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        args.putInt("current", current);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    private void reload() {
        webview_debai.reload();
        webview_anwser_A.reload();
        webview_anwser_C.reload();
        webview_anwser_D.reload();
        webview_anwser_B.reload();
        txt_cauhoi.reload();
        txt_cauhoi.setWebViewClient(new WebViewClient());
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
        View view = inflater.inflate(R.layout.fragment_docvatraloi, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getActivity()).load(R.drawable.icon_broad).into(img_bang);
        Glide.with(getActivity()).load(R.drawable.icon_broad).into(img_broad_question);
        Glide.with(getActivity()).load(R.drawable.ic_zoom)
                .placeholder(R.drawable.ic_zoom)
                .into(icon_zoom);
        // init();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initLoadImage();
        initData();
        initEvent();
        return view;
    }

    public void initWebview_white_text(final WebView webview, String link_web) {
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
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        webview.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
        webview.getSettings().setJavaScriptEnabled(true);
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;color: #fff;}</style>"
              //  + "<style type=\"text/css\">body{color: #fff;}"
                + "</style></head>"
                + "<body>"
                + "<div>"
                + StringUtil.convert_html(link_web)
                + "</div>"
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
                            case R.id.txt_cauhoi:
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mCauhoi.getsTextDebai() != null)
                                            initWebview_white_text(webview_debai_full, mCauhoi.getsTextDebai());
                                    }
                                }, 1000);
                                break;
                            case R.id.webview_debai_full:

                                break;
                        }
                    }
                }.start();
            }
        });
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
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style>"
                + "</style></head>"
                + "<body>"
                + "<div>"
                + StringUtil.convert_html(link_web)
                + "</div>"
                + "</body></html>";
/*        webview.loadDataWithBaseURL("", text,
                "text/html", "UTF-8", "");*/
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
                                        scroll_read_text.scrollTo(0, 0);
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
        webview.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webview.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
     /*   String pish = "<html><body  align='center'>";
        String pas = "</body></html>";*/
        String text = "<html><head>"
                + "</style></head>"
                + "<body>"
                + "<div>"
                + StringUtil.convert_html(link_web)
                + "</div>"
                + "</body></html>";
        webview.loadDataWithBaseURL("", text,
                "text/html", "UTF-8", "");
       /* webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");*/

    }

    private void initLoadImage() {
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
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
        icon_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_xemdiem.setVisibility(View.INVISIBLE);
                rl_show_doanvan.setVisibility(View.VISIBLE);
                Animation animationRotale = AnimationUtils.loadAnimation(getContext(),
                        R.anim.animation_show_question);
                rl_show_doanvan.startAnimation(animationRotale);
            }
        });
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_1));
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
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gone_question_view(true);
                btn_xemdiem.setVisibility(View.VISIBLE);
            }
        });
    }

    public void gone_question_view(boolean isCheck) {
        if (isCheck) {
            if (rl_show_doanvan.getVisibility() == View.VISIBLE) {
                Animation animationRotale = AnimationUtils.loadAnimation(getContext(),
                        R.anim.animation_show_question_close);
                rl_show_doanvan.startAnimation(animationRotale);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_show_doanvan.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        }
    }

    @BindView(R.id.webview_debai_full)
    WebView webview_debai_full;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    private void initData() {
        if (mCauhoi.getsHTML_CONTENT() != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview_center(webview_debai, mCauhoi.getsHTML_CONTENT());
                }
            });
        }

        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
            ll_webview_A.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
                }
            });
        } else {
            ll_webview_A.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
            ll_webview_B.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
                }
            });
        } else {
            ll_webview_B.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
            ll_webview_C.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
                }
            });
        } else {
            ll_webview_C.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
            ll_webview_D.setVisibility(View.VISIBLE);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview(webview_anwser_D, mCauhoi.getsHTML_D());
                }
            });
        } else {
            ll_webview_D.setVisibility(View.GONE);
        }
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null && mCauhoi.getsPOINT()
                != null && mCauhoi.getsPOINT().length() > 0)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                != null && mCauhoi.getsSubNumberCau().equals("1")) {
            showDialogLoading();
        }
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview_white_text(txt_cauhoi, mCauhoi.getsTextDebai());
            }
        });
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
}
