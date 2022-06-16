package neo.vn.test365children.Fragment.ReviewExercises;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAnNoicau;
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
public class FragmentReviewNoicau extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    List<DapAnNoicau> mLisDapanA, mLisDapanB;
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

    String sDangchon = "";
    Map<String, String> map_answer_chil;
    Map<String, String> map_answer_true;

    @BindView(R.id.text_lable_dapan)
    TextView text_lable_dapan;
    @BindView(R.id.ll_dapan_traloi)
    LinearLayout ll_dapan_traloi;
    @BindView(R.id.ll_question)
    LinearLayout ll_questioln;
    @BindView(R.id.img_background)
    ImageView img_background;
/*    @BindView(R.id.ll_question)
    LinearLayout ll_question;*/

    public static FragmentReviewNoicau newInstance(CauhoiDetail restaurant) {
        FragmentReviewNoicau restaurantDetailFragment = new FragmentReviewNoicau();
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
        View view = inflater.inflate(R.layout.fragment_noicau_review, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        initData();
        return view;
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

    List<String> mLisAnwser_A;
    List<String> mLisAnwser_B;
    List<String> mLisQuestion_A;
    List<String> mLisQuestionr_B;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    List<String> mLisAnwser_B_traloi;

    private void initData() {
        try {
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsNumberDe().equals("1") && mCauhoi.getsSubNumberCau()
                    != null && mCauhoi.getsSubNumberCau().equals("1")) {
                showDialogLoading();
            }
            Glide.with(getContext()).load(R.drawable.bg_chem_hoa_qua).into(img_background);
            if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
                if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                    ll_dapan_traloi.setVisibility(View.VISIBLE);
                    text_lable_dapan.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                } else {
                    ll_dapan_traloi.setVisibility(View.GONE);
                    text_lable_dapan.setVisibility(View.GONE);
                    Glide.with(getActivity()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                }
            } else {
                ll_questioln.setVisibility(View.GONE);
                Glide.with(getActivity()).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
            }
            if (!mCauhoi.isDalam()) {
                ll_questioln.setVisibility(View.GONE);
                Glide.with(getActivity()).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
            }
            mLisAnwser_B_traloi = new ArrayList<>();
            mLisDapanB = new ArrayList<>();
            mLisDapanA = new ArrayList<>();
            mLisAnwser_A = new ArrayList<>();
            mLisAnwser_B = new ArrayList<>();
            mLisQuestion_A = new ArrayList<>();
            mLisQuestionr_B = new ArrayList<>();
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            String[] egg1 = mCauhoi.getsHTML_A().split("::");
            String[] egg2 = mCauhoi.getsHTML_B().split("::");
            String[] egg3 = mCauhoi.getsHTML_C().split("::");
            String[] egg4 = mCauhoi.getsHTML_D().split("::");
            if (egg1 == null)
                return;
            if (egg2 == null)
                return;
            if (egg3 == null)
                return;
            if (egg4 == null)
                return;
            map_answer_true.put("egg_1", mCauhoi.getsHTML_A());
            map_answer_true.put("egg_2", mCauhoi.getsHTML_B());
            map_answer_true.put("egg_3", mCauhoi.getsHTML_C());
            map_answer_true.put("egg_4", mCauhoi.getsHTML_D());
            if (egg1[0] == null)
                return;
            mLisAnwser_A.add(egg1[0]);
            if (egg2[0] == null)
                return;
            mLisAnwser_A.add(egg2[0]);
            if (egg3[0] == null)
                return;
            mLisAnwser_A.add(egg3[0]);
            if (egg4[0] == null)
                return;
            mLisAnwser_A.add(egg4[0]);
            if (egg1[1] == null)
                return;
            mLisAnwser_B.add(egg1[1]);
            if (egg2[1] == null)
                return;
            mLisAnwser_B.add(egg2[1]);
            if (egg3[1] == null)
                return;
            mLisAnwser_B.add(egg3[1]);
            if (egg4[1] == null)
                return;
            mLisAnwser_B.add(egg4[1]);

            mLisAnwser_B_traloi.add(egg1[1]);
            mLisAnwser_B_traloi.add(egg2[1]);
            mLisAnwser_B_traloi.add(egg3[1]);
            mLisAnwser_B_traloi.add(egg4[1]);

            if (mCauhoi.getsEGG_1_RESULT() != null && mCauhoi.getsEGG_1_RESULT().length() > 0) {
                String[] egg_result1 = mCauhoi.getsEGG_1_RESULT().split("::");
                if (egg_result1.length > 0 && egg_result1[0] != null) {
                    mLisQuestion_A.add(egg_result1[0]);
                } else mLisQuestion_A.add("");
                if (egg_result1.length > 1 && egg_result1[1] != null) {
                    mLisQuestionr_B.add(egg_result1[1]);
                } else mLisQuestionr_B.add("");
            } else {
                mLisQuestion_A.add("");
                mLisQuestionr_B.add("");
            }
            if (mCauhoi.getsEGG_2_RESULT() != null && mCauhoi.getsEGG_2_RESULT().length() > 0) {
                String[] egg_result2 = mCauhoi.getsEGG_2_RESULT().split("::");
                if (egg_result2.length > 0 && egg_result2[0] != null) {
                    mLisQuestion_A.add(egg_result2[0]);
                } else
                    mLisQuestion_A.add("");
                if (egg_result2.length > 1 && egg_result2[1] != null) {
                    mLisQuestionr_B.add(egg_result2[1]);
                } else
                    mLisQuestionr_B.add("");
            } else {
                mLisQuestionr_B.add("");
                mLisQuestion_A.add("");
            }
            if (mCauhoi.getsEGG_3_RESULT() != null && mCauhoi.getsEGG_3_RESULT().length() > 0) {
                String[] egg_result3 = mCauhoi.getsEGG_3_RESULT().split("::");
                if (egg_result3.length > 0 && egg_result3[0] != null) {
                    mLisQuestion_A.add(egg_result3[0]);
                } else
                    mLisQuestion_A.add("");
                if (egg_result3.length > 1 && egg_result3[1] != null) {
                    mLisQuestionr_B.add(egg_result3[1]);
                } else
                    mLisQuestionr_B.add("");
            } else {
                mLisQuestion_A.add("");
                mLisQuestionr_B.add("");
            }
            if (mCauhoi.getsEGG_3_RESULT() != null && mCauhoi.getsEGG_3_RESULT().length() > 0) {
                String[] egg_result4 = mCauhoi.getsEGG_4_RESULT().split("::");
                if (egg_result4.length > 0 && egg_result4[0] != null) {
                    mLisQuestion_A.add(egg_result4[0]);
                } else
                    mLisQuestion_A.add("");
                if (egg_result4.length > 1 && egg_result4[1] != null) {
                    mLisQuestionr_B.add(egg_result4[1]);
                } else
                    mLisQuestionr_B.add("");
            } else {
                mLisQuestionr_B.add("");
                mLisQuestion_A.add("");
            }

            initTraloi();
            if (mLisQuestion_A.size() > 0) {
                if (mLisQuestion_A.get(0) != null)
                    initWebview(webview_dapannoicau_A_1, StringUtil.convert_html(mLisQuestion_A.get(0)));
                else
                    rl_dapanA_1.setVisibility(View.GONE);
            if (mLisQuestion_A.get(1) != null)
                initWebview(webview_dapannoicau_A_2, StringUtil.convert_html(mLisQuestion_A.get(1)));
            else
                rl_dapanA_2.setVisibility(View.GONE);
            if (mLisQuestion_A.get(2) != null)
                initWebview(webview_dapannoicau_A_3, StringUtil.convert_html(mLisQuestion_A.get(2)));
            else
                rl_dapanA_3.setVisibility(View.GONE);
            if (mLisQuestion_A.get(3) != null)
                initWebview(webview_dapannoicau_A_4, StringUtil.convert_html(mLisQuestion_A.get(3)));
            else
                rl_dapanA_4.setVisibility(View.GONE);
            if (mLisQuestionr_B.get(0) != null)
                initWebview(webview_dapannoicau_B_1, StringUtil.convert_html(mLisQuestionr_B.get(0)));
            else
                rl_dapanB_1.setVisibility(View.GONE);
            if (mLisQuestionr_B.get(1) != null)
                initWebview(webview_dapannoicau_B_2, StringUtil.convert_html(mLisQuestionr_B.get(1)));
            else
                rl_dapanB_2.setVisibility(View.GONE);
            if (mLisQuestionr_B.get(2) != null)
                initWebview(webview_dapannoicau_B_3, StringUtil.convert_html(mLisQuestionr_B.get(2)));
            else
                rl_dapanB_3.setVisibility(View.GONE);
            if (mLisQuestionr_B.get(3) != null)
                initWebview(webview_dapannoicau_B_4, StringUtil.convert_html(mLisQuestionr_B.get(3)));
            else
                rl_dapanB_4.setVisibility(View.GONE);
            } else {
                ll_questioln.setVisibility(View.GONE);
            }


            rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
            rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

            rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
            rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

            rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
            rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));

            rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
            rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    boolean isImageA = false, isImageB = false;
    int iHeightmax = 0;

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
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + link_web + pas,
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
                        int i = 0;
                        switch (view.getId()) {
                            case R.id.webview_dapannoicau_A_1:
                                Log.i(TAG, "onFinish: A1");
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestionr_B.get(0) != null) {
                                            Log.i(TAG, "onFinish: A1 start B1");
                                            initWebview(webview_dapannoicau_B_1,
                                                    StringUtil.convert_html(mLisQuestionr_B.get(0)));
                                        } else
                                            initWebview(webview_dapannoicau_B_1,
                                                    StringUtil.convert_html(""));

                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_2:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestionr_B.get(1) != null) {
                                            Log.i(TAG, "onFinish: A2 start B2");
                                            initWebview(webview_dapannoicau_B_2,
                                                    StringUtil.convert_html(mLisQuestionr_B.get(1)));
                                        } else
                                            initWebview(webview_dapannoicau_B_2,
                                                    StringUtil.convert_html(""));

                                       /* initWebview(webview_dapannoicau_B_2,
                                                StringUtil.convert_html(mLisAnwser_B.get(1)));*/
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_3:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestionr_B.get(2) != null) {
                                            Log.i(TAG, "run: A3 start B3");
                                            initWebview(webview_dapannoicau_B_3,
                                                    StringUtil.convert_html(mLisQuestionr_B.get(2)));
                                        } else
                                            initWebview(webview_dapannoicau_B_3,
                                                    StringUtil.convert_html(""));

                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_4:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestionr_B.get(3) != null) {
                                            Log.i(TAG, "run: A4 start B4");
                                            initWebview(webview_dapannoicau_B_4,
                                                    StringUtil.convert_html(mLisQuestionr_B.get(3)));
                                        } else {
                                            initWebview(webview_dapannoicau_B_4,
                                                    StringUtil.convert_html(""));
                                        }

                                    }
                                });

                                break;
                            case R.id.webview_dapannoicau_B_1:
                                Log.i(TAG, "onFinish: B1");
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestion_A.get(1) != null) {
                                            Log.i(TAG, "onFinish: B1 start A2");
                                            initWebview(webview_dapannoicau_A_2,
                                                    StringUtil.convert_html(mLisQuestion_A.get(1)));
                                        } else
                                            initWebview(webview_dapannoicau_A_2,
                                                    StringUtil.convert_html(""));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_2:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestion_A.get(2) != null)
                                            initWebview(webview_dapannoicau_A_3,
                                                    StringUtil.convert_html(mLisQuestion_A.get(2)));
                                        else
                                            initWebview(webview_dapannoicau_A_3,
                                                    StringUtil.convert_html(""));

                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_3:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLisQuestion_A.get(3) != null)
                                            initWebview(webview_dapannoicau_A_4,
                                                    StringUtil.convert_html(mLisQuestion_A.get(3)));
                                        else
                                            initWebview(webview_dapannoicau_A_4,
                                                    StringUtil.convert_html(""));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_4:
                                hideDialogLoading();
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
                                if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
                                    if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                                        new Handler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                initWebview(webview_dapannoicau_A_traloi_1,
                                                        StringUtil.convert_html(mLisAnwser_A.get(0)));
                                            }
                                        });
                                    } else {

                                    }
                                }

                                break;
                            case R.id.webview_dapannoicau_A_traloi_1:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_B_traloi_1,
                                                StringUtil.convert_html(mLisAnwser_B_traloi.get(0)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_traloi_2:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_B_traloi_2,
                                                StringUtil.convert_html(mLisAnwser_B_traloi.get(1)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_traloi_3:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_B_traloi_3,
                                                StringUtil.convert_html(mLisAnwser_B_traloi.get(2)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_A_traloi_4:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_B_traloi_4,
                                                StringUtil.convert_html(mLisAnwser_B_traloi.get(3)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_traloi_1:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_A_traloi_2,
                                                StringUtil.convert_html(mLisAnwser_A.get(1)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_traloi_2:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_A_traloi_3,
                                                StringUtil.convert_html(mLisAnwser_A.get(2)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_traloi_3:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_dapannoicau_A_traloi_4,
                                                StringUtil.convert_html(mLisAnwser_A.get(3)));
                                    }
                                });
                                break;
                            case R.id.webview_dapannoicau_B_traloi_4:
                                if (rl_dapanA_traloi_1.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanA_traloi_1.getHeight();
                                }
                                if (rl_dapanA_traloi_2.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanA_traloi_2.getHeight();
                                }
                                if (rl_dapanA_traloi_3.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanA_traloi_3.getHeight();
                                }
                                if (rl_dapanA_traloi_4.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanA_traloi_4.getHeight();
                                }
                                if (rl_dapanB_traloi_1.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanB_traloi_1.getHeight();
                                }
                                if (rl_dapanB_traloi_2.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanB_traloi_2.getHeight();
                                }
                                if (rl_dapanB_traloi_3.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanB_traloi_3.getHeight();
                                }
                                if (rl_dapanB_traloi_4.getHeight() > iHeightmax) {
                                    iHeightmax = rl_dapanB_traloi_4.getHeight();
                                }
                                if (iHeightmax > 0) {
                                    setHeight(iHeightmax);
                                }
                                break;
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

    private void setHeightAll(int iHeight, View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = iHeight;
        view.setLayoutParams(params);
    }

    private void initTraloi() {
      /*  initWebview(webview_dapannoicau_A_traloi_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
        initWebview(webview_dapannoicau_A_traloi_2, StringUtil.convert_html(mLisAnwser_A.get(1)));
        initWebview(webview_dapannoicau_A_traloi_3, StringUtil.convert_html(mLisAnwser_A.get(2)));
        initWebview(webview_dapannoicau_A_traloi_4, StringUtil.convert_html(mLisAnwser_A.get(3)));

        initWebview(webview_dapannoicau_B_traloi_1, StringUtil.convert_html(mLisAnwser_B.get(0)));
        initWebview(webview_dapannoicau_B_traloi_2, StringUtil.convert_html(mLisAnwser_B.get(1)));
        initWebview(webview_dapannoicau_B_traloi_3, StringUtil.convert_html(mLisAnwser_B.get(2)));
        initWebview(webview_dapannoicau_B_traloi_4, StringUtil.convert_html(mLisAnwser_B.get(3)));*/

        rl_dapanA_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        rl_dapanB_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

        rl_dapanA_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        rl_dapanB_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

        rl_dapanA_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
        rl_dapanB_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));

        rl_dapanA_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
        rl_dapanB_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
    }
}
