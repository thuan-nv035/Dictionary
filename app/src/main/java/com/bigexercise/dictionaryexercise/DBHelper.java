package com.bigexercise.dictionaryexercise;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_FULL_PATH = "";
    private Context mContext;
    public static final String DATABASE_NAME = "my_dic.db";
    public static final int DATABASE_VERSION = 1;
    public SQLiteDatabase mDB;
    public String DATABASE_LOCATION = "";

    private final String TBL_ENG_VN = "eng_vn";
    private final String TBL_VN_ENG = "eng_vn";
    private final String TBL_BOOKMARK = "bookmark";

    private final String COL_KEY = "key";
    private final String COL_VALUE = "value";


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public DBHelper(Context context) throws IOException {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        mContext = context;
        DATABASE_LOCATION = "data/data/"+mContext.getOpPackageName()+"/database/";
        DATABASE_FULL_PATH = DATABASE_LOCATION + DATABASE_NAME;

        if (!isExistringDB()) {
            try {
                extractAssetToDatabaseDirectory(DATABASE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_FULL_PATH, null);
    }

    boolean isExistringDB() {
        File file = new File(DATABASE_FULL_PATH);
        return file.exists();
    }

    public void extractAssetToDatabaseDirectory(String fileName) throws IOException { //copy the database

        int length;
        InputStream sourceDatabase = this.mContext.getAssets().open(fileName);
        File destinationPath = new File(DATABASE_FULL_PATH);
        OutputStream destination =new FileOutputStream(destinationPath);

        byte[] buffer = new byte[4096];
        while ((length = sourceDatabase.read(buffer)) > 0) {
            destination.write(buffer, 0 ,length);
        }

        sourceDatabase.close();
        destination.flush();
        destination.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //query word base on key
    public ArrayList<String> getWord(int dicType) {
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM" + tableName;
        Cursor result =  mDB.rawQuery(q, null);

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()) {
            source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    public Word getWord(String key, int dicType) {
        String tableName = getTableName(dicType);
        String q = "SELECT * FROM"+tableName+"WHERE upper([key]) = upper(?)";
        Cursor result =  mDB.rawQuery(q,new String[]{key});

        Word word = new Word();
        while (result.moveToNext()) {
            word.key =  result.getString(result.getColumnIndex(COL_KEY));
            word.value =  result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;
    }
    //insert word to bookmark

    public void addBookmark(Word word) {
        try {
            String q = "INSERT INTO bookmark(["+COL_KEY+"],["+COL_VALUE+"]) VALUES (?,?);";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex) {

        }
    }

    // remove word to boolmark
    public void removeBookmark(Word word) {
        try {
            String q = "DELETE FROM bookmark WHERE upper(["+COL_KEY+"]) = upper(?) AND ["+COL_VALUE+"] = ? ;";
            mDB.execSQL(q, new Object[]{word.key, word.value});
        }catch (SQLException ex) {

        }
    }


    //query all word from bookmark
    public ArrayList<String> getWordALLFromBookmark(String key, int dicType) {
        String q = "SELECT * FROM bookmark ORDER BY [date] DESC";
        Cursor result =  mDB.rawQuery(q,new String[]{key});

        ArrayList<String> source = new ArrayList<>();
        while (result.moveToNext()) {
           source.add(result.getString(result.getColumnIndex(COL_KEY)));
        }
        return source;
    }

    //query is word mark
    public boolean isWordMark(Word word) {
        String q = "SELECT * FROM booknark WHERE upper([Key]) = upper(?) AND [value] = ?";
        Cursor result =  mDB.rawQuery(q,new String[]{word.key, word.value});

        return  result.getCount() > 0;
    }

    public Word getWordFromBookmark(String key) {
        String q = "SELECT * FROM booknark WHERE upper([Key]) = upper(?)";
        Cursor result =  mDB.rawQuery(q,null);
        Word word =new Word();
        while (result.moveToNext()) {
            word.key = result.getString(result.getColumnIndex(COL_KEY));
            word.value = result.getString(result.getColumnIndex(COL_VALUE));
        }
        return word;

    }


    public String getTableName(int dicType) {
        String tableName = "";
        if (dicType == R.id.action_en_vi) {
            tableName = TBL_ENG_VN;
        }else if (dicType == R.id.action_vi_en) {
            tableName = TBL_VN_ENG;
        }
        return tableName;
    }
}
