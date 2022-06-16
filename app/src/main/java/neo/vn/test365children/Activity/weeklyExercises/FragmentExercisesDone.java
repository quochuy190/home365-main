package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Activity.ReviewExercises.ActivityExercisesDetail;
import neo.vn.test365children.Activity.weeklyExercises.adapter.AdapterExerTaken;
import neo.vn.test365children.Activity.weeklyExercises.adapter.AdapterListExerLast;
import neo.vn.test365children.Activity.weeklyExercises.adapter.AdapterListExerTaken;
import neo.vn.test365children.Adapter.AdapterItemMenuLambaitap;
import neo.vn.test365children.Adapter.AdapterObjBaitapTuan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Listener.setOnItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.DETAILSItem;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerLastObj;
import neo.vn.test365children.Models.ExerTakenObj;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.HomeworkDone;
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
public class FragmentExercisesDone extends BaseFragment  implements ImpBaitap.View {
    PresenterBaitap mPresenter;
    String sUserMe = "", sUserCon = "", sMon, sPassWord = "";
    List<ExerTakenObj> mListLast;
    AdapterListExerTaken adapter;
    @BindView(R.id.rcvExerTaken)
    RecyclerView rcvExerTaken;
    @BindView(R.id.txt_notify_need)
    TextView tvDataEmpty;
    RecyclerView.LayoutManager mLayoutManager;
    public static FragmentExercisesDone newInstance() {
        FragmentExercisesDone fragment = new FragmentExercisesDone();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exer_done, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new PresenterBaitap(this);
        initEvent();
        initbaitaptuan();
        initData();
        return view;
    }
    private void initbaitaptuan() {
        mListLast = new ArrayList<>();
        adapter = new AdapterListExerTaken(mListLast, getContext());
        rcvExerTaken.setAdapter(adapter);

    }

    private void initData() {
        if (isNetwork()) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
           // showDialogLoading();
            mPresenter.get_excercise_taken(sUserMe, sUserCon);
        }
    }

    private void initEvent() {

    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_get_excercise_needed(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_start_taken(ErrorApi mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_submit_execercise(ErrorApi mLis) {

    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void showHomeworkDone(HomeworkDone objResponWeek) {
        hideDialogLoading();
        List<DETAILSItem> mList = new ArrayList<DETAILSItem>();
        if (objResponWeek.getERROR().equals("0000") && objResponWeek.getDETAILS() != null) {
            if (objResponWeek.getDETAILS().size() > 0) {
                rcvExerTaken.setVisibility(View.VISIBLE);
                tvDataEmpty.setVisibility(View.GONE);
            } else {
                tvDataEmpty.setVisibility(View.VISIBLE);
                rcvExerTaken.setVisibility(View.INVISIBLE);

            }
            mList.addAll(objResponWeek.getDETAILS());
            Collections.sort(mList);
            Log.d("TAG", "onCreate: " + mList.size());

            List<ExerTakenObj> mListLast = new ArrayList<>();
            List<String> mKeyWeek = new ArrayList<>();
            String sWeek = mList.get(0).getWEEKNAME();
            mKeyWeek.add(sWeek);
            for (int i = 0; i < (mList.size()); i++) {
                if (!sWeek.equals(mList.get(i).getWEEKNAME())) {
                    sWeek = mList.get(i).getWEEKNAME();
                    mKeyWeek.add(sWeek);
                }
            }
            for (String s : mKeyWeek){
                List<DETAILSItem> mBaiTap = new ArrayList<>();
                for (DETAILSItem objBaitap: mList){
                    if (objBaitap.getWEEKNAME().equals(s)){
                        mBaiTap.add(objBaitap);
                    }
                }
                mListLast.add(new ExerTakenObj(s, mBaiTap));
            }
            adapter.updateList(mListLast);
            adapter.notifyDataSetChanged();
        } else if (objResponWeek.getERROR().equals("0002")) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            sPassWord = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
            showDialogLoading();
            // mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassWord);
        } else {
            showDialogNotify("Thông báo", objResponWeek.getRESULT());
        }
        Log.d(TAG, "show_get_excercise_expired: "+objResponWeek.getERROR());

    }
}
