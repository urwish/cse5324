package com.notesmates.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.notesmates.R;

@SuppressLint("NewApi")
public class ResourcesFragment extends Fragment {
	
	public ResourcesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_resources, container, false);
         
        return rootView;
    }
}
