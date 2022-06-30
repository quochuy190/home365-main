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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class FragmentChemchuoi extends BaseFragment implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.ll_cauhoi)
    LinearLayout ll_cauhoi;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
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
    LinearLayout ll_anwser_A;
    @BindView(R.id.ll_anwser_B)
    LinearLayout ll_anwser_B;
    @BindView(R.id.ll_anwser_C)
    LinearLayout ll_anwser_C;
    @BindView(R.id.ll_anwser_D)
    LinearLayout ll_anwser_D;
    @BindView(R.id.txt_dapan_D)
    RelativeLayout rl_dapan_D;
    @BindView(R.id.txt_dapan_B)
    RelativeLayout rl_dapan_B;
    @BindView(R.id.txt_dapan_C)
    RelativeLayout rl_dapan_C;
    @BindView(R.id.txt_dapan_A)
    RelativeLayout rl_dapan_A;
    @BindView(R.id.img_hoaqua_D)
    ImageView img_hoaqua_D;
    @BindView(R.id.img_hoaqua_C)
    ImageView img_hoaqua_C;
    @BindView(R.id.img_hoaqua_B)
    ImageView img_hoaqua_B;
    @BindView(R.id.img_hoaqua_A)
    ImageView img_hoaqua_A;
    private String sAnwser = "";
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.img_reload)
    ImageView img_reload;

    public static FragmentChemchuoi newInstance(CauhoiDetail restaurant, int current) {
        FragmentChemchuoi restaurantDetailFragment = new FragmentChemchuoi();
        Bundle args = new Bundle();
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        args.putInt("current", current);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    private int current;

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
        View view = inflater.inflate(R.layout.fragment_chemchuoi, container, false);
        ButterKnife.bind(this, view);
        initImage();
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.setBackground(getResources().getDrawable(R.drawable.btn_gray_black));
        initEvent();
        return view;
    }

    private void initImage() {
        Glide.with(getContext()).load(R.drawable.fruit_banana)
                .placeholder(R.drawable.fruit_banana).into(img_hoaqua_A);
        Glide.with(getContext()).load(R.drawable.fruit_berry)
                .placeholder(R.drawable.fruit_banana).into(img_hoaqua_B);
        Glide.with(getContext()).load(R.drawable.fruit_coconut)
                .placeholder(R.drawable.fruit_banana).into(img_hoaqua_C);
        Glide.with(getContext()).load(R.drawable.fruit_pineapple)
                .placeholder(R.drawable.fruit_banana).into(img_hoaqua_D);
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
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_hoaqua_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_hoaqua_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_hoaqua_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_hoaqua_D);
                                    break;
                            }
                        } else {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_hoaqua_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_hoaqua_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_hoaqua_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_hoaqua_D);
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
                txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            Glide.with(getActivity()).load(R.drawable.bg_chem_hoa_qua).into(img_background);
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
        switch (v.getId()) {
            case R.id.ll_anwser_A:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_A);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "A";
                    anwser();
                }
                break;
            case R.id.ll_anwser_B:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_B);
                    img_hoaqua_A.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "B";
                    anwser();
                }
                break;
            case R.id.ll_anwser_C:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_C);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_A.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "C";
                    anwser();
                }
                break;
            case R.id.ll_anwser_D:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_D);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_A.clearAnimation();
                    sAnwser = "D";
                    anwser();
                }
                break;
        }
    }

    private void set_anwser_playing() {
        try {
            sAnwser = mCauhoi.getsANSWER_CHILD();
            img_anwser_chil.setVisibility(View.VISIBLE);
            if (sAnwser.equals(mCauhoi.getsANSWER())) {
                Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                switch (mCauhoi.getsANSWER()) {
                    case "A":
                        animation_anwsertrue(img_hoaqua_A);
                        break;
                    case "B":
                        animation_anwsertrue(img_hoaqua_B);
                        break;
                    case "C":
                        animation_anwsertrue(img_hoaqua_C);
                        break;
                    case "D":
                        animation_anwsertrue(img_hoaqua_D);
                        break;
                }

            } else {
                Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                switch (mCauhoi.getsANSWER()) {
                    case "A":
                        animation_anwsertrue(img_hoaqua_A);
                        break;
                    case "B":
                        animation_anwsertrue(img_hoaqua_B);
                        break;
                    case "C":
                        animation_anwsertrue(img_hoaqua_C);
                        break;
                    case "D":
                        animation_anwsertrue(img_hoaqua_D);
                        break;
                }
                switch (mCauhoi.getsANSWER_CHILD()) {
                    case "A":
                        animation_click(img_hoaqua_A);
                        break;
                    case "B":
                        animation_click(img_hoaqua_B);
                        break;
                    case "C":
                        animation_click(img_hoaqua_C);
                        break;
                    case "D":
                        animation_click(img_hoaqua_D);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void animation_click(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_chemchuoi);
        img.startAnimation(animationRotale);
    }

    private void animation_anwsertrue(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau_dung);
        img.startAnimation(animationRotale);
    }

    boolean isdouble_click = false;

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

    private void anwser() {
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
    }

    private void set_anwser(String sAnwser, boolean isAnwser) {
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
//        String pish = "<html><body  align='center'>";
//        String font = "<style type=\\\"text/css\\\">@font-face {font-family: MyFont;src: url(\\\"file:///android_asset/utm_avo.ttf\\\")}";
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
                                break;
                            case R.id.webview_anwser_A:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_B:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_C:
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
                                        if (rl_dapan_A.getHeight() > iHeightmax) {
                                            iHeightmax = rl_dapan_A.getHeight();
                                        }
                                        if (rl_dapan_B.getHeight() > iHeightmax) {
                                            iHeightmax = rl_dapan_B.getHeight();
                                        }
                                        if (rl_dapan_C.getHeight() > iHeightmax) {
                                            iHeightmax = rl_dapan_C.getHeight();
                                        }
                                        if (rl_dapan_D.getHeight() > iHeightmax) {
                                            iHeightmax = rl_dapan_D.getHeight();
                                        }
                                        if (iHeightmax > 0) {
                                            setHeightAll(iHeightmax, rl_dapan_A);
                                            setHeightAll(iHeightmax, rl_dapan_B);
                                            setHeightAll(iHeightmax, rl_dapan_C);
                                            setHeightAll(iHeightmax, rl_dapan_D);
                                        }
                                        //reload();
                                        if (mCauhoi.isDalam()) {
                                            set_anwser_playing();
                                        }
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

    int iHeightmax = 0;

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
                switch (v.getId()) {
                    case R.id.webview_anwser_A:
                        if (!isClickXemdiem) {
                            animation_click(img_hoaqua_A);
                            img_hoaqua_B.clearAnimation();
                            img_hoaqua_C.clearAnimation();
                            img_hoaqua_D.clearAnimation();
                            sAnwser = "A";
                            anwser();
                        }
                        break;
                    case R.id.webview_anwser_B:
                        if (!isClickXemdiem) {
                            animation_click(img_hoaqua_B);
                            img_hoaqua_A.clearAnimation();
                            img_hoaqua_C.clearAnimation();
                            img_hoaqua_D.clearAnimation();
                            sAnwser = "B";
                            anwser();
                        }
                        break;
                    case R.id.webview_anwser_C:
                        if (!isClickXemdiem) {
                            animation_click(img_hoaqua_C);
                            img_hoaqua_B.clearAnimation();
                            img_hoaqua_A.clearAnimation();
                            img_hoaqua_D.clearAnimation();
                            sAnwser = "C";
                            anwser();
                        }
                        break;
                    case R.id.webview_anwser_D:
                        if (!isClickXemdiem) {
                            animation_click(img_hoaqua_D);
                            img_hoaqua_B.clearAnimation();
                            img_hoaqua_C.clearAnimation();
                            img_hoaqua_A.clearAnimation();
                            sAnwser = "D";
                            anwser();
                        }
                        break;
                }
                break;
        }
        return false;
    }
}
