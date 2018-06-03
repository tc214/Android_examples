package org.tc.mainstruct;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    public boolean isTopStart = true;
    public boolean mIsPause = false;
    private Activity ctx;
    private View mBaseView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ctx = this.getActivity();

        if (mBaseView == null) {
            mBaseView = ctx.getLayoutInflater().inflate(R.layout.fragment_message, null);
        } else {
            ViewGroup parent = (ViewGroup) mBaseView.getParent();
            if (parent != null) {
                parent.removeView(mBaseView);
            }
        }
        return mBaseView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        mIsPause = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        mIsPause = true;
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        isTopStart = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isTopStart = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
