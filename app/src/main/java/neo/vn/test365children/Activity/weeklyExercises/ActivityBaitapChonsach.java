package neo.vn.test365children.Activity.weeklyExercises;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Adapter.AdapterBaitapChonSach;
import neo.vn.test365children.Adapter.AdapterBoSach;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.BoSach;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;

/**
 * Created 11/03/2022 at 15:49
 * Company: VHM
 */
public class ActivityBaitapChonsach extends BaseActivity {
    @BindView(R.id.rcvBaitapChonSach)
    RecyclerView rcvBaitapChonSach;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txtDataEmpty)
    TextView txtDataEmpty;
    List<Baitap_Tuan> mList;
    AdapterBaitapChonSach mAdapter;
    int idBosach;
    @Override
    public int setContentViewId() {
        return R.layout.activity_baitap_chonbosach;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initChonSach();
        initEvent();
    }

    private void initData() {
        idBosach = getIntent().getIntExtra(Constants.KEY_SEND_ID_BOSACH, 0);
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initChonSach() {
        try {
            mList = new ArrayList<>();
            if (App.mBaiTapTuan.size()==0)
                return;

            List<Baitap_Tuan> mListData = App.mBaiTapTuan.stream().filter(p -> p.getBOOK_ID()!=null)
                    .filter(p -> p.getBOOK_ID().equals(""+idBosach)).collect(Collectors.toList());
            if (mListData!=null&&mListData.size()>0){
                txtDataEmpty.setVisibility(View.GONE);
            }else
                txtDataEmpty.setVisibility(View.VISIBLE);
            mList.addAll(mListData);
            Log.e("TAG", "initChonSach: "+App.mBaiTapTuan.size() );
            mAdapter = new AdapterBaitapChonSach(mList, this);
            //    recycleBaitap.setNestedScrollingEnabled(false);
            rcvBaitapChonSach.setHasFixedSize(true);
            rcvBaitapChonSach.setAdapter(mAdapter);
            mAdapter.setOnIListener(new ItemClickListener() {
                @Override
                public void onClickItem(int position, Object item) {
                    KeyboardUtil.play_click_button(ActivityBaitapChonsach.this);
                    Baitap_Tuan obj = (Baitap_Tuan) item;
                    Intent intent = new Intent(ActivityBaitapChonsach.this, ActivityStartBaitap.class);
                    intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
