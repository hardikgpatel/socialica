package com.test.socketchat.activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.socketchat.R;
import com.test.socketchat.activity.adapter.CounteryAdapter;
import com.test.socketchat.activity.response.ResponseVerifyUser;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private Spinner spCountry;
    private ArrayList<String> arrCoutry, arrCode;
    private EditText edtCountryCode;
    private CounteryAdapter adapter;
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private EditText edtPhoneNumber, edtOtpOne, edtOptTwo, edtOtpThree, edtOtpFour, edtOtpFive, edtOtpSix;
    private TextView tvContinue, tvOtpSms, tvOtpCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();
        try {
            //startActivity(new Intent(SplashScreenActivity.this,UpdateProfileActivity.class));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            findViewById(R.id.tv_welcome).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (findViewById(R.id.tv_welcome).getVisibility() == View.VISIBLE) {
                        onEndSplash();
                    }
                }
            }, 3000);

            spCountry = findViewById(R.id.sp_country);
            edtCountryCode = findViewById(R.id.edt_country_code);
            edtPhoneNumber = findViewById(R.id.edt_phone_number);
            tvContinue = findViewById(R.id.tv_continue);
            edtOtpOne = findViewById(R.id.edt_opt_one);
            edtOptTwo = findViewById(R.id.edt_opt_two);
            edtOtpThree = findViewById(R.id.edt_opt_three);
            edtOtpFour = findViewById(R.id.edt_opt_four);
            edtOtpFive = findViewById(R.id.edt_opt_five);
            edtOtpSix = findViewById(R.id.edt_opt_six);
            tvOtpCall = findViewById(R.id.tv_otp_call);
            tvOtpSms = findViewById(R.id.tv_otp_sms);

            arrCoutry = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country_arrays)));
            arrCode = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.country_code)));
            Log.e(TAG, "onCreate: " + "Country: " + arrCoutry.size() + " Code: " + arrCode.size());
            adapter = new CounteryAdapter(arrCoutry, SplashScreenActivity.this);
            spCountry.setAdapter(adapter);
            spCountry.setSelection(98);
            spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    edtCountryCode.setText(arrCode.get(position));
                    Log.e(TAG, "onItemSelected: " + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edtPhoneNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtPhoneNumber.getText().length() == 10) {
                        tvContinue.setEnabled(true);
                        tvContinue.setTextColor(getResources().getColor(android.R.color.white));
                        InputMethodManager iim = (InputMethodManager) getSystemService(SplashScreenActivity.this.INPUT_METHOD_SERVICE);
                        iim.hideSoftInputFromWindow(edtPhoneNumber.getWindowToken(), 0);
                    } else {
                        tvContinue.setEnabled(false);
                        tvContinue.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtOtpOne.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOtpOne.getText().length() == 1) {
                        edtOptTwo.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtOptTwo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOptTwo.getText().length() == 1) {
                        edtOtpThree.requestFocus();
                    } else {
                        edtOtpOne.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtOtpThree.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOtpThree.getText().length() == 1) {
                        edtOtpFour.requestFocus();
                    } else {
                        edtOptTwo.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtOtpFour.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOtpFour.getText().length() == 1) {
                        edtOtpFive.requestFocus();
                    } else {
                        edtOtpThree.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtOtpFive.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOtpFive.getText().length() == 1) {
                        edtOtpSix.requestFocus();
                    } else {
                        edtOtpFour.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtOtpSix.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtOtpSix.getText().length() == 1) {
                        onVerifyOtp();
                    } else {
                        edtOtpFive.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onEndSplash() {
        if (SocketChatApp.getSession().isLoggedIn()) {
            startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
            finish();
        }else{
            findViewById(R.id.tv_welcome).setVisibility(View.GONE);
            findViewById(R.id.ll_register).setVisibility(View.VISIBLE);
        }
    }

    public void onContinue(View view) {
        new AlertDialog.Builder(SplashScreenActivity.this)
                .setMessage(edtPhoneNumber.getText() + ", do you want to continue with this number.?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getRegister();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void getRegister() {
        String strContact = edtCountryCode.getText().toString().substring(1, edtCountryCode.getText().length()) + edtPhoneNumber.getText();
        Call<ResponseVerifyUser> callRegister = SocketChatApp.getApiService().requestOTP(strContact);
        callRegister.enqueue(new Callback<ResponseVerifyUser>() {
            @Override
            public void onResponse(Call<ResponseVerifyUser> call, Response<ResponseVerifyUser> response) {
                Log.e(TAG, "onResponse: status - "+response.code() );
                if (response.isSuccessful()) {
                    if (response.code()==200) {
                        findViewById(R.id.ll_otp).setVisibility(View.VISIBLE);
                        findViewById(R.id.ll_register).setVisibility(View.GONE);
                        edtOtpOne.requestFocus();
                        onStartVerifyOtp();
                    }else{
                        Toast.makeText(SplashScreenActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseVerifyUser> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this, "Error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onStartVerifyOtp() {
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {

                String time = ("" + String.format("%d : %d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                tvOtpCall.setText(time);
                tvOtpSms.setText(time);
            }

            public void onFinish() {
                tvOtpSms.setText("Resend");
                tvOtpSms.setEnabled(true);
                tvOtpSms.setTextColor(Color.WHITE);
                tvOtpCall.setText("Call");
                tvOtpCall.setTextColor(Color.WHITE);
                tvOtpCall.setEnabled(true);
            }
        }.start();
    }

    public void onResendSMS(View view) {
        tvOtpSms.setEnabled(false);
        tvOtpSms.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tvOtpCall.setEnabled(false);
        tvOtpCall.setTextColor(getResources().getColor(android.R.color.darker_gray));
        Toast.makeText(this, "SMS Resend", Toast.LENGTH_SHORT).show();
        getRegister();
    }

    public void onCall(View view) {
        Toast.makeText(this, "Calling", Toast.LENGTH_SHORT).show();
        tvOtpSms.setEnabled(false);
        tvOtpSms.setTextColor(getResources().getColor(android.R.color.darker_gray));
        tvOtpCall.setEnabled(false);
        tvOtpCall.setTextColor(getResources().getColor(android.R.color.darker_gray));
        Toast.makeText(this, "SMS Resend", Toast.LENGTH_SHORT).show();
        onStartVerifyOtp();
    }

    private void onVerifyOtp() {
        final String strContact = edtCountryCode.getText().toString().substring(1, edtCountryCode.getText().length()) + edtPhoneNumber.getText();
        String strOTP = edtOtpOne.getText().toString() + edtOptTwo.getText() + edtOtpThree.getText() + edtOtpFour.getText() + edtOtpFive.getText() + edtOtpSix.getText();
        Call<ResponseVerifyUser> callRegister = SocketChatApp.getApiService().verifyOTP(strContact, strOTP);
        callRegister.enqueue(new Callback<ResponseVerifyUser>() {
            @Override
            public void onResponse(Call<ResponseVerifyUser> call, Response<ResponseVerifyUser> response) {
                Log.e(TAG, "onResponse: status - "+response.code() );

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Toast.makeText(SplashScreenActivity.this, "message: Match: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        SocketChatApp.getSession().createLoginSession(strContact);
                        startActivity(new Intent(SplashScreenActivity.this, UpdateProfileActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SplashScreenActivity.this, "message: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SplashScreenActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseVerifyUser> call, Throwable t) {
                Toast.makeText(SplashScreenActivity.this, "Error : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
