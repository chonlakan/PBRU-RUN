package pbru.chonlakan.yaemsak.pbru_run;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avata0RadioButton, avata1RadioButton,
                        avata2RadioButton, avata3RadioButton, avata4RadioButton;
    private String nameString, userString, passwordString, avatarString;
    private int indexAnInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.radioButton:
                        indexAnInt = 0;
                        break;
                    case R.id.radioButton2:
                        indexAnInt = 1;
                        break;
                    case R.id.radioButton3:
                        indexAnInt = 2;
                        break;
                    case R.id.radioButton4:
                        indexAnInt = 3;
                        break;
                    case R.id.radioButton5:
                        indexAnInt = 4;
                        break;
                }

            }
        });


    }   // Main Method

    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvatar);
        avata0RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avata1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avata2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avata3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avata4RadioButton = (RadioButton) findViewById(R.id.radioButton5);

    }   // bindWidget

    public void clickSignUpSign(View view) {

        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
            //Have Space
            Toast.makeText(this, "กรุณากรอกทุกช่อง มีช่องว่าง?",
                    Toast.LENGTH_SHORT).show();

        } else {
            //No Space
            uploadValueToServer();
        }


    }   // clickSign

    private void uploadValueToServer() {

        Log.d("pbruV1", "name ==> " + nameString);
        Log.d("pbruV1", "user ==> " + userString);
        Log.d("pbruV1", "password ==> " + passwordString);
        Log.d("pbruV1", "avata ==> " + Integer.toString(indexAnInt));

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("User", userString)
                .add("Password", passwordString)
                .add("Avata", Integer.toString(indexAnInt))
                .add("Lat", "0")
                .add("Lng", "0")
                .add("Gold", "0")
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/pbru3/add_user_master.php")
                                 .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });//Call



    }   // upload

}   // Main Class