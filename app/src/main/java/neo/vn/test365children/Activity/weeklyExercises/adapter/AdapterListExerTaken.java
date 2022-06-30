package neo.vn.test365children.Activity.weeklyExercises.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Models.ExerLastObj;
import neo.vn.test365children.Models.ExerTakenObj;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListExerTaken extends RecyclerView.Adapter<AdapterListExerTaken.TopicViewHoder> {
    private List<ExerTakenObj> listAirport;
    private Context context;

    public AdapterListExerTaken(List<ExerTakenObj> listAirport, Context context) {
        this.listAirport = listAirport;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_exer_last, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        ExerTakenObj airport = listAirport.get(position);
        holder.tvTitleExer.setText(""+airport.getTitle());
        AdapterExerTaken checkBoxAdapter = new AdapterExerTaken(airport.getmList(), context);
        holder.rcvExerLast.setAdapter(checkBoxAdapter);

        holder.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!airport.isExtend()){
                    holder.rcvExerLast.setVisibility(View.GONE);
                    airport.setExtend(!airport.isExtend());
                    holder.imgDown.setRotation(180);
                }else{
                    holder.rcvExerLast.setVisibility(View.VISIBLE);
                    airport.setExtend(!airport.isExtend());
                    holder.imgDown.setRotation(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAirport.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.rcvExerLast)
        RecyclerView rcvExerLast;
        @BindView(R.id.tvTitleExer)
        TextView tvTitleExer;
        @BindView(R.id.imgDown)
        ImageView imgDown;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          //  context.startActivity(new Intent(context, ActivityLambaitap.class));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<ExerTakenObj> list) {
        listAirport = list;
        notifyDataSetChanged();
    }
}
