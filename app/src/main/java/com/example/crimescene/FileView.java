package com.example.crimescene;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rd.PageIndicatorView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileView extends BottomSheetDialogFragment {
    TextView title;
    ViewPager viewPager;

    public FileView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_file_view, container, false);
        viewPager = view.findViewById(R.id.view_pager_file);
        title = view.findViewById(R.id.case_title);
        title.setText(UserInfo.getInstance().getCurrentFile());
        ViewPagerAdapter adapter = new com.example.crimescene.ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new CaseNotesFragment());
        adapter.addFragment(new CaseRecordingsFragment());
        adapter.addFragment(new CasePhotosFragment());
        PageIndicatorView pageIndicatorView = view.findViewById(R.id.indicator);
        pageIndicatorView.setCount(3);
        pageIndicatorView.setSelection(2);
        pageIndicatorView.setViewPager(viewPager);

        viewPager.setAdapter(adapter);

        return view;
    }
}
