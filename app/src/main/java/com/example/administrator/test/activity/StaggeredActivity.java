package com.example.administrator.test.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.test.R;
import com.example.administrator.test.bean.ContributeItemData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaggeredActivity extends AppCompatActivity {

    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private ArrayList<ContributeItemData> contributeItemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staggered);
        ButterKnife.bind(this);
        rvData.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        contributeItemData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            contributeItemData.add(new ContributeItemData(String.valueOf('A' + i), "title " + i, v -> {
                Toast.makeText(StaggeredActivity.this,"card click "+ finalI,Toast.LENGTH_SHORT).show();
            }));
        }
//        rvData.setAdapter();
    }
}
