package neo.vn.test365children.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.BoSach;
import neo.vn.test365children.Models.City;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterBoSach extends RecyclerView.Adapter<AdapterBoSach.TopicViewHoder> {
    private List<BoSach> listAirport;
    private Context context;
    private ItemClickListener OnIListener;


    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterBoSach(List<BoSach> listAirport, Context context) {
        this.listAirport = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bosach, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        BoSach airport = listAirport.get(position);
        holder.tvNameBook.setText(airport.getName());
        Glide.with(context).load(airport.getImage()).into(holder.imgBook);


    }

    @Override
    public int getItemCount() {
        return listAirport.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tvNameBook)
        TextView tvNameBook;
        @BindView(R.id.imgBook)
        ImageView imgBook;
        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.onClickItem(getLayoutPosition(), listAirport.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<BoSach> list) {
        listAirport = list;
        notifyDataSetChanged();
    }
}
