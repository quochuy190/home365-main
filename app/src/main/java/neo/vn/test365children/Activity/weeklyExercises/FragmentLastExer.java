package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Activity.weeklyExercises.adapter.AdapterListExerLast;
import neo.vn.test365children.Adapter.AdapterItemMenuLambaitap;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentCompleteBaitap;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerLastObj;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

import static neo.vn.test365children.Untils.StringUtil.get_current_time;

/**
 * Created 11/03/2022 at 15:56
 * Company: VHM
 */
public class FragmentLastExer extends BaseFragment implements  ImpBaitap.View{
    PresenterBaitap mPresenter;
    String sUserMe = "", sUserCon = "", sMon, sPassWord = "";
    List<ExerLastObj> mListLast;
    AdapterListExerLast adapter;
    @BindView(R.id.rcvLastExer)
    RecyclerView rcvLastExer;
    @BindView(R.id.tvDataEmpty)
    TextView tvDataEmpty;
    public static FragmentLastExer newInstance() {
        FragmentLastExer fragment = new FragmentLastExer();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_week, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new PresenterBaitap(this);
        initEvent();
        initbaitaptuan();
        initData();
        return view;
    }

    private void initEvent() {

    }

    private void initbaitaptuan() {
        mListLast = new ArrayList<>();
        adapter = new AdapterListExerLast(mListLast, getContext());
        rcvLastExer.setAdapter(adapter);

    }

    private void initData() {
        if (isNetwork()) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
          //  showDialogLoading();
            mPresenter.get_api_get_excercise_expired(sUserMe, sUserCon);
        }
    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {

    }

    @Override
    public void show_get_excercise_needed(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {
        hideDialogLoading();
        List<Baitap_Tuan> mList = new ArrayList<Baitap_Tuan>();
        if (objResponWeek.getsERROR().equals("0000") && objResponWeek.getsINFO() != null) {
            if (objResponWeek.getsINFO().size() > 0) {
                rcvLastExer.setVisibility(View.VISIBLE);
                tvDataEmpty.setVisibility(View.GONE);
            } else {
                tvDataEmpty.setVisibility(View.VISIBLE);
                rcvLastExer.setVisibility(View.INVISIBLE);

            }
            mList.addAll(objResponWeek.getsINFO());
            Collections.sort(mList);
            Log.d("TAG", "onCreate: " + mList.size());

            List<ExerLastObj> mListLast = new ArrayList<>();
            List<String> mKeyWeek = new ArrayList<>();
            String sWeek = mList.get(0).getsWEEK_ID();
            mKeyWeek.add(sWeek);
            for (int i = 0; i < (mList.size()); i++) {
                if (!sWeek.equals(mList.get(i).getsWEEK_ID())) {
                    sWeek = mList.get(i).getsWEEK_ID();
                    mKeyWeek.add(sWeek);
                }
            }
            for (String s : mKeyWeek){
                List<Baitap_Tuan> mBaiTap = new ArrayList<>();
                for (Baitap_Tuan objBaitap: mList){
                    if (objBaitap.getsWEEK_ID().equals(s)){
                        mBaiTap.add(objBaitap);
                    }
                }
                mListLast.add(new ExerLastObj(s, mBaiTap));
            }
            adapter.updateList(mListLast);
        } else if (objResponWeek.getsERROR().equals("0002")) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            sPassWord = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
            showDialogLoading();
            // mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassWord);
        } else {
            showDialogNotify("Thông báo", objResponWeek.getsRESULT());
        }
        Log.d(TAG, "show_get_excercise_expired: "+objResponWeek.getsERROR());

    }

    @Override
    public void show_start_taken(ErrorApi mLis) {

    }

    @Override
    public void show_submit_execercise(ErrorApi mLis) {

    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void showHomeworkDone(HomeworkDone homeworkDone) {

    }
}
