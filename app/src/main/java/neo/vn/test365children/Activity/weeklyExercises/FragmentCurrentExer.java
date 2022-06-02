package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Bundle;
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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityLogin;
import neo.vn.test365children.Activity.ActivityMenuBaitap;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Adapter.AdapterItemMenuLambaitap;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentCompleteBaitap;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.HomeworkDone;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlLogin;
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
public class FragmentCurrentExer extends BaseFragment implements ImpBaitap.View{
    PresenterBaitap mPresenter;
    String sUserMe = "", sUserCon = "", sMon, sPassWord = "";
    @BindView(R.id.recycle_menu_baitap)
    RecyclerView recycle_baitap_tuan;
    List<Baitap_Tuan> lisBaitap;
    AdapterItemMenuLambaitap adapter_baitaptuan;
    @BindView(R.id.txt_notify_need)
    TextView txt_notify_need;
    RecyclerView.LayoutManager mLayoutManager;
    public static FragmentCurrentExer newInstance() {
        FragmentCurrentExer fragment = new FragmentCurrentExer();
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
        View view = inflater.inflate(R.layout.fragment_exer_current_week, container, false);
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
        lisBaitap = new ArrayList<>();
        adapter_baitaptuan = new AdapterItemMenuLambaitap(lisBaitap, getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        recycle_baitap_tuan.setHasFixedSize(true);
        recycle_baitap_tuan.setLayoutManager(mLayoutManager);
        recycle_baitap_tuan.setItemAnimator(new DefaultItemAnimator());
        recycle_baitap_tuan.setAdapter(adapter_baitaptuan);
        adapter_baitaptuan.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                KeyboardUtil.play_click_button(getActivity());
                Baitap_Tuan obj = (Baitap_Tuan) item;
                Intent intent = new Intent(getContext(), ActivityStartBaitap.class);
                intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        if (isNetwork()) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            showDialogLoading();
            mPresenter.get_api_get_excercise_needed(sUserMe, sUserCon, get_current_time());
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
    public void show_get_excercise_needed(ResponseObjWeek obj) {
        hideDialogLoading();
        lisBaitap.clear();
        if (obj.getsERROR().equals("0000") && obj.getsINFO() != null) {
            boolean isMuabai = false;
            for (Baitap_Tuan objWeek : obj.getsINFO()) {
                if (objWeek.getsSTATE_BUY().equals("1")) {
                    lisBaitap.add(objWeek);
                }
            }
            if (lisBaitap.size() > 0) {
                recycle_baitap_tuan.setVisibility(View.VISIBLE);
                txt_notify_need.setVisibility(View.GONE);
            } else {
                txt_notify_need.setVisibility(View.VISIBLE);
                recycle_baitap_tuan.setVisibility(View.INVISIBLE);
                if (obj.getsINFO().size() > 0) {
                    txt_notify_need.setText("Bạn đã làm hết bài tập tuần này, mời bạn làm tiếp vào tuần sau nhé");
                } else {
                    txt_notify_need.setText("Bạn đã làm hết bài tập tuần này, mời bạn làm tiếp vào tuần sau nhé");
                }

            }
            adapter_baitaptuan.notifyDataSetChanged();
        } else if (obj.getsERROR().equals("0002")) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            sPassWord = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
            showDialogLoading();
           // mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassWord);
        } else {
            showDialogNotify("Thông báo", obj.getsRESULT());
        }
    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {

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
