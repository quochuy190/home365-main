package neo.vn.test365children.MenuSearch;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDanhsachKhoihoc;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;


/**
 * Created by QQ on 10/16/2017.
 */

public class ActivityDanhsachKhoihoc extends BaseActivity {

    private List<String> mLisAirport;
    private AdapterDanhsachKhoihoc adapterService;
    @BindView(R.id.recycle_list_service)
    RecyclerView recycle_service;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.edt_search_service)
    EditText edt_search_service;
    List<String> temp;
    String sUserId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.fragment_service);
        ButterKnife.bind(this);
        KeyboardUtil.hideSoftKeyboard(this);
        //  initData();
        App.mKhoihoc = null;
        edt_search_service.setVisibility(View.VISIBLE);
        initAppbar();
        init();
        initData();
        initEvent();

    }

    TextView txt_title;

    public void initAppbar() {

        ImageView img_back = findViewById(R.id.img_back);
        txt_title = findViewById(R.id.txt_title_main);
        txt_title.setVisibility(View.GONE);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_recycleview;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void initEvent() {
        edt_search_service.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });


    }

    void filter(String text) {
        temp.clear();
        for (String d : mLisAirport) {
            if (d.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                temp.add(d);
            }
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.contains(text)) {

            }
        }
        //update recyclerview
        adapterService.updateList(temp);
       /* adapterService.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                App.mQuantity = temp.get(position);
                finish();
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });*/
       adapterService.setOnIListener(new ItemClickListener() {
           @Override
           public void onClickItem(int position, Object item) {
               App.mKhoihoc = (String) item;
               finish();
           }
       });
    }

    private void init() {
        mLisAirport = new ArrayList<>();
        temp = new ArrayList<>();
        adapterService = new AdapterDanhsachKhoihoc(temp, this);
        mLayoutManager = new GridLayoutManager(this, 1);
        recycle_service.setNestedScrollingEnabled(false);
        recycle_service.setHasFixedSize(true);
        recycle_service.setLayoutManager(mLayoutManager);
        recycle_service.setItemAnimator(new DefaultItemAnimator());
        recycle_service.setAdapter(adapterService);
        adapterService.updateList(temp);
       /* adapterService.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                App.mQuantity = mLisAirport.get(position);
                finish();

            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });*/
       adapterService.setOnIListener(new ItemClickListener() {
           @Override
           public void onClickItem(int position, Object item) {
               App.mKhoihoc = (String) item;
               finish();
           }
       });

    }

    private void initData() {
        mLisAirport.add("1");
        mLisAirport.add("2");
//        mLisAirport.add("3");
//        mLisAirport.add("4");
//        mLisAirport.add("5");
        temp.addAll(mLisAirport);
        adapterService.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
