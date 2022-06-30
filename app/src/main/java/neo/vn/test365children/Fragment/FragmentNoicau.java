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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAnNoicau;
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
public class FragmentNoicau extends BaseFragment implements View.OnTouchListener {
    private static final String TAG = "FragmentNoicau";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    List<DapAnNoicau> mLisDapanA, mLisDapanB;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    @BindView(R.id.webview_dapannoicau_A_1)
    WebView webview_dapannoicau_A_1;
    @BindView(R.id.webview_dapannoicau_A_traloi_1)
    WebView webview_dapannoicau_A_traloi_1;
    @BindView(R.id.webview_dapannoicau_A_2)
    WebView webview_dapannoicau_A_2;
    @BindView(R.id.webview_dapannoicau_A_traloi_2)
    WebView webview_dapannoicau_A_traloi_2;
    @BindView(R.id.webview_dapannoicau_A_3)
    WebView webview_dapannoicau_A_3;
    @BindView(R.id.webview_dapannoicau_A_traloi_3)
    WebView webview_dapannoicau_A_traloi_3;
    @BindView(R.id.webview_dapannoicau_A_traloi_4)
    WebView webview_dapannoicau_A_traloi_4;
    @BindView(R.id.webview_dapannoicau_A_4)
    WebView webview_dapannoicau_A_4;
    @BindView(R.id.rl_dapanA_1)
    RelativeLayout rl_dapanA_1;
    @BindView(R.id.rl_dapanA_2)
    RelativeLayout rl_dapanA_2;
    @BindView(R.id.rl_dapanA_3)
    RelativeLayout rl_dapanA_3;
    @BindView(R.id.rl_dapanA_4)
    RelativeLayout rl_dapanA_4;

    @BindView(R.id.rl_dapanA_traloi_1)
    RelativeLayout rl_dapanA_traloi_1;
    @BindView(R.id.rl_dapanA_traloi_2)
    RelativeLayout rl_dapanA_traloi_2;
    @BindView(R.id.rl_dapanA_traloi_3)
    RelativeLayout rl_dapanA_traloi_3;
    @BindView(R.id.rl_dapanA_traloi_4)
    RelativeLayout rl_dapanA_traloi_4;

    @BindView(R.id.img_dapan_A_1)
    ImageView img_dapan_A_1;
    @BindView(R.id.img_dapan_A_2)
    ImageView img_dapan_A_2;
    @BindView(R.id.img_dapan_A_3)
    ImageView img_dapan_A_3;
    @BindView(R.id.img_dapan_A_4)
    ImageView img_dapan_A_4;

    @BindView(R.id.webview_dapannoicau_B_1)
    WebView webview_dapannoicau_B_1;
    @BindView(R.id.webview_dapannoicau_B_2)
    WebView webview_dapannoicau_B_2;
    @BindView(R.id.webview_dapannoicau_B_3)
    WebView webview_dapannoicau_B_3;
    @BindView(R.id.webview_dapannoicau_B_4)
    WebView webview_dapannoicau_B_4;

    @BindView(R.id.webview_dapannoicau_B_traloi_1)
    WebView webview_dapannoicau_B_traloi_1;
    @BindView(R.id.webview_dapannoicau_B_traloi_2)
    WebView webview_dapannoicau_B_traloi_2;
    @BindView(R.id.webview_dapannoicau_B_traloi_3)
    WebView webview_dapannoicau_B_traloi_3;
    @BindView(R.id.webview_dapannoicau_B_traloi_4)
    WebView webview_dapannoicau_B_traloi_4;

    @BindView(R.id.rl_dapanB_1)
    RelativeLayout rl_dapanB_1;
    @BindView(R.id.rl_dapanB_2)
    RelativeLayout rl_dapanB_2;
    @BindView(R.id.rl_dapanB_3)
    RelativeLayout rl_dapanB_3;
    @BindView(R.id.rl_dapanB_4)
    RelativeLayout rl_dapanB_4;

    @BindView(R.id.rl_dapanB_traloi_1)
    RelativeLayout rl_dapanB_traloi_1;
    @BindView(R.id.rl_dapanB_traloi_2)
    RelativeLayout rl_dapanB_traloi_2;
    @BindView(R.id.rl_dapanB_traloi_3)
    RelativeLayout rl_dapanB_traloi_3;
    @BindView(R.id.rl_dapanB_traloi_4)
    RelativeLayout rl_dapanB_traloi_4;

    @BindView(R.id.img_dapan_B_1)
    ImageView img_dapan_B_1;
    @BindView(R.id.img_dapan_B_2)
    ImageView img_dapan_B_2;
    @BindView(R.id.img_dapan_B_3)
    ImageView img_dapan_B_3;
    @BindView(R.id.img_dapan_B_4)
    ImageView img_dapan_B_4;
    boolean isDapanA_1 = false, isDapanA_2 = false, isDapanA_3 = false, isDapanA_4 = false;
    boolean isDangchon = false;
    String sDangchon = "";
    Map<String, String> map_answer_chil;
    Map<String, String> map_answer_true;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    @BindView(R.id.text_lable_dapan)
    TextView text_lable_dapan;
    @BindView(R.id.ll_dapan_traloi)
    LinearLayout ll_dapan_traloi;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.img_background)
    ImageView img_background;
    String[] arayClickA1 = {"", ""};
    String[] arayClickA2 = {"", ""};
    String[] arayClickA3 = {"", ""};
    String[] arayClickA4 = {"", ""};
    @BindView(R.id.img_reload)
    ImageView img_reload;

    public static FragmentNoicau newInstance(CauhoiDetail restaurant) {
        FragmentNoicau restaurantDetailFragment = new FragmentNoicau();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
        map_answer_chil = new LinkedHashMap<>();
        map_answer_true = new LinkedHashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noicau_all, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        Glide.with(getContext()).load(R.drawable.bg_doc_hieu).into(img_background);

        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview_dapannoicau_A_1.reload();
                webview_dapannoicau_A_2.reload();
                webview_dapannoicau_A_3.reload();
                webview_dapannoicau_A_4.reload();
                webview_dapannoicau_B_1.reload();
                webview_dapannoicau_B_2.reload();
                webview_dapannoicau_B_3.reload();
                webview_dapannoicau_B_4.reload();

                webview_dapannoicau_A_1.setWebViewClient(new WebViewClient());
                webview_dapannoicau_A_2.setWebViewClient(new WebViewClient());
                webview_dapannoicau_A_3.setWebViewClient(new WebViewClient());
                webview_dapannoicau_A_4.setWebViewClient(new WebViewClient());
                webview_dapannoicau_B_1.setWebViewClient(new WebViewClient());
                webview_dapannoicau_B_2.setWebViewClient(new WebViewClient());
                webview_dapannoicau_B_3.setWebViewClient(new WebViewClient());
                webview_dapannoicau_B_4.setWebViewClient(new WebViewClient());
            }
        });
        webview_dapannoicau_A_1.setOnTouchListener(this);
        webview_dapannoicau_A_2.setOnTouchListener(this);
        webview_dapannoicau_A_3.setOnTouchListener(this);
        webview_dapannoicau_A_4.setOnTouchListener(this);
        webview_dapannoicau_B_1.setOnTouchListener(this);
        webview_dapannoicau_B_2.setOnTouchListener(this);
        webview_dapannoicau_B_3.setOnTouchListener(this);
        webview_dapannoicau_B_4.setOnTouchListener(this);

        webview_dapannoicau_A_traloi_1.setOnTouchListener(this);
        webview_dapannoicau_A_traloi_2.setOnTouchListener(this);
        webview_dapannoicau_A_traloi_3.setOnTouchListener(this);
        webview_dapannoicau_A_traloi_4.setOnTouchListener(this);
        webview_dapannoicau_B_traloi_1.setOnTouchListener(this);
        webview_dapannoicau_B_traloi_2.setOnTouchListener(this);
        webview_dapannoicau_B_traloi_3.setOnTouchListener(this);
        webview_dapannoicau_B_traloi_4.setOnTouchListener(this);


        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sPoint = 0;
                if (!isClickXemdiem) {
                    img_anwser_chil.setVisibility(View.VISIBLE);
                    if (App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).isAnserTrue()) {
                        Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                        EventBus.getDefault().post(new MessageEvent("Point_true",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    } else {
                        initTraloi();
                        text_lable_dapan.setVisibility(View.VISIBLE);
                        ll_dapan_traloi.setVisibility(View.VISIBLE);
                        CauhoiDetail obj = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1)
                                .getLisInfo().get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1);
                        float sTotalPoint = Float.parseFloat(obj.getsPOINT());
                        if (obj.getsHTML_A().equals(obj.getsEGG_1_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_B().equals(obj.getsEGG_2_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_C().equals(obj.getsEGG_3_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_D().equals(obj.getsEGG_4_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                        if (sPoint > 0) {
                            EventBus.getDefault().post(new MessageEvent("Point_false", sPoint, 0));
                        } else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                    EventBus.getDefault().post(new MessageEvent(Constants.KEY_SAVE_LIST_EXER_PLAYING, 0, 0));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview_dapannoicau_A_1.clearFormData();
        webview_dapannoicau_A_1.clearCache(true);
        webview_dapannoicau_A_1.clearHistory();
        webview_dapannoicau_A_2.clearFormData();
        webview_dapannoicau_A_2.clearCache(true);
        webview_dapannoicau_A_2.clearHistory();
        webview_dapannoicau_A_3.clearFormData();
        webview_dapannoicau_A_3.clearCache(true);
        webview_dapannoicau_A_3.clearHistory();
        webview_dapannoicau_A_4.clearFormData();
        webview_dapannoicau_A_4.clearCache(true);
        webview_dapannoicau_A_4.clearHistory();
        webview_dapannoicau_B_4.clearFormData();
        webview_dapannoicau_B_4.clearCache(true);
        webview_dapannoicau_B_4.clearHistory();
        webview_dapannoicau_B_3.clearFormData();
        webview_dapannoicau_B_3.clearCache(true);
        webview_dapannoicau_B_3.clearHistory();
        webview_dapannoicau_B_2.clearFormData();
        webview_dapannoicau_B_2.clearCache(true);
        webview_dapannoicau_B_2.clearHistory();
        webview_dapannoicau_B_1.clearFormData();
        webview_dapannoicau_B_1.clearCache(true);
        webview_dapannoicau_B_1.clearHistory();
    }

    public void check_anwser() {
        boolean isEgg1 = false, isEgg2 = false, isEgg3 = false, isEgg4 = false;
        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
        if (map_answer_chil.get("egg_1") != null) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                    .setsEGG_1_RESULT(map_answer_chil.get("egg_1"));
            if (map_answer_chil.get("egg_1").equals(map_answer_true.get("egg_1"))) {
                isEgg1 = true;
            }
        }
        if (map_answer_chil.get("egg_2") != null) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                    .setsEGG_2_RESULT(map_answer_chil.get("egg_2"));
            if (map_answer_chil.get("egg_2").equals(map_answer_true.get("egg_2"))) {
                isEgg2 = true;
            }
        }
        if (map_answer_chil.get("egg_3") != null) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                    .setsEGG_3_RESULT(map_answer_chil.get("egg_3"));
            if (map_answer_chil.get("egg_3").equals(map_answer_true.get("egg_3"))) {
                isEgg3 = true;
            }
        }
        if (map_answer_chil.get("egg_4") != null) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                    .setsEGG_4_RESULT(map_answer_chil.get("egg_4"));
            String s = map_answer_chil.get("egg_4");
            if (map_answer_chil.get("egg_4").equals(map_answer_true.get("egg_4"))) {
                isEgg4 = true;
            }
        }
        if (isEgg1 && isEgg2 && isEgg3 && isEgg4) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
        } else {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
            String sAnswerChil = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_1_RESULT() + "#*#" +
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_2_RESULT() + "#*#" +
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_3_RESULT() + "#*#" +
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_4_RESULT();
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnswerChil);
        }
    }

    List<String> mLisAnwser_A;
    List<String> mLisAnwser_B;
    List<String> mLisAnwser_B_traloi;
    List<String> mLisAnwser_A_traloi;
    String[] egg1, egg2, egg3, egg4;

    private void initData() {
        try {
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                    != null && mCauhoi.getsSubNumberCau().equals("1")) {
                showDialogLoading();
            }
            text_lable_dapan.setVisibility(View.GONE);
            ll_dapan_traloi.setVisibility(View.GONE);
            mLisDapanB = new ArrayList<>();
            mLisDapanA = new ArrayList<>();
            mLisAnwser_A = new ArrayList<>();
            mLisAnwser_B = new ArrayList<>();
            mLisAnwser_A_traloi = new ArrayList<>();
            mLisAnwser_B_traloi = new ArrayList<>();
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            if (!mCauhoi.isDalam()) {
                if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
                    egg1 = mCauhoi.getsHTML_A().split("::");
                    map_answer_true.put("egg_1", mCauhoi.getsHTML_A());
                }
                if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
                    egg2 = mCauhoi.getsHTML_B().split("::");
                    map_answer_true.put("egg_2", mCauhoi.getsHTML_B());
                }
                if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
                    egg3 = mCauhoi.getsHTML_C().split("::");
                    map_answer_true.put("egg_3", mCauhoi.getsHTML_C());
                }
                if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
                    egg4 = mCauhoi.getsHTML_D().split("::");
                    map_answer_true.put("egg_4", mCauhoi.getsHTML_D());
                }
                if (egg1 != null) {
                    mLisAnwser_A.add(egg1[0]);
                    mLisAnwser_B.add(egg1[1]);
                    mLisAnwser_B_traloi.add(egg1[1]);
                }
                if (egg2 != null) {
                    mLisAnwser_A.add(egg2[0]);
                    mLisAnwser_B.add(egg2[1]);
                    mLisAnwser_B_traloi.add(egg2[1]);
                }
                if (egg3 != null) {
                    mLisAnwser_A.add(egg3[0]);
                    mLisAnwser_B.add(egg3[1]);
                    mLisAnwser_B_traloi.add(egg3[1]);
                }
                if (egg4 != null) {
                    mLisAnwser_A.add(egg4[0]);
                    mLisAnwser_B.add(egg4[1]);
                    mLisAnwser_B_traloi.add(egg4[1]);
                }
                //  initTraloi();
                if (mLisAnwser_B.size() > 0)
                    Collections.shuffle(mLisAnwser_B);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (mLisAnwser_A.size()>0&&mLisAnwser_A.get(0) != null)
                            initWebview(webview_dapannoicau_A_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
                    }
                });
            } else {
                String[] egg1 = mCauhoi.getsHTML_A().split("::");
                String[] egg2 = mCauhoi.getsHTML_B().split("::");
                String[] egg3 = mCauhoi.getsHTML_C().split("::");
                String[] egg4 = mCauhoi.getsHTML_D().split("::");

                String[] egg1_chil = mCauhoi.getsEGG_1_RESULT().split("::");
                String[] egg2_chil = mCauhoi.getsEGG_2_RESULT().split("::");
                String[] egg3_chil = mCauhoi.getsEGG_3_RESULT().split("::");
                String[] egg4_chil = mCauhoi.getsEGG_4_RESULT().split("::");
                if ((egg1_chil[0] != null)) {
                    mLisAnwser_A.add(egg1_chil[0]);
                } else {
                    mLisAnwser_A.add("");
                }
                if ((egg2_chil[0] != null)) {
                    mLisAnwser_A.add(egg2_chil[0]);
                } else {
                    mLisAnwser_A.add("");
                }
                if ((egg3_chil[0] != null)) {
                    mLisAnwser_A.add(egg3_chil[0]);
                } else {
                    mLisAnwser_A.add("");
                }
                if ((egg4_chil[0] != null)) {
                    mLisAnwser_A.add(egg4_chil[0]);
                } else {
                    mLisAnwser_A.add("");
                }

                if ((egg1_chil.length > 1) && (egg1_chil[1] != null)) {
                    mLisAnwser_B.add(egg1_chil[1]);
                } else {
                    mLisAnwser_B.add("");
                }
                if ((egg2_chil.length > 1) && (egg2_chil[1] != null)) {
                    mLisAnwser_B.add(egg2_chil[1]);
                } else {
                    mLisAnwser_B.add("");
                }
                if ((egg3_chil.length > 1) && (egg3_chil[1] != null)) {
                    mLisAnwser_B.add(egg3_chil[1]);
                } else {
                    mLisAnwser_B.add("");
                }
                if ((egg4_chil.length > 1) && (egg4_chil[1] != null)) {
                    mLisAnwser_B.add(egg4_chil[1]);
                } else {
                    mLisAnwser_B.add("");
                }
                if ((egg1.length > 0) && (egg1[0] != null)) {
                    mLisAnwser_A_traloi.add(egg1[0]);
                } else {
                    mLisAnwser_A_traloi.add("");
                }
                if ((egg2.length > 0) && (egg2[0] != null)) {
                    mLisAnwser_A_traloi.add(egg2[0]);
                } else {
                    mLisAnwser_A_traloi.add("");
                }
                if ((egg3.length > 0) && (egg3[0] != null)) {
                    mLisAnwser_A_traloi.add(egg3[0]);
                } else {
                    mLisAnwser_A_traloi.add("");
                }
                if ((egg4.length > 0) && (egg4[0] != null)) {
                    mLisAnwser_A_traloi.add(egg4[0]);
                } else {
                    mLisAnwser_A_traloi.add("");
                }
                if ((egg1.length > 1) && (egg1[1] != null)) {
                    mLisAnwser_B_traloi.add(egg1[1]);
                } else {
                    mLisAnwser_B_traloi.add("");
                }
                if ((egg2.length > 1) && (egg2[1] != null)) {
                    mLisAnwser_B_traloi.add(egg2[1]);
                } else {
                    mLisAnwser_B_traloi.add("");
                }
                if ((egg3.length > 1) && (egg3[1] != null)) {
                    mLisAnwser_B_traloi.add(egg3[1]);
                } else {
                    mLisAnwser_B_traloi.add("");
                }
                if ((egg4.length > 1) && (egg4[1] != null)) {
                    mLisAnwser_B_traloi.add(egg4[1]);
                } else {
                    mLisAnwser_B_traloi.add("");
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        initWebview(webview_dapannoicau_A_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
                    }
                });
                rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

                rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

                rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));

                rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
            }
            if (mCauhoi.isDalam()) {
                img_anwser_chil.setVisibility(View.VISIBLE);
                if (mCauhoi.isAnserTrue()) {
                    Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                } else {
                    initTraloi_chil();
                    Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                    text_lable_dapan.setVisibility(View.VISIBLE);
                    ll_dapan_traloi.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void initWebview(WebView webview_debai, String link_web) {
        webview_debai.getSettings();
        webview_debai.clearHistory();
        webview_debai.clearFormData();
        webview_debai.clearCache(true);
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/utm_avo.ttf\")}body {font-family: MyFont;font-size: medium;text-align: center;}</style></head><body>";
        String pas = "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");
        webview_debai.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        try {
                            int i = 0;
                            switch (view.getId()) {
                                case R.id.webview_dapannoicau_A_1:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_B_1,
                                                    StringUtil.convert_html(mLisAnwser_B.get(0)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_A_2:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_B_2,
                                                    StringUtil.convert_html(mLisAnwser_B.get(1)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_A_3:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_B_3,
                                                    StringUtil.convert_html(mLisAnwser_B.get(2)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_A_4:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_B_4,
                                                    StringUtil.convert_html(mLisAnwser_B.get(3)));
                                        }
                                    });

                                    break;
                                case R.id.webview_dapannoicau_B_1:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_A_2,
                                                    StringUtil.convert_html(mLisAnwser_A.get(1)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_B_2:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_A_3,
                                                    StringUtil.convert_html(mLisAnwser_A.get(2)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_B_3:
                                    new Handler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initWebview(webview_dapannoicau_A_4,
                                                    StringUtil.convert_html(mLisAnwser_A.get(3)));
                                        }
                                    });
                                    break;
                                case R.id.webview_dapannoicau_B_4:
                                    if (rl_dapanA_1.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanA_1.getHeight();
                                    }
                                    if (rl_dapanA_2.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanA_2.getHeight();
                                    }
                                    if (rl_dapanA_3.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanA_3.getHeight();
                                    }
                                    if (rl_dapanA_4.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanA_4.getHeight();
                                    }
                                    if (rl_dapanB_1.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanB_1.getHeight();
                                    }
                                    if (rl_dapanB_2.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanB_2.getHeight();
                                    }
                                    if (rl_dapanB_3.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanB_3.getHeight();
                                    }
                                    if (rl_dapanB_4.getHeight() > iHeightmax) {
                                        iHeightmax = rl_dapanB_4.getHeight();
                                    }
                                    if (iHeightmax > 0) {
                                        setHeight(iHeightmax);
                                    }
                                    hideDialogLoading();
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
    }

    public void setHeight(int iHeight) {
        setHeightAll(iHeight, rl_dapanA_1);
        setHeightAll(iHeight, rl_dapanA_2);
        setHeightAll(iHeight, rl_dapanA_3);
        setHeightAll(iHeight, rl_dapanA_4);
        setHeightAll(iHeight, rl_dapanB_1);
        setHeightAll(iHeight, rl_dapanB_2);
        setHeightAll(iHeight, rl_dapanB_3);
        setHeightAll(iHeight, rl_dapanB_4);

        setHeightAll(iHeight, rl_dapanA_traloi_1);
        setHeightAll(iHeight, rl_dapanA_traloi_2);
        setHeightAll(iHeight, rl_dapanA_traloi_3);
        setHeightAll(iHeight, rl_dapanA_traloi_4);
        setHeightAll(iHeight, rl_dapanB_traloi_4);
        setHeightAll(iHeight, rl_dapanB_traloi_3);
        setHeightAll(iHeight, rl_dapanB_traloi_2);
        setHeightAll(iHeight, rl_dapanB_traloi_1);
    }

    private void initTraloi() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_2, StringUtil.convert_html(mLisAnwser_A.get(1)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_3, StringUtil.convert_html(mLisAnwser_A.get(2)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_4, StringUtil.convert_html(mLisAnwser_A.get(3)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_1, StringUtil.convert_html(mLisAnwser_B_traloi.get(0)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                initWebview(webview_dapannoicau_B_traloi_2, StringUtil.convert_html(mLisAnwser_B_traloi.get(1)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_3, StringUtil.convert_html(mLisAnwser_B_traloi.get(2)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_4, StringUtil.convert_html(mLisAnwser_B_traloi.get(3)));
            }
        });
        rl_dapanA_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        rl_dapanB_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

        rl_dapanA_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        rl_dapanB_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

        rl_dapanA_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
        rl_dapanB_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));

        rl_dapanA_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
        rl_dapanB_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
    }

    private void initTraloi_chil() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_1, StringUtil.convert_html(mLisAnwser_A_traloi.get(0)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_2, StringUtil.convert_html(mLisAnwser_A_traloi.get(1)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_3, StringUtil.convert_html(mLisAnwser_A_traloi.get(2)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_A_traloi_4, StringUtil.convert_html(mLisAnwser_A_traloi.get(3)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_1, StringUtil.convert_html(mLisAnwser_B_traloi.get(0)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                initWebview(webview_dapannoicau_B_traloi_2, StringUtil.convert_html(mLisAnwser_B_traloi.get(1)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_3, StringUtil.convert_html(mLisAnwser_B_traloi.get(2)));
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initWebview(webview_dapannoicau_B_traloi_4, StringUtil.convert_html(mLisAnwser_B_traloi.get(3)));
            }
        });
        rl_dapanA_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        rl_dapanB_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

        rl_dapanA_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        rl_dapanB_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

        rl_dapanA_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
        rl_dapanB_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));

        rl_dapanA_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
        rl_dapanB_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isClickXemdiem) {
                    switch (v.getId()) {
                        case R.id.webview_dapannoicau_A_1:
                            initEvent_AClick("A1");
                            break;
                        case R.id.webview_dapannoicau_A_2:
                            initEvent_AClick("A2");
                            break;
                        case R.id.webview_dapannoicau_A_3:
                            initEvent_AClick("A3");
                            break;
                        case R.id.webview_dapannoicau_A_4:
                            initEvent_AClick("A4");
                            break;
                        case R.id.webview_dapannoicau_B_1:
                            initEvent_AClick("B1");
                            break;
                        case R.id.webview_dapannoicau_B_2:
                            initEvent_AClick("B2");
                            break;
                        case R.id.webview_dapannoicau_B_3:
                            initEvent_AClick("B3");
                            break;
                        case R.id.webview_dapannoicau_B_4:
                            initEvent_AClick("B4");
                            break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    boolean isClickA1, isClickA2, isClickA3, isClickA4 = false;

    private void initEvent_AClick(String sClick) {
        EventBus.getDefault().post(new MessageEvent("mp3", Float.parseFloat
                (mCauhoi.getsPOINT()), 0));
        switch (sClick) {
            case "A1":
                if (!isDangchon) {
                    if (!isDapanA_1) {
                        rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                        sDangchon = "A1";
                        isDapanA_1 = true;
                        arayClickA1[0] = "A1";
                        isDangchon = true;
                    } else if (isClickA1) {
                        rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sDangchon = "";
                        isDapanA_1 = false;
                        arayClickA1[0] = "";
                        isDangchon = false;
                    }
                } else {
                    if (sDangchon.equals("A1")) {
                        rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sDangchon = "";
                        isDapanA_1 = false;
                        arayClickA1[0] = "";
                        isDangchon = false;
                    }
                }

                break;
            case "A2":
                if (!isDangchon) {
                    if (!isDapanA_2) {
                        rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                        sDangchon = "A2";
                        isDapanA_2 = true;
                        arayClickA2[0] = "A2";
                        isDangchon = true;
                    } else if (isClickA2) {
                        rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sDangchon = "";
                        isDapanA_2 = false;
                        arayClickA2[0] = "";
                        isDangchon = false;
                    }
                } else if (sDangchon.equals("A2")) {
                    rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    sDangchon = "";
                    isDapanA_2 = false;
                    arayClickA2[0] = "";
                    isDangchon = false;
                }
                break;
            case "A3":
                if (!isDangchon) {
                    if (!isDapanA_3) {
                        rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                        sDangchon = "A3";
                        isDapanA_3 = true;
                        arayClickA3[0] = "A3";
                        isDangchon = true;
                    } else if (isClickA3) {
                        rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sDangchon = "";
                        isDapanA_3 = false;
                        arayClickA3[0] = "";
                        isDangchon = false;
                    }
                } else if (sDangchon.equals("A3")) {
                    rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    sDangchon = "";
                    isDapanA_3 = false;
                    arayClickA3[0] = "";
                    isDangchon = false;
                }
                break;
            case "A4":
                if (!isDangchon) {
                    if (!isDapanA_4) {
                        rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                        sDangchon = "A4";
                        isDapanA_4 = true;
                        arayClickA4[0] = "A4";
                        isDangchon = true;
                    } else if (isClickA4) {
                        rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sDangchon = "";
                        isDapanA_4 = false;
                        arayClickA4[0] = "";
                        isDangchon = false;
                    }
                } else if (sDangchon.equals("A4")) {
                    rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                    sDangchon = "";
                    isDapanA_4 = false;
                    arayClickA4[0] = "";
                    isDangchon = false;
                }
                break;
            case "B1":
                if (isDangchon) {
                    initClickEventB(rl_dapanB_1, "B1", sDangchon, 0);
                    check_anwser();
                } else {
                    initNotClickB("B1", rl_dapanB_1, 1);
                }
                break;
            case "B2":
                if (isDangchon) {
                    initClickEventB(rl_dapanB_2, "B2", sDangchon, 1);
                    check_anwser();
                } else {
                    initNotClickB("B2", rl_dapanB_2, 2);
                }
                break;
            case "B3":
                if (isDangchon) {
                    initClickEventB(rl_dapanB_3, "B3", sDangchon, 2);
                    check_anwser();
                } else {
                    initNotClickB("B3", rl_dapanB_3, 3);
                }
                break;
            case "B4":
                if (isDangchon) {
                    initClickEventB(rl_dapanB_4, "B4", sDangchon, 3);
                    check_anwser();
                } else {
                    initNotClickB("B4", rl_dapanB_4, 4);
                }
                break;
        }
    }

    boolean isUnClick = false;
    String sUnClick = "";

    private void initNotClickB(String sClick, RelativeLayout rl, int postion) {
        if (!isUnClick) {
            switch (sClick) {
                case "B1":
                    if (isClickB1) {
                        rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sUnClick = "B1";
                        isClickB1 = false;
                        isDangchon = true;
                        if (arayClickA1[1].length() > 0 && arayClickA1[1].equals("B1")) {
                            sDangchon = arayClickA1[0];
                            arayClickA1[1] = "";
                        } else if (arayClickA2[1].length() > 0 && arayClickA2[1].equals("B1")) {
                            sDangchon = arayClickA2[0];
                            arayClickA2[1] = "";
                        } else if (arayClickA3[1].length() > 0 && arayClickA3[1].equals("B1")) {
                            sDangchon = arayClickA3[0];
                            arayClickA3[1] = "";
                        } else if (arayClickA4[1].length() > 0 && arayClickA4[1].equals("B1")) {
                            sDangchon = arayClickA4[0];
                            arayClickA4[1] = "";
                        }
                    }
                    break;
                case "B2":
                    if (isClickB2) {
                        rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sUnClick = "B2";
                        isClickB2 = false;
                        isDangchon = true;
                        if (arayClickA1[1].length() > 0 && arayClickA1[1].equals("B2")) {
                            sDangchon = arayClickA1[0];
                            arayClickA1[1] = "";
                        } else if (arayClickA2[1].length() > 0 && arayClickA2[1].equals("B2")) {
                            sDangchon = arayClickA2[0];
                            arayClickA2[1] = "";
                        } else if (arayClickA3[1].length() > 0 && arayClickA3[1].equals("B2")) {
                            sDangchon = arayClickA3[0];
                            arayClickA3[1] = "";
                        } else if (arayClickA4[1].length() > 0 && arayClickA4[1].equals("B2")) {
                            sDangchon = arayClickA4[0];
                            arayClickA4[1] = "";
                        }
                    }
                    break;
                case "B3":
                    if (isClickB3) {
                        rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sUnClick = "B3";
                        isClickB3 = false;
                        isDangchon = true;
                        if (arayClickA1[1].length() > 0 && arayClickA1[1].equals("B3")) {
                            sDangchon = arayClickA1[0];
                            arayClickA1[1] = "";
                        } else if (arayClickA2[1].length() > 0 && arayClickA2[1].equals("B3")) {
                            sDangchon = arayClickA2[0];
                            arayClickA2[1] = "";
                        } else if (arayClickA3[1].length() > 0 && arayClickA3[1].equals("B3")) {
                            sDangchon = arayClickA3[0];
                            arayClickA3[1] = "";
                        } else if (arayClickA4[1].length() > 0 && arayClickA4[1].equals("B3")) {
                            sDangchon = arayClickA4[0];
                            arayClickA4[1] = "";
                        }
                    }
                    break;
                case "B4":
                    if (isClickB4) {
                        rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        sUnClick = "B4";
                        isClickB4 = false;
                        isDangchon = true;
                        if (arayClickA1[1].length() > 0 && arayClickA1[1].equals("B4")) {
                            sDangchon = arayClickA1[0];
                            arayClickA1[1] = "";
                        } else if (arayClickA2[1].length() > 0 && arayClickA2[1].equals("B4")) {
                            sDangchon = arayClickA2[0];
                            arayClickA2[1] = "";
                        } else if (arayClickA3[1].length() > 0 && arayClickA3[1].equals("B4")) {
                            sDangchon = arayClickA3[0];
                            arayClickA3[1] = "";
                        } else if (arayClickA4[1].length() > 0 && arayClickA4[1].equals("B4")) {
                            sDangchon = arayClickA4[0];
                            arayClickA4[1] = "";
                        }
                    }
                    break;
            }

        }


    }

    boolean isClickB1, isClickB2, isClickB3, isClickB4 = false;

    private void initClickEventB(RelativeLayout rl, String sClick, String schon, int position) {
        switch (schon) {
            case "A1":
                switch (sClick) {
                    case "B1":
                        if (!isClickB1) {
                            arayClickA1[1] = sClick;
                            map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB1 = true;
                        }
                        break;
                    case "B2":
                        if (!isClickB2) {
                            arayClickA1[1] = sClick;
                            map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB2 = true;
                        }
                        break;
                    case "B3":
                        if (!isClickB3) {
                            arayClickA1[1] = sClick;
                            map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB3 = true;
                        }
                        break;
                    case "B4":
                        if (!isClickB4) {
                            arayClickA1[1] = sClick;
                            map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB4 = true;
                        }
                        break;
                }

                break;
            case "A2":
                switch (sClick) {
                    case "B1":
                        if (!isClickB1) {
                            arayClickA2[1] = sClick;
                            map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB1 = true;
                        }
                        break;
                    case "B2":
                        if (!isClickB2) {
                            arayClickA2[1] = sClick;
                            map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB2 = true;
                        }
                        break;
                    case "B3":
                        if (!isClickB3) {
                            arayClickA2[1] = sClick;
                            map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB3 = true;
                        }
                        break;
                    case "B4":
                        if (!isClickB4) {
                            arayClickA2[1] = sClick;
                            map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB4 = true;
                        }
                        break;
                }
                break;
            case "A3":
                switch (sClick) {
                    case "B1":
                        if (!isClickB1) {
                            arayClickA3[1] = sClick;
                            map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB1 = true;
                        }
                        break;
                    case "B2":
                        if (!isClickB2) {
                            arayClickA3[1] = sClick;
                            map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB2 = true;
                        }
                        break;
                    case "B3":
                        if (!isClickB3) {
                            arayClickA3[1] = sClick;
                            map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB3 = true;
                        }
                        break;
                    case "B4":
                        if (!isClickB4) {
                            arayClickA3[1] = sClick;
                            map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.mau_tim));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB4 = true;
                        }
                        break;
                }
                break;
            case "A4":
                switch (sClick) {
                    case "B1":
                        if (!isClickB1) {
                            arayClickA4[1] = sClick;
                            map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB1 = true;
                        }
                        break;
                    case "B2":
                        if (!isClickB2) {
                            arayClickA4[1] = sClick;
                            map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB2 = true;
                        }
                        break;
                    case "B3":
                        if (!isClickB3) {
                            arayClickA4[1] = sClick;
                            map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB3 = true;
                        }
                        break;
                    case "B4":
                        if (!isClickB4) {
                            arayClickA4[1] = sClick;
                            map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(position));
                            rl.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                            isDangchon = false;
                            sDangchon = "";
                            isClickB4 = true;
                        }
                        break;
                }
                break;
        }
    }
}
