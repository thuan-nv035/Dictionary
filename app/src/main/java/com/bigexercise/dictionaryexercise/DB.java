package com.bigexercise.dictionaryexercise;

public class DB {
    public static String[] getData(int id) {
        if (id == R.id.action_en_vi) {
            return getenvi();
        } else  if (id == R.id.action_vi_en) {
            return getvnei();
        }
        return new String[0];
    }
    public static String[] getenvi() {
        String[] source = new String[] {
            "A",
                "a la mode",
                "a level",
                "A (1)",
                "A (2)",
                "a - bomd",
                "a - riot",
                "a - ripple",
                "a - road"
        };
        return source;
    }

    public static String[] getvnei() {
        String[] source = new String[] {
                "mỗi",
                "lên cấp",
                "cặp đôi",
                "đường",
                "học tập",
                "xe",
                "nhà",
                "trường học",
                "công nghệ thông tin",
                "điện tử viễn thông",
                "tin học"
        };
        return source;
    }
}
