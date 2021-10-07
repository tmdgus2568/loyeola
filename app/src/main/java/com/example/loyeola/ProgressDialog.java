package com.example.loyeola;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

public class ProgressDialog extends Dialog {


    public ProgressDialog(Context context) {
        super(context);

        // 다이얼로그 제목 안보이게 함
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
        System.out.println("progress on!!!!!!");


    }
}
