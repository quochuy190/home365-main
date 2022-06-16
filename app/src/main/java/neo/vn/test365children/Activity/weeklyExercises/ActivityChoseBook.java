package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Activity.CustomViewPager;
import neo.vn.test365children.Adapter.AdapterBoSach;
import neo.vn.test365children.Adapter.AdapterItemMenuLambaitap;
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.BoSach;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;

import static java.security.AccessController.getContext;

/**
 * Created 11/03/2022 at 15:49
 * Company: VHM
 */
public class ActivityChoseBook extends BaseActivity {
    @BindView(R.id.rcvChonSach)
    RecyclerView rcvChonSach;
    @BindView(R.id.img_back)
    ImageView img_back;
    List<BoSach> lisBaitap;
    AdapterBoSach mAdapter;
    Baitap_Tuan objBaitapTuan;
    @Override
    public int setContentViewId() {
        return R.layout.activity_selected_book;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initChonSach();
        initEvent();
    }

    private void initData() {
        objBaitapTuan = getIntent().getParcelableExtra(Constants.KEY_SEND_BAITAPTUAN);
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initChonSach() {
        if (objBaitapTuan==null)
            return;
        lisBaitap = new ArrayList<>();
        if (objBaitapTuan.getsSUBJECT_ID().equals("3")){
            lisBaitap.add(new BoSach(3,"i-Learn Smart Start", R.drawable.img_i_learn));
            lisBaitap.add(new BoSach(4,"Tiếng Anh (NXB)", R.drawable.img_ta_nhaxuatban));
        }else {
            lisBaitap.add(new BoSach(1,"Bộ sách cánh diều", R.drawable.canh_dieu));
            lisBaitap.add(new BoSach(2,"Bộ sách kết nối tri thức", R.drawable.ketnoitrithuc));
        }
        Log.e("TAG", "initChonSach: "+App.mBaiTapTuan.size() );
        mAdapter = new AdapterBoSach(lisBaitap, this);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        rcvChonSach.setHasFixedSize(true);
        rcvChonSach.setAdapter(mAdapter);
        mAdapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                BoSach obBoSach = (BoSach) item;
                Intent intent = new Intent(ActivityChoseBook.this, ActivityBaitapChonsach.class);
                intent.putExtra(Constants.KEY_SEND_ID_BOSACH, obBoSach.getId());
                startActivity(intent);
            }
        });
    }

}
