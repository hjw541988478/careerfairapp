package in.harvesday.careerfairs.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.harvesday.careerfairs.R;
import in.harvesday.careerfairs.bean.CareerFair;
import in.harvesday.careerfairs.parse.SpiderEngine;

/**
 * Created by hjw on 2015/9/10 0010.
 */
public class CareerFairsActivity extends AppCompatActivity {

    private List<CareerFair> curFairs = new LinkedList<>();
    RecyclerViewListAdapter adapter = null;
    private int page = 1;

    @Bind(R.id.id_toolbar)
    Toolbar toolbar;

    @Bind(R.id.id_list)
    RecyclerView list;

    @Bind(R.id.id_fab)
    FloatingActionButton fab;

    @Bind(R.id.id_drawer_nav_view)
    NavigationView navView;

    @Bind(R.id.id_drawer_layout)
    DrawerLayout navDrawer;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x100)
                adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careerfairs_list);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setCheckable(true);
                navDrawer.closeDrawers();
                return true;
            }
        });

        adapter = new RecyclerViewListAdapter();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
        new CareerFairTask().execute(1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            navDrawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    class CareerFairTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                SpiderEngine.initConfigs();
                List<String> adds = SpiderEngine.getCarrerFairLinks(params[0]);
                curFairs.clear();
                curFairs.addAll(SpiderEngine.getCareerFairListDetails(adds));
                if (curFairs.size() != 0)
                    mHandler.sendEmptyMessage(0x100);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_careerfair, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.time.setText(curFairs.get(position).careerFairStartTime);
            holder.addr.setText(curFairs.get(position).careerFairAddr);
            holder.cop.setText(curFairs.get(position).careerFairCopName);
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
//                    intent.putExtra("title", data.get(position));
//                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return curFairs.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.id_cf_time)
            TextView time;

            @Bind(R.id.id_cf_addr)
            TextView addr;

            @Bind(R.id.id_cf_cop)
            TextView cop;

            View rootView;

            public MyViewHolder(View itemView) {
                super(itemView);
                rootView = itemView;
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
