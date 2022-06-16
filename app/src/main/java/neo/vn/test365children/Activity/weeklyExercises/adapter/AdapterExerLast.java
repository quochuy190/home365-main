package neo.vn.test365children.Activity.weeklyExercises.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityLambaitap;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Activity.weeklyExercises.ActivityChoseBook;
import neo.vn.test365children.App;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterExerLast extends RecyclerView.Adapter<AdapterExerLast.TopicViewHoder> {
    private List<Baitap_Tuan> mList;
    private List<Baitap_Tuan> listData;
    private Context context;

    public AdapterExerLast(List<Baitap_Tuan> listAirport, Context context) {
        this.mList = listAirport;
        this.listData = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_lambaitap, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        Baitap_Tuan obj = mList.get(position);
        //  holder.txt_name.setText(obj.getsSUBJECT_NAME());
        switch (obj.getsSUBJECT_ID()) {
            case "1":
                holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_maths);
                //  Glide.with(context).load(R.drawable.ic_exer_maths).into(holder.img_item_menu_lambai);
                break;
            case "2":
                holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_literature);
                //   Glide.with(context).load(R.drawable.ic_exer_literature).into(holder.img_item_menu_lambai);
                break;
            case "3":
                holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_eng);
                //   Glide.with(context).load(R.drawable.ic_exer_eng).into(holder.img_item_menu_lambai);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_menu_lambai)
        TextView txt_name;
        @BindView(R.id.img_item_menu_lambai)
        ImageView img_item_menu_lambai;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            //KeyboardUtil.play_click_button(context.g);
            Baitap_Tuan obj = (Baitap_Tuan) mList.get(getLayoutPosition());
            Intent intent = new Intent(context, ActivityChoseBook.class);
            intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
            App.mBaiTapTuan.clear();

            List<Baitap_Tuan> mListData = listData.stream()
                    .filter(p -> p.getsSUBJECT_ID().equals(obj.getsSUBJECT_ID())).collect(Collectors.toList());
            App.mBaiTapTuan.addAll(mListData);
            context.startActivity(intent);
          //  context.startActivity(new Intent(context, ActivityLambaitap.class));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateList(List<Baitap_Tuan> list) {
        mList.clear();
        listData.clear();
        listData.addAll(list);
        List<Baitap_Tuan> mListToan = listData.stream()
                .filter(p -> p.getsSUBJECT_ID().equals("1")).collect(Collectors.toList());
        if (mListToan!=null&&mListToan.size()>0){
            Baitap_Tuan objToan = mListToan.get(0);
            objToan.setsSUBJECT_NAME("Môn Toán");
            mList.add(objToan);
        }
        List<Baitap_Tuan> mListTV = listData.stream()
                .filter(p -> p.getsSUBJECT_ID().equals("2")).collect(Collectors.toList());
        if (mListTV!=null&&mListTV.size()>0){
            Baitap_Tuan objTV = mListTV.get(0);
            objTV.setsSUBJECT_NAME("Môn Tiếng Việt");
            mList.add(objTV);
        }
        List<Baitap_Tuan> mListTA = listData.stream()
                .filter(p -> p.getsSUBJECT_ID().equals("3")).collect(Collectors.toList());
        if (mListTA!=null&&mListTA.size()>0){
            Baitap_Tuan objTA = mListTA.get(0);
            objTA.setsSUBJECT_NAME("Môn Tiếng Anh");
            mList.add(objTA);
        }
        notifyDataSetChanged();
    }
}
