package com.zao.zaochat.znote;

public class NotesInfo {

    private String id;
    private String phone;
    private String mode;
    private Long time;
    private String name;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "NotesInfo{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", mode='" + mode + '\'' +
                ", time=" + time +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
