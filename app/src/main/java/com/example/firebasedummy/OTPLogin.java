package com.example.firebasedummy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.firebasedummy.databinding.ActivityOtploginBinding;

public class OTPLogin extends AppCompatActivity {

    PinView p;
    ActivityOtploginBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_otplogin);

        b.otpEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 6)
                {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("otp", s.toString());
                    setResult(RESULT_OK, resultIntent);

                    //Validate
                    Toast.makeText(OTPLogin.this, "Done OTP Scene", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
