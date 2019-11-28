package com.example.administrator.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.test.MyApplication;
import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.util.ViewUtils;
import com.example.administrator.test.view.CardIndicator;
import com.example.administrator.test.view.layoutManager.CardStackLayoutManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SlidingCardActivity extends AppCompatActivity {

    private RecyclerView rvCard;
    private CardIndicator cardIndicator;
    private MyAdapter mAdapter;
    private List<CardData> cardDataList = new ArrayList<>();
    private int curPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_card);
        rvCard = findViewById(R.id.rv_card);
        mAdapter = new MyAdapter();
//        mAdapter.setHasStableIds(true);
        rvCard.setItemAnimator(null);
        rvCard.setAdapter(mAdapter);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(0.8f, true,
                ViewUtils.dipToPx(this, 20), 2);
        rvCard.setLayoutManager(cardStackLayoutManager);
        rvCard.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                View topView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                int position = layoutManager.getPosition(topView);
                if(layoutManager instanceof CardStackLayoutManager){
                    position = ((CardStackLayoutManager) layoutManager).getTopItemPosition();
                }
//                LogUtil.d(MessageFormat.format("topView pos:{0},count:{1}", position,cardDataList.size()));
                // 滑到倒数第二个时加载下一页
                if (position >= cardDataList.size() - 3) {
                    fetchData(false);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
//                    return;
//                }
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                if (layoutManager == null) {
//                    return;
//                }
//                View topView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
//                int position = layoutManager.getPosition(topView);
//                if(layoutManager instanceof CardStackLayoutManager){
//                    position = ((CardStackLayoutManager) layoutManager).getTopItemPosition();
//                }
//                LogUtil.d(MessageFormat.format("topView pos:{0},count:{1}", position,cardDataList.size()));
//                // 滑到倒数第二个时加载下一页
//                if (position >= cardDataList.size() - 3) {
//                    fetchData(false);
//                }
            }
        });
        cardIndicator = findViewById(R.id.card_indicator);
        cardIndicator.attachToRecyelerView(rvCard);

        cardStackLayoutManager.setOnStackChangeListener(new CardStackLayoutManager.OnStackChangeListener() {
            @Override
            public void onTopStackChange(int oldPosition, int newPostion) {
                LogUtil.w(MessageFormat.format("old:{0},newPos:{1}", oldPosition, newPostion));
                if (curPos == newPostion) {
                    return;
                }
                cardIndicator.slide(newPostion - oldPosition > 0);
                curPos = newPostion;
            }
        });
//        cardIndicator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardIndicator.slide(true);
//            }
//        });

        findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardIndicator.slide(false);
//                rvCard.scrollToPosition(--curPos);
                mAdapter.notifyDataSetChanged();
            }
        });
        findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardIndicator.slide(true);
                rvCard.scrollToPosition(5);
            }
        });

        fetchData(true);
    }

    private void fetchData(boolean refresh) {
        ArrayList<CardData> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CardData guideRecordCardData = new CardData("title " + i,"https://www.baidu.com/img/baidu_jgylogo3.gif");
            items.add(guideRecordCardData);
        }
        if (refresh) {
            cardDataList.clear();
            cardDataList.addAll(items);
            mAdapter.notifyDataSetChanged();
        } else {
            int preSize = cardDataList.size();
            cardDataList.addAll(items);
            LogUtil.w("update data");
            mAdapter.notifyItemRangeInserted(preSize, items.size());
            cardIndicator.updateDotCount();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//        private int[] imgs = {
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//
//        };
//        String[] titles = {"Acknowl", "Belief", "Confidence", "Dreaming", "Happiness", "Confidence"};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_topic_read_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.setData(position);
            CardData cardData = cardDataList.get(position);
            Glide.with(MyApplication.getContext()).load(cardData.imgRes).into(holder.imgBg);
            holder.tvTitle.setText(cardData.title);
//            holder.imgBg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return cardDataList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgBg;
            TextView tvTitle;
            TextView tvBottom;
            private int mPosition;

            public ViewHolder(View itemView) {
                super(itemView);
//                ButterKnife.bind(this, itemView);
                imgBg = itemView.findViewById(R.id.iv_cover);
                tvTitle = itemView.findViewById(R.id.tv_card_title);
            }

//            @OnClick(R.id.iv_cover)
//            public void onCoverClick(){
//                Toast.makeText(getApplicationContext(),"click cover:"+mPosition,Toast.LENGTH_SHORT).show();
//            }
//
//            @OnClick(R.id.iv_cover)
//            public void onTitleClick(){
//                Toast.makeText(getApplicationContext(),"click title:"+mPosition,Toast.LENGTH_SHORT).show();
//            }
//
            public void setData(int position) {
                mPosition = position;
                imgBg.setOnClickListener(v -> {
                    Toast.makeText(getApplicationContext(),"click cover:"+mPosition,Toast.LENGTH_SHORT).show();
                });
                tvTitle.setOnClickListener(v -> {
                    Toast.makeText(getApplicationContext(),"click title:"+mPosition,Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    static class CardData {
        public String title;
        public String imgRes;

        public CardData(String title, String imgRes) {
            this.title = title;
            this.imgRes = imgRes;
        }
    }
}
