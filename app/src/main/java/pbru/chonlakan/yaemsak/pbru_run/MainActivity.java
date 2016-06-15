package pbru.chonlakan.yaemsak.pbru_run;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru3/get_user.php";
    private EditText userEditText, passwordEditText;
    private ImageView imageView;
    private static final String urlLogo = "http://swiftcodingthai.com/pbru3/logo_pbru.png";
    private String userString , passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BinWidget
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        imageView = (ImageView) findViewById(R.id.imageView);

        //Load Logo From Server
        Picasso.with(this).load(urlLogo).resize(140,140).into(imageView);

        //Request SQLite เพื่อสร้าง SQLite Database
        myManage = new MyManage(this);

        deleteDatabase();

        //สั่งให้ trade ทำงาน
        ConnectedServer connectedServer = new ConnectedServer();
        connectedServer.execute();


    }//Main method

    public void clickSignIn(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")) {
            Toast.makeText(this, "มีช่องว่าง กรุณากรอกทุกช่อง", Toast.LENGTH_SHORT).show();
        } else {
            searchMyUser();
        }

    }//clickSignIn

    private void searchMyUser() {

        try {
            String[] resultStrings = myManage.searchUser(userString);

            if (passwordString.equals(resultStrings[3])) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("Login", resultStrings);
                startActivity(intent);

                Toast.makeText(this, "Welcome " + resultStrings[1], Toast.LENGTH_SHORT).show();

                finish();

            } else {
                Toast.makeText(this, "Password False", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(this,"ไม่มี " + userString+ " ในฐานข้อมูลของเรา",
                    Toast.LENGTH_SHORT).show();
        }
    }//SearchMyUser

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
