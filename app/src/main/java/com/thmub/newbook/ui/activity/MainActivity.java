package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseActivity;
import com.thmub.newbook.bean.bmob.MyUser;
import com.thmub.newbook.model.remote.BmobRepository;
import com.thmub.newbook.ui.adapter.TabFragmentPageAdapter;
import com.thmub.newbook.ui.fragment.BookShelfFragment;
import com.thmub.newbook.ui.fragment.BookStoreFragment;
import com.thmub.newbook.ui.fragment.DiscoverFragment;
import com.thmub.newbook.utils.SnackbarUtils;
import com.thmub.newbook.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 主界面activity
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*************************Constant**************************/
    public static final int REQUEST_LAND = 1;
    public static final int REQUEST_USER_INFO = 2;

    /*****************************View********************************/

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_tab_title)
    TabLayout mainTabTitle;
    @BindView(R.id.main_vp_content)
    ViewPager mainVpContent;

    private View drawerHeader;
    private ImageView drawerIv;
    private TextView drawerTvAccount, drawerTvMail;
    private Switch swNightMode;

    /*****************************Variable********************************/

    private TabFragmentPageAdapter tabAdapter;

    private MyUser currentUser;

    /*****************************Initialization********************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initSwipeBackEnable() {
        return false;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //上述方法在androidx中废弃
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawerHeader = navigationView.inflateHeaderView(R.layout.app_drawer_header);
        drawerIv = drawerHeader.findViewById(R.id.drawer_iv);
        drawerTvAccount = drawerHeader.findViewById(R.id.drawer_tv_name);
        drawerTvMail = drawerHeader.findViewById(R.id.drawer_tv_mail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        tabAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new BookShelfFragment(), "书架");
        tabAdapter.addFragment(new BookStoreFragment(), "书城");
        tabAdapter.addFragment(new DiscoverFragment(), "发现");

        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //header
        refreshDrawerHeader();

        //viewPage
        mainVpContent.setAdapter(tabAdapter);
        mainVpContent.setOffscreenPageLimit(3);
        mainTabTitle.setupWithViewPager(mainVpContent);

        //夜间模式
        swNightMode = navigationView.getMenu()
                .findItem(R.id.action_night).getActionView().findViewById(R.id.menu_switch);
        swNightMode.setChecked(isNightTheme());
    }

    @Override
    protected void initClick() {
        super.initClick();
        //监听菜单栏头部
        drawerHeader.setOnClickListener(view -> {
            //用户不存在则跳转登录界面
            if (currentUser == null)
                startActivityForResult(new Intent(mContext, UserLandActivity.class), REQUEST_LAND);
            else
                startActivityForResult(new Intent(mContext, FragmentActivity.class)
                                .putExtra(FragmentActivity.EXTRA_FRAGMENT_TYPE, FragmentActivity.FRAGMENT_TYPE_USER_INFO)
                        , REQUEST_USER_INFO);
        });

        //侧滑菜单栏
        navigationView.setNavigationItemSelectedListener(this);

        //监听夜间模式switch
        swNightMode.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isPressed()) {
                setNightTheme(b);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @OnClick({R.id.toolbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }


    /*****************************Event********************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_bar, menu);
        return true;
    }

    /**
     * 导航栏菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_source:  //书源管理
                startActivity(new Intent(this, BookSourceActivity.class));
                break;
            case R.id.action_download:  //下载管理
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.action_replace:  //替换管理
                startActivity(new Intent(this, ReplacementActivity.class));
                break;
            case R.id.action_setting:  //设置
                startActivity(new Intent(this, FragmentActivity.class)
                        .putExtra(FragmentActivity.EXTRA_FRAGMENT_TYPE, FragmentActivity.FRAGMENT_TYPE_SETTING));
                break;
            case R.id.action_about:  //关于
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.action_night:  //夜间模式
                setNightTheme(!isNightTheme());
                swNightMode.setChecked(isNightTheme());
                break;
            case R.id.action_sync:  //同步书架
                if (currentUser == null)
                    ToastUtils.show(mContext, "请先登陆");
                else
                    BmobRepository.getInstance().syncBook(currentUser.getObjectId());
                break;
        }
        //关窗
        drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 处理返回事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果登陆账户，则刷新Drawer
        if (requestCode == REQUEST_LAND || requestCode == REQUEST_USER_INFO) {
            currentUser = BmobUser.getCurrentUser(MyUser.class);
            refreshDrawerHeader();
        }
    }

    /**
     * 刷新用户信息
     */
    private void refreshDrawerHeader() {
        if (currentUser == null) {
            drawerIv.setImageDrawable(getDrawable(R.mipmap.ic_default_portrait));
            drawerTvAccount.setText("账户");
            drawerTvMail.setText("点我登陆");
            return;
        }
        if (currentUser.getPortrait() != null)
            Glide.with(mContext).load(currentUser.getPortrait()).into(drawerIv);
        drawerTvAccount.setText(currentUser.getUsername());
        drawerTvMail.setText(currentUser.getEmail());
    }
}
