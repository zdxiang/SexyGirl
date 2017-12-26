package cn.zdxiang.sexygirl;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import cn.zdxiang.mysuites.base.SuitesActivity;
import cn.zdxiang.mysuites.ui.AdWebActivity;
import cn.zdxiang.mysuites.utils.ToastUtils;
import cn.zdxiang.sexygirl.base.BaseFragmentStateAdapter;
import cn.zdxiang.sexygirl.base.ResultCallback;
import cn.zdxiang.sexygirl.constant.APPKeys;
import cn.zdxiang.sexygirl.constant.AdCons;
import cn.zdxiang.sexygirl.constant.Apis;
import cn.zdxiang.sexygirl.model.Category;
import cn.zdxiang.sexygirl.model.ImgUrlModel;
import cn.zdxiang.sexygirl.ui.fragment.SexyGirlFragment;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends SuitesActivity implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;


    private ImageView ivHeaderBg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImmersionBar();
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setNavImageInRandom();
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ivHeaderBg = headerView.findViewById(R.id.iv_header_bg);
        navigationView.setNavigationItemSelectedListener(this);
        requestData();
        setNavImageInRandom();
    }

    protected void initImmersionBar() {
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tabLayout.removeOnTabSelectedListener(this);
    }

    private void setNavImageInRandom() {
        List<ImgUrlModel> all = DataSupport.findAll(ImgUrlModel.class);
        if (all == null || all.size() == 0) {
            //设置默认图片
            Log.d("fuckyou", "设置默认图片");
        } else {
            Random rand = new Random();
            int position = rand.nextInt(all.size() - 1);
            Glide.with(this).load(all.get(position).getUrl()).transition(new DrawableTransitionOptions().crossFade(300)).into(ivHeaderBg);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        switch (id) {
            case R.id.nav_shop:
                AdWebActivity.start(MainActivity.this, AdCons.BIAN_XIAN_MAO_TEMAI, getString(R.string.shop));
                MobclickAgent.onEvent(MainActivity.this, "shop");
                break;

            case R.id.nav_earn:
                AdWebActivity.start(MainActivity.this, AdCons.BIAN_XIAN_MAO_CHOU_JIANG, getString(R.string.free_choujiang));
                MobclickAgent.onEvent(MainActivity.this, "choujiang");
                break;

            case R.id.nav_make_money:
                AdWebActivity.start(MainActivity.this, AdCons.BIAN_XIAN_MAO_JIEDAI, getString(R.string.make_money));
                MobclickAgent.onEvent(MainActivity.this, "jiedai");
                break;

            case R.id.nav_live:
                AdWebActivity.start(MainActivity.this, AdCons.BIAN_XIAN_MAO_FULI, getString(R.string.fuli_she));
                MobclickAgent.onEvent(MainActivity.this, "fuli");
                break;
        }
        return true;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        changeTabTextSize(tab, 18);
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        changeTabTextSize(tab, 14);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void changeTabTextSize(TabLayout.Tab tab, int size) {
        View customView = tab.getCustomView();
        if (customView == null) return;
        if (customView instanceof TextView) {
            TextView textView = (TextView) tab.getCustomView();
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("showapi_appid", APPKeys.SHOWAPI_APPID);
        params.put("showapi_sign", APPKeys.SHOWAPI_SECRET);
        OkHttpUtils.get().url(Apis.DOMAIN + Apis.PATH_PICTURE_CATEGORY).params(params).build().execute(new ResultCallback<Category>() {

            @Override
            public void onBefore(Request request, int id) {
                avi.show();
            }

            @Override
            public void onAfter(int id) {
                avi.hide();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(MainActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(Category response, int id) {
                if (response.getShowapi_res_code() == 0) {
                    Category.ShowapiResBodyBean showapi_res_body = response.getShowapi_res_body();
                    if (showapi_res_body != null) {
                        List<Category.ShowapiResBodyBean.ListBeanX> list = showapi_res_body.getList();
                        for (int i = 0; i < list.size(); i++) {
                            String name = list.get(i).getName();
                            if (name.equals("美女图片")) {
                                List<Category.ShowapiResBodyBean.ListBeanX.ListBean> sexyGirlList = list.get(i).getList();
                                List<String> titles = new ArrayList<>();
                                List<Fragment> fragments = new ArrayList<>();
                                for (int j = 0; j < sexyGirlList.size(); j++) {
                                    String title = sexyGirlList.get(j).getName();
                                    titles.add(title);
                                    fragments.add(SexyGirlFragment.newInstance(title, sexyGirlList.get(j).getId()));
                                }
                                initTab(titles, fragments);
                                viewPager.setOffscreenPageLimit(1);
                            }
                        }
                    }
                } else {
                    ToastUtils.show(MainActivity.this, response.getShowapi_res_error());
                }
            }
        });
    }

    private void initTab(List<String> titles, List<Fragment> fragments) {
        BaseFragmentStateAdapter adapter = new BaseFragmentStateAdapter(fragments, titles, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < titles.size(); i++) {
            TextView textView = new TextView(this);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, i == 0 ? 18 : 14);
            textView.setGravity(Gravity.CENTER);
            textView.setText(titles.get(i));
            if (tabLayout.getTabAt(i) != null) {
                tabLayout.getTabAt(i).setCustomView(textView);
            }
        }
    }
}
