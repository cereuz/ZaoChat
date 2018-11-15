package com.zao.zaochat.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.zao.zaochat.R;
import com.zao.zaochat.Socket.SocketClient;
import com.zao.zaochat.Socket.SocketServer;
import com.zao.zaochat.WifiTool.WifiTools;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.global.GenerateRandom;
import com.zao.zaochat.object.SocketUser;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.utils.TextUse;
import com.zao.zaochat.widget.CreatingDialog;

public class CreateRoomActivity extends AppCompatActivity implements TextWatcher,CompoundButton.OnCheckedChangeListener,View.OnFocusChangeListener,View.OnClickListener {

        TextInputLayout til_create_room_name;
        TextInputLayout til_create_room_password;
        AppCompatEditText acet_create_room_name;
        AppCompatEditText acet_create_room_password;
        RadioButton rb_create_with_password;
        RadioButton rb_create_no_password;
        ImageView iv_password_see_no;
        ImageView iv_back;
        ImageView iv_choosen;

    private Context mContext;
    /**
     * 密码是否可见状态的切换
     */
    private static boolean password_seen = false;
    /**
     * 输入框被选中状态，1：第一个（名称）被选中，2：第二个（密码）被选中
     */
    private static int on_focus = 1;
    /**
     * RadioButton是否选中状态
     */
    private static boolean radio_button_check = true;

    private WifiTools wifiTools;
    private CreatingDialog creatingDialog;

    /**
     * Socket相关的
     */
    private SocketClient socketClient;
    private SocketServer socketServer;
    private SocketUser socketUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mContext=this;
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        til_create_room_name = (TextInputLayout)findViewById(R.id.til_create_room_name);
        til_create_room_password = (TextInputLayout)findViewById(R.id.til_create_room_password);

        acet_create_room_name = (AppCompatEditText)findViewById(R.id.acet_create_room_name);
        acet_create_room_name.setOnFocusChangeListener(this);
        acet_create_room_name.addTextChangedListener(this);
        acet_create_room_password = (AppCompatEditText)findViewById(R.id.acet_create_room_password);
        acet_create_room_password.setOnFocusChangeListener(this);
        acet_create_room_password.addTextChangedListener(this);

        rb_create_with_password = (RadioButton)findViewById(R.id.rb_create_with_password);
        rb_create_with_password.setOnCheckedChangeListener(this);
        rb_create_no_password = (RadioButton)findViewById(R.id.rb_create_no_password);
        rb_create_no_password.setOnCheckedChangeListener(this);

        iv_password_see_no = (ImageView)findViewById(R.id.iv_password_see_no);
        iv_password_see_no.setOnClickListener(this);

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_choosen = (ImageView)findViewById(R.id.iv_choosen);
        iv_choosen.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /**
         * 初始化的时候，设置默认有密码
         */
        rb_create_with_password.setChecked(radio_button_check);

        /**
         * 初始化加载框
         */
        creatingDialog = new CreatingDialog(this, getLayoutInflater(), R.layout.dialog_creating, getResources().getString(R.string.dialog_creating_title));
        /**
         * 初始化socketClient  ----- socketServer  ---------- SocketUser
         */
        socketClient = new SocketClient();
        socketServer = new SocketServer();
        socketUser = new SocketUser(ConstantC.USER_DEFAULT_NAME, ConstantC.USER_DEFAULT_ILLUSTRATION, "man", "NOICON", GenerateRandom.randomW());
    }


    public SocketServer getSocketServer() {
        return socketServer;
    }

    public SocketClient getSocketClient() {
        return socketClient;
    }

    public SocketUser getSocketUser() {
        return socketUser;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.e("beforeTextChanged ===" + s + "=" + s.length() + "=" + start + "=" + count + "=" + after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.e("onTextChanged ===" + s + "=" + s.length() + "=" + start + "=" + count + "=" + after);
                int length = s.toString().replace(" ", "").length();
        /**
         * 1：第一个（名称）被选中，2：第二个（密码）被选中
         */
             if(on_focus == 1){
                 if(TextUse.containSpace(s)){
                     til_create_room_name.setHint("名称不能包含空格");
                 } else if(length < 2){
/*                      til_create_room_name.setError("名称不能为空");
                      til_create_room_name.setErrorEnabled(true);*/
                       til_create_room_name.setHint("名称至少为2个字符");
                      LogUtil.e("名称至少为2个字符");
                  } else {
                      til_create_room_name.setErrorEnabled(false);
                       til_create_room_name.setHint("名称");
                  }
                } else if (on_focus == 2){
                 if(TextUse.containSpace(s)){
                     til_create_room_password.setHint("密码不能包含空格");
                 } else
                 if(length < 8){
//                       til_create_room_password.setHint(new SpannedString("密码不能为空"));
/*                       til_create_room_password.setError("密码不能为空");
                       til_create_room_password.setErrorEnabled(true);*/
                       til_create_room_password.setHint("密码至少需要8个字符");
                      LogUtil.e("密码至少需要8个字符");
                   } else {
                       til_create_room_password.setErrorEnabled(false);
                       til_create_room_password.setHint("密码");
                   }
                }
    }

    @Override
    public void afterTextChanged(Editable editable) {
                LogUtil.e("afterTextChanged ===" + editable);
    }

    /**
     * RadioButton的事件监听
     * @param compoundButton
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                LogUtil.e("onCheckedChanged ===" + compoundButton.getId() + "="+isChecked);
             switch (compoundButton.getId()){
                 case R.id.rb_create_with_password :
                     if(isChecked){
                         til_create_room_password.setVisibility(View.VISIBLE);
                         iv_password_see_no.setVisibility(View.VISIBLE);
                     }
                     break;
                 case R.id.rb_create_no_password :
                     if(isChecked){
                         til_create_room_password.setVisibility(View.INVISIBLE);
                         iv_password_see_no.setVisibility(View.INVISIBLE);
                     }
                     break;
             }
    }


    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.acet_create_room_name :
                on_focus = 1;
                LogUtil.e("acet_create_room_name==" + on_focus);
                break;
            case R.id.acet_create_room_password :
                on_focus = 2;
                LogUtil.e("acet_create_room_password==" + on_focus);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_password_see_no:
                if (password_seen) {
                    acet_create_room_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//设置密码不可见
                    iv_password_see_no.setImageResource(R.drawable.locked);
                    password_seen = false;
                } else {
                    acet_create_room_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码可见，如果只设置TYPE_TEXT_VARIATION_PASSWORD则无效
                    iv_password_see_no.setImageResource(R.drawable.unlock);
                    password_seen = true;
                }
                acet_create_room_password.setSelection(acet_create_room_password.getText().length());//将光标移至文字末尾
                break;
            case R.id.iv_back:
                LogUtil.e("iv_back");
                this.finish();
                break;
            case R.id.iv_choosen:
                ivChoosen();
                break;
                }
        }


    /**
     *  确认按钮点击时候的逻辑
     *  如果选中有密码，则判断用户名和密码是否符合要求，是否有空格，是否为空
     *  如果选中不设置密码，则只需要判断用户名是否符合。
     *
      */
    private void ivChoosen() {
        boolean choosen_can = false;
        LogUtil.e("iv_choosen");
        final String acet_name = acet_create_room_name.getText().toString();
        final String acet_password = acet_create_room_password.getText().toString();

        wifiTools = WifiTools.getInstance(mContext);
        LogUtil.e("创建聊天室==" + acet_name + "=" + acet_password);
        WifiConfiguration wifiConfiguration = null;
        if (!rb_create_with_password.isChecked()) {
            if (TextUtils.isEmpty(acet_name) || TextUse.containSpace(acet_name)|| acet_name.length()< 2) {
                shakeMe(til_create_room_name);
            } else {
                wifiConfiguration = wifiTools.createWifiInfo(ConstantC.WIFI_AP_PRE + acet_name, "", "WifiAP", wifiTools.WIFICIPHER_NOPASS);
                LogUtil.e("createWifiInfoWithPassword===" + acet_name + "=" + acet_password + "=" + choosen_can);
                choosen_can = true;
            }
          }  else {
                if (TextUtils.isEmpty(acet_name) || TextUse.containSpace(acet_name) || acet_name.length() < 2) {
                    shakeMe(til_create_room_name);
                    choosen_can = false;
                }
                if (TextUtils.isEmpty(acet_password) || TextUse.containSpace(acet_password) || acet_password.length() < 8) {
                    shakeMe(til_create_room_password);
                    choosen_can = false;
                }  else {
                    LogUtil.e("iv_choosen ===" + choosen_can);
                    wifiConfiguration = wifiTools.createWifiInfo(ConstantC.WIFI_AP_PRE + acet_name, acet_password, "WifiAP", wifiTools.WIFICIPHER_WPA2);
                    LogUtil.e("createWifiInfoWithPassword===" + acet_name + "=" + acet_password);
                    choosen_can = true;
                }
             }

        /**
         * setupMethod.invoke()  这里需要设置CHANGE_NETWORK_STATE权限，不然会报错。
         */
        if(choosen_can){
                 LogUtil.e(wifiConfiguration + "=Cool");
                 wifiTools.createHotSpot(wifiConfiguration);
                 creatingDialog.initParameterAndShow();
                 new Thread() {
                     @Override
                     public void run() {
                         System.out.println("createServer");
                         while (wifiTools.getWifiApState() != wifiTools.WIFI_AP_STATE_ENABLED) {
                             try {
                                 Thread.sleep(500);
                                 LogUtil.e("wait_ap_enabled");
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }

                         LogUtil.e("Handler-sendMessage ：" + acet_name + "=" + acet_password + "=" + socketUser.getUserName() + "=" + socketUser.getIsMan());

                         /**
                          * 1.跳转到聊天室界面
                          * 2.关闭加载进度框
                          * 3.关闭当前Activity
                          */
                         Intent intent = new Intent(mContext,ChatRoomActivity.class);
                         intent.putExtra(ConstantC.CLIENT_SERVER_TAG,ConstantC.WIFI_AP_ENABLED);
                         startActivity(intent);
                         creatingDialog.dismiss();
                         CreateRoomActivity.this.finish();
                     }
                 }.start();
             }   else   {
                 LogUtil.e("条件不符合，暂时不能创建聊天室");
             }

           }

    /**
     * 控件抖动效果
     * @param view
     */
    private void shakeMe(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }
}
