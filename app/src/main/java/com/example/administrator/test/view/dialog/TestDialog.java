package com.example.administrator.test.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 20:33 2019/8/2
 */
public class TestDialog extends Dialog {

    private List<String> strList;

    public TestDialog(@NonNull Context context) {
        super(context, R.style.BottomDialogTheme);
        configWindow(context);
        setContentView(R.layout.dialog_test);
        init();
    }

    private void init() {
        RecyclerView listTest = findViewById(R.id.list_test);
        strList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strList.add("0_" + i);
        }
//        listTest.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strList));
        listTest.setAdapter(new TAdapter());
        listTest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        final EditText edTest = findViewById(R.id.ed1);
        edTest.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edTest.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        edTest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(edTest.getText())) {
                        Toast.makeText(getContext(), "TTT", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }


    private void configWindow(Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = screenWidth;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }

    private class TAdapter extends RecyclerView.Adapter<TAdapter.VHolder> {

        @Override
        public TAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VHolder(LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null));
        }

        @Override
        public void onBindViewHolder(TAdapter.VHolder holder, int position) {
            holder.tvName.setText(strList.get(position));
        }

        @Override
        public int getItemCount() {
            return strList.size();
        }

        class VHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            public VHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(android.R.id.text1);
                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new InputDialog(v.getContext()).show();
                    }
                });
            }
        }
    }
}
