package com.zao.zaochat.znote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zao.zaochat.utils.ConstantValue;

public class NotesSqliteOpenHelper extends SQLiteOpenHelper {
    public NotesSqliteOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库中表的方法
        sqLiteDatabase.execSQL(ConstantValue.DATABASE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
