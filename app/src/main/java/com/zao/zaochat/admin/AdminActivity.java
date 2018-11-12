package com.zao.zaochat.admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.Socket.SocketClient;
import com.zao.zaochat.Socket.SocketServer;
import com.zao.zaochat.WifiTool.WifiTools;
import com.zao.zaochat.fragment.WifiListFragment;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.global.GenerateRandom;
import com.zao.zaochat.myapp.BaseActivity;
import com.zao.zaochat.object.SocketUser;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.widget.CreatingDialog;

import java.io.File;

public class AdminActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private static final String TAG = "AdminActivity";

    RelativeLayout rl_home;
    RelativeLayout rl_status;
    RelativeLayout rl_group;
    RelativeLayout rl_profile;
    /**
     * 初始化四个Fragment
     */
    private WifiListFragment homeFragment;
    private StatusFragment   statusFragment;
    private GroupFragment   groupFragment;
    private ProfileFragment   profileFragment;
    //设置修改图片
    ImageView iv_home;
    ImageView iv_status;
    ImageView iv_group;
    ImageView iv_profile;
    //设置修改文字
    TextView  tv_onezao_home;
    TextView  tv_onezao_status;
    TextView  tv_onezao_group;
    TextView  tv_onezao_profile;

    private WifiTools wifiTools;
    private SocketClient socketClient;
    private SocketServer socketServer;
    private SocketUser socketUser;

    private CreatingDialog creatingDialog;
    private String userIcon;
    /**
     * Fragment
     */
    FragmentManager manager;
    /**
     * 监听来自子线程的消息
     *
     * @return
     */
/*    public Handler getMainHandler() {
        return mainHandler;
    }

    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            {
                switch (msg.what) {
                    case ConstantC.CLIENT_PREPARE:
                        Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                        intent.putExtra(ConstantC.CLIENT_SERVER_TAG,ConstantC.CLIENT_PREPARE);
                        startActivity(intent);
                        break;
                    case ConstantC.CLIENT_CONNECT_SERVER:
                        ToastUtil.showT(getApplicationContext(), ConstantC.CONNECT_ROOM_OK);
                        ChatRoomActivity chat = new ChatRoomActivity();
                        chat.setSign(ConstantC.CLIENT_SIGN);
                        chat.setSocketUser(socketUser);
                        socketClient.setSocketUser(socketUser);
                        creatingDialog.dismiss();
                        Intent intent_client = new Intent(getApplicationContext(),ChatRoomActivity.class);
                        intent_client.putExtra(ConstantC.SIGN_CLIENT_SERVER,ConstantC.CLIENT_SIGN);
                        startActivity(intent_client);
                        break;
                    case ConstantC.WIFI_AP_ENABLED:
                        socketServer.setSocketUser(socketUser);
                        ChatRoomActivity chat2 = new ChatRoomActivity();
                        Intent intent_server = new Intent(getApplicationContext(),ChatRoomActivity.class);
                        intent_server.putExtra(ConstantC.SIGN_CLIENT_SERVER,ConstantC.SERVER_SIGN);
                        startActivity(intent_server);
                        Log.i(TAG,"已经切换");
                        chat2.setSign(ConstantC.SERVER_SIGN);
                        chat2.setSocketUser(socketUser);
                        creatingDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }
    };*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nav);

        initParameter();
        initDrawer();
        initView();
        initHome();
    }


    /**
     * 初始化相关的参数
     */
    void initParameter() {
        socketUser = new SocketUser(ConstantC.USER_DEFAULT_NAME, ConstantC.USER_DEFAULT_ILLUSTRATION, "man", "2", GenerateRandom.randomW());

        wifiTools = WifiTools.getInstance(this);
        socketClient = new SocketClient();
        socketServer = new SocketServer();
        wifiTools.closeHotSpot();
        wifiTools.openWifi();
        userIcon = "1";
        creatingDialog = new CreatingDialog(this, getLayoutInflater(), R.layout.dialog_creating, getResources().getString(R.string.dialog_creating_title));
    }

    private void initView() {
        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        rl_home.setOnClickListener(this);
        rl_status = (RelativeLayout) findViewById(R.id.rl_status);
        rl_status.setOnClickListener(this);
        rl_group = (RelativeLayout) findViewById(R.id.rl_group);
        rl_group.setOnClickListener(this);
        rl_profile = (RelativeLayout) findViewById(R.id.rl_profile);
        rl_profile.setOnClickListener(this);

        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_status = (ImageView) findViewById(R.id.iv_status);
        iv_group = (ImageView) findViewById(R.id.iv_group);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

        tv_onezao_home = (TextView) findViewById(R.id.tv_onezao_home);
        tv_onezao_status = (TextView) findViewById(R.id.tv_onezao_status);
        tv_onezao_group = (TextView) findViewById(R.id.tv_onezao_group);
        tv_onezao_profile = (TextView) findViewById(R.id.tv_onezao_profile);
    }

    /**
     * 初始化左侧的抽屉
     */
    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * 侧滑栏的顶部布局，header
         */
        View headerView = navigationView.getHeaderView(0);
        TextView tv_nav_header_desc = (TextView) headerView.findViewById(R.id.tv_nav_header_desc);
        TextView tv_nav_header_name = (TextView) headerView.findViewById(R.id.tv_nav_header_name);
        ImageView ivNavHeader = (ImageView) headerView.findViewById(R.id.iv_nav_header);
        ivNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LogUtil.i("点击了侧滑栏的头像");
            }
        });
    }



    /**
     * 侧滑栏目的点击事件
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_camera :
                LogUtil.i("点击了nav_camera");
                break;
            case R.id.nav_gallery :
                LogUtil.i("点击了nav_gallery");
                break;
            case R.id.nav_slideshow :
                LogUtil.e("点击了nav_slideshow");
                showFile();
                break;
            case R.id.nav_manage :
                LogUtil.i("点击了nav_manage");
                break;
            case R.id.nav_share :
                LogUtil.i("点击了nav_share");
                break;
            case R.id.nav_send :
                LogUtil.i("点击了nav_send");
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 显示收到的文件
     */
    private void showFile() {
            File file = new File(ConstantC.ZAO_CHAT_PATH);


            if(null==file || !file.exists()){
                LogUtil.i("没有接收到任何文件。");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri data;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                data = FileProvider.getUriForFile(this, "com.zao.zaochat.fileprovider", file);
            } else {
                data = Uri.fromFile(file);
            }
            LogUtil.e(data.toString());
            intent.setDataAndType(data, "*/*");
//            intent.setDataAndType(Uri.fromFile(file), "file/*");
            try {
                startActivity(intent);
//            startActivity(Intent.createChooser(intent,"选择浏览工具"));
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }

    /**
     * 监听并控制返回按钮的点击事件
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 单个控件的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switchFour(view);
    }

    /**
     * 底部的四个按钮的状态切换，并结合Fragment
     * @param view
     */
    private void switchFour(View view) {
        switch (view.getId()){
            case R.id.rl_home :
                initHome();
                break;

            case R.id.rl_status :
                switchFragment(2);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_status.setImageResource(R.mipmap.ic_tab_status_active);
                tv_onezao_status.setTextColor(getResources().getColor(R.color.colorGreen));
                break;

            case R.id.rl_group :
                switchFragment(3);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_group.setImageResource(R.mipmap.ic_tab_group_active);
                tv_onezao_group.setTextColor(getResources().getColor(R.color.colorGreen));
                break;

            case R.id.rl_profile :
                switchFragment(4);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_profile.setImageResource(R.mipmap.ic_tab_profile_active);
                tv_onezao_profile.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
        }
    }

    //初始化首页数据
    private void initHome() {
        //获取fragment  管理者
        manager = getSupportFragmentManager();
        switchFragment(1);
        //将四个的图片全部设置为未点击状态。
        clearIcon();
        iv_home.setImageResource(R.mipmap.ic_tab_home_active);
        tv_onezao_home.setTextColor(getResources().getColor(R.color.colorGreen));

        /**
         * 如果wifi没有开启，则开启wifi
         */
        if(!wifiTools.isWifiConnected(this)){
            wifiTools.openWifi();
        }
    }

    /**
     * 将底部的四个tab置为没有选中状态。
     */
    private  void  clearIcon(){
        iv_home.setImageResource(R.mipmap.ic_tab_home_normal);
        iv_status.setImageResource(R.mipmap.ic_tab_status_normal);
        iv_group.setImageResource(R.mipmap.ic_tab_group_normal);
        iv_profile.setImageResource(R.mipmap.ic_tab_profile_normal);
        tv_onezao_home.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_status.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_group.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_profile.setTextColor(getResources().getColor(R.color.colorText));
    }

    /**
     * 顶部弹出的菜单栏的控制和点击事件
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*        //设置左上角的图标的点击事件  ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);*/
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdminUtils.AdminMenu(AdminActivity.this, item);
        return super.onOptionsItemSelected(item);
    }



    void switchFragment(int index) {

        switch (index) {
            case 1:
                if (statusFragment != null) {
                    manager.beginTransaction().hide(statusFragment).commit();
                }
                if (groupFragment != null) {
                    manager.beginTransaction().hide(groupFragment).commit();
                }
                if (profileFragment != null) {
                    manager.beginTransaction().hide(profileFragment).commit();
                }
                Log.i(TAG, "切换" + index);
                if (homeFragment == null) {
                    homeFragment = new WifiListFragment();
                    manager.beginTransaction().add(R.id.fragment_onezao_0403, homeFragment).commit();
                } else {
                    Log.i(TAG, "已初始化，切换" + index);
                    manager.beginTransaction().show(homeFragment).commit();
                }
                break;
          case 2:
              if (homeFragment != null) {
                  manager.beginTransaction().hide(homeFragment).commit();
              }
              if (groupFragment != null) {
                  manager.beginTransaction().hide(groupFragment).commit();
              }
              if (profileFragment != null) {
                  manager.beginTransaction().hide(profileFragment).commit();
              }
              Log.i(TAG, "切换" + index);
              if (statusFragment == null) {
                  statusFragment = new StatusFragment();
                  manager.beginTransaction().add(R.id.fragment_onezao_0403, statusFragment).commit();
              } else {
                  Log.i(TAG, "已初始化，切换" + index);
                  manager.beginTransaction().show(statusFragment).commit();
              }
                break;
            case 3:
                if (homeFragment != null) {
                    manager.beginTransaction().hide(homeFragment).commit();
                }
                if (statusFragment != null) {
                    manager.beginTransaction().hide(statusFragment).commit();
                }
                if (profileFragment != null) {
                    manager.beginTransaction().hide(profileFragment).commit();
                }
                Log.i(TAG, "切换" + index);
                if (groupFragment == null) {
                    groupFragment = new GroupFragment();
                    manager.beginTransaction().add(R.id.fragment_onezao_0403, groupFragment).commit();
                } else {
                    Log.i(TAG, "已初始化，切换" + index);
                    manager.beginTransaction().show(groupFragment).commit();
                }
                break;
            case 4:
                if (homeFragment != null) {
                    manager.beginTransaction().hide(homeFragment).commit();
                }
                if (statusFragment != null) {
                    manager.beginTransaction().hide(statusFragment).commit();
                }
                if (groupFragment != null) {
                    manager.beginTransaction().hide(groupFragment).commit();
                }
                Log.i(TAG, "切换" + index);
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                    manager.beginTransaction().add(R.id.fragment_onezao_0403, profileFragment).commit();
                } else {
                    Log.i(TAG, "已初始化，切换" + index);
                    manager.beginTransaction().show(profileFragment).commit();
                }
                break;
            default:
                break;
        }

    }
}
