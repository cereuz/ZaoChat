package com.zao.zaochat.model;

import java.io.Serializable;

/**
 * Created by WangChang on 2016/4/28.
 */
public class ChatModel implements Serializable {
    private String icon = "";
    private String content = "";
    private String type = "";
    private String nick = "";
    private String illustration = "";
    private String sex = "";
    private String time="";
    private String sign="";
    private String file_path = "";

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_name) {
        this.file_path = file_name;
    }

    @Override
    public String toString() {
        return "ChatModel{" +
                "icon='" + icon + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", nick='" + nick + '\'' +
                ", illustration='" + illustration + '\'' +
                ", sex='" + sex + '\'' +
                ", time='" + time + '\'' +
                ", sign='" + sign + '\'' +
                ", file_path='" + file_path + '\'' +
                '}';
    }
}
