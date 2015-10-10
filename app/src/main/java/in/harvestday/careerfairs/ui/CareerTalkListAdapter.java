package in.harvestday.careerfairs.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.harvestday.careerfairs.R;
import in.harvestday.careerfairs.bean.CareerTalk;
import in.harvestday.library.HarvestRecyclerViewAdapter;

/**
 * Created by hjw on 15/10/10.
 */
public class CareerTalkListAdapter extends HarvestRecyclerViewAdapter {

    public List<CareerTalk> data = new LinkedList<>();

    private Context context;

    public CareerTalkListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CareerTalkHolder onCreateViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_careerfair, parent, false);
        return new CareerTalkHolder(root);
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CareerTalkHolder) {
            ((CareerTalkHolder) holder).time.setText(data.get(position).startDatetime);
            ((CareerTalkHolder) holder).addr.setText(data.get(position).hostRoom);
            ((CareerTalkHolder) holder).cop.setText(data.get(position).copName);
            ((CareerTalkHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailActivity.class);
                    intent.putExtra("url", data.get(position).url);
                    context.startActivity(intent);
                }
            });
        }
    }

    class CareerTalkHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.id_cf_time)
        TextView time;

        @Bind(R.id.id_cf_addr)
        TextView addr;

        @Bind(R.id.id_cf_cop)
        TextView cop;

        View rootView;

        public CareerTalkHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
