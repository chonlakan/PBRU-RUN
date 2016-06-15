package pbru.chonlakan.yaemsak.pbru_run;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase, readSqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_name = "Name";
    public static final String column_user = "User";
    public static final String column_password = "Password";
    public static final String column_avata = "Avata";
    public static final String column_gold = "Gold";


    public MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
        readSqLiteDatabase = myOpenHelper.getReadableDatabase();

    }//Constructor

    public String[] searchUser(String strUser) {
        try {

            String[] resultStrings = null;
            Cursor cursor = readSqLiteDatabase.query(user_table,
                    new String[]{column_id, column_name, column_user, column_password, column_avata, column_gold},
                    column_user + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    resultStrings = new String[cursor.getColumnCount()];
                    for (int i=0;i<cursor.getColumnCount();i++) {
                        resultStrings[i] = cursor.getString(i);
                    }

                }   //if2
            }   // if1
            cursor.close();
            return resultStrings;


        } catch (Exception e) {
            return null;
        }

    }//searchUser

    public long addNewUser(String strId,
                           String strName,
                           String strUser,
                           String strPassword,
                           String strAvata,
                           String strGold) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_id, strId);
        contentValues.put(column_name, strName);
        contentValues.put(column_user, strUser);
        contentValues.put(column_password, strPassword);
        contentValues.put(column_avata, strAvata);
        contentValues.put(column_gold, strGold);


        return sqLiteDatabase.insert(user_table,null, contentValues);
    }
}//Main Class
