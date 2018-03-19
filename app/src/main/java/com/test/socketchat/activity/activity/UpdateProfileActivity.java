package com.test.socketchat.activity.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.adapter.StatusAdapter;
import com.test.socketchat.activity.response.ResponseRegisterUser;
import com.test.socketchat.activity.utility.BlurBuilder;
import com.test.socketchat.activity.utility.CircleTransform;
import com.test.socketchat.activity.utility.ImageFilePath;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.test.socketchat.activity.utility.CommonUtils.compressImage;

public class UpdateProfileActivity extends AppCompatActivity implements StatusAdapter.StatusSelect {

    private RecyclerView rvStatus;
    private ArrayList<String> arrStatus;
    private StatusAdapter adapter;
    private EditText edtStatus, edtDisplayName, edtEmail, edtUserName;
    private boolean isEditeble = false;
    private ImageView ivProfile;
    private static final String TAG = UpdateProfileActivity.class.getSimpleName();
    private View statusDialogView;
    private TextView tvStatus;
    private Dialog dialog;
    private boolean isImageSelected = false;
    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_SETTING = 2;
    private static final int REQUEST_CAMERA = 3;
    private Uri uri;
    private MultipartBody.Part profileImage;

    //private ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        //getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statusDialogView = getLayoutInflater().inflate(R.layout.dialog_status_layout, null, false);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rvStatus = statusDialogView.findViewById(R.id.rv_status);
        edtStatus = findViewById(R.id.edt_status);
        edtDisplayName = findViewById(R.id.edt_profile_name);
        edtEmail = findViewById(R.id.edt_email);
        ivProfile = findViewById(R.id.iv_profile);
        edtUserName = findViewById(R.id.edt_username);
        tvStatus = findViewById(R.id.tv_status);
        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.profile));
        arrStatus = new ArrayList<>();
        adapter = new StatusAdapter(arrStatus, this);
        rvStatus.setLayoutManager(new LinearLayoutManager(UpdateProfileActivity.this));
        rvStatus.setAdapter(adapter);
        //sv = findViewById(R.id.sv);

        arrStatus.addAll(Arrays.asList(getResources().getStringArray(R.array.country_arrays)));
        adapter.notifyDataSetChanged();
        edtStatus.setText(arrStatus.get(0));


        try {
            Call<ResponseRegisterUser> callGetProfile=null;
            Intent intUser = getIntent();
            if (intUser.hasExtra("userId") && !(intUser.getStringExtra("userId").equals(SocketChatApp.getSession().getUserID()))) {
                callGetProfile= SocketChatApp.getApiService().getProfile(intUser.getStringExtra("userId"));
                edtEmail.setEnabled(false);
                edtDisplayName.setEnabled(false);
                edtUserName.setEnabled(false);
                tvStatus.setEnabled(false);
                findViewById(R.id.iv_change_status).setVisibility(View.GONE);
                findViewById(R.id.fab_go).setVisibility(View.GONE);
                findViewById(R.id.tv_change_image).setVisibility(View.GONE);
            }else{
                callGetProfile= SocketChatApp.getApiService().getProfile(SocketChatApp.getSession().getUserID());
            }
            callGetProfile.enqueue(new Callback<ResponseRegisterUser>() {
                @Override
                public void onResponse(Call<ResponseRegisterUser> call, Response<ResponseRegisterUser> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        edtDisplayName.setText(response.body().getUser().get(0).getDisplayName());
                        edtStatus.setText(response.body().getUser().get(0).getStatus());
                        edtEmail.setText(response.body().getUser().get(0).getEmail());
                        edtUserName.setText(response.body().getUser().get(0).getUserName().toString());
                        Picasso.get().load(getResources().getString(R.string.host) + "profile/" + response.body().getUser().get(0).getUserProfilePhoto()).transform(new CircleTransform()).error(R.drawable.profile).into(ivProfile);
                        //urlProfile=getResources().getString(R.string.host) + "profile/" + response.body().getUser().get(0).getUserProfilePhoto();
                        setBackground(getResources().getString(R.string.host) + "profile/" + response.body().getUser().get(0).getUserProfilePhoto());
                        /*ivProfile.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setBackground();
                            }
                        }, 1000);*/
                    }
                }

                @Override
                public void onFailure(Call<ResponseRegisterUser> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setBackground(urlProfile);

        edtDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setBackground(String sUrl) {
        try{
            Bitmap image;
            if(sUrl!=null){
                Log.e(TAG, "setBackground: "+sUrl );
                /*URL url = new URL(sUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());*/
                new getImageFromURL().execute(sUrl);
            }else{
                BitmapDrawable drawable = (BitmapDrawable) ivProfile.getDrawable();
                image = drawable.getBitmap();
                Bitmap blurB = BlurBuilder.blur(UpdateProfileActivity.this, image);
                findViewById(R.id.iv_back).setBackground(new BitmapDrawable(getResources(), blurB));
            }
        }catch (Exception e){
            Log.e(TAG, "setBackground: " );
            e.printStackTrace();
        }
        /*BitmapDrawable drawable = (BitmapDrawable) ivProfile.getDrawable();
        Bitmap original = drawable.getBitmap();
        Bitmap blurB = BlurBuilder.blur(UpdateProfileActivity.this, original);
        findViewById(R.id.iv_back).setBackground(new BitmapDrawable(getResources(), blurB));*/
    }

    class getImageFromURL extends AsyncTask<String,Void,Void>{

        Bitmap image;

        @Override
        protected Void doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                image = BitmapFactory.decodeStream(input);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Bitmap blurB = BlurBuilder.blur(UpdateProfileActivity.this, image);
            findViewById(R.id.iv_back).setBackground(new BitmapDrawable(getResources(), blurB));
        }
    }

    private boolean isValidEmail() {
        String strEmail = edtEmail.getText().toString().trim();
        if (!TextUtils.isEmpty(strEmail) && Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            return true;
        }
        edtEmail.setError("Enter Valid Email");
        return false;
    }

    private boolean isValidDisplayname() {
        String strDisplayName = edtDisplayName.getText().toString().trim();
        String[] names = strDisplayName.split(" ");
        if (names.length > 2 && names.length < 6) {
            for (int i = 0; i < names.length; i++) {
                if (names[i].length() < 0) {
                    edtDisplayName.setError("Enter Valid Name");
                    return false;
                }
            }
            return true;
        }
        edtDisplayName.setError("Enter Valid Name");
        return false;
    }

    public void onChangeStatus(View view) {
        if (isEditeble) {
            tvStatus.setVisibility(View.VISIBLE);
            edtStatus.setVisibility(View.GONE);
            findViewById(R.id.iv_change_status).setBackground(getResources().getDrawable(R.drawable.ic_edit));
            tvStatus.setText(edtStatus.getText());
            isEditeble = false;
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            findViewById(R.id.tv_status).setVisibility(View.GONE);
            edtStatus.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    edtStatus.requestFocus();
                    //sv.fullScroll(View.FOCUS_DOWN);
                    edtStatus.setFocusableInTouchMode(true);
                    InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    mgr.showSoftInput(edtStatus, InputMethodManager.SHOW_IMPLICIT);

                }
            }, 1000);
            SocketChatApp.getSession().updateSetus(edtStatus.getText().toString());
            findViewById(R.id.iv_change_status).setBackground(getResources().getDrawable(R.drawable.ic_check));
            isEditeble = true;
        }
    }

    @Override
    public void onSelectStatus(int position) {
        SocketChatApp.getSession().updateSetus(edtStatus.getText().toString());
        edtStatus.setText(arrStatus.get(position));
        tvStatus.setText(arrStatus.get(position));
        dialog.cancel();
    }

    public void onClickGo(View view) {
        if (isValidDisplayname() && isValidEmail()) {
            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), SocketChatApp.getSession().getUserID());
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString());
            RequestBody displayName = RequestBody.create(MediaType.parse("text/plain"), edtDisplayName.getText().toString());
            RequestBody status = RequestBody.create(MediaType.parse("text/plain"), edtStatus.getText().toString());
            RequestBody userName = RequestBody.create(MediaType.parse("text/plain"), edtUserName.getText().toString().trim());
            Log.e(TAG, "onClickGo: Image file: " + profileImage.body());
            Call<ResponseRegisterUser> callRegister = SocketChatApp.getApiService().registerUserProfile(userId, email, displayName, status, userName, profileImage);
            Log.e(TAG, "onClickGo: request param : " + callRegister.request().body().toString());
            try {
                callRegister.enqueue(new Callback<ResponseRegisterUser>() {
                    @Override
                    public void onResponse(Call<ResponseRegisterUser> call, Response<ResponseRegisterUser> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "onResponse: status - " + response.code() + " Response: " + response.body().getUser());
                            if (response.code() == 200) {
                                Log.e(TAG, "onResponse: response: " + response.body().getUser());
                                startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                                SocketChatApp.getSession().setUserData(response.body().getUser().get(0));
//                            finish();
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, "Fail to register", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "onResponse: " + response.errorBody());
                            Toast.makeText(UpdateProfileActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegisterUser> call, Throwable t) {
                        Toast.makeText(UpdateProfileActivity.this, "something wrong : " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSelectStatus(View view) {
        dialog = new Dialog(UpdateProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(statusDialogView);
        dialog.show();
    }

    public void onSelectImage(View view) {
        View selectImageDialoge = getLayoutInflater().inflate(R.layout.dialoge_prion_pick_image, null, false);
        ImageView ivCamera = selectImageDialoge.findViewById(R.id.iv_camera);
        ImageView ivGallery = selectImageDialoge.findViewById(R.id.iv_gallery);
        final Dialog dialog = new Dialog(UpdateProfileActivity.this);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromCamera();
                dialog.cancel();
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromGallary();
                dialog.cancel();
            }
        });
        dialog.setTitle("Select Image From");
        dialog.setContentView(selectImageDialoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void selectFromGallary() {
        Dexter.withActivity(UpdateProfileActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        onShowPermisionDialoge();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(UpdateProfileActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    private void selectFromCamera() {
        Dexter.withActivity(UpdateProfileActivity.this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), REQUEST_CAMERA);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        onShowPermisionDialoge();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(UpdateProfileActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    private void onShowPermisionDialoge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY) {

            Uri selectedImageUri = data.getData();
            String selectedImagePath = ImageFilePath.getPath(getApplicationContext(), selectedImageUri);
            try {
                if (selectedImagePath != null) {
                    //String complressFile;
                    File file = new File(selectedImagePath);
                    Log.e(TAG, "onActivityResult: image size " + file.length() / 1024);
                    if ((file.length() / 1024) > 500) {
                        String compress = compressImage(selectedImagePath);
                        Toast.makeText(this, "path : " + compress, Toast.LENGTH_SHORT).show();
                        file = new File(compress);
                    }
                    uri = data.getData();
                    ivProfile.setImageURI(uri);
                    Log.e(TAG, "onActivityResult: File Image : " + file.getPath());
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/png"), file);
                    profileImage = MultipartBody.Part.createFormData("userPhoto", SocketChatApp.getSession().getUserID(), mFile);
                    isImageSelected = true;
                    Toast.makeText(this, "Done : " + file.getName(), Toast.LENGTH_SHORT).show();
                    setBackground(null);
                } else {
                    Toast.makeText(this, "unable to find image", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivProfile.setImageBitmap(photo);
            uri = getImageUri(getApplicationContext(), photo);
            File file = new File(getRealPathFromURI(uri));
            Log.e(TAG, "onActivityResult: image size " + file.length() / 1024);
            if ((file.length() / 1024) > 500) {
                String compress = compressImage(getRealPathFromURI(uri));
                Toast.makeText(this, "path : " + compress, Toast.LENGTH_SHORT).show();
                file = new File(compress);
            }
            Log.e(TAG, "onActivityResult: File Image : " + file.getPath());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/png"), file);
            profileImage = MultipartBody.Part.createFormData("userPhoto", SocketChatApp.getSession().getUserID(), mFile);
            isImageSelected = true;
            Toast.makeText(this, "Done : " + file.getName(), Toast.LENGTH_SHORT).show();
            setBackground(null);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}

