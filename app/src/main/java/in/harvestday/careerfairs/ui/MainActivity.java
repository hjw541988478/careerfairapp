package in.harvestday.careerfairs.ui;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.harvestday.careerfairs.R;
import in.harvestday.careerfairs.bean.CareerTalk;
import in.harvestday.careerfairs.service.CareerTalkService;
import in.harvestday.careerfairs.http.OkHttpClientManager;
import in.harvestday.library.HarvestRecyclerView;

public class MainActivity extends AppCompatActivity implements CareerTalkService.CareerFairRequestListener {
    @Bind(R.id.id_toolbar)
    Toolbar toolbar;

    @Bind(R.id.id_list)
    HarvestRecyclerView mRecyclerView;

    @OnClick(R.id.id_fab)
    void onRefresh() {
        manager.scrollToPosition(0);
        refresh();
    }

    @Bind(R.id.id_drawer_nav_view)
    NavigationView navView;

    @Bind(R.id.id_drawer_layout)
    DrawerLayout navDrawer;

    CareerTalkListAdapter adapter;
    LinearLayoutManager manager;
    HarvestRecyclerView.OnRefreshListener onRefreshListener;
    HarvestRecyclerView.OnLoadMoreListener onLoadMoreListener;

    private void refresh() {
        isRefresh = true;
        page = 1;
        isLoading = true;
        CareerTalkService.getInstance().fetchList(page, uid, pageSize, MainActivity.this);
    }

    private int page = 1;
    private int uid = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpClientManager.cancelTag(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initData();
    }


    private void initData() {
        mRecyclerView.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 800);
    }

    private void initViews() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.id_menu_csu:
                        uid = 1;
                        break;
                    case R.id.id_menu_hu:
                        uid = 2;
                        break;

                }
                refresh();
                menuItem.setCheckable(true);
                navDrawer.closeDrawers();
                return true;
            }
        });

        navView.setCheckedItem(R.id.id_menu_csu);

        manager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setEmptyView(getResources().getDrawable(R.drawable.ic_results_empty), "no data", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        onRefreshListener = new HarvestRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading)
                    return;
                refresh();
            }
        };
        onLoadMoreListener = new HarvestRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (isLoading)
                    return;
                isLoading = true;
                isRefresh = false;
                CareerTalkService.getInstance().fetchList(++page, uid, pageSize, MainActivity.this);
            }
        };
        adapter = new CareerTalkListAdapter(MainActivity.this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setCustomFooter(LayoutInflater.from(MainActivity.this).inflate(R.layout.view_load_more, null), null);
        mRecyclerView.setRefreshEnabled(true, onRefreshListener);
        mRecyclerView.setLoadMoreEnabled(true, onLoadMoreListener);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            navDrawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(List<CareerTalk> talks) {
        if (talks.size() > 0) {
            if (isRefresh) {
                adapter.data.clear();
            }
            adapter.data.addAll(talks);
            adapter.notifyDataSetChanged();
            if (isRefresh)
                manager.scrollToPosition(0);
        }
        isLoading = false;
    }

    @Override
    public void onFailure(String errMsg) {
        isLoading = false;
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
