package com.todor.diabetes.ui;

import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragment extends Fragment {

    public abstract String getFragmentTitle();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getFragmentTitle());
    }
}
