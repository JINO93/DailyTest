package com.example.administrator.test.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.test.MyApplication;
import com.example.administrator.test.R;
import com.example.administrator.test.util.LogUtil;
import com.example.administrator.test.view.BottomContainerSheetBehavior;
import com.example.administrator.test.view.itemanimator.BaseItemAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private static int count = 0;

    private int mContent;
    boolean isFirstShow = true;
    private RecyclerView rv1;
    private List<Integer> mDatas = new ArrayList<>();

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = count++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = Gravity.BOTTOM;
        TextView tvContent = view.findViewById(R.id.tv_content);
        tvContent.setText(mContent + "");
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment blankFragment = BlankFragment.newInstance();
                BottomContainerDialogFragment.add(BlankFragment.this, new ContentFragment());
            }
        });
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (getFragmentManager().getBackStackEntryCount() > 0) {
//                    getFragmentManager().popBackStack();
//                }
//                BottomContainerDialogFragment.back(BlankFragment.this);
                mDatas.add(5,100);
                MyAdapter adapter = (MyAdapter) rv1.getAdapter();
//                adapter.isShow = true;
                adapter.notifyItemInserted(5);
            }
        });
        rv1 = view.findViewById(R.id.rv1);
        rv1.setItemAnimator(new CustsomItemAnimator());
        RecyclerView rv2 = view.findViewById(R.id.rv2);
        for (int i = 0; i < 20; i++) {
            mDatas.add(i + 1);
        }

        initRecycler(rv1);
        initRecycler(rv2);
    }
    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            setScrollable(null, rv);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    private void setScrollable(View bottomSheet, RecyclerView recyclerView){
        if (getParentFragment() instanceof BottomSheetDialogFragment) {
            Window window = ((BottomSheetDialogFragment) getParentFragment()).getDialog().getWindow();
            bottomSheet = window.findViewById(android.support.design.R.id.design_bottom_sheet);
        }
        if (bottomSheet == null) {
            return;
        }
        ViewGroup.LayoutParams params = bottomSheet.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) params;
            CoordinatorLayout.Behavior behavior = coordinatorLayoutParams.getBehavior();
            if (behavior != null && behavior instanceof BottomContainerSheetBehavior)
                ((BottomContainerSheetBehavior)behavior).setNestedScrollingChildRef(recyclerView);
        }
    }

    private void initRecycler(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new MyAdapter());
        rv.addOnItemTouchListener(onItemTouchListener);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            if (isFirstShow) {
                isFirstShow = false;
                return null;
            }
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_in);
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_out);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private int[] imgs = {
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,
                R.mipmap.ic_launcher_,

        };
        String[] titles = {"Acknowl", "Belief", "Confidence", "Dreaming", "Happiness", "Confidence"};

        boolean isShow = false;

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_topic_read_card, parent, false);
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {
            LogUtil.e("onBindViewHolder :" + position);
            Glide.with(MyApplication.getContext()).load(imgs[position % imgs.length]).into(holder.imgBg);
            holder.tvTitle.setText(mDatas.get(position) + " " + titles[position % titles.length]);
            holder.imgBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(v.getContext(),mDatas.get(position),Toast.LENGTH_SHORT).show();
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),position+ " click",Toast.LENGTH_SHORT).show();
//                    notifyItemRemoved(position);
                }
            });
//            holder.itemView.setVisibility(position == 0 && !isShow ? View.GONE : View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgBg;
            TextView tvTitle;
            TextView tvBottom;

            public ViewHolder(View itemView) {
                super(itemView);
                imgBg = itemView.findViewById(R.id.iv_cover);
                tvTitle = itemView.findViewById(R.id.tv_card_title);
            }
        }
    }

    class CustsomItemAnimator extends BaseItemAnimator {

        @Override
        protected void animateRemoveImpl(RecyclerView.ViewHolder holder) {
            ViewCompat.animate(holder.itemView)
                    .alpha(0)
                    .setDuration(getRemoveDuration())
                    .setInterpolator(mInterpolator)
                    .setListener(new DefaultRemoveVpaListener(holder))
                    .setStartDelay(getRemoveDelay(holder))
                    .start();
        }

        @Override
        protected void animateAddImpl(RecyclerView.ViewHolder holder) {

        }
    }
}
