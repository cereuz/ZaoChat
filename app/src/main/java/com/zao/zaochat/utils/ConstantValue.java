package com.zao.zaochat.utils;

public class ConstantValue {

    /**
     * 打log使用的tag
     */
    public static final String TAG = "ZAO";
    /**
     *  设置更新的SharedPreferences存储在本地的 文件 名称
     */
    public static final String UPDATE_FILE = "update0730";
    /**
     * 是否开启更新的 key
     */
    public static final String OPEN_UPDATE = "open_update";
    /**
     * 进入界面之前的，设置密码
     */
    public static final String MOBLE_SAFE_PWD = "moble_safe_pwd";
    /**
     * 提示用户密码不能为空
     */
    public static final String NOTICE_PWD = "密码不能为空！";
    /**
     * 密码输入不一致
     */
    public static final String NOTICE_PWD_SAME = "两次密码输入不一致";
    /**
     * 是否设置完成导航界面的key
     */
    public static final String SETUP_OVER = "setup_over";
    /**
     * SIM 卡 绑定序列号的key
     */
    public static final String SIM_NUMBER = "sim_number";
    /**
     * 发送报警短信手机号码的key
     */
    public static final String CONTACT_PHONE = "contact_phone";
    /**
     * 是否开启安全保护的key
     */
    public static final String OPEN_SECURITY = "open_security";
    /**
     * 归属地服务的类名称
     */
    public static final String ADDRESS_SERVICE_CLASS = "com.onezao.zao.mobilesafe.service.AddressService";
    /**
     * 黑名单服务的类名称
     */
    public static final String BLACKNUMBER_SERVICE_CLASS = "com.onezao.zao.mobilesafe.service.BlackNumberService";
    /**
     * 自定义Toast，设置其不同的样式的索引的key值
     */
    public static final String TOAST_STYLE = "toast_style";
    /**
     * 左上角的x坐标的key
     */
    public static final String TOAST_LOCATION_X = "toast_location_x";
    /**
     * 左上角的y坐标的key
     */
    public static final String TOAST_LOCATION_Y = "toast_location_y";
    /**
     * 创建黑名单数据库
     */
    public static final String DADABASE_BLACKNUMBER = "mobilesafe_bn";
    /**
     * 程序锁的数据库的名称
     */
    public static final String DADABASE_APPLOCK = "mobilesafe_applock";
   /**
     * 创建黑名单数据库的表
     */
    public static final String DATABASE_TABLE_BLACKNUMBER = "create table blacknumber (_bn_id integer primary key autoincrement , bn_phone varchar(20),bn_mode varchar(6),bn_time varchar(60));";
    /**
     * 创建程序锁数据库的表
     */
    public static final String DATABASE_CREATE_TABLE_APPLOCK = "CREATE table applock (_id integer primary key autoincrement,packagename varchar(60),time varchar(60));";
    /**
     * 数据库表的名称
     */
    public static final String DATABASE_BLACKNUMBER_TABLE_NAME = "blacknumber";
    /**
     * AppLock数据库表的名称
     */
    public static final String DATABASE_APPLOCK_TABLE_NAME = "applock";
    /**
     * 创建的Wifi名称的前缀
     */
    public static final String WIFI_AP_PRE = "ZAO";
    /**
     *Wifi 可以使用的状态码
     */
    public final static int WIFI_AP_ENABLED = 12351;
    /**
     *  时间常量，4秒钟  即 4000 毫秒
     */
    public static int FOUR_SECOND = 4000;
    public static int SIX_SECOND = 6000;
    public static int ONE_SECOND = 1000;
}
