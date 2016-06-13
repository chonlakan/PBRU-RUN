package pbru.chonlakan.yaemsak.pbru_run;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, userEditText, passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton avatar0RadioButton , avatar1RadioButton, avatar2RadioButton,
                        avatar3RadioButton, avatar4RadioButton;
    private String nameString, userString, passwordString, avatarString;
    private int indexAnInt = 0;

    //http://swiftcodingthai.com/pbru3/add_user_master.php


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //BindWidget
        bindWidget();

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (indexAnInt) {
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
                    case  R.id.radioButton5:
                        indexAnInt = 4;
                        break;
                }

            }
        });

    }//Main Method

    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvatar);
        avatar0RadioButton = (RadioButton) findViewById(R.id.radioButton);
        avatar1RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        avatar2RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        avatar3RadioButton = (RadioButton) findViewById(R.id.radioButton4);
        avatar4RadioButton = (RadioButton) findViewById(R.id.radioButton5);

    }//BindWidget

    public void clickSignUpSign(View view) {
        nameString = nameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //check Space
        if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
            //Have Space
            Toast.makeText(this, "มีช่องว่าง กรุณากรอกทุกช่อง", Toast.LENGTH_SHORT).show();

        } else {
            //No Space
            uploadValueToServer();
        }



    }//clickSignUpSign

    private void uploadValueToServer() {

        Log.d("pbruV1" , "Name ==>" + nameString);
        Log.d("pbruV1" , "User ==>" + userString);
        Log.d("pbruV1" , "Pass ==>" + passwordString);
        Log.d("pbruV1" , "Avatar ==>" + Integer.toString(indexAnInt));

    }//UpLoadToServer

}//Main Class
