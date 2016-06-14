package pbru.chonlakan.yaemsak.pbru_run;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru3/get_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request SQLite เพื่อสร้าง SQLite Database
        myManage = new MyManage(this);

        deleteDatabase();

        //สั่งให้ trade ทำงาน
        ConnectedServer connectedServer = new ConnectedServer();
        connectedServer.execute();


    }//Main method

    private class ConnectedServer extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try{
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            }catch (Exception e){
                return null;
            }
        }//doIn back

        @Override //ทำงานหลังโหลด
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("pbruV2", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0; i<jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strId = jsonObject.getString("id");
                    String strName = jsonObject.getString("Name");
                    String strUser= jsonObject.getString("User");
                    String strPassword = jsonObject.getString("Password");
                    String strAvata = jsonObject.getString("Avata");
                    String strGold = jsonObject.getString("Gold");

                    myManage.addNewUser(strId, strName, strUser, strPassword, strAvata, strGold);

                }//for

                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
                cursor.moveToFirst();
                Log.d("pbruV3", "cursor == >" + cursor.getCount());


            } catch (Exception e) {
                e.printStackTrace();
            }


        }//onPostEx

    }//Connected


    private void deleteDatabase() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,MODE_PRIVATE,null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);

    }


    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }//clickSignUpMain


}//Main Class
