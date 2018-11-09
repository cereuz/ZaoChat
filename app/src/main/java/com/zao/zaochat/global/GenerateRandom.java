package com.zao.zaochat.global;

/**
 * 项目名称：HotChat2
 * 类描述：
 * 创建人：qq985
 * 创建时间：2016/12/14 17:41
 * 修改人：qq985
 * 修改时间：2016/12/14 17:41
 * 修改备注：
 */
public class GenerateRandom {

    //        socketUser = new SocketUser(ConstantC.USER_DEFAULT_NAME, ConstantC.USER_DEFAULT_ILLUSTRATION, "man", "1", GenerateRandom.randomW());
    /**
     * 返回一万到99999之间的整数
     */
    public static int randomW() {
        return (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
    }

    /**
     * 返回 0 到 10之间的整数
     */
    public static int randomTen() {
        return (int) (Math.random() * 10);
    }

    /**
     * 返回随机的默认名称
     */
    public static String randomIcon() {
        return (int) (Math.random() * 3) + 1 + "";
    }

    /**
     * 返回随机的默认名称
     */
    public static String randomSex() {
        switch ( (int)Math.random() * 2){
            case 0:
            return "man";
            case 1:
            return "women";
        }
        return null;
    }

    /**
     * 返回随机的默认名称
     */
    public static String randomUSER_DEFAULT_NAME() {
        switch (randomTen()){
            case 0 :
                return "斑马";
            case 1 :
                return "蚂蚁";
            case 2 :
                return "蝴蝶";
            case 3 :
                return "大象";
            case 4 :
                return "猩猩";
            case 5 :
                return "老虎";
            case 6 :
                return "犀牛";
            case 7 :
                return "猫咪";
            case 8 :
                return "狮子";
            case 9 :
                return "天天";
            case 10 :
                return "小白";
        }
        return null;
    }
}
