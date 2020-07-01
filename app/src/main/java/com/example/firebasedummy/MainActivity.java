package com.example.firebasedummy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText e1;
    ProgressDialog p;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verificationCode;
    private Button button;
    String currentCode;
    public static String APPTAG = "AjayAPP";

    private final int REQUEST_OTP_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText);
        auth = FirebaseAuth.getInstance();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                //Magic

                p.cancel();

                Toast.makeText(MainActivity.this, "AutoVerified", Toast.LENGTH_SHORT).show();

                doSignIn(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                p.cancel();
                openOTPLogin();
                currentCode = s;
                verificationCode = s;
                Toast.makeText(getApplicationContext(), "Code sent to the number", Toast.LENGTH_SHORT).show();
            }
        };

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               send_sms(view);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_OTP_CODE:

                if(resultCode == RESULT_OK)
                {
                    if (data != null) {
                        String OTP = data.getExtras().getString("otp");
                        PhoneAuthCredential p = PhoneAuthProvider.getCredential(verificationCode, OTP);
                        doSignIn(p);

                    }
                }
        }
    }

    private void doSignIn(PhoneAuthCredential phoneAuthCredential) {
        Log.d("AjayAPP", "Sign In:Start");
        Toast.makeText(this, "Sign In Complete!:", Toast.LENGTH_SHORT).show();
        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AjayAPP", "signInWithCredential:success");
                            openMapsActivity();
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("AjayAPP", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }



                    }
                });
    }

    public void openOTPLogin(){
        Intent myIntent = new Intent(this, OTPLogin.class);
        startActivityForResult(myIntent, REQUEST_OTP_CODE);
    }

    public void send_sms(View v) {
        String number = e1.getText().toString();

        p = new ProgressDialog(MainActivity.this);
        p.setTitle("Doing one verify");
        p.setCancelable(false);
        p.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallback);
    }

    public void openMapsActivity(){
        Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapsIntent);
    }

}
//    }

    //    public void signInWithPhone(PhoneAuthCredential credential)
//    {
//        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                    Toast.makeText(getApplicationContext(), "User signed in successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//    public void verify(View v)
//    {
//
//    }

//    private EditText mChildValueEditText;
//    private Button mAddButton, mRemoveButton;

//        mChildValueEditText = (EditText) findViewById(R.id.childValueEditText);
//        mAddButton = (Button) findViewById(R.id.addButton);
//        mRemoveButton = (Button) findViewById(R.id.removeButton);
//
//        FirebaseDatabase database =  FirebaseDatabase.getInstance();
//        final DatabaseReference mRef = database.getReference("simCoder");
//
//        mAddButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String childValue = mChildValueEditText.getText().toString();
//                mRef.setValue(childValue);
//            }
//        });
//
//        mRemoveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRef.removeValue();
//            }
//        });

//    final EditText editText = findViewById(R.id.editText);
//        //String number = editText.getText().toString();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference("message");
//
////        FireBaseAuth auth;
//
//        Button button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = editText.getText().toString();
//                myRef.setValue(number);
//            }
//        });