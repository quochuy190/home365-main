package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    public int setContentViewId() {
        return R.layout.activity_selected_book;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initChonSach();
        initEvent();
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
        lisBaitap = new ArrayList<>();
        lisBaitap.add(new BoSach("Cánh diều", R.drawable.canh_dieu));
        lisBaitap.add(new BoSach("Kết nối tri thức", R.drawable.ketnoitrithuc));

        mAdapter = new AdapterBoSach(lisBaitap, this);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        rcvChonSach.setHasFixedSize(true);
        rcvChonSach.setAdapter(mAdapter);
        mAdapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Intent intent = new Intent(ActivityChoseBook.this, ActivityWeeklyExer.class);
                startActivity(intent);
            }
        });
    }

}
