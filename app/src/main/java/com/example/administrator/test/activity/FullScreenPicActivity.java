package com.example.administrator.test.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.test.R;

public class FullScreenPicActivity extends AppCompatActivity {

    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_pic);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        showDialog();
        return super.onTouchEvent(event);
    }

    private void showDialog() {
        if (isAdd) {
            return;
        }
        new FullScreenPicDialog().show(getFragmentManager(), "JINO");
        isAdd = !isAdd;
    }

    public static class FullScreenPicDialog extends DialogFragment{


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.SplashDialog);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Dialog dialog = super.onCreateDialog(savedInstanceState);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = 0;
            }
            return dialog;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(R.drawable.icon_splash);
            return imageView;
        }

//        @Override
//        public void show(FragmentManager manager, String tag) {
//            if (!isAdded()) {
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.add(FullScreenPicDialog.this, tag);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        }
    }
}
