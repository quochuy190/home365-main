package neo.vn.test365children.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterItemMenuLambaitap extends RecyclerView.Adapter<AdapterItemMenuLambaitap.TopicViewHoder> {
    private List<Baitap_Tuan> listChildren;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterItemMenuLambaitap(List<Baitap_Tuan> listAirport, Context context) {
        this.listChildren = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu_lambaitap, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, int position) {
        Baitap_Tuan obj = listChildren.get(position);
        //  holder.txt_name.setText(obj.getsSUBJECT_NAME());
        switch (obj.getsSUBJECT_ID()) {
            case "1":
                holder.txt_name.setText("Toán" + " - Tuần " + obj.getsWEEK_ID());
                Glide.with(context).load(R.drawable.ic_exer_maths).into(holder.img_item_menu_lambai);
                break;
            case "2":
                holder.txt_name.setText("Tiếng Việt" + " - Tuần " + obj.getsWEEK_ID());
                Glide.with(context).load(R.drawable.ic_exer_literature).into(holder.img_item_menu_lambai);
                break;
            case "3":
                holder.txt_name.setText("Tiếng Anh" + " - Tuần " + obj.getsWEEK_ID());
                Glide.with(context).load(R.drawable.ic_exer_eng).into(holder.img_item_menu_lambai);
                break;
        }
      /*  switch (obj.getsSUBJECT_ID()) {
            case "1":
                Glide.with(context).load(R.drawable.img_menu_toan).into(holder.img_item_menu_lambai);
                break;
            case "2":
                Glide.with(context).load(R.drawable.img_menu_blue).into(holder.img_item_menu_lambai);
                break;
            case "3":
                Glide.with(context).load(R.drawable.img_menu_tim).into(holder.img_item_menu_lambai);
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return listChildren.size();
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

        @Override
        public void onClick(View v) {
            KeyboardUtil.animation_click_button(context, img_item_menu_lambai);
            OnIListener.onClickItem(getLayoutPosition(), listChildren.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<Baitap_Tuan> list) {
        listChildren = list;
        notifyDataSetChanged();
    }
}
