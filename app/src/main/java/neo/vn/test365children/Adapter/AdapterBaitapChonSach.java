package neo.vn.test365children.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

public class AdapterBaitapChonSach extends RecyclerView.Adapter<AdapterBaitapChonSach.TopicViewHoder> {
    private List<Baitap_Tuan> listChildren;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterBaitapChonSach(List<Baitap_Tuan> listAirport, Context context) {
        this.listChildren = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_baitap_chonsach, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, int position) {
        Baitap_Tuan obj = listChildren.get(position);
        holder.tvNameBaitap.setText(obj.getsNAME());
        holder.tvNameRequi.setText(obj.getsREQUIREMENT());
        holder.btnLamBaitap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnIListener.onClickItem(position, obj);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listChildren.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tvNameBaitap)
        TextView tvNameBaitap;
        @BindView(R.id.tvNameRequi)
        TextView tvNameRequi;
        @BindView(R.id.btnLamBaitap)
        Button btnLamBaitap;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
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
