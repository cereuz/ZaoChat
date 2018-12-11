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
    public static final String DADABASE_NOTES = "zaochat_znote";
    /**
     * 程序锁的数据库的名称
     */
    public static final String DADABASE_APPLOCK = "mobilesafe_applock";
    public static final String XIAO_YAO_YOU = "  逍遥游 庄子 \n北冥有鱼，其名曰鲲。鲲之大，不知其几千里也；化而为鸟，其名为鹏。鹏之背，不知其几千里也；怒而飞，其翼若垂天之云。是鸟也，海运则将徙于南冥。南冥者，天池也。齐谐者，志怪者也。谐之言曰：“鹏之徙于南冥也，水击三千里，抟扶摇而上者九万里，去以六月息者也。”野马也，尘埃也，生物之以息相吹也。天之苍苍，其正色邪？其远而无所至极邪？其视下也，亦若是则已矣。且夫水之积也不厚，则其负大舟也无力。 覆杯水于坳堂之上，则芥为之舟；置杯焉则胶，水浅而舟大也。风之积也不厚，则其负大翼也无力，故九万里则风斯在下矣。而后乃今培风，背负青天而莫之夭阏者，而后乃今将图南。蜩与学鸠笑之曰：“我决起而飞，抢榆枋，时则不至，而控于地而已矣；奚以之九万里而南为？”适莽苍者，三飡而反，腹犹果然；适百里者，宿舂粮；适千里者，三月聚粮。之二虫又何知？小知不及大知，小年不及大年。奚以知其然也？ 朝菌不知晦朔，蟪蛄不知春秋，此小年也。楚之南有冥灵者，以五百岁为春，五百岁为秋；上古有大椿者，以八千岁为春，八千岁为秋。而彭祖乃今以久特闻，众人匹之，不亦悲乎？ 汤之问棘也是已：“穷发之北有冥海者，天池也。有鱼焉，其广数千里，未有知其修者，其名曰鲲。有鸟焉，其名为鹏，背若太山，翼若垂天之云；抟扶摇、羊角而上者九万里，绝云气，负青天，然后图南，且适南冥也。斥鴳笑之曰：‘彼且奚适也？我腾跃而上，不过数仞而下，翱翔蓬蒿之间，此亦飞之至也。而彼且奚适也？’”此小大之辩也。 故夫知效一官、行比一乡、德合一君、而徵一国者，其自视也亦若此矣。而宋荣子犹然笑之。且举世而誉之而不加劝，举世而非之而不加沮，定乎内外之分，辩乎荣辱之境，斯已矣。彼其于世，未数数然也。虽然，犹有未树也。夫列子御风而行，泠然善也，旬有五日而后反。彼于致福者，未数数然也。此虽免乎行，犹有所待者也。若夫乘天地之正，而御六气之辩，以游无穷者，彼且恶乎待哉？故曰：至人无己，神人无功，圣人无名。 听风者无耳，千里眼无目，悲喜交加的时候是个真实的梦靥； 2018年11月11日 07时02分01秒 星期日";

    /**
     * 创建日记数据库的表
     */
    public static final String DATABASE_ZNOTE_TABLE = "znote";
    public static final String DATABASE_ZNOTE_ID = "_znote_id";
    public static final String DATABASE_ZNOTE_NAME = "z_name";
    public static final String DATABASE_ZNOTE_MODE = "z_mode";
    public static final String DATABASE_ZNOTE_TIME = "z_time";
    public static final String DATABASE_ZNOTE_NOTE = "z_notes";
    public static final String DATABASE_TABLE_NOTES = "create table " + DATABASE_ZNOTE_TABLE + " ( " + DATABASE_ZNOTE_ID + " integer primary key autoincrement ," + DATABASE_ZNOTE_NAME + " varchar(20)," + DATABASE_ZNOTE_MODE + " varchar(6)," + DATABASE_ZNOTE_TIME + " bigint(60)," + DATABASE_ZNOTE_NOTE + " text);";
//    public static final String DATABASE_TABLE_NOTES = "create table znote (_znote_id integer primary key autoincrement , z_phone varchar(20),z_mode varchar(6),z_time varchar(60)),z_notes text;";

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
