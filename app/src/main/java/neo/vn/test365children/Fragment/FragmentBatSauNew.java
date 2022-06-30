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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.CauhoiDetail;
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
public class FragmentBatSauNew extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "FragmentBatSauNew";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.ll_cauhoi)
    LinearLayout ll_cauhoi;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
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

    @BindView(R.id.ll_anwser_A)
    RelativeLayout ll_anwser_A;
    @BindView(R.id.ll_anwser_B)
    RelativeLayout ll_anwser_B;
    @BindView(R.id.ll_anwser_C)
    RelativeLayout ll_anwser_C;
    @BindView(R.id.ll_anwser_D)
    RelativeLayout ll_anwser_D;

    @BindView(R.id.img_saucon_A)
    ImageView img_saucon_A;
    @BindView(R.id.img_saucon_B)
    ImageView img_saucon_B;
    @BindView(R.id.img_saucon_C)
    ImageView img_saucon_C;
    @BindView(R.id.img_saucon_D)
    ImageView img_saucon_D;
    private String sAnwser = "";
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.ll_webview_A)
    RelativeLayout ll_webview_A;
    @BindView(R.id.ll_webview_B)
    RelativeLayout ll_webview_B;
    @BindView(R.id.ll_webview_C)
    RelativeLayout ll_webview_C;
    @BindView(R.id.ll_webview_D)
    RelativeLayout ll_webview_D;
    @BindView(R.id.img_la_A)
    ImageView img_la_A;
    @BindView(R.id.img_la_C)
    ImageView img_la_C;
    @BindView(R.id.img_la_B)
    ImageView img_la_B;
    @BindView(R.id.img_la_D)
    ImageView img_la_D;
    @BindView(R.id.img_reload)
    ImageView img_reload;

    public static FragmentBatSauNew newInstance(CauhoiDetail restaurant, int current) {
        FragmentBatSauNew restaurantDetailFragment = new FragmentBatSauNew();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        args.putInt("current", current);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    private int current;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
        current = getArguments().getInt("current");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batsau_new, container, false);
        ButterKnife.bind(this, view);
        int[] arrayImage = {R.drawable.icon_sau, R.drawable.ic_sau_bo,
                R.drawable.ic_butterfly_red, R.drawable.ic_butterfly,
                R.drawable.ic_sau_pink};
        int iRandom = new Random().nextInt(4);
        initImage(arrayImage[iRandom]);
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initEvent();
        return view;
    }

    private void initImage(int iImage) {
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_A);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_C);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_B);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_D);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_A);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_B);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_C);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_D);
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        img_reload.setOnClickListener(this);
        ll_anwser_A.setOnClickListener(this);
        ll_anwser_B.setOnClickListener(this);
        ll_anwser_C.setOnClickListener(this);
        ll_anwser_D.setOnClickListener(this);

        webview_anwser_A.setOnTouchListener(this);
        webview_anwser_B.setOnTouchListener(this);
        webview_anwser_C.setOnTouchListener(this);
        webview_anwser_D.setOnTouchListener(this);
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    img_anwser_chil.setVisibility(View.VISIBLE);
                    boolean isTrue = false;
                    if (isTraloi) {
                        if (sAnwser.equals(mCauhoi.getsANSWER())) {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_true",
                                    Float.parseFloat(mCauhoi.getsPOINT()), 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_saucon_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_saucon_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_saucon_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_saucon_D);
                                    break;
                            }

                        } else {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_false_sau", 0, 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_saucon_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_saucon_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_saucon_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_saucon_D);
                                    break;
                            }

                        }
                    }
                    isClickXemdiem = true;
                    EventBus.getDefault().post(new MessageEvent(Constants.KEY_SAVE_LIST_EXER_PLAYING, 0, 0));
                }
            }
        });
    }

    private void initData() {
        try {
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                    != null && mCauhoi.getsSubNumberCau().equals("1")) {
                showDialogLoading();
            }
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                txt_lable.setText(Html.fromHtml("Bài" + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            Glide.with(getActivity()).load(R.drawable.bg_nghe_nhin).into(img_background);
            initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
                }
            });
            if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
                ll_anwser_A.setVisibility(View.VISIBLE);
            } else {
                ll_anwser_A.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
                ll_anwser_B.setVisibility(View.VISIBLE);
            } else {
                ll_anwser_B.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
                ll_anwser_C.setVisibility(View.VISIBLE);
            } else {
                ll_anwser_C.setVisibility(View.GONE);
            }
            if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
                ll_anwser_D.setVisibility(View.VISIBLE);
            } else {
                ll_anwser_D.setVisibility(View.GONE);
            }
            if (mCauhoi.isDalam()) {
                isClickXemdiem = true;
                sAnwser = mCauhoi.getsANSWER_CHILD();
                anwser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_reload) {
            reload();
        }
        if (!isAnimation) {
            switch (v.getId()) {
                case R.id.ll_anwser_A:
                    if (!isClickXemdiem) {
                        animation_click(img_saucon_A);
                        img_saucon_B.clearAnimation();
                        img_saucon_C.clearAnimation();
                        img_saucon_D.clearAnimation();
                        img_saucon_D.setVisibility(View.VISIBLE);
                        img_saucon_B.setVisibility(View.VISIBLE);
                        img_saucon_C.setVisibility(View.VISIBLE);
                        sAnwser = "A";
                        anwser();
                    }
                    break;
                case R.id.ll_anwser_B:
                    if (!isClickXemdiem) {
                        animation_click(img_saucon_B);
                        img_saucon_A.clearAnimation();
                        img_saucon_C.clearAnimation();
                        img_saucon_D.clearAnimation();
                        img_saucon_D.setVisibility(View.VISIBLE);
                        img_saucon_A.setVisibility(View.VISIBLE);
                        img_saucon_C.setVisibility(View.VISIBLE);
                        sAnwser = "B";
                        anwser();
                    }
                    break;
                case R.id.ll_anwser_C:
                    if (!isClickXemdiem) {
                        animation_click(img_saucon_C);
                        img_saucon_A.clearAnimation();
                        img_saucon_B.clearAnimation();
                        img_saucon_D.clearAnimation();
                        img_saucon_D.setVisibility(View.VISIBLE);
                        img_saucon_B.setVisibility(View.VISIBLE);
                        img_saucon_A.setVisibility(View.VISIBLE);
                        sAnwser = "C";
                        anwser();
                    }
                    break;
                case R.id.ll_anwser_D:
                    if (!isClickXemdiem) {
                        animation_click(img_saucon_D);
                        img_saucon_A.clearAnimation();
                        img_saucon_B.clearAnimation();
                        img_saucon_C.clearAnimation();
                        img_saucon_A.setVisibility(View.VISIBLE);
                        img_saucon_B.setVisibility(View.VISIBLE);
                        img_saucon_C.setVisibility(View.VISIBLE);
                        sAnwser = "D";
                        anwser();
                    }

                    break;
            }
        }
    }

    boolean isAnimation = false;

    private void animation_click(final ImageView img) {
        isAnimation = true;
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau);
        img.startAnimation(animationRotale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img.setVisibility(View.GONE);
                isAnimation = false;
            }
        }, 2000);

    }

    private void animation_anwsertrue(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau_dung);
        img.startAnimation(animationRotale);
    }

    boolean isdouble_click = false;

    private void anwser() {
        try {
            if (!isdouble_click) {
                isdouble_click = true;
                btn_xemdiem.setEnabled(true);
                btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_1));
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                if (sAnwser.length() > 0) {
                    switch (sAnwser) {
                        case "A":
                            if (mCauhoi.getsANSWER().equals("A"))
                                set_anwser("A", true);
                            else
                                set_anwser("A", false);
                            break;
                        case "B":
                            if (mCauhoi.getsANSWER().equals("B"))
                                set_anwser("B", true);
                            else
                                set_anwser("B", false);
                            break;
                        case "C":
                            if (mCauhoi.getsANSWER().equals("C"))
                                set_anwser("C", true);
                            else
                                set_anwser("C", false);
                            break;
                        case "D":
                            if (mCauhoi.getsANSWER().equals("D"))
                                set_anwser("D", true);
                            else
                                set_anwser("D", false);
                            break;
                    }
                }
                isTraloi = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isdouble_click = false;
                    }
                }, 1000);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void set_anwser(String sAnwser, boolean isAnwser) {
        try {
            if (isAnwser) {
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
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnwser);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    int iHeightmax = 0;

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
//        String pish = "<html><body " +
//                "@font-face {\n" +
//                "     font-family: yourfont;\n" +
//                "     src: url(\"file:///android_res/utm_avo.ttf\")\n" +
//                "}" +
//                " align='center'>";
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pas = "</body></html>";
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
                        int i = 0;
                        switch (view.getId()) {
                            case R.id.webview_debai:
                                webview_debai.setVisibility(View.VISIBLE);
                                break;
                            case R.id.webview_anwser_A:
                                webview_anwser_A.setVisibility(View.VISIBLE);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_B:
                                webview_anwser_B.setVisibility(View.VISIBLE);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_C:
                                webview_anwser_C.setVisibility(View.VISIBLE);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_D, mCauhoi.getsHTML_D());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_D:
                                new CountDownTimer(1000, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        webview_anwser_D.setVisibility(View.VISIBLE);
                                        // initShowView();
                                        if (ll_webview_A.getHeight() > iHeightmax) {
                                            iHeightmax = ll_webview_A.getHeight();
                                        }
                                        if (ll_webview_B.getHeight() > iHeightmax) {
                                            iHeightmax = ll_webview_B.getHeight();
                                        }
                                        if (ll_webview_C.getHeight() > iHeightmax) {
                                            iHeightmax = ll_webview_C.getHeight();
                                        }
                                        if (ll_webview_D.getHeight() > iHeightmax) {
                                            iHeightmax = ll_webview_D.getHeight();
                                        }
                                        if (iHeightmax > 0) {
                                            setHeightAll(iHeightmax, ll_webview_A);
                                            setHeightAll(iHeightmax, ll_webview_B);
                                            setHeightAll(iHeightmax, ll_webview_C);
                                            setHeightAll(iHeightmax, ll_webview_D);
                                        }
                                    }
                                }.start();
                                if (mCauhoi.isDalam()) {
                                    set_anwser_playing();
                                }
                                hideDialogLoading();
                                break;
                        }
                    }
                }.start();
            }
        });
    }

    private void set_anwser_playing() {
        sAnwser = mCauhoi.getsANSWER_CHILD();
        img_anwser_chil.setVisibility(View.VISIBLE);
        if (sAnwser.equals(mCauhoi.getsANSWER())) {
            Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
            switch (mCauhoi.getsANSWER()) {
                case "A":
                    animation_anwsertrue(img_saucon_A);
                    break;
                case "B":
                    animation_anwsertrue(img_saucon_B);
                    break;
                case "C":
                    animation_anwsertrue(img_saucon_C);
                    break;
                case "D":
                    animation_anwsertrue(img_saucon_D);
                    break;
            }

        } else {
            Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
            switch (mCauhoi.getsANSWER()) {
                case "A":
                    animation_anwsertrue(img_saucon_A);
                    break;
                case "B":
                    animation_anwsertrue(img_saucon_B);
                    break;
                case "C":
                    animation_anwsertrue(img_saucon_C);
                    break;
                case "D":
                    animation_anwsertrue(img_saucon_D);
                    break;
            }
            switch (mCauhoi.getsANSWER_CHILD()) {
                case "A":
                    img_saucon_A.setVisibility(View.GONE);
                    break;
                case "B":
                    img_saucon_B.setVisibility(View.GONE);
                    break;
                case "C":
                    img_saucon_C.setVisibility(View.GONE);
                    break;
                case "D":
                    img_saucon_D.setVisibility(View.GONE);
                    break;
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

    private void setHeightAll(final int iHeight, final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = iHeight;
                view.setLayoutParams(params);
            }
        });

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
                                animation_click(img_saucon_A);
                                img_saucon_B.clearAnimation();
                                img_saucon_C.clearAnimation();
                                img_saucon_D.clearAnimation();
                                img_saucon_D.setVisibility(View.VISIBLE);
                                img_saucon_B.setVisibility(View.VISIBLE);
                                img_saucon_C.setVisibility(View.VISIBLE);
                                sAnwser = "A";
                                anwser();
                            }

                            break;
                        case R.id.webview_anwser_B:
                            if (!isClickXemdiem) {
                                animation_click(img_saucon_B);
                                img_saucon_A.clearAnimation();
                                img_saucon_C.clearAnimation();
                                img_saucon_D.clearAnimation();
                                img_saucon_D.setVisibility(View.VISIBLE);
                                img_saucon_A.setVisibility(View.VISIBLE);
                                img_saucon_C.setVisibility(View.VISIBLE);
                                sAnwser = "B";
                                anwser();
                            }

                            break;
                        case R.id.webview_anwser_C:
                            if (!isClickXemdiem) {
                                animation_click(img_saucon_C);
                                img_saucon_A.clearAnimation();
                                img_saucon_B.clearAnimation();
                                img_saucon_D.clearAnimation();
                                img_saucon_D.setVisibility(View.VISIBLE);
                                img_saucon_B.setVisibility(View.VISIBLE);
                                img_saucon_A.setVisibility(View.VISIBLE);
                                sAnwser = "C";
                                anwser();
                            }
                            break;
                        case R.id.webview_anwser_D:
                            if (!isClickXemdiem) {
                                animation_click(img_saucon_D);
                                img_saucon_A.clearAnimation();
                                img_saucon_B.clearAnimation();
                                img_saucon_C.clearAnimation();
                                img_saucon_A.setVisibility(View.VISIBLE);
                                img_saucon_B.setVisibility(View.VISIBLE);
                                img_saucon_C.setVisibility(View.VISIBLE);
                                sAnwser = "D";
                                anwser();
                            }
                            break;
                    }
                }
                break;
        }
        return false;
    }
}
