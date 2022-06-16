package neo.vn.test365children.Activity.weeklyExercises.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityStartBaitap;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Activity.ReviewExercises.ActivityExercisesDetail;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.DETAILSItem;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterExerTaken extends RecyclerView.Adapter<AdapterExerTaken.TopicViewHoder> {
    private List<DETAILSItem> listAirport;
    private Context context;

    public AdapterExerTaken(List<DETAILSItem> listAirport, Context context) {
        this.listAirport = listAirport;
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
        DETAILSItem obj = listAirport.get(position);
        holder.txt_name.setText(obj.getSUBJECTNAME());
        switch (obj.getSUBJECTID()) {
            case 1:
                //holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_maths);
                //  Glide.with(context).load(R.drawable.ic_exer_maths).into(holder.img_item_menu_lambai);
                break;
            case 2:
                //holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_literature);
                //   Glide.with(context).load(R.drawable.ic_exer_literature).into(holder.img_item_menu_lambai);
                break;
            case 3:
               // holder.txt_name.setText(obj.getsSUBJECT_NAME());
                holder.img_item_menu_lambai.setImageResource(R.drawable.ic_exer_eng);
                //   Glide.with(context).load(R.drawable.ic_exer_eng).into(holder.img_item_menu_lambai);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listAirport.size();
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
            //KeyboardUtil.play_click_button(context.g);
//            Baitap_Tuan obj = (Baitap_Tuan) listAirport.get(getLayoutPosition());
//            Intent intent = new Intent(context, ActivityStartBaitap.class);
//            intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
//            context.startActivity(intent);
          //  context.startActivity(new Intent(context, ActivityLambaitap.class));
            Intent intent = new Intent(context, ActivityExercisesDetail.class);
            intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL, listAirport.get(getLayoutPosition()).getEXERCISEID() + "");
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<DETAILSItem> list) {
        listAirport = list;
        notifyDataSetChanged();
    }
}
