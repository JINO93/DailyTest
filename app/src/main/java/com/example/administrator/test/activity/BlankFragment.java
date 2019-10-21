package com.example.administrator.test.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.administrator.test.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private static int count = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mContent;
    boolean isFirstShow = true;


    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
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
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
//                fragmentTransaction.replace(R.id.fragment_container, blankFragment);
//                fragmentTransaction.commit();
                BottomContainerDialogFragment.add(BlankFragment.this,new ContentFragment());
            }
        });
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (getFragmentManager().getBackStackEntryCount() > 0) {
//                    getFragmentManager().popBackStack();
//                }
                BottomContainerDialogFragment.back(BlankFragment.this);
            }
        });
//        view.getLayoutParams().height = 1000 - mContent * 50;
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
}
