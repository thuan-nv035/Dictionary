package com.bigexercise.dictionaryexercise;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

// Read and Write file
// Sử dụng sharePreference để lưu trạng thái của Icon
public class Global {
    // Ghi File
    public static void saveState(Activity activity, String key, String value){ // File sẽ lưu dạng key-value
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);// Chế độ truy cập là chỉ có thiết bị này được truy cập
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
     // Đọc File
    public static String getState(Activity activity, String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,null);
    }
}
