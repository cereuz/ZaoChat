package com.zao.zaochat.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zao.zaochat.R;
import com.zao.zaochat.Socket.SocketClient;
import com.zao.zaochat.Socket.SocketServer;
import com.zao.zaochat.WifiTool.WifiTools;
import com.zao.zaochat.adapter.ChatRoomAdapter;
import com.zao.zaochat.global.ConstantC;
import com.zao.zaochat.global.GenerateRandom;
import com.zao.zaochat.global.MessageType;
import com.zao.zaochat.model.ChatModel;
import com.zao.zaochat.model.ItemModel;
import com.zao.zaochat.object.SocketUser;
import com.zao.zaochat.utils.LogUtil;
import com.zao.zaochat.utils.ToastUtil;
import com.zao.zaochat.utils.ZaoUtils;
import com.zao.zaochat.widget.UserInfoDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.zao.zaochat.global.GetFilePathByUri.getPathByUri4kitkat;

public class ChatRoomActivity extends AppCompatActivity implements SocketServer.SConnectListener, SocketClient.ConnectListener{

    private Context mContext;
    private WifiTools wifiTools;
    /**
     * Socket相关的
     */
    private SocketClient socketClient;
    private SocketServer socketServer;
    private SocketUser socketUser;

    /**
     * 界面控件
     */
    RecyclerView rv_chatroom;
    EditText et_chatroom;
    TextView tv_chatroom_text;
    TextView tv_chatroom_file;
    ChatRoomAdapter chatRoomAdapter;

    private int sign;
    private String content;


    /**
     * 监听来自子线程的消息
     *
     * @return
     */

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            {
                switch (msg.what) {
                    case ConstantC.CLIENT_PREPARE:
                        new ConnectServer().start();
                        break;
                    case ConstantC.CLIENT_CONNECT_SERVER:
                        ToastUtil.showT(mContext, ConstantC.CONNECT_ROOM_OK + "=" +socketUser.getUserName() + "=" +socketUser.getSign());
                        setSign(ConstantC.CLIENT_SIGN);
                        setSocketUser(socketUser);
                        socketClient.setSocketUser(socketUser);
                        break;
                    case ConstantC.WIFI_AP_ENABLED:
                        socketServer.setSocketUser(socketUser);
                        LogUtil.i("已经切换");
                        setSign(ConstantC.SERVER_SIGN);
                        setSocketUser(socketUser);
                        break;
                    case ConstantC.SEND_MESSAGE_Z:
                        sendMessageZ();
                        break;
                    default:
                        break;
                }
            }
        }
    };


    /**
     * 初始化WifiTool参数
     * 初始化socketClient  ----- socketServer  ---------- SocketUser
     */
    private void initParameter() {
        wifiTools = WifiTools.getInstance(mContext);
        socketUser = new SocketUser(GenerateRandom.randomUSER_DEFAULT_NAME(), ConstantC.USER_DEFAULT_ILLUSTRATION, GenerateRandom.randomSex(), GenerateRandom.randomIcon(), GenerateRandom.randomW());

        /**
         * 获取前面Activity传过来的值
         */
        if(getIntent().getExtras() != null) {
            int intent_tag = getIntent().getExtras().getInt(ConstantC.CLIENT_SERVER_TAG);
            LogUtil.e("INTENT_TAG : " + intent_tag );
            if(intent_tag == ConstantC.CLIENT_PREPARE){
                socketClient = new SocketClient();
                socketClient.setOnConnectListener(this);
                mHandler.sendEmptyMessage(ConstantC.CLIENT_PREPARE);
            } else if(intent_tag == ConstantC.WIFI_AP_ENABLED){
                socketServer = new SocketServer();
                socketServer.setOnSConnectListener(this);
                socketServer.createServer();
                mHandler.sendEmptyMessageDelayed(ConstantC.WIFI_AP_ENABLED,ConstantC.SIX_SENCOND);
            }
        }
     }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        LogUtil.i("CHAT Activity CREATED");
        mContext=this;
        initView();
        initData();
        initParameter();
    }

    /**
     * 初始化界面
     */
    private void initView() {

        rv_chatroom = (RecyclerView)findViewById(R.id.rv_chatroom);
        et_chatroom = (EditText) findViewById(R.id.et_chatroom);
        tv_chatroom_text = (TextView) findViewById(R.id.tv_chatroom_text);
        tv_chatroom_file = (TextView) findViewById(R.id.tv_chatroom_file);
        rv_chatroom.setHasFixedSize(true);
        rv_chatroom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_chatroom.setAdapter(chatRoomAdapter = new ChatRoomAdapter());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        {
            et_chatroom.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    content = s.toString().trim();
                    if (content.length() < 1) {
                        content = " ";
                    }
                }
            });

            tv_chatroom_text.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    mHandler.sendEmptyMessage(ConstantC.SEND_MESSAGE_Z);
//                sendMessageZ();
                }
            });

            tv_chatroom_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendFile();
                }
            });

            chatRoomAdapter.setOnChatModelItemClickListener(new ChatRoomAdapter.OnChatModelItemClickListener() {
                @Override
                public void onIconClick(ChatModel model) {
                    UserInfoDialog userInfoDialog=new UserInfoDialog(mContext,ChatRoomActivity.this.getLayoutInflater(),R.layout.dialog_user_info,model);
                    userInfoDialog.initParameterAndShow();
                }
            });


        }
    }


    /**
     * 服务器接受到消息并进行处理
     * @param msg
     */
    @Override
    public void onSReceiveData(final String msg) {
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.i("设置服务器监听收到消息" + msg);
                    ArrayList<ItemModel> data = new ArrayList<>();
                    ChatModel model = new ChatModel();


                    String userSign = msg.substring(0, MessageType.ID_LEN);
                    LogUtil.i("该消息由用户" + userSign + "发送");

                    String[] infoMsg = msg.substring(MessageType.ID_LEN).split("/");

                    String rMsg = infoMsg[5];
                    LogUtil.i("消息内容为" + rMsg);

                    model.setNick(infoMsg[0]);
                    model.setIllustration(infoMsg[1]);
                    model.setSex(infoMsg[2]);
                    model.setIcon(infoMsg[3]);
                    model.setSign(infoMsg[4]);
                    model.setTime(ZaoUtils.getSystemTimeMore(3));
                    model.setContent(rMsg);
                    data.add(new ItemModel(ItemModel.CHAT_A, model));
                    chatRoomAdapter.addAll(data);
                    chatRoomAdapter.notifyDataSetChanged();
                    rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);

                }
            });

        }
    }

    /**
     * 服务器显示自己发送的消息
     * @param msg 待处理的消息
     */
    @Override
    public void onSNotify(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sign == ConstantC.SERVER_SIGN) {
                    LogUtil.i("设置服务器给出通知" + msg);
                    ArrayList<ItemModel> data = new ArrayList<>();
                    ChatModel model = new ChatModel();

                    String[] infoMsg = msg.substring(MessageType.MS_LEN).split("/");
                    String rMsg = infoMsg[5];
                    LogUtil.i("消息内容为" + rMsg);

                    model.setIcon(socketUser.getIcon());
                    model.setNick(socketUser.getUserName());
                    model.setIllustration(socketUser.getUserIllustration());
                    model.setSex(socketUser.getIsMan());
                    model.setSign(socketUser.getSign() + "");
                    model.setTime(ZaoUtils.getSystemTimeMore(3));
                    model.setContent(rMsg);
                    data.add(new ItemModel(ItemModel.CHAT_B, model));
                    chatRoomAdapter.addAll(data);
                    chatRoomAdapter.notifyDataSetChanged();
                    rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
                }
            }
        });

    }

    /**
     * 服务端检测到客户端连接的接口
     */
    @Override
    public void onClientConnected() {
        ToastUtil.showT(mContext, ConstantC.USER_CONNECTED + "=" +socketUser.getUserName() + "=" +socketUser.getSign());
    }

    /**
     * 客户端处理数据并显示
     * @param msg 待处理的消息
     * @param type 消息类型
     */
    @Override
    public void onReceiveData(final String msg, final String type) {
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case MessageType.CLIENT_MESSAGE_SIGN:
                        if (sign == ConstantC.CLIENT_SIGN) {
                            LogUtil.i("客户端监听收到消息" + msg);
                            String user_sign = msg.substring(0, MessageType.ID_LEN);
                            String msg2 = msg.substring(MessageType.ID_LEN);
                            LogUtil.i("客户端监修改后消息" + msg2);
                            LogUtil.i( "本机标志为" + socketUser.getSign());
                            LogUtil.i( "消息标志为" + user_sign);
                            if (!user_sign.equals(socketUser.getSign() + "")) {
                                LogUtil.i( "该消息由用户" + user_sign + "发送");
                                ArrayList<ItemModel> data = new ArrayList<>();
                                ChatModel model = new ChatModel();

                                String[] infoMsg = msg.substring(MessageType.ID_LEN).split("/");
                                String rMsg = infoMsg[5];
                                LogUtil.i( "消息内容为" + rMsg);
                                LogUtil.e("CLIENT_MESSAGE_SIGN : " + infoMsg[0] + infoMsg[1] + infoMsg[2] + infoMsg[3] + infoMsg[4] + infoMsg[5] );

                                model.setNick(infoMsg[0]);
                                model.setIllustration(infoMsg[1]);
                                model.setSex(infoMsg[2]);
                                model.setIcon(infoMsg[3]);
                                model.setSign(infoMsg[4]);
                                model.setContent(rMsg);
                                model.setTime(ZaoUtils.getSystemTimeMore(3));
                                data.add(new ItemModel(ItemModel.CHAT_A, model));

                                chatRoomAdapter.addAll(data);
                                chatRoomAdapter.notifyDataSetChanged();
                                rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
                            } else {
                                LogUtil.i( "该消息由用户自己发生");
                            }
                        }
                        break;
                    case MessageType.SERVER_MESSAGE_SIGN:
                        if (sign == ConstantC.CLIENT_SIGN) {
                            LogUtil.i( "客户端监听收到消息" + msg);
                            LogUtil.i( "该消息由用户服务器发送");
                            ArrayList<ItemModel> data = new ArrayList<>();
                            ChatModel model = new ChatModel();

                            String[] infoMsg = msg.substring(MessageType.MS_LEN).split("/");
                            String rMsg = infoMsg[5];
                            LogUtil.i( "消息内容为" + rMsg);
                            LogUtil.e("CLIENT_SIGN : " + infoMsg[0] + infoMsg[1] + infoMsg[2] + infoMsg[3] + infoMsg[4] + infoMsg[5] );
                            model.setNick(infoMsg[0]);
                            model.setIllustration(infoMsg[1]);
                            model.setSex(infoMsg[2]);
                            model.setIcon(infoMsg[3]);
                            model.setSign(infoMsg[4]);
                            model.setTime(ZaoUtils.getSystemTimeMore(3));
                            model.setContent(rMsg);
                            data.add(new ItemModel(ItemModel.CHAT_A, model));

                            chatRoomAdapter.addAll(data);
                            chatRoomAdapter.notifyDataSetChanged();
                            rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);

                        }
                        break;
                    case MessageType.SERVER_FILE_RECEIVED_SIGN:
                        if (sign == ConstantC.CLIENT_SIGN) {
                            LogUtil.i( "客户端监听收到消息" + msg);
                            String user_sign = msg.substring(0, MessageType.ID_LEN);
                            String msg2 = msg.substring(MessageType.ID_LEN);
                            LogUtil.i( "客户端监修改后消息" + msg2);
                            LogUtil.i( "本机标志为" + socketUser.getSign());
                            LogUtil.i( "消息标志为" + user_sign);
                            if (user_sign.equals(socketUser.getSign() + "")) {
                                LogUtil.i( "该消息服务器接受到文件");
                                ArrayList<ItemModel> data = new ArrayList<>();
                                ChatModel model = new ChatModel();

                                String[] infoMsg = msg.substring(MessageType.ID_LEN).split("/");
                                String rMsg = infoMsg[5];
                                LogUtil.i( "消息内容为" + rMsg);

                                model.setNick(infoMsg[0]);
                                model.setIllustration(infoMsg[1]);
                                model.setSex(infoMsg[2]);
                                model.setIcon(infoMsg[3]);
                                model.setSign(infoMsg[4]);
                                model.setTime(ZaoUtils.getSystemTimeMore(3));
                                model.setContent(rMsg);
                                data.add(new ItemModel(ItemModel.CHAT_A, model));

                                chatRoomAdapter.addAll(data);
                                chatRoomAdapter.notifyDataSetChanged();
                                rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
                            } else {
                                LogUtil.i( "该消息服务器接受到文件,但不是发给自己的");
                            }
                        }
                        break;
                    default:
                        LogUtil.i( "收到了未知消息");
                        break;
                }

            }
        });
    }


    /**
     * 客户端显示自己发送的消息
     * @param msg 待处理的消息
     */
    @Override
    public void onNotify(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sign == ConstantC.CLIENT_SIGN) {

                    LogUtil.i("设置客户端给出通知 ：" + msg);

                    ArrayList<ItemModel> data = new ArrayList<>();
                    ChatModel model = new ChatModel();

                    String[] infoMsg = msg.substring(MessageType.MS_LEN + MessageType.ID_LEN).split("/");
                    String rMsg = infoMsg[5];
                    LogUtil.e("消息内容为 ：" + rMsg);

                    model.setIcon(socketUser.getIcon());
                    model.setNick(socketUser.getUserName());
                    model.setIllustration(socketUser.getUserIllustration());
                    model.setSex(socketUser.getIsMan());
                    model.setSign(socketUser.getSign() + "");
                    model.setTime(ZaoUtils.getSystemTimeMore(3));
                    model.setContent(rMsg);

                    data.add(new ItemModel(ItemModel.CHAT_B, model));
                    chatRoomAdapter.addAll(data);
                    chatRoomAdapter.notifyDataSetChanged();
                    rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case ConstantC.CHAT_SERVER_FILE_PICKED:
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();  //得到uri，后面将uri转化成file的过程
                    File file = new File(getPathByUri4kitkat(mContext, uri));
//                    Toast.makeText(getActivity(), file.toString(), Toast.LENGTH_SHORT).show();
                    if (file.length() > 5242880) {
                        ToastUtil.showT(mContext, "文件大小不得大于5MB!");
                        break;
                    }
                    try {
                        socketServer.sendFile(socketUser.getSign() + "", file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    LogUtil.e("file_pick_error");
                }
                break;

            case ConstantC.CHAT_CLIENT_FILE_PICKED:
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();  //得到uri，后面将uri转化成file的过程
                    File file = new File(getPathByUri4kitkat(mContext, uri));
                    if (file.length() > 5242880) {
                        ToastUtil.showT(mContext, "文件大小不得大于5MB!");
                        break;
                    }

                    try {
                        socketClient.sendFile(socketUser.toString(), file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtil.e("file_pick_error");
                }

                break;

            default:
                break;
        }
    }

    /**
     * 设置是服务器还是客户端的标记
     * @param sign
     */
    public void setSign(int sign) {
        this.sign = sign;
    }

    /**
     * 用于调用客户端或服务器发送文件的函数
     */
    void sendFile() {
        if (sign == ConstantC.CLIENT_SIGN) {
            if (wifiTools.getConnectedHotIP().size() >= 2) {
                LogUtil.i( "发送消息给服务器");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, ConstantC.CHAT_CLIENT_FILE_PICKED);
            } else {
                LogUtil.i( ConstantC.NO_USERS_TIPS);
                ToastUtil.showT(mContext, ConstantC.NO_USERS_TIPS);
            }
        } else {
            if (wifiTools.getConnectedHotIP().size() >= 2) {
                LogUtil.i( "发送文件给客户端");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, ConstantC.CHAT_SERVER_FILE_PICKED);
            } else {
                LogUtil.i( ConstantC.NO_USERS_TIPS);
                ToastUtil.showT(mContext, ConstantC.NO_USERS_TIPS);
            }
        }
    }

    /**
     * 用于调用客户端或服务器发送消息并更新界面的函数
     */
    void sendMessageZ() {
        try {
            if (sign == ConstantC.CLIENT_SIGN) {
                LogUtil.i("发送消息给服务器");
                if (wifiTools.getConnectedHotIP().size() >= 2) {
                    if (socketClient != null) {
                        socketClient.sendMessageToServer(socketUser.toString() + content, MessageType.CLIENT_MESSAGE_SIGN);
                    } else {
                        LogUtil.i("socketClient为null=客户端");
                        ToastUtil.showT(mContext, "请确认你是否连接聊天室=客户端" );
                    }
                } else {
                    LogUtil.i(ConstantC.NO_USERS_TIPS);
                    ToastUtil.showT(mContext, ConstantC.NO_USERS_TIPS);
                    return;
                }
            } else {
                if (wifiTools.getConnectedHotIP().size() >= 2) {
                    LogUtil.i("发送消息给客户端");
                    if (socketServer != null) {
                        socketServer.sendMessageToClient(socketUser.toString() + content, MessageType.SERVER_MESSAGE_SIGN);
                    } else {
                        LogUtil.i("socketServer为null =服务器端");
                        ToastUtil.showT(mContext, "请确认你是否连接聊天室=服务器端");
                    }
                } else {
                    LogUtil.i(ConstantC.NO_USERS_TIPS);
                    ToastUtil.showT(mContext, ConstantC.NO_USERS_TIPS);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ArrayList<ItemModel> data = new ArrayList<>();
//        ChatModel model = new ChatModel();
//        model.setIcon("http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg");
//        model.setContent(content);
//        data.add(new ItemModel(ItemModel.CHAT_B, model));
//        chatAdapter.addAll(data);
        rv_chatroom.scrollToPosition(chatRoomAdapter.getItemCount() - 1);
        et_chatroom.setText("");
        hideKeyBorad(et_chatroom);
    }

    public void setSocketUser(SocketUser socketUser) {
        this.socketUser = socketUser;
    }

    /**
     * 用于隐藏当前页面的键盘
     * @param v
     */
    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

/*    public SocketClient getSocketClient() {
        return socketClient;
    }*/


    class ConnectServer extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            while (wifiTools.getConnectedHotIP().size() < 2) {
                try {
                    Thread.sleep(500);
                    System.out.println("wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            socketClient = getSocketClient();
            socketClient.createClient(wifiTools.getConnectedHotIP().get(1));
            Message msg = new Message();
            msg.what = ConstantC.CLIENT_CONNECT_SERVER;
            mHandler.sendMessage(msg);
        }
    }
}
