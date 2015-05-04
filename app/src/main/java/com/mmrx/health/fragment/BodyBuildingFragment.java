package com.mmrx.health.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmrx.health.R;
import com.mmrx.health.activity.AlarmActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class BodyBuildingFragment extends Fragment {

    View mInflater;
    public BodyBuildingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflater = inflater.inflate(R.layout.fragment_body_building, container, false);
        Intent intent = new Intent(this.getActivity(), AlarmActivity.class);
        startActivity(intent);
        return mInflater;
    }



}
