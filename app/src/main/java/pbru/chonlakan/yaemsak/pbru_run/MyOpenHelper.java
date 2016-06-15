package pbru.chonlakan.yaemsak.pbru_run;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    public static final String database_name = "pbru_run.db ";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTABLE (" +
                                "_id integer primary key," +
                                "Name text," +
                                "User text," +
                                "Password text," +
                                "Avata text," +
                                "Gold text);";


    public MyOpenHelper(Context context) {
        super(context, database_name, null ,database_version);//null ค่าของ cursor ที่เป็นตัวเก็บข้อมูลที่ได้จากการ query

    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_user_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}//main Class
