package com.test.socketchat.activity.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.socketchat.R;

public class PostUpdateActivity extends AppCompatActivity {

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);

         View bottomSheet=findViewById(R.id.ll);
         sheetBehavior=BottomSheetBehavior.from(bottomSheet);
         sheetBehavior.setHideable(false);
         //sheetBehavior.setPeekHeight(140);
         findViewById(R.id.ll_attachment).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(sheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                     sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                 }else{
                     sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                 }
             }
         });
         sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
             @Override
             public void onStateChanged(@NonNull View bottomSheet, int newState) {

             }

             @Override
             public void onSlide(@NonNull View bottomSheet, float slideOffset) {

             }
         });

    }

    public void onClickBack(View view) {
        finish();
    }
}
